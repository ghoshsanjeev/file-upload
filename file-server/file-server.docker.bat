@REM docker stop $(docker ps -a -q --filter ancestor=file-server)
@REM docker rm $(sudo docker ps -a -q --filter ancestor=file-server)
@REM docker image rm -f coldfusion/file-server
@REM docker build -t coldfusion/file-server .
@REM docker tag coldfusion/file-server coldfusion/file-server:0.1
@REM docker push coldfusion/file-server:0.1
@REM
@REM docker pull coldfusion/file-server:0.1
@REM docker run -it coldfusion/file-server:0.1
@REM docker run -e "SPRING_PROFILES_ACTIVE=dev" -it coldfusion/file-server:0.1

docker image rm -f file-server && mvn clean install && docker build -f backend.Dockerfile -t file-server .

@REM docker build -t file-server .
@REM docker image rm -f file-server