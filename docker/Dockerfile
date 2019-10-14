# Most Dockerfiles start from a parent image.
# FROM *** indicates what base image you will use.
FROM centos:7

# Maintainer name
# MAINTAINER lechat thecat <frozenyogurt845@gmail.com>

# To use commands, use "RUN".
RUN yum -y update 
RUN yum install -y epel-release
RUN yum -y install https://centos7.iuscommunity.org/ius-release.rpm
RUN yum -y install  git \
                    java-1.8.0-openjdk \
                    maven \
                    mariadb-server

ENV MYSQL_ROOT_PASSWORD=root

# "EXPOSE" specifies which port you will open.
EXPOSE 8080

ENV PATH /opt/jdk/jdk-1.8.0/bin:$PATH

VOLUME /JavaChatBoard
WORKDIR /JavaChatBoard
COPY . /JavaChatBoard
#RUN mvn clean package 
#CMD exec java -jar /JavaChatBoard/target/chatboard-0.0.1-SNAPSHOT.jar --spring.config.location=file:/JavaChatBoard/src/main/resources/docker.application.properties
RUN chmod 777 /JavaChatBoard/docker/docker.sh
RUN chmod +x /JavaChatBoard/docker/docker.sh
CMD sh /JavaChatBoard/docker/docker.sh
# RUN mvn clean package 
# CMD is used to start modules.
# For example: CMD ["command", "argument 1","argument 2"]

