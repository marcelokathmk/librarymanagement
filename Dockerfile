FROM openjdk:17-jdk
ARG JAR_FILE=librarymanagement-server/target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]