package org.example.knockin.global.batch;

import java.time.LocalDate;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberDeleteScheduler {
    private final JobOperator jobOperator;
    private final Job memberDeleteJob;

    @Value("${batch.member-delete.chunk-size:10}")
    private int chunkSize;

    @Scheduled(cron = "${batch.member-delete.cron:0 0 3 * * *}", zone = "Asia/Seoul")
    public void run() throws Exception {
        ZoneId scheduleZone = ZoneId.of("Asia/Seoul");
        String cutoff = LocalDate.now(scheduleZone).atTime(3, 0).atZone(scheduleZone).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().minusDays(5).toString();
        JobParameters parameters = new JobParametersBuilder().addString("cutoff", cutoff).addLong("chunkSize", (long) chunkSize).toJobParameters();

        try {
            JobExecution execution = jobOperator.start(memberDeleteJob, parameters);

            if (execution.getStatus() == BatchStatus.FAILED) {
                log.error("회원 영구 삭제 배치 실패. executionId: {}, cutoff: {}", execution.getId(), cutoff);
            } else {
                log.info("회원 영구 삭제 배치 실행. executionId: {}, status: {}", execution.getId(), execution.getStatus());
            }
        } catch (JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException e) {
            log.info("회원 영구 삭제 배치가 이미 실행 중이거나 완료되었습니다. cutoff: {}", cutoff);
        }
    }
}
