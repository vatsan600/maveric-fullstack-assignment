FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar transaction-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/transaction-0.0.1-SNAPSHOT.jar"]