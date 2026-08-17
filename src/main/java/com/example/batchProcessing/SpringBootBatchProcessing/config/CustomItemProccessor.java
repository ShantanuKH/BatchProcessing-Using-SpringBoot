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
        // employeeID is an int, so it cannot be null.
        // We treat 0 or negative values as invalid.
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

        LocalDate endDate;

        // If end date is empty, employee is considered active
        if (item.getEndDate() == null ||
                item.getEndDate().trim().isEmpty()) {

            endDate = LocalDate.now();

            item.setEndDate(null);

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

        // Calculate duration
        long days =
                ChronoUnit.DAYS.between(
                        startDate,
                        endDate
                );

        long years =
                ChronoUnit.YEARS.between(
                        startDate,
                        endDate
                );

        String duration =
                days + " days (" + years + " years)";

        item.setTotalDurationInCompany(duration);

        return item;
    }
}