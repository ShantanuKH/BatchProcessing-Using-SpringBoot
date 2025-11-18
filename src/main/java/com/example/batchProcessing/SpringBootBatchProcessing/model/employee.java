package com.example.batchProcessing.SpringBootBatchProcessing.model;

public class employee {

    private int employeeID;
    private String name;
    private String department;
    private String startDate;
    private String endDate;
    private String totalDurationInCompany;

    public employee() {
    }

    public employee(int employeeID, String name, String department, String startDate, String endDate) {
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

    public String getTotalDurationInCompany() {
        return totalDurationInCompany;
    }

    public void setTotalDurationInCompany(String totalDurationInCompany) {
        this.totalDurationInCompany = totalDurationInCompany;
    }
}
