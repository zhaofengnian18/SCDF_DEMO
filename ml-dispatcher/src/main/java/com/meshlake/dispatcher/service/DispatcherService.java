package com.meshlake.dispatcher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.dataflow.rest.client.DataFlowTemplate;
import org.springframework.cloud.dataflow.rest.resource.AppRegistrationResource;
import org.springframework.stereotype.Service;

import com.meshlake.dispatcher.api.model.AppRegisterDto;

@Service
public class DispatcherService {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(DispatcherService.class);

    @Autowired
    DataFlowTemplate dataFlowTemplate;

    public AppRegistrationResource register(AppRegisterDto registerInfo) {
        return dataFlowTemplate.appRegistryOperations().register(registerInfo.getName(), registerInfo.getType(),
                registerInfo.getUri(), registerInfo.getMetadataUri(), registerInfo.isForce());
    }

}
