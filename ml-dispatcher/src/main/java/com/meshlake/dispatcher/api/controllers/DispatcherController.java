package com.meshlake.dispatcher.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.dataflow.rest.resource.AppRegistrationResource;
import org.springframework.cloud.dataflow.rest.resource.TaskDefinitionResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

import com.meshlake.dispatcher.api.model.AppRegisterDto;
import com.meshlake.dispatcher.api.model.TaskDto;
import com.meshlake.dispatcher.service.DispatcherService;
import com.meshlake.dispatcher.service.TaskService;

@Slf4j
@RestController
public class DispatcherController {

    @Autowired
    private DispatcherService dispatcherService;

    @Autowired
    private TaskService taskService;

    public static final String ENDPOINT_LAUNCH = "/dispatcher/launch";
    public static final String ENDPOINT_REGISTER = "/dispatcher/register";
    public static final String ENDPOINT_CREATE = "/dispatcher/create";


    /**
     * launch a  task
     *
     */
    @PostMapping(ENDPOINT_LAUNCH)
    public ResponseEntity<String> launch(@RequestBody TaskDto taskDto) {
        taskService.launch(taskDto);
        return ResponseEntity.ok("ok");
    }

    /**
     * register tasks
     *
     */
    @PostMapping(ENDPOINT_REGISTER)
    public ResponseEntity<AppRegistrationResource> register(@RequestBody AppRegisterDto registerInfo) {
        return ResponseEntity.ok(dispatcherService.register(registerInfo));
    }

     /**
     * register tasks
     *
     * 
     */
    @PostMapping(ENDPOINT_CREATE)
    public ResponseEntity<TaskDefinitionResource> create(@RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.create(taskDto));
    }
}
