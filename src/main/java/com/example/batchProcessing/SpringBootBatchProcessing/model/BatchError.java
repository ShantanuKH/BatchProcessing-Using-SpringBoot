package com.example.batchProcessing.SpringBootBatchProcessing.model;

import java.time.LocalDateTime;

public class BatchError {

    private Long id;
    private Long executionId;

    private Integer employeeId;
    private String employeeName;
    private String department;

    // Original values from CSV
    private String startDate;
    private String endDate;

    private String errorType;
    private String errorMessage;

    private LocalDateTime createdAt;

    public BatchError() {
    }

    public BatchError(
            Long executionId,
            Integer employeeId,
            String employeeName,
            String department,
            String startDate,
            String endDate,
            String errorType,
            String errorMessage) {

        this.executionId = executionId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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