FROM openjdk:21-jdk
ARG JAR_FILE=target/*.jar
COPY ./target/restful-export-import-api-0.0.1-SNAPSHOT.jar exportimportapi.jar
CMD "java","-jar","export-import-api.jar"