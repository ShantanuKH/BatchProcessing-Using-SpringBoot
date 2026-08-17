package com.example.batchProcessing.SpringBootBatchProcessing.controller;

import com.example.batchProcessing.SpringBootBatchProcessing.model.BatchError;
import com.example.batchProcessing.SpringBootBatchProcessing.repository.BatchErrorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batch")
public class BatchErrorController {

    private final BatchErrorRepository batchErrorRepository;

    public BatchErrorController(
            BatchErrorRepository batchErrorRepository) {

        this.batchErrorRepository = batchErrorRepository;
    }

    @GetMapping("/executions/{executionId}/errors")
    public List<BatchError> getErrors(
            @PathVariable Long executionId) {

        return batchErrorRepository
                .findByExecutionId(executionId);
    }
}