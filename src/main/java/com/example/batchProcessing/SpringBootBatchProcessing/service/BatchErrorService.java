package com.example.batchProcessing.SpringBootBatchProcessing.service;

import com.example.batchProcessing.SpringBootBatchProcessing.model.BatchError;
import com.example.batchProcessing.SpringBootBatchProcessing.repository.BatchErrorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BatchErrorService {

    private final BatchErrorRepository batchErrorRepository;

    public BatchErrorService(BatchErrorRepository batchErrorRepository) {
        this.batchErrorRepository = batchErrorRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveError(BatchError error) {

        batchErrorRepository.save(error);
    }
}