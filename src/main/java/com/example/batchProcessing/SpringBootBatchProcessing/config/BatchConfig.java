package com.example.batchProcessing.SpringBootBatchProcessing.config;

import com.example.batchProcessing.SpringBootBatchProcessing.exception.InvalidEmployeeDataException;
import com.example.batchProcessing.SpringBootBatchProcessing.model.employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    @Bean
    public Job jobBean(
            JobRepository jobRepository,
            JobCompletionNotificationImplementaion listener,
            Step steps) {

        return new JobBuilder("job", jobRepository)
                .listener(listener)
                .start(steps)
                .build();
    }

    @Bean
    public Step steps(
            JobRepository jobRepository,
            DataSourceTransactionManager transactionManager,
            ItemReader<employee> reader,
            ItemProcessor<employee, employee> processor,
            ItemWriter<employee> writer,
            BatchSkipListener batchSkipListener) {

        return new StepBuilder("jobStep", jobRepository)
                .<employee, employee>chunk(5, transactionManager)

                .reader(reader)
                .processor(processor)
                .writer(writer)

                .faultTolerant()

                .skip(InvalidEmployeeDataException.class)

                .skipLimit(10)

                // Register StepExecutionListener
                .listener(
                        (StepExecutionListener) batchSkipListener
                )

                // Register SkipListener
                .listener(
                        (SkipListener<employee, employee>) batchSkipListener
                )

                .build();
    }

    @Bean
    public FlatFileItemReader<employee> reader() {

        return new FlatFileItemReaderBuilder<employee>()
                .name("itemReader")
                .resource(new ClassPathResource("data.csv"))
                .linesToSkip(1)
                .delimited()
                .names(
                        "employeeID",
                        "name",
                        "department",
                        "startDate",
                        "endDate"
                )
                .targetType(employee.class)
                .build();
    }

    @Bean
    public ItemProcessor<employee, employee> itemProcessor() {

        return new CustomItemProccessor();
    }

    @Bean
    public ItemWriter<employee> itemWriter(
            DataSource dataSource) {

        return new JdbcBatchItemWriterBuilder<employee>()
                .sql("""
                        INSERT INTO employees
                        (
                            employee_id,
                            name,
                            department,
                            start_date,
                            end_date,
                            duration
                        )
                        VALUES
                        (
                            :employeeID,
                            :name,
                            :department,
                            :startDateValue,
                            :endDateValue,
                            :totalDurationInCompany
                        )
                        ON CONFLICT (employee_id)
                        DO UPDATE SET
                            name = EXCLUDED.name,
                            department = EXCLUDED.department,
                            start_date = EXCLUDED.start_date,
                            end_date = EXCLUDED.end_date,
                            duration = EXCLUDED.duration
                        """)
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }
}