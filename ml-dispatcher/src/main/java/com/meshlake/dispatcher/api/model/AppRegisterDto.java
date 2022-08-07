package com.meshlake.dispatcher.api.model;

import org.springframework.cloud.dataflow.core.ApplicationType;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AppRegisterDto {

    private String name;

    private ApplicationType type;

    private String uri; 

    private String metadataUri;

    private boolean force;
    
}
