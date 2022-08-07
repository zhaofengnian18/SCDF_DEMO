package com.meshlake.dispatcher.api.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    
    private String cronExpression;

    private String taskName;

    private List<String> taskDefinitionNames;

    private String description;
}
