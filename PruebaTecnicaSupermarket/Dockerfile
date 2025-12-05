FROM eclipse-temurin:21-jre-ubi9-minimal
ARG JAR_FILE=target/supermarket-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_prueba_tecnica_super.jar
EXPOSE 9091
ENTRYPOINT ["java","-jar", "app_prueba_tecnica_super.jar"]