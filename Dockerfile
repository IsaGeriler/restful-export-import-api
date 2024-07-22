FROM openjdk:21
ADD target/restful-export-import-api-0.0.1-SNAPSHOT.jar restful-export-import-api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "restful-export-import-api.jar"]