# Spring Boot 3 Master

## Test용 계정 정보 (공통)

- username : `in28minutes`
- password : `dummy`

## myfirstwebapp

- JSP와 Spring 생태계를 이용한 Todo 웹앱
  ### MySQL Docker Container
  - Docker 명령어
  ```
  docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=todos-user --env MYSQL_PASSWORD=dummytodos --env MYSQL_DATABASE=todos --name mysql --publish 3306:3306 mysql:8-oracle
  ```
  ### 인증 헤더 (Basic Authentication)
  - `Authorization` : `Basic aW4yOG1pbnV0ZXM6ZHVtbXk=`
  #### InMemoryUserDetailsService 이용

## restful-web-services

- Spring 생태계를 이용한 간단한 SNS RestFul API

  ### MySQL Docker Container

  - Docker 명령어

  ```
  docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=social-media-user --env MYSQL_PASSWORD=dummypassword --env MYSQL_DATABASE=social-media-database --name mysql --publish 3306:3306 mysql:8-oracle
  ```

  ### 인증 헤더 (Basic Authentication)

  - `Authorization` : `Basic aW4yOG1pbnV0ZXM6ZHVtbXk=`

  #### InMemoryUserDetailsService 이용

  ### RestFul API

  #### Prefix : `http://localhost:8080/jpa`

  1. #### User API

  - 모든 user 가져오기 : `GET` `/users`
  - user 생성하기 : `POST` `/users`
    - JSON 예시
    ```json
    {
      "name": "Ravi",
      "birthDate": "1998-05-12"
    }
    ```
  - user 1명 정보 가져오기 : `GET` `/users/${user_id}`
  - user 삭제 : `DELETE` `/users/${user_id}`

  2. #### Post API

  - user 1명의 모든 post 가져오기 : `GET` `/users/${user_id}/posts`
  - 특정 user의 post 생성 : `POST` `/users/${user_id}/posts`

    - JSON 예시

    ```json
    {
      "name": "Ravi v2",
      "birthDate": "1998-05-12"
    }
    ```

  - 특정 user의 특정 post 가져오기 : `GET` `/users/${user_id}/posts/${post_id}`

# restful-web-services-todo

- React와 Spring 생태계를 이용한 Todo SPA웹앱
  ### MySQL Docker Container
  - Docker 명령어
  ```
  docker run --detach --env MYSQL_ROOT_PASSWORD=dummypassword --env MYSQL_USER=todos-user --env MYSQL_PASSWORD=dummytodos --env MYSQL_DATABASE=todos --name mysql --publish 3306:3306 mysql:8-oracle
  ```
  ### 인증헤더 (JWT Auth)
  - `Authorization` : `Bearer ${token}`
  #### InMemoryUserDetailsService 이용

## learn-spring-security

- ### Basic Auth, JWT Auth, JdbcUserDetailsService, SecurityFilterChain 등을 다룸
- ### 인증 헤더 (Basic Auth)
  `Authorization` : `Basic aW4yOG1pbnV0ZXM6ZHVtbXk=`
- ### 인증 헤더 (JWT Auth)
  `Authorization` : `Bearer ${token}`

## learn-oauth

### Oauth 기초, 실행시 application.properties에서 google client id와 secret을 설정해 줘야함

```properties
spring.security.oauth2.client.registration.google.client-id=YOUR_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_SECRET
```

### 구글 클라우드의 콘솔에서 API 및 서비스탭에서 사용자 인증정보를 만들어야함

- [구글 클라우드 콘솔 바로가기](https://console.cloud.google.com/)

### 생성된 클라이언트 아이디와 시크릿으로 세팅하면 정상적으로 동작한다.

# 주의) GitHub같은 public한 곳에 코드를 공개한다면 Oauth키가 노출될 수 있으니 주의하자
