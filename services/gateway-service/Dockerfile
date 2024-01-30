FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar gateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/gateway-0.0.1-SNAPSHOT.jar"]