package com.example.batchProcessing.SpringBootBatchProcessing.repository;

import com.example.batchProcessing.SpringBootBatchProcessing.model.BatchError;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public class BatchErrorRepository {

    private final JdbcTemplate jdbcTemplate;

    public BatchErrorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(BatchError error) {

        String sql = """
                INSERT INTO batch_error
                (
                    execution_id,
                    employee_id,
                    employee_name,
                    department,
                    start_date,
                    end_date,
                    error_type,
                    error_message
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                """;

        Date startDate = null;
        Date endDate = null;

        if (error.getStartDate() != null &&
                !error.getStartDate().trim().isEmpty()) {

            try {
                startDate = Date.valueOf(error.getStartDate());
            } catch (IllegalArgumentException ignored) {
                // Keep NULL if date is invalid
            }
        }

        if (error.getEndDate() != null &&
                !error.getEndDate().trim().isEmpty()) {

            try {
                endDate = Date.valueOf(error.getEndDate());
            } catch (IllegalArgumentException ignored) {
                // Keep NULL if date is invalid
            }
        }

        jdbcTemplate.update(
                sql,
                error.getExecutionId(),
                error.getEmployeeId(),
                error.getEmployeeName(),
                error.getDepartment(),
                startDate,
                endDate,
                error.getErrorType(),
                error.getErrorMessage()
        );
    }

    public List<BatchError> findByExecutionId(Long executionId) {

        String sql = """
                SELECT
                    id,
                    execution_id,
                    employee_id,
                    employee_name,
                    department,
                    start_date,
                    end_date,
                    error_type,
                    error_message,
                    created_at
                FROM batch_error
                WHERE execution_id = ?
                ORDER BY id
                """;

        return jdbcTemplate.query(
                sql,
                new Object[]{executionId},
                (rs, rowNum) -> {

                    BatchError error = new BatchError();

                    error.setId(
                            rs.getLong("id")
                    );

                    error.setExecutionId(
                            rs.getLong("execution_id")
                    );

                    // Employee ID can be NULL for READ_ERROR
                    int employeeId =
                            rs.getInt("employee_id");

                    if (rs.wasNull()) {
                        error.setEmployeeId(null);
                    } else {
                        error.setEmployeeId(employeeId);
                    }

                    error.setEmployeeName(
                            rs.getString("employee_name")
                    );

                    error.setDepartment(
                            rs.getString("department")
                    );

                    // Keep dates as String in BatchError
                    error.setStartDate(
                            rs.getString("start_date")
                    );

                    error.setEndDate(
                            rs.getString("end_date")
                    );

                    error.setErrorType(
                            rs.getString("error_type")
                    );

                    error.setErrorMessage(
                            rs.getString("error_message")
                    );

                    if (rs.getTimestamp("created_at") != null) {

                        error.setCreatedAt(
                                rs.getTimestamp("created_at")
                                        .toLocalDateTime()
                        );
                    }

                    return error;
                }
        );
    }
}