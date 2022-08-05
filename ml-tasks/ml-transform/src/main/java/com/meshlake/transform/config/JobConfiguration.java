package com.meshlake.transform.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.meshlake.transform.utils.JdbcUtils;


@Configuration
@EnableTask
public class JobConfiguration {

    private static Log logger
      = LogFactory.getLog(JobConfiguration.class);

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Boolean result = JdbcUtils.createTable("SCDF_TRANSFORM_TEST");
            logger.info("Create table result is ");
            logger.info(result);
        };
    }
}
