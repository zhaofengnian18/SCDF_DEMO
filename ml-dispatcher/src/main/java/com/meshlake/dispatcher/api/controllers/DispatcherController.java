package com.meshlake.dispatcher.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import com.meshlake.dispatcher.service.DispatcherService;

@Slf4j
@RestController
public class DispatcherController {

    @Autowired
    private DispatcherService dispatcherService;

    public static final String ENDPOINT_LAUNCH = "/dispatcher/launch";
    public static final String ENDPOINT_REGISTER = "/dispatcher/register";
    public static final String ENDPOINT_CREATE = "/dispatcher/create";


    /**
     * launch a  task
     *
     * 
     */
    @PostMapping(ENDPOINT_LAUNCH)
    public ResponseEntity<Boolean> launch() {
        return ResponseEntity.ok(dispatcherService.launch());
    }

    /**
     * register tasks
     *
     * 
     */
    @PostMapping(ENDPOINT_REGISTER)
    public ResponseEntity<Boolean> register() {
        return ResponseEntity.ok(dispatcherService.register());
    }

     /**
     * register tasks
     *
     * 
     */
    @PostMapping(ENDPOINT_CREATE)
    public ResponseEntity<Boolean> create() {
        return ResponseEntity.ok(dispatcherService.create());
    }
}
