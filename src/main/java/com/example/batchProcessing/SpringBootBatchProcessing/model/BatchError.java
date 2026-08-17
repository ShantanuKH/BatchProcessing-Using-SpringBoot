package com.example.batchProcessing.SpringBootBatchProcessing.model;

import java.time.LocalDateTime;

public class BatchError {

    private Long id;
    private Long executionId;
    private Integer employeeId;
    private String errorType;
    private String errorMessage;
    private LocalDateTime createdAt;

    public BatchError() {
    }

    public BatchError(
            Long executionId,
            Integer employeeId,
            String errorType,
            String errorMessage) {

        this.executionId = executionId;
        this.employeeId = employeeId;
        this.errorType = errorType;
        this.errorMessage = errorMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Long executionId) {
        this.executionId = executionId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}