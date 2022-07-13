FROM openjdk:18

## Add the wait script to the image
ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /wait
RUN chmod +x /wait

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} ./backend.jar
COPY target/classes/*.properties .

CMD /wait && java -jar backend.jar