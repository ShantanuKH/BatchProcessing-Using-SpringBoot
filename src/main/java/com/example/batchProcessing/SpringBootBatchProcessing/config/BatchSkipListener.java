package com.example.batchProcessing.SpringBootBatchProcessing.config;

import com.example.batchProcessing.SpringBootBatchProcessing.model.BatchError;
import com.example.batchProcessing.SpringBootBatchProcessing.model.employee;
import com.example.batchProcessing.SpringBootBatchProcessing.service.BatchErrorService;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class BatchSkipListener
        implements SkipListener<employee, employee>,
        StepExecutionListener {

    private final BatchErrorService batchErrorService;

    private Long executionId;

    public BatchSkipListener(
            BatchErrorService batchErrorService) {

        this.batchErrorService = batchErrorService;
    }

    // ---------------------------------------------------------
    // StepExecutionListener
    // ---------------------------------------------------------

    @Override
    public void beforeStep(StepExecution stepExecution) {

        this.executionId =
                stepExecution
                        .getJobExecution()
                        .getId();

        System.out.println(
                "Batch execution ID captured: "
                        + executionId
        );
    }

    @Override
    public ExitStatus afterStep(
            StepExecution stepExecution) {

        return null;
    }

    // ---------------------------------------------------------
    // SkipListener
    // ---------------------------------------------------------

    @Override
    public void onSkipInRead(
            Throwable throwable) {

        BatchError error = new BatchError(
                executionId,
                null,
                null,
                null,
                null,
                null,
                "READ_ERROR",
                throwable.getMessage()
        );

        batchErrorService.saveError(error);
    }

    @Override
    public void onSkipInProcess(
            employee item,
            Throwable throwable) {

        BatchError error = new BatchError(
                executionId,
                item.getEmployeeID(),
                item.getName(),
                item.getDepartment(),

                // Raw CSV values are Strings
                item.getStartDate(),
                item.getEndDate(),

                "PROCESSING_ERROR",
                throwable.getMessage()
        );

        batchErrorService.saveError(error);
    }

    @Override
    public void onSkipInWrite(
            employee item,
            Throwable throwable) {

        BatchError error = new BatchError(
                executionId,
                item.getEmployeeID(),
                item.getName(),
                item.getDepartment(),

                // Raw CSV values are Strings
                item.getStartDate(),
                item.getEndDate(),

                "WRITE_ERROR",
                throwable.getMessage()
        );

        batchErrorService.saveError(error);
    }
}