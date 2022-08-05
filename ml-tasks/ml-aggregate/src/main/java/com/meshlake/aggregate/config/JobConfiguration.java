package com.meshlake.aggregate.config;

import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.meshlake.aggregate.utils.JdbcUtils;


@Configuration
@EnableTask
public class JobConfiguration {

    private static Log logger
      = LogFactory.getLog(JobConfiguration.class);

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Boolean result = JdbcUtils.aggregate("SCDF_TRANSFORM_TEST","SCDF_AGGREGATE_TEST");
            logger.info("Create table result is ");
            logger.info(result);
            ResultSet rs = JdbcUtils.getSampleData("SCDF_AGGREGATE_TEST");
            logger.info("Sample data is ");
            logger.info(rs);
        };
    }
}
