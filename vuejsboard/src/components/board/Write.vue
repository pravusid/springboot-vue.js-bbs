<template>
  <div class="container">
    <div class="row">
      <h1>게시물 작성</h1>
    </div>
    <form method="post" action="/board">
      <div class="row">
        <div class="input-field">
          <i class="material-icons prefix">account_circle</i>
          <input id="name" name="username" type="text" class="validate" disabled>
          <label for="name">작성자</label>
        </div>
      </div>
      <div class="row">
        <div class="input-field">
          <i class="material-icons prefix">subject</i>
          <input id="title" name="title" type="text" class="validate">
          <label for="title">제목</label>
        </div>
      </div>
      <div class="row">
        <div class="col s12">
          <textarea id="content" name="content"></textarea>
        </div>
      </div>
      <div class="row center">
        <button class="btn" type="submit">작성 완료</button>
        <a class="btn grey" href="/board">취소</a>
      </div>
    </form>
  </div>
</template>
