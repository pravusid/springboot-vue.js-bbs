# SpringBoot와 Vue.js를 이용한 Toy Project

## 목표

Spring Boot로 RESTful 서버를 Vue.js를 이용한 Front 제작

View를 작성하는데 이용되는 자바스크립트 기술의 이해를 위해 상대적으로 진입장벽이 낮은 Vue.js를 이용한다.

최신 자바스크립트 기술 - ES6를 transpiling, webpack으로 build... - 이해를 위해 구현과정 중 이를 적극 사용해본다.

## 주제

트위터와 유사한 형태의 타임라인, 즉 글 작성과 리스트가 한 페이지에서 이루어짐

회원 기능을 만들고 다른 사람을 follow 하면 해당 회원의 글을 자신의 timeline에서 볼 수 있게 한다.

## 구현

### 서버

#### Server 사용기술

- Spring Boot
- JPA(Hibernate)
- MariaDB

#### Server 제작 내용

- 서버와 클라이언트는 JSON형식으로 data를 주고 받는다
- 글을 쓰고 사진첨부, 댓글작성 순으로 기능을 확장한다
- 회원 가입시 validation check는 client, server 모두에서 진행

### 클라이언트

#### Client 사용기술

- HTML / CSS
- Vue.js
- Axios

#### Client 제작 내용

- Axios를 이용하여 서버와 AJAX통신을 수행한다
- Page Reloading이 없는 SPA에 가까운 방식을 목표로 한다
