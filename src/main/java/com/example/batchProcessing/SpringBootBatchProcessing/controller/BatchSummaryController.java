package com.example.batchProcessing.SpringBootBatchProcessing.controller;

import com.example.batchProcessing.SpringBootBatchProcessing.model.BatchSummary;
import com.example.batchProcessing.SpringBootBatchProcessing.service.BatchSummaryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/batch/executions")
public class BatchSummaryController {

    private final BatchSummaryService batchSummaryService;

    public BatchSummaryController(
            BatchSummaryService batchSummaryService) {

        this.batchSummaryService = batchSummaryService;
    }

    @GetMapping("/{executionId}/summary")
    public BatchSummary getSummary(
            @PathVariable Long executionId) {

        return batchSummaryService.getSummary(executionId);
    }
}