package com.meshlake.dispatcher.service;

import java.util.Collections;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.dataflow.rest.client.DataFlowTemplate;
import org.springframework.cloud.dataflow.rest.resource.TaskDefinitionResource;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import com.meshlake.dispatcher.api.model.TaskDto;

@Service
public class TaskService extends QuartzJobBean {

    @Autowired
    DataFlowTemplate dataFlowTemplate;

    @Autowired
    private SchedulerService scheduleService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String taskName = context.getJobDetail().getJobDataMap().getString("taskName");
        TaskDto taskDto = new TaskDto();
        taskDto.setTaskName(taskName);
        this.execute(taskDto);
    }

    public void launch(TaskDto task) {
        if (task.getCronExpression().isBlank()) {
            this.execute(task);
            return;
        }
        this.scheduleService.register(task);
    }

    private long execute(TaskDto task) {
        return dataFlowTemplate.taskOperations().launch(task.getTaskName(), Collections.EMPTY_MAP,
                Collections.EMPTY_LIST);
    }

    public TaskDefinitionResource create(TaskDto task) {
        return dataFlowTemplate.taskOperations().create(task.getTaskName(), task.getTaskDefinitionNames().get(0),
                task.getDescription());
    }
}
