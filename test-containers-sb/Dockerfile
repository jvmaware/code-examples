# base image
# FROM openjdk:19-alpine3.16
FROM --platform=linux/amd64 openjdk:20-jdk-oracle

# metadata
LABEL MAINTAINER="sumit@gaurs.net"

# workspace
WORKDIR /opt/app/

# add the jar
ADD ./target/test-containers-sb-0.0.1-SNAPSHOT.jar app.jar

# expose port
EXPOSE 8080

# execute the jar
ENTRYPOINT java -Xms1G -Xmx1536m -Dspring.profiles.active=local -jar /opt/app/app.jar