package com.meshlake.dispatcher.service;

import java.time.Instant;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meshlake.dispatcher.api.model.TaskDto;

/**
 * Register JobDetails by the given ingest plan.
 */
@Slf4j
@Service
public class SchedulerService {

    @Autowired
    private Scheduler scheduler;

    public void register(TaskDto task){
        JobDetail detail = buildJobDtail(task);
        Trigger trigger = buildJobTrigger(detail, task);
        try {
            this.scheduler.scheduleJob(detail, trigger);
        } catch (Exception e) {
            log.error("Failed to register job {}", task.getTaskName(), e);
        }
    }

    private JobDetail buildJobDtail(TaskDto task) {
        return JobBuilder.newJob(TaskService.class)
                .withIdentity(task.getTaskName())
                .usingJobData("taskName", task.getTaskName())
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, TaskDto task) {
        TriggerBuilder<Trigger> builder = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(task.getTaskName())
                .startAt(Date.from(Instant.now()));

        return builder
                .withSchedule(CronScheduleBuilder.cronSchedule(task.getCronExpression()))
                .build();
    }
}
