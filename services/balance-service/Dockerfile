FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar balance-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/balance-0.0.1-SNAPSHOT.jar"]