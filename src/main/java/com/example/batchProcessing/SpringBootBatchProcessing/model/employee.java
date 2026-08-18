package com.example.batchProcessing.SpringBootBatchProcessing.model;

import java.time.LocalDate;

public class employee {

    private int employeeID;
    private String name;
    private String department;

    // Raw values coming from CSV
    private String startDate;
    private String endDate;

    // Parsed values used by PostgreSQL
    private LocalDate startDateValue;
    private LocalDate endDateValue;

    // Duration stored as number of days
    private int totalDurationInCompany;

    public employee() {
    }

    public employee(
            int employeeID,
            String name,
            String department,
            String startDate,
            String endDate) {

        this.employeeID = employeeID;
        this.name = name;
        this.department = department;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LocalDate getStartDateValue() {
        return startDateValue;
    }

    public void setStartDateValue(LocalDate startDateValue) {
        this.startDateValue = startDateValue;
    }

    public LocalDate getEndDateValue() {
        return endDateValue;
    }

    public void setEndDateValue(LocalDate endDateValue) {
        this.endDateValue = endDateValue;
    }

    public int getTotalDurationInCompany() {
        return totalDurationInCompany;
    }

    public void setTotalDurationInCompany(int totalDurationInCompany) {
        this.totalDurationInCompany = totalDurationInCompany;
    }
}