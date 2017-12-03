<template>
  <div>
    <div class="container">
      <div class="row valign-wrapper">
        <div class="col s6">
          <h1>게시판</h1>
        </div>
        <div class="col s2">
          <select id="filter">
            <option class="filter" value="title">제목</option>
            <option class="filter" value="content">내용</option>
            <option class="filter" value="user">작성자</option>
            <option class="filter" value="comment">댓글</option>
            <option class="filter" value="all">제목+내용+작성자</option>
          </select>
        </div>
        <div class="col s3">
          <input id="keyword" type="text" class="validate">
        </div>
        <div class="col s1">
          <button id="searchBtn" class="col s12 waves-effect btn teal lighten-2">
            <i class="material-icons center">search</i></button>
        </div>
      </div>

      <div class="collection">
        <a class="collection-item row">
            <span class="col s7">
              <span>게시물</span>&nbsp;
              <span class="red-text">
                [<span>0</span>]
              </span>
            </span>
          <small class="col s2 center-align">작성자</small>
          <small class="col s2 center-align">작성시각</small>
          <small class="col s1 center-align">조회수</small>
        </a>
        <a class="collection-item row" v-for="one in boardList" :key="one.id">
          <span class="col s7">
            <span>{{ one.title }}</span>&nbsp;
            <span class="red-text">
              [<span>0</span>]
            </span>
          </span>
          <small class="col s2 center-align">{{ one.writer }}</small>
          <small class="col s2 center-align">작성시각</small>
          <small class="col s1 center-align">조회수</small>
        </a>
      </div>

      <div class="row valign-wrapper">
        <div class="col s6">
          <ul class="pagination">
            <li class="waves-effect"><a><i
                class="material-icons">chevron_left</i></a>
            </li>
              <li th:class="active teal lighten-2">
                <a href="#"></a>
              </li>
            <li class="waves-effect"><a href="#"><i
                class="material-icons">chevron_right</i></a>
            </li>
          </ul>
        </div>
        <div class="col s6 right-align">
          <a class="waves-effect btn" href="/board/write">글쓰기</a>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // dummy data
      boardList: [
        { id: 1, title: '제목1', writer: '홍길동' },
        { id: 2, title: '제목2', writer: '홍길순' },
      ],
    };
  },
};
</script>
