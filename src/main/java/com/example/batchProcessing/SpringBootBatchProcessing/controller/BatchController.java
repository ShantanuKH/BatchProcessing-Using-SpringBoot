package com.example.batchProcessing.SpringBootBatchProcessing.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.batch.core.JobInstance;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job job;
    private final JobExplorer jobExplorer;

    public BatchController(
            JobLauncher jobLauncher,
            Job job,
            JobExplorer jobExplorer) {

        this.jobLauncher = jobLauncher;
        this.job = job;
        this.jobExplorer = jobExplorer;
    }

    @PostMapping("/run")
    public String runBatch() {

        try {

            JobParameters jobParameters =
                    new JobParametersBuilder()
                            .addLong(
                                    "run.id",
                                    System.currentTimeMillis())
                            .toJobParameters();

            JobExecution jobExecution =
                    jobLauncher.run(job, jobParameters);

            return "Batch started successfully. Execution ID: "
                    + jobExecution.getId();

        } catch (Exception e) {

            return "Batch failed to start: "
                    + e.getMessage();
        }
    }

    @GetMapping("/status/{executionId}")
    public Map<String, Object> getBatchStatus(
            @PathVariable Long executionId) {

        JobExecution jobExecution =
                jobExplorer.getJobExecution(executionId);

        if (jobExecution == null) {

            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Execution not found: " + executionId
            );
        }

        Map<String, Object> response =
                new LinkedHashMap<>();

        response.put(
                "executionId",
                jobExecution.getId());

        response.put(
                "jobName",
                jobExecution
                        .getJobInstance()
                        .getJobName());

        response.put(
                "status",
                jobExecution.getStatus());

        response.put(
                "startTime",
                jobExecution.getStartTime());

        response.put(
                "endTime",
                jobExecution.getEndTime());

        response.put(
                "exitStatus",
                jobExecution
                        .getExitStatus()
                        .getExitCode());

        if (!jobExecution
                .getStepExecutions()
                .isEmpty()) {

            var stepExecution =
                    jobExecution
                            .getStepExecutions()
                            .iterator()
                            .next();

            response.put(
                    "stepName",
                    stepExecution.getStepName());

            response.put(
                    "readCount",
                    stepExecution.getReadCount());

            response.put(
                    "writeCount",
                    stepExecution.getWriteCount());

            response.put(
                    "skipCount",
                    stepExecution.getSkipCount());

            response.put(
                    "commitCount",
                    stepExecution.getCommitCount());

            response.put(
                    "rollbackCount",
                    stepExecution.getRollbackCount());
        }

        return response;
    }

    @GetMapping("/executions")
    public List<Map<String, Object>> getExecutions() {

        List<Map<String, Object>> response = new ArrayList<>();

        List<JobInstance> jobInstances =
                jobExplorer.findJobInstancesByJobName(
                        job.getName(),
                        0,
                        50
                );

        for (JobInstance jobInstance : jobInstances) {

            List<JobExecution> executions =
                    jobExplorer.getJobExecutions(jobInstance);

            for (JobExecution execution : executions) {

                Map<String, Object> executionData =
                        new LinkedHashMap<>();

                executionData.put(
                        "executionId",
                        execution.getId()
                );

                executionData.put(
                        "jobName",
                        jobInstance.getJobName()
                );

                executionData.put(
                        "status",
                        execution.getStatus()
                );

                executionData.put(
                        "startTime",
                        execution.getStartTime()
                );

                executionData.put(
                        "endTime",
                        execution.getEndTime()
                );

                executionData.put(
                        "exitStatus",
                        execution.getExitStatus().getExitCode()
                );

                if (!execution.getStepExecutions().isEmpty()) {

                    var stepExecution =
                            execution.getStepExecutions()
                                    .iterator()
                                    .next();

                    executionData.put(
                            "readCount",
                            stepExecution.getReadCount()
                    );

                    executionData.put(
                            "writeCount",
                            stepExecution.getWriteCount()
                    );

                    executionData.put(
                            "skipCount",
                            stepExecution.getSkipCount()
                    );

                    executionData.put(
                            "rollbackCount",
                            stepExecution.getRollbackCount()
                    );
                }

                response.add(executionData);
            }
        }

        return response;
    }

    @GetMapping("/executions/{executionId}")
    public Map<String, Object> getExecutionDetails(
            @PathVariable Long executionId) {

        JobExecution execution =
                jobExplorer.getJobExecution(executionId);

        if (execution == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Execution not found: " + executionId
            );
        }

        Map<String, Object> response =
                new LinkedHashMap<>();

        response.put(
                "executionId",
                execution.getId()
        );

        response.put(
                "jobName",
                execution.getJobInstance().getJobName()
        );

        response.put(
                "status",
                execution.getStatus()
        );

        response.put(
                "startTime",
                execution.getStartTime()
        );

        response.put(
                "endTime",
                execution.getEndTime()
        );

        response.put(
                "exitStatus",
                execution.getExitStatus().getExitCode()
        );

        List<Map<String, Object>> steps =
                new ArrayList<>();

        for (StepExecution stepExecution :
                execution.getStepExecutions()) {

            Map<String, Object> step =
                    new LinkedHashMap<>();

            step.put(
                    "stepName",
                    stepExecution.getStepName()
            );

            step.put(
                    "status",
                    stepExecution.getStatus()
            );

            step.put(
                    "readCount",
                    stepExecution.getReadCount()
            );

            step.put(
                    "writeCount",
                    stepExecution.getWriteCount()
            );

            step.put(
                    "skipCount",
                    stepExecution.getSkipCount()
            );

            step.put(
                    "commitCount",
                    stepExecution.getCommitCount()
            );

            step.put(
                    "rollbackCount",
                    stepExecution.getRollbackCount()
            );

            steps.add(step);
        }

        response.put("steps", steps);

        return response;
    }
}