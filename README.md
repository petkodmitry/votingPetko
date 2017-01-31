# VOTING Java Application

This is a simple java application which provides all necessary actions to work with various topics for voting.
Client-server communication: REST; content type: JSON.

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
$ git clone -b master https://github.com/petkodmitry/votingPetko
$ cd votingPetko
$ mvn clean install
```
The application will be available on:

[`http://localhost:8080/votingPetko`]()

## 5. Actions description

#### a) Create New Theme

* mandatory fields

**_themeName_** -- the actual voting theme

**_options_** -- the array of answer options

**_optionName_** -- the actual answer option

`[http://localhost:8080/votingPetko/themes]` (method: POST) or

`[http://localhost:8080/votingPetko/addtheme]`  for web version

#### b) Get Theme by ID
`[http://localhost:8080/votingPetko/themes/{id}]` (method: GET)

#### c) Start Voting
`[http://localhost:8080/votingPetko/themes/{id}/start]` (method: PUT)

#### d) Stop Voting
`[http://localhost:8080/votingPetko/themes/{id}/stop]` (method: PUT)

#### e) Get All Themes
`[http://localhost:8080/votingPetko/themes]` (method: GET)

#### f) Get All Opened Themes
`[http://localhost:8080/votingPetko/opened]` (method: GET)

#### g) Register Voice
`[http://localhost:8080/votingPetko/themes/{id}/{optionId}]` (method: PUT)

#### h) View Theme's Statistics
`[http://localhost:8080/votingPetko/statistics/{id}]` (method: GET)
