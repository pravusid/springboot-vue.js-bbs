[![Build Status](https://travis-ci.org/pravusid/springboot-vue.js-bbs.svg?branch=master)](https://travis-ci.org/pravusid/springboot-vue.js-bbs)

# Toy Project: Spring Boot + SSR / CSR

1. Spring Boot + Thymeleaf
2. Spring Boot + Vue.js 2

## 목표

1. Spring Boot, Spring data JPA로 서버를 구성한다.
2. 템플릿 엔진으로 Thymeleaf를 사용한다.
3. 2번까지의 과정이 끝나면 동일 서버에 HTTP API(REST)를 추가한다.
4. 템플릿 엔진으로 구현한 클라이언트단과 동일한 기능을 Vue.js를 활용하여 제작한다.
5. 동일한 두 웹 어플리케이션을 제작하는 것이 최종 목표이다.

## 주제

무한계층 댓글 기능의 회원제 게시판을 구현한다.

## 구현

### 서버

#### Server 사용기술

- Spring Boot
- Spring data JPA(Hibernate)
- H2 Database
- MySQL
- Spring Security
- Spring Security OAuth2

#### Server 제작 내용

완성됨

- 회원제 게시판 제작을 목표로 한다
- 댓글은 무한계층형으로 구현하고 삭제시 동일 노드의 게시물이 모두 삭제 되도록 만든다.
- 오픈소스 에디터를 적용한다.
- 게시물, 댓글 검색기능을 적용한다.
- 회원 가입시 유효성 검사는 client, server 모두에서 진행
- 스프링 시큐리티를 통해 가입, 로그인, 비밀번호 암호화등을 적용한다.
- HTTP API(REST)로 서버와 클라이언트가 JSON 데이터를 주고 받는다
- OAuth2 Grant Type은 implicit으로 진행
- JWT를 통해서 사용자 인증을 진행한다

### Thymeleaf 클라이언트

템플릿엔진으로 기존에 사용해봤던 JSP 이외의 후보에서 선택한 Thymeleaf를 사용한다.

Spring boot starter thymeleaf는 ~~3버전이 아니어서 다소 문제가 발생했다. 따라서 기존 문법을 사용하였다.~~

thymeleaf를 3버전으로 변경하고 최신 문법을 적용하였다.

#### Thymeleaf 클라이언트 사용 기술

- Thymeleaf (Spring-boot-starter)
- [Materialize CSS](http://materializecss.com/)
- [Trumbowyg Editor](https://github.com/Alex-D/Trumbowyg)

### Vue.JS 클라이언트

View를 작성하는데 이용되는 자바스크립트 기술의 이해를 위해 상대적으로 진입장벽이 낮은 Vue.js를 이용한다.

최신 자바스크립트 기술 - ES6를 transpiling, webpack으로 build... - 이해를 위해 구현과정 중 이를 적극 사용해본다.

#### Vue.JS Client 사용기술

- vue-cli: project scaffolding
- vue.js 2
- vuex: 상태 관리
- vue-router: SPA 라우팅
- axios: HTTP client
- lodash: 유틸성 함수 일부 사용
- vue-quill-editor: Trumbowyg Editor 대체
- [Materialize CSS](http://materializecss.com/): CSS만 사용

#### Client 제작 내용

- Page Reloading이 없는 SPA를 목표로 한다
- ES6 문법을 사용하여 작성하고 babel로 크로스 컴파일 하며, webpack으로 번들링한다
- vuex를 사용하여 상태관리를 한다
- props를 통해 컴포넌트간 데이터를 전송한다
- EventBus로 컴포넌트간 이벤트처리를 한다
- vue-router로 새로고침 없이 리소스 경로 관리를 한다
- axios를 이용하여 서버와 HTTP 통신을 수행한다
