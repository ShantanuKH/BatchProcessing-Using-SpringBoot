package com.example.batchProcessing.SpringBootBatchProcessing.controller;

import com.example.batchProcessing.SpringBootBatchProcessing.model.employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping
    public List<employee> getEmployees() {

        String sql = """
                SELECT
                    EmployeeID,
                    Name,
                    Department,
                    StartDate,
                    EndDate,
                    Duration
                FROM Employees
                ORDER BY EmployeeID
                """;

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {

                    employee emp = new employee();

                    emp.setEmployeeID(
                            resultSet.getInt("EmployeeID"));

                    emp.setName(
                            resultSet.getString("Name"));

                    emp.setDepartment(
                            resultSet.getString("Department"));

                    emp.setStartDate(
                            resultSet.getString("StartDate"));

                    emp.setEndDate(
                            resultSet.getString("EndDate"));

                    emp.setTotalDurationInCompany(
                            resultSet.getString("Duration"));

                    return emp;
                }
        );
    }
}