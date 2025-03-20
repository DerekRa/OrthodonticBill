FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} orthodonticbill.jar
ENTRYPOINT ["java", "-jar", "/orthodonticbill.jar"]
EXPOSE 8082