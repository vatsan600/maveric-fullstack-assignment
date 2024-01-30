FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar eureka-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/eureka-0.0.1-SNAPSHOT.jar"]