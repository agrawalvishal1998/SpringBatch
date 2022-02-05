package com.techprimers.springbatchexample.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/load")
public class RunBatch {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @GetMapping
    public BatchStatus load() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        Map<String, JobParameter> maps=new HashMap<>();
        maps.put("time",new JobParameter(System.currentTimeMillis()));
        JobParameters jobParameters=new JobParameters(maps);
        JobExecution jobExecution=jobLauncher.run(job,jobParameters);

        System.out.println("JobExecution:"+jobExecution.getStatus());
        System.out.println("Batch job is running ...");
        while(jobExecution.isRunning())
            System.out.println("...");

        return jobExecution.getStatus();
    }
}
