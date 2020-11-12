<img src="https://github.com/lechatthecat/JavaChatBoard/blob/master/pic/chat.png" width="50%">

# At first
This is still in development. Many functionalities are incomplete.
  
# Compatibility
Checked that this project works in Linux (Ubuntu, CentOS7).  
I am not sure if this works in Windows/Mac, but this should work in them also because this is made by Java.

# Use in Docker
You need docker and docker-compose.   
(If some service is already using port 8080 or 3306, please stop the service beforehand)
```
$ git clone https://github.com/lechatthecat/JavaChatBoard.git
$ cd ./JavaChatBoard
$ docker-compose up -d --build
```
Then wait until the build is finished. Logs are:
```
$ docker logs --tail 50 --follow --timestamps webapp
```
The web app should be running on: http://localhost:8080
If it fails, run "$ docker-compose up -d --build" several times.

You have two test users by default.  
id: test, password: 12345678  
id: test2, password: 12345678  

To get inside the container:
```
$ docker-compose exec webapp /bin/bash
```

To check containers' status:
```
$ docker ps -a
```
To stop docker containers:
```
$ docker-compose down 
$ sudo docker stop $(sudo docker ps -aq)
```
To delete docker objects:
```
$ docker system prune --volumes -f
```
To delete everything of docker:
```
$ docker-compose down -v --rmi all --remove-orphans
$ docker system prune --force --volumes --all
$ docker rm $(docker ps -a -q) -f
$ docker rmi $(docker images -q) -f
$ docker volume rm $(docker volume ls -q)
```

# Use without Docker
At first, you need Java8 and MariaDB (or mysql57) for the this project.
```
$ sudo apt-get install -y java-1.8.0-openjdk
$ sudo apt-get install -y mariadb-server
```
Clone the project in your local and compile it.  
You need Maven to compile this.
```
$ git clone https://github.com/lechatthecat/JavaChatBoard.git
$ cd JavaChatBoard
$ mvn clean package 
```
You need to create default data in the DB.  
The path for source command must be an absolute path.
```
$ sudo mysql -u [your user name of mariaDB] -p[your password of mariaDB]
$ source [PathToTheClonedJavaChatBoard]/JavaChatBoard/src/main/resources/sql/tables.sql
$ exit
```
You can start the project now.
```
$ cd [PathToTheClonedJavaChatBoard]/JavaChatBoard
$ java -jar ./target/chatboard-0.0.1-SNAPSHOT.jar
```
Now this app should be running on http://localhost:8080  
  
You have two test users by default.  
id: test, password: 12345678  
id: test2, password: 12345678  

