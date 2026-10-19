package org.example.knockin.global.batch;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.ZoneId;
import org.mockito.ArgumentCaptor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.test.util.ReflectionTestUtils;

class MemberDeleteSchedulerTest {
    @Test
    void usesStableDailyParametersAndConfiguredChunkSize() throws Exception {
        JobOperator operator = mock(JobOperator.class);
        Job job = mock(Job.class);
        JobExecution execution = mock(JobExecution.class);
        when(execution.getStatus()).thenReturn(BatchStatus.COMPLETED);
        when(operator.start(eq(job), any())).thenReturn(execution);
        var scheduler = new MemberDeleteScheduler(operator, job);
        ReflectionTestUtils.setField(scheduler, "chunkSize", 10);

        scheduler.run();
        scheduler.run();

        var parameters = ArgumentCaptor.forClass(JobParameters.class);
        verify(operator, times(2)).start(eq(job), parameters.capture());
        assertThat(parameters.getAllValues().get(0)).isEqualTo(parameters.getAllValues().get(1));
        assertThat(parameters.getValue().getLong("chunkSize")).isEqualTo(10L);
        ZoneId seoul = ZoneId.of("Asia/Seoul");
        assertThat(parameters.getValue().getString("cutoff")).isEqualTo(
                LocalDate.now(seoul).atTime(3, 0).atZone(seoul).withZoneSameInstant(ZoneId.systemDefault())
                        .toLocalDateTime().minusDays(5).toString());
    }

    @Test
    void alreadyCompletedDailyJobIsNotAnError() throws Exception {
        JobOperator operator = mock(JobOperator.class);
        Job job = mock(Job.class);
        when(operator.start(eq(job), any())).thenThrow(new JobInstanceAlreadyCompleteException("completed"));
        var scheduler = new MemberDeleteScheduler(operator, job);
        ReflectionTestUtils.setField(scheduler, "chunkSize", 10);
        assertThatCode(scheduler::run).doesNotThrowAnyException();
    }
}
