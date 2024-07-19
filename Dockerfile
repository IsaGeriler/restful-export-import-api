FROM openjdk:21-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ./target/restful-export-import-api-0.0.1-SNAPSHOT.jar export-import-api.jar
ENTRYPOINT ["java", "-jar", "export-import-api.jar"]