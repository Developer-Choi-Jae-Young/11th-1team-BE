package org.example.knockin.global.batch;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.example.knockin.member.service.impl.MemberServiceImpl;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.DefaultJobParametersValidator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.database.Order;
import org.springframework.batch.infrastructure.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration(proxyBeanMethods = false)
public class MemberDeleteBatchConfig {
    @Bean
    public Job memberDeleteJob(JobRepository jobRepository, Step memberDeleteStep) {
        return new JobBuilder("memberDeleteJob", jobRepository)
                .validator(new DefaultJobParametersValidator(new String[]{"cutoff", "chunkSize"}, new String[]{}))
                .start(memberDeleteStep).build();
    }

    @Bean
    @JobScope
    public Step memberDeleteStep(JobRepository jobRepository, PlatformTransactionManager transactionManager,
                                 DataSource dataSource, MemberServiceImpl memberService,
                                 @Value("#{jobParameters['cutoff']}") String cutoff,
                                 @Value("#{jobParameters['chunkSize']}") Integer chunkSize) throws Exception {
        LocalDateTime thresholdDate = LocalDateTime.parse(cutoff);

        ItemReader<Long> reader = new JdbcPagingItemReaderBuilder<Long>()
                .name("memberDeleteReader")
                .dataSource(dataSource)
                .selectClause("SELECT id")
                .fromClause("FROM member")
                .whereClause("WHERE is_delete = true AND deleted_at < :cutoff")
                .parameterValues(Map.of("cutoff", Timestamp.valueOf(thresholdDate)))
                .sortKeys(Map.of("id", Order.ASCENDING))
                .pageSize(chunkSize)
                .rowMapper((rs, rowNum) -> rs.getLong("id"))
                .saveState(true)
                .build();

        return new StepBuilder("memberDeleteStep", jobRepository).<Long, Long>chunk(chunkSize).transactionManager(transactionManager)
                .reader(reader).writer(chunk -> memberService.hardDeleteMember(List.copyOf(chunk.getItems()), thresholdDate)).build();
    }
}
