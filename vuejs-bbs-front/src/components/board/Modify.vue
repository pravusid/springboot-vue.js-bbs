<template>
  <div class="container">
    <div class="row">
      <h1>게시물 수정</h1>
    </div>
    <form method="post" th:action="|/board/${detail.id}?${_csrf.parameterName}=${_csrf.token}|">
      <input type="hidden" name="_method" value="put">
      <input type="hidden" name="query" th:value="${query}">
      <div class="row">
        <div class="input-field">
          <i class="material-icons prefix">account_circle</i>
          <input id="name" type="text" class="validate" th:value="${detail.user.name}" disabled>
          <label for="name">작성자</label>
        </div>
      </div>
      <div class="row">
        <div class="input-field">
          <i class="material-icons prefix">subject</i>
          <input id="title" name="title" type="text" class="validate" th:value="${detail.title}">
          <label for="title">제목</label>
        </div>
      </div>
      <div class="row">
        <div class="col s12">
          <textarea id="content" name="content" th:text="${detail.content}"></textarea>
        </div>
      </div>
      <div class="row center">
        <a class="btn" type="submit">수정하기</a>
        <a class="btn grey" th:href="|/board/${detail.id}?${query}|">취소</a>
      </div>
    </form>
  </div>
</template>
