package com.example.batchProcessing.SpringBootBatchProcessing.config;

import com.example.batchProcessing.SpringBootBatchProcessing.exception.InvalidEmployeeDataException;
import com.example.batchProcessing.SpringBootBatchProcessing.model.employee;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class CustomItemProccessor implements ItemProcessor<employee, employee> {

    @Override
    public employee process(employee item) {

        // Validate Employee ID
        if (item.getEmployeeID() <= 0) {

            throw new InvalidEmployeeDataException(
                    "Invalid or missing Employee ID"
            );
        }

        // Validate Name
        if (item.getName() == null ||
                item.getName().trim().isEmpty()) {

            throw new InvalidEmployeeDataException(
                    "Employee name is missing for Employee ID: "
                            + item.getEmployeeID()
            );
        }

        // Validate Department
        if (item.getDepartment() == null ||
                item.getDepartment().trim().isEmpty()) {

            throw new InvalidEmployeeDataException(
                    "Department is missing for Employee ID: "
                            + item.getEmployeeID()
            );
        }

        // Validate Start Date
        if (item.getStartDate() == null ||
                item.getStartDate().trim().isEmpty()) {

            throw new InvalidEmployeeDataException(
                    "Start date is missing for Employee ID: "
                            + item.getEmployeeID()
            );
        }

        // Parse Start Date
        LocalDate startDate;

        try {

            startDate = LocalDate.parse(
                    item.getStartDate()
            );

        } catch (DateTimeParseException e) {

            throw new InvalidEmployeeDataException(
                    "Invalid start date for Employee ID: "
                            + item.getEmployeeID()
                            + ". Expected format: yyyy-MM-dd",
                    e
            );
        }

        // Parse End Date
        LocalDate endDate;

        // Empty end date means employee is currently active
        if (item.getEndDate() == null ||
                item.getEndDate().trim().isEmpty()) {

            endDate = LocalDate.now();

        } else {

            try {

                endDate = LocalDate.parse(
                        item.getEndDate()
                );

            } catch (DateTimeParseException e) {

                throw new InvalidEmployeeDataException(
                        "Invalid end date for Employee ID: "
                                + item.getEmployeeID()
                                + ". Expected format: yyyy-MM-dd",
                        e
                );
            }
        }

        // Validate date relationship
        if (endDate.isBefore(startDate)) {

            throw new InvalidEmployeeDataException(
                    "End date cannot be before start date for Employee ID: "
                            + item.getEmployeeID()
            );
        }

        // Store parsed dates
        item.setStartDateValue(startDate);
        item.setEndDateValue(endDate);

        // Calculate duration in days
        long days = ChronoUnit.DAYS.between(
                startDate,
                endDate
        );

        // PostgreSQL duration column is INTEGER
        item.setTotalDurationInCompany((int) days);

        return item;
    }
}