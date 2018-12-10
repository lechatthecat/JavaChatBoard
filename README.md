# Compatibility
Checked that this project works in Linux (Ubuntu, CentOS7).  
I am not sure if this works in Windows/Mac, but this should work in them also because this is made by Java.

# How to use
At first, you need Java8 and MariaDB (or mysql57) for the this project.
```
$ sudo apt-get install -y java-1.8.0-openjdk
$ sudo apt-get install -y mariadb-server
```
Clone the project in your local and compile it.
```
$ git clone https://github.com/lechatthecat/JavaChatBoard.git
$ cd JavaChatBoard
$ mvn clean package 
```
You need to create default data in the DB.  
The path for source command must be an absolute path.
```
$ sudo mysql -u [your user name of mariaDB] -p[your password of mariaDB]
$ create database chatboard;
$ use chatboard;
$ source [PathToTheClonedJavaChatBoard]/JavaChatBoard/src/main/resources/tables.sql
```
You can start the project now.
```
$ cd [PathToTheClonedJavaChatBoard]/JavaChatBoard
$ java -jar ./target/chatboard-0.0.1-SNAPSHOT.jar
```
You have two test users by default.  
id: test, password: 12345678  
id: test2, password: 12345678  
