package com.example.batchProcessing.SpringBootBatchProcessing.config;

import com.example.batchProcessing.SpringBootBatchProcessing.model.employee;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CustomItemProccessor implements ItemProcessor<employee, employee> {

    @Override
    public employee process(employee item) {

        LocalDate startDate = LocalDate.parse(item.getStartDate());

        LocalDate endDate;


        if (item.getEndDate() == null || item.getEndDate().trim().isEmpty()) {
            endDate = LocalDate.now();
            item.setEndDate(null);
        } else {
            endDate = LocalDate.parse(item.getEndDate());
        }


        long days = ChronoUnit.DAYS.between(startDate, endDate);
        long years = ChronoUnit.YEARS.between(startDate, endDate);


        String duration = days + " days (" + years + " years)";
        item.setTotalDurationInCompany(duration);

        return item;
    }
}
