FROM openjdk:18

ADD https://github.com/ufoscout/docker-compose-wait/releases/download/2.9.0/wait /wait
RUN chmod +x /wait

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} ./backend.jar
RUN mkdir conf
COPY target/classes/*.properties /conf/.


CMD /wait && java -jar backend.jar
#CMD /wait && java -cp .:./backend.jar com.db.fileserver.FileServerApplication