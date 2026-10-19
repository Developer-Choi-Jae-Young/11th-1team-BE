package org.example.knockin.global.batch;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.boot.batch.jdbc.autoconfigure.BatchDataSourceScriptDatabaseInitializer;
import org.springframework.boot.batch.jdbc.autoconfigure.BatchJdbcProperties;
import org.springframework.boot.sql.init.DatabaseInitializationMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

class MemberDeleteBatchSchemaTest {
    @Test
    void bundledPostgresSchemaCanInitializeAgainWithoutDeletingHistoryOrResettingSequences() throws Exception {
        // Use the bundled PostgreSQL DDL against an isolated H2 compatibility database.
        var dataSource = new DriverManagerDataSource(
                "jdbc:h2:mem:batch-schema-" + UUID.randomUUID() + ";MODE=PostgreSQL;DB_CLOSE_DELAY=-1", "sa", "");
        var properties = new BatchJdbcProperties();
        properties.setInitializeSchema(DatabaseInitializationMode.ALWAYS);
        properties.setPlatform("postgresql");
        var jdbc = new JdbcTemplate(dataSource);
        try {
            new BatchDataSourceScriptDatabaseInitializer(dataSource, properties).afterPropertiesSet();
            jdbc.update("INSERT INTO BATCH_JOB_INSTANCE (JOB_INSTANCE_ID, VERSION, JOB_NAME, JOB_KEY) VALUES (1, 0, ?, ?)",
                    "memberDeleteJob", "existing-job-key");
            Long previousId = jdbc.queryForObject("SELECT NEXT VALUE FOR BATCH_JOB_INSTANCE_SEQ", Long.class);

            new BatchDataSourceScriptDatabaseInitializer(dataSource, properties).afterPropertiesSet();

            assertThat(jdbc.queryForObject("SELECT JOB_NAME FROM BATCH_JOB_INSTANCE WHERE JOB_INSTANCE_ID = 1", String.class))
                    .isEqualTo("memberDeleteJob");
            assertThat(jdbc.queryForObject("SELECT NEXT VALUE FOR BATCH_JOB_INSTANCE_SEQ", Long.class))
                    .isGreaterThan(previousId);
        } finally {
            jdbc.execute("SHUTDOWN");
        }
    }
}
