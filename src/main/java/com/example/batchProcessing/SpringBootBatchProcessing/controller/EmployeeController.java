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
                    employee_id,
                    name,
                    department,
                    start_date,
                    end_date,
                    duration
                FROM employees
                ORDER BY employee_id
                """;

        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {

                    employee emp = new employee();

                    emp.setEmployeeID(
                            resultSet.getInt("employee_id"));

                    emp.setName(
                            resultSet.getString("name"));

                    emp.setDepartment(
                            resultSet.getString("department"));

                    emp.setStartDate(
                            resultSet.getString("start_date"));

                    emp.setEndDate(
                            resultSet.getString("end_date"));

                    emp.setTotalDurationInCompany(
                            resultSet.getInt("duration"));

                    return emp;
                }
        );
    }
}