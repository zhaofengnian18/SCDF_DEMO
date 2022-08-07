##
#  Generic dockerfile for dbt image building.
#  See README for operational details
##


##
# base image (abstract)
##
FROM openjdk:11

ARG APP_ENV=prod

EXPOSE 8800

ENV DATAFLOW_URI='http://dataflow-server:9393'

ARG EXTRACTED=build/extracted
COPY ${EXTRACTED}/dependencies/ ./
COPY ${EXTRACTED}/spring-boot-loader ./
COPY ${EXTRACTED}/snapshot-dependencies  ./
COPY ${EXTRACTED}/application ./

ENTRYPOINT ["java", "-Ddataflow.uri=${DATAFLOW_URI}","org.springframework.boot.loader.JarLauncher"]

