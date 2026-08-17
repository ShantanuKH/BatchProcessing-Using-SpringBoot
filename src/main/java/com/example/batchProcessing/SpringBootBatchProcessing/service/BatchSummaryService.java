package com.example.batchProcessing.SpringBootBatchProcessing.service;

import com.example.batchProcessing.SpringBootBatchProcessing.model.BatchSummary;
import com.example.batchProcessing.SpringBootBatchProcessing.repository.BatchSummaryRepository;
import org.springframework.stereotype.Service;

@Service
public class BatchSummaryService {

    private final BatchSummaryRepository batchSummaryRepository;

    public BatchSummaryService(
            BatchSummaryRepository batchSummaryRepository) {

        this.batchSummaryRepository = batchSummaryRepository;
    }

    public BatchSummary getSummary(Long executionId) {

        return batchSummaryRepository.findByExecutionId(executionId);
    }
}