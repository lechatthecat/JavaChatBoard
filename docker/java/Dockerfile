# Most Dockerfiles start from a parent image.
# FROM *** indicates what base image you will use.
FROM centos:7

# Maintainer name
# MAINTAINER lechat thecat <frozenyogurt845@gmail.com>

# To use commands, use "RUN".
RUN yum -y update 
RUN yum install -y epel-release
RUN yum -y install https://centos7.iuscommunity.org/ius-release.rpm
RUN yum -y install git 
RUN yum -y install java-1.8.0-openjdk
RUN yum -y install maven
RUN yum -y install mariadb-server
RUN yum -y install vim

ENV MYSQL_ROOT_PASSWORD=root

# "EXPOSE" specifies which port you will open.
EXPOSE 8080

ENV PATH /opt/jdk/jdk-1.8.0/bin:$PATH

# CMD is used to start modules.
# For example: CMD ["command", "argument 1","argument 2"]
RUN cd /
RUN git clone https://github.com/lechatthecat/JavaChatBoard.git
CMD cd / \
    && mysql -u root -proot --host 127.0.0.1 --port 3307 \
    && source /JavaChatBoard/src/main/resources/tables.sql \
    && exit 
# Caution! This downloads & installs all dependencies. It might take minutes.
RUN cd JavaChatBoard \
    && mvn clean package 
#    && java -jar /JavaChatBoard/target/chatboard-0.0.1-SNAPSHOT.jar
