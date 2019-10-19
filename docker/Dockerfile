# Most Dockerfiles start from a parent image.
# FROM *** indicates what base image you will use.
FROM adoptopenjdk/openjdk8

# Maintainer name
# MAINTAINER lechat thecat <frozenyogurt845@gmail.com>

# To use commands, use "RUN".
RUN apt-get update -y && \
    apt-get install -y git \
    maven

# "EXPOSE" specifies which port you will open.
EXPOSE 8080

#RUN mvn clean package 
#CMD exec java -jar /JavaChatBoard/target/chatboard-0.0.1-SNAPSHOT.jar --spring.config.location=file:/JavaChatBoard/src/main/resources/docker.application.properties

CMD sh -x /JavaChatBoard/docker/docker.sh
# RUN mvn clean package 
# CMD is used to start modules.
# For example: CMD ["command", "argument 1","argument 2"]

