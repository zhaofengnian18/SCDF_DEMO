package com.meshlake.dispatcher.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DispatcherService {

    @SuppressWarnings("unused")
    private static final Logger LOG = LoggerFactory.getLogger(DispatcherService.class);

    private String shellJarName = "spring-cloud-dataflow-shell-2.5.3.RELEASE.jar";

    @Value("${workdir}")
    private String workdir;

    @Value("${dataflow.uri}")
    private String dataflowUri;

    public boolean launch(){
        runCommand(getWorkdir(),  "java", "-jar",shellJarName,"--dataflow.uri="+dataflowUri,"--spring.shell.commandFile=launch.shell");
        return true;
    }

    public boolean register(){
        runCommand(getWorkdir(), "java", "-jar", shellJarName,"--dataflow.uri="+dataflowUri,"--spring.shell.commandFile=register.shell");
        return true;
    }

    public boolean create(){
        runCommand(getWorkdir(), "java", "-jar", shellJarName,"--dataflow.uri="+dataflowUri,"--spring.shell.commandFile=createTask.shell");
        return true;
    }

    private String getWorkdir() {
        if (!workdir.endsWith(FileSystems.getDefault().getSeparator())) {
            return workdir + FileSystems.getDefault().getSeparator();
        }
        LOG.info("workdir={}", workdir);
        return workdir;
    }

    private void runCommand(String workdir, String... commands) {
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.directory(new File(workdir));
        try {
            Process p = pb.start();
            int exitCode = p.waitFor();
            LOG.info("exitCode = {}", exitCode);
            String line;
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(p.getInputStream(), StandardCharsets.UTF_8));
            while ((line = reader.readLine()) != null) {
                LOG.info(line);
            }
            reader.close();
            if (exitCode != 0) {
                String message = Stream.of(commands).collect(Collectors.joining(" "));
                throw new Exception(message + " failed with exit code " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error("Error run command", e);
            throw new RuntimeException(e);
        }

    }

}
