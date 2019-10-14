#!/bin/bash
cd /JavaChatBoard
mvn clean install
exec java -jar /JavaChatBoard/target/chatboard-0.0.1-SNAPSHOT.jar --spring.config.location=file:/JavaChatBoard/src/main/resources/docker.application.properties

/bin/bash
