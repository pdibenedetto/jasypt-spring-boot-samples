FROM openjdk:17-jdk-slim

RUN groupadd -g 1000 spring && useradd -u 1000 -g spring -s /bin/sh spring
USER spring:spring

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8888
VOLUME /tmp
VOLUME /target
ENTRYPOINT ["java","-jar","/app.jar"]