# Spring Boot 3 Master

## Test용 계정 정보 (공통)

- username : `in28minutes`
- password : `dummy`

## myfirstwebapp

- JSP와 Spring 생태계를 이용한 Todo 웹앱
- ### MySQL Docker Container
  - Docker 명령어
  ```
  docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=todos-user --env MYSQL_PASSWORD=dummytodos --env MYSQL_DATABASE=todos --name mysql --publish 3306:3306 mysql:8-oracle
  ```

## restful-web-services

- Spring 생태계를 이용한 간단한 SNS RestFul API
- ### MySQL Docker Container
  - Docker 명령어
  ```
  docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=social-media-user --env MYSQL_PASSWORD=dummypassword --env MYSQL_DATABASE=social-media-database --name mysql --publish 3306:3306 mysql:8-oracle
  ```

# restful-web-services-todo

- React와 Spring 생태계를 이용한 Todo SPA웹앱
- ### MySQL Docker Container
  - Docker 명령어
  ```
  docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=todos-user --env MYSQL_PASSWORD=dummytodos --env MYSQL_DATABASE=todos --name mysql --publish 3306:3306 mysql:8-oracle
  ```
