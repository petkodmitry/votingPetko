# VOTING Java Application

This is a simple java application which provides all necessary actions to work with various topics for voting.

## 1. Technologies used
* Spring Boot 1.4.2.RELEASE
* MySql 5.1.40
* jUnit 4.12
* Maven 3.0.5

## 2. Before usage

This application stores all the data in MySQL database. It's settings can automatically be realized
by running the [`runFirst.sql`](https://github.com/petkodmitry/votingPetko/blob/master/src/main/java/by/petko/scripts/runFirst.sql) script.

## 3. Running project locally
```
$ git clone https://github.com/petkodmitry/votingPetko
$ cd votingPetko
$ mvn clean install
```
The application will be available on:
```
http://localhost:8080/votingPetko
```
