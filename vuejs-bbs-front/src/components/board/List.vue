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
        <a class="collection-item row" v-for="one in list" :key="one.id">
          <router-link :to="{ path: '/board/' + one.id }">
          <span class="col s7">
            <span>{{ one.title }}</span>&nbsp;
            <span class="red-text">
              [<span>0</span>]
            </span>
          </span>
          <small class="col s2 center-align">{{ one.name }}</small>
          <small class="col s2 center-align">{{ one.regdate }}</small>
          <small class="col s1 center-align">{{ one.hit }}</small>
          </router-link>
        </a>
      </div>

      <div class="row valign-wrapper">
        <div class="col s6">
          <ul class="pagination">
            <li class="waves-effect">
              <a @click="previous"><i class="material-icons">chevron_left</i></a>
            </li>
            <router-link tag="li" v-for="present in presentedPages" :key="present"
                active-class="active"
                :to="fullPath(present)" exact>
              <a>{{ present + 1 }}</a>
            </router-link>
            <li class="waves-effect">
              <a @click="forward"><i class="material-icons">chevron_right</i></a>
            </li>
          </ul>
        </div>
        <div class="col s6 right-align">
          <router-link to="/board/write" class="waves-effect btn">글쓰기</router-link>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import axios from 'axios';
import _ from 'lodash';

export default {
  mounted() {
    const page = this.$route.query.page;
    if (page === undefined) {
      this.$router.push('/board?page=0');
    } else {
      this.loadPage(page);
    }
  },

  data: () => ({
    pagination: {
      numberOfElements: 0,
      totalElements: 0,
      isFirst: 0,
      isLast: 0,
      currentPage: 0,
      totalPages: 0,
      pageSize: 0,
    },
    list: [],
    blockSize: 5,
  }),

  methods: {
    loadPage(pageNo) {
      axios.get(`/api/v1/board?page=${pageNo}`).then((res) => {
        this.list = res.data.content;
        this.pagination = {
          numberOfElements: res.data.numberOfElements,
          totalElements: res.data.totalElements,
          isFirst: res.data.first,
          isLast: res.data.last,
          currentPage: res.data.number,
          totalPages: res.data.totalPages,
          pageSize: res.data.size,
        };
      });
    },

    fullPath(val) {
      return { path: '/board', query: { page: val } };
    },

    previous() {
      const page = (_.first(this.presentedPages) - 1 < 0) ? 0 : _.first(this.presentedPages) - 1;
      this.$router.push(this.fullPath(page));
    },

    forward() {
      const page = (_.last(this.presentedPages) + 1 === this.pagination.totalPages) ?
        this.pagination.totalPages - 1 : _.last(this.presentedPages) + 1;
      this.$router.push(this.fullPath(page));
    },
  },

  computed: {
    presentedPages() {
      const current = this.pagination.currentPage;
      const blockSize = this.blockSize;
      const total = this.pagination.totalPages;

      const startOfBlock = parseInt(current / blockSize, 10) * blockSize;
      const endOfBlock = startOfBlock + blockSize;
      const complimentedEOB = (endOfBlock > total) ? total : endOfBlock;
      return _.range(startOfBlock, complimentedEOB);
    },
  },

  beforeRouteUpdate(to, from, next) {
    if (to.query.page === undefined) {
      this.$router.push('/board?page=0');
    } else {
      this.loadPage(to.query.page);
      next();
    }
  },

};
</script>
