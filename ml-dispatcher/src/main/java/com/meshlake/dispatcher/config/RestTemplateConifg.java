package com.meshlake.dispatcher.config;

import java.net.URI;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.dataflow.rest.client.DataFlowTemplate;
import org.springframework.cloud.dataflow.rest.client.VndErrorResponseErrorHandler;
import org.springframework.cloud.dataflow.rest.job.StepExecutionHistory;
import org.springframework.cloud.dataflow.rest.support.jackson.ExecutionContextJacksonMixIn;
import org.springframework.cloud.dataflow.rest.support.jackson.ExitStatusJacksonMixIn;
import org.springframework.cloud.dataflow.rest.support.jackson.JobExecutionJacksonMixIn;
import org.springframework.cloud.dataflow.rest.support.jackson.JobInstanceJacksonMixIn;
import org.springframework.cloud.dataflow.rest.support.jackson.JobParameterJacksonMixIn;
import org.springframework.cloud.dataflow.rest.support.jackson.JobParametersJacksonMixIn;
import org.springframework.cloud.dataflow.rest.support.jackson.StepExecutionHistoryJacksonMixIn;
import org.springframework.cloud.dataflow.rest.support.jackson.StepExecutionJacksonMixIn;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.Jackson2HalModule;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConifg {

    @Value("${dataflow.uri}")
    private static String dataflowUri;
    
    @Bean
    public static RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new VndErrorResponseErrorHandler(restTemplate.getMessageConverters()));
        for (HttpMessageConverter<?> converter : restTemplate.getMessageConverters()) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                final MappingJackson2HttpMessageConverter jacksonConverter = (MappingJackson2HttpMessageConverter) converter;
                jacksonConverter.getObjectMapper()
                        .registerModule(new Jackson2HalModule())
                        .addMixIn(JobExecution.class, JobExecutionJacksonMixIn.class)
                        .addMixIn(JobParameters.class, JobParametersJacksonMixIn.class)
                        .addMixIn(JobParameter.class, JobParameterJacksonMixIn.class)
                        .addMixIn(JobInstance.class, JobInstanceJacksonMixIn.class)
                        .addMixIn(ExitStatus.class, ExitStatusJacksonMixIn.class)
                        .addMixIn(StepExecution.class, StepExecutionJacksonMixIn.class)
                        .addMixIn(ExecutionContext.class, ExecutionContextJacksonMixIn.class)
                        .addMixIn(StepExecutionHistory.class, StepExecutionHistoryJacksonMixIn.class);
            }
        }
        return restTemplate;
    }

    @Bean
    public static DataFlowTemplate dataFlowTemplate(RestTemplate restTemplate){
        try {
            return new DataFlowTemplate(new URI("http://localhost:9393"), restTemplate);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
