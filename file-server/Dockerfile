FROM openjdk:18

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} backend.jar
COPY target/classes/*.properties .

ENTRYPOINT ["java","-jar","/backend.jar"]