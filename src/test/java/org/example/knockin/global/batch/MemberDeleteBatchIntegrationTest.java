package org.example.knockin.global.batch;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;
import org.example.knockin.member.entity.Member;
import org.example.knockin.member.entity.MemberRole;
import org.example.knockin.member.repository.MemberRepository;
import org.example.knockin.member.service.impl.MemberServiceImpl;
import org.example.knockin.verification.entity.LoginProviderType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:member-delete-batch;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
        "batch.member-delete.chunk-size=2", "batch.member-delete.cron=-",
        "spring.batch.job.enabled=false", "seed.load-test.records-per-entity=30",
        "logging.level.org.hibernate.SQL=WARN"
})
@ActiveProfiles("test")
class MemberDeleteBatchIntegrationTest {
    @Autowired JobOperator jobOperator;
    @Autowired Job memberDeleteJob;
    @Autowired JdbcTemplate jdbc;
    @Autowired MemberRepository members;
    @Autowired MemberServiceImpl memberService;
    @Autowired PlatformTransactionManager transactionManager;

    @BeforeEach
    void resetEligibility() {
        jdbc.update("UPDATE member SET is_delete = false, deleted_at = NULL");
    }

    @Test
    void deletesSeededMembersAcrossChunks() throws Exception {
        long members = jdbc.queryForObject("SELECT COUNT(*) FROM member", Long.class);
        assertThat(members).isGreaterThan(2);
        LocalDateTime cutoff = LocalDateTime.of(2026, 10, 14, 3, 0);
        // Delete one seeded member first, preserving other members and their shared relationships.
        Long oneMember = jdbc.queryForObject("SELECT id FROM member WHERE provider_id = 'user2_provider_789'", Long.class);
        jdbc.update("UPDATE member SET is_delete = true, deleted_at = ? WHERE id = ?", cutoff.minusDays(1), oneMember);
        long alarmsBefore = jdbc.queryForObject("SELECT COUNT(*) FROM alarm", Long.class);
        jdbc.execute("CREATE TABLE seeded_delete_blocker (member_id BIGINT REFERENCES member(id))");
        jdbc.update("INSERT INTO seeded_delete_blocker(member_id) VALUES (?)", oneMember);
        var partial = jobOperator.start(memberDeleteJob, new JobParametersBuilder()
                .addString("cutoff", cutoff.minusMinutes(1).toString()).addLong("chunkSize", 2L).toJobParameters());
        try {
            assertThat(partial.getStatus()).isEqualTo(BatchStatus.FAILED);
            assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM alarm", Long.class)).isEqualTo(alarmsBefore);
            assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM member", Long.class)).isEqualTo(members);
        } finally {
            jdbc.execute("DROP TABLE seeded_delete_blocker");
        }
        partial = jobOperator.restart(partial);
        assertThat(partial.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM member", Long.class)).isEqualTo(members - 1);
        for (String[] subtype : new String[][]{{"CHATTING_REQUIRED", "chatting_required_alarm"},
                {"ROOM_MATCHING", "roommate_matching_required_alarm"}, {"ROOMMATE_CALENDAR", "roommate_calendar_alarm"}}) {
            assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM alarm a WHERE a.type = ? AND NOT EXISTS "
                    + "(SELECT 1 FROM " + subtype[1] + " child WHERE child.id = a.id)", Long.class, subtype[0]))
                    .as("JOINED alarm parent must not remain without %s", subtype[1]).isZero();
        }
        jdbc.update("UPDATE member SET is_delete = true, deleted_at = ?", cutoff.minusDays(1));

        var execution = jobOperator.start(memberDeleteJob,
                new JobParametersBuilder().addString("cutoff", cutoff.toString()).addLong("chunkSize", 2L).toJobParameters());

        assertThat(execution.getStatus()).withFailMessage("%s", execution.getAllFailureExceptions())
                .isEqualTo(BatchStatus.COMPLETED);
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM member", Long.class)).isZero();
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM roommate_board", Long.class)).isZero();
        assertThat(jdbc.queryForObject("SELECT COUNT(*) FROM basic_information", Long.class)).isZero();
        var step = execution.getStepExecutions().iterator().next();
        assertThat(step.getReadCount()).isEqualTo(members - 1);
        assertThat(step.getCommitCount()).isGreaterThan(1);
    }
    @Test
    void failedChunkRollsBackAndRestartsAfterLastCommittedId() throws Exception {
        LocalDateTime cutoff = LocalDateTime.of(2026, 10, 14, 3, 1);
        List<Long> ids = IntStream.range(0, 5)
                .mapToObj(i -> member(true, cutoff.minusDays(1))).toList();
        jdbc.execute("CREATE TABLE batch_delete_blocker (member_id BIGINT REFERENCES member(id))");
        jdbc.update("INSERT INTO batch_delete_blocker(member_id) VALUES (?)", ids.get(3));
        try {
            var parameters = new JobParametersBuilder().addString("cutoff", cutoff.toString())
                    .addLong("chunkSize", 2L).toJobParameters();
            var failed = jobOperator.start(memberDeleteJob, parameters);
            assertThat(failed.getStatus()).isEqualTo(BatchStatus.FAILED);
            assertThat(members.findAllById(ids)).extracting(Member::getId).containsExactlyInAnyOrderElementsOf(ids.subList(2, 5));
            assertThat(failed.getStepExecutions().iterator().next().getRollbackCount()).isPositive();

            jdbc.update("DELETE FROM batch_delete_blocker");
            var restarted = jobOperator.restart(failed);
            assertThat(restarted.getStatus()).withFailMessage("%s", restarted.getAllFailureExceptions())
                    .isEqualTo(BatchStatus.COMPLETED);
            assertThat(restarted.getJobInstance().getInstanceId()).isEqualTo(failed.getJobInstance().getInstanceId());
            assertThat(restarted.getStepExecutions().iterator().next().getReadCount()).isEqualTo(3);
            assertThat(members.findAllById(ids)).isEmpty();
        } finally {
            jdbc.execute("DROP TABLE batch_delete_blocker");
        }
    }

    @Test
    void excludesActiveRecentBoundaryAndMissingDeletionDates() throws Exception {
        LocalDateTime cutoff = LocalDateTime.of(2026, 10, 14, 3, 2);
        Long old = member(true, cutoff.minusDays(1));
        List<Long> protectedIds = List.of(member(false, cutoff.minusDays(1)),
                member(true, cutoff.plusDays(1)), member(true, cutoff), member(true, null));
        var execution = jobOperator.start(memberDeleteJob, new JobParametersBuilder()
                .addString("cutoff", cutoff.toString()).addLong("chunkSize", 2L).toJobParameters());
        assertThat(execution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(members.findById(old)).isEmpty();
        assertThat(members.findAllById(protectedIds)).hasSize(4);
    }

    @Test
    void writerRechecksEligibilityEvenWhenReaderAlreadySelectedMember() {
        LocalDateTime cutoff = LocalDateTime.of(2026, 10, 14, 3, 3);
        Long recovered = member(true, cutoff.minusDays(1));
        Long deletedAgain = member(true, cutoff.minusDays(1));
        Long eligible = member(true, cutoff.minusDays(1));
        // These IDs represent the reader's snapshot before recovery/new withdrawal.
        List<Long> selectedIds = List.of(recovered, deletedAgain, eligible);
        jdbc.update("UPDATE member SET is_delete = false, deleted_at = NULL WHERE id = ?", recovered);
        jdbc.update("UPDATE member SET deleted_at = ? WHERE id = ?", cutoff.plusDays(1), deletedAgain);
        new TransactionTemplate(transactionManager).executeWithoutResult(status ->
                memberService.hardDeleteMember(selectedIds, cutoff));
        assertThat(members.findAllById(selectedIds)).extracting(Member::getId)
                .containsExactlyInAnyOrder(recovered, deletedAgain);
    }

    private Long member(boolean deleted, LocalDateTime deletedAt) {
        return members.save(Member.builder().providerType(LoginProviderType.KAKAO)
                .providerId(UUID.randomUUID().toString()).role(MemberRole.USER)
                .isDelete(deleted).deletedAt(deletedAt).build()).getId();
    }

}
