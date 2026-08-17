package com.example.batchProcessing.SpringBootBatchProcessing.repository;

import com.example.batchProcessing.SpringBootBatchProcessing.model.BatchSummary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BatchSummaryRepository {

    private final JdbcTemplate jdbcTemplate;

    public BatchSummaryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public BatchSummary findByExecutionId(Long executionId) {

        String sql = """
                SELECT
                    je.JOB_EXECUTION_ID,
                    ji.JOB_NAME,
                    je.STATUS,
                    je.START_TIME,
                    je.END_TIME,
                    se.READ_COUNT,
                    se.WRITE_COUNT,
                    se.STATUS AS STEP_STATUS
                FROM BATCH_JOB_EXECUTION je
                JOIN BATCH_JOB_INSTANCE ji
                    ON je.JOB_INSTANCE_ID = ji.JOB_INSTANCE_ID
                LEFT JOIN BATCH_STEP_EXECUTION se
                    ON je.JOB_EXECUTION_ID = se.JOB_EXECUTION_ID
                WHERE je.JOB_EXECUTION_ID = ?
                ORDER BY se.STEP_EXECUTION_ID
                LIMIT 1
                """;

        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{executionId},
                (rs, rowNum) -> {

                    BatchSummary summary = new BatchSummary();

                    summary.setExecutionId(
                            rs.getLong("JOB_EXECUTION_ID")
                    );

                    summary.setJobName(
                            rs.getString("JOB_NAME")
                    );

                    summary.setStatus(
                            rs.getString("STATUS")
                    );

                    int readCount =
                            rs.getInt("READ_COUNT");

                    int writeCount =
                            rs.getInt("WRITE_COUNT");

                    int failedRecords =
                            readCount - writeCount;

                    summary.setTotalRecords(readCount);
                    summary.setSuccessfulRecords(writeCount);
                    summary.setFailedRecords(failedRecords);

                    double successRate = readCount == 0
                            ? 0.0
                            : (writeCount * 100.0) / readCount;

                    summary.setSuccessRate(
                            Math.round(successRate * 100.0) / 100.0
                    );

                    if (rs.getTimestamp("START_TIME") != null) {
                        summary.setStartTime(
                                rs.getTimestamp("START_TIME")
                                        .toLocalDateTime()
                        );
                    }

                    if (rs.getTimestamp("END_TIME") != null) {
                        summary.setEndTime(
                                rs.getTimestamp("END_TIME")
                                        .toLocalDateTime()
                        );
                    }

                    return summary;
                }
        );
    }
}