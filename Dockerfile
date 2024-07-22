FROM openjdk:21
VOLUME /tmp
VOLUME /app/data
ARG JAR_FILE=./target/restful-export-import-api-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]