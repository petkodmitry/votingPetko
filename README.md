# VOTING Java Application

This is a simple standalone java application which provides all necessary actions to work with various topics for voting.
Client-server communication: REST; content type: JSON.

## 1. Technologies used

* Spring Boot 1.5.1.RELEASE
* HSqlDB 2.3.4
* jUnit 4.12
* Maven 3.0.5

## 2. Running project locally
a) **_Downloading_**
```
[for Git Bash:]
$ git clone -b master https://github.com/petkodmitry/votingPetko
$ cd votingPetko
```
or just download ZIP archive on your computer, unzip it and go to the unzipped votingPetko directory

b) **_Installation_**

_*NIX_
```
$ chmod 775 mvnw
$ ./mvnw clean install
```
_Windows_
```
$ mvnw.cmd clean install

```
c) **_Running_**
```
$ cd target
$ java -jar votingPetko.war
```
The application will be available on:

[`http://localhost:8080/`](http://localhost:8080/)

## 3. Actions description

#### a) Create New Theme

* mandatory fields

**_themeName_** -- the actual voting theme

**_options_** -- the array of answer options

**_optionName_** -- the actual answer option

`[http://localhost:8080/themes]` (method: POST)

#### b) Get Theme by ID
`[http://localhost:8080/themes/{id}]` (method: GET)

#### c) Start Voting
`[http://localhost:8080/themes/{id}/start]` (method: PUT)

#### d) Stop Voting
`[http://localhost:8080/themes/{id}/stop]` (method: PUT)

#### e) Get All Themes
`[http://localhost:8080/themes]` (method: GET)

#### f) Get All Opened Themes
`[http://localhost:8080/themes?status=opened]` (method: GET)

#### g) Register Voice
`[http://localhost:8080/themes/{id}/{optionId}]` (method: PUT)

#### h) View Theme's Statistics
`[http://localhost:8080/themes/{id}/statistics]` (method: GET)
