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
          <a id="searchBtn" class="col s12 waves-effect btn teal lighten-2">
            <i class="material-icons center">search</i></a>
        </div>
      </div>

      <div class="collection">
        <router-link tag="a"
            :to="{ name: 'Board/Detail', params: { id: one.id }, query: query }"
            class="collection-item row" v-for="one in list" :key="one.id">
          <span class="col s7">
            <span>{{ one.title }}</span>&nbsp;
            <span class="red-text">
              [<span>{{ one.comments.length }}</span>]
            </span>
          </span>
          <small class="col s2 center-align">{{ one.user.name }}</small>
          <small class="col s2 center-align">{{ one.regdate }}</small>
          <small class="col s1 center-align">{{ one.hit }}</small>
        </router-link>
      </div>

      <div class="row valign-wrapper">
        <div class="col s6">
          <ul class="pagination">
            <li class="waves-effect">
              <a @click="previous"><i class="material-icons">chevron_left</i></a>
            </li>
            <router-link tag="li" v-for="present in presentedPages" :key="present"
                active-class="active" :to="fullPath(present)" exact>
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
  created() {
    this.beforeLoadPage();
  },

  data: () => ({
    pagination: {},
    list: [],
    blockSize: 5,
    query: {},
  }),

  methods: {
    beforeLoadPage() {
      this.query = this.$route.query;
      if (this.query.page === undefined) {
        this.$router.push({ path: '/board', query: { page: 0 } });
      } else {
        this.loadPage();
      }
    },

    loadPage() {
      axios.get(`/api/v1/board?page=${this.query.page}`).then((res) => {
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
      const target = _.cloneDeep(this.query);
      target.page = val;
      return { path: '/board', query: target };
    },

    previous() {
      const page = (_.first(this.presentedPages) - 1 < 0) ?
        0 : _.first(this.presentedPages) - 1;
      this.$router.push(this.fullPath(page));
    },

    forward() {
      const total = (this.pagination.totalPages === 0) ? 0 : this.pagination.totalPages - 1;
      const page = (_.last(this.presentedPages) === total) ?
        total : _.last(this.presentedPages) + 1;
      this.$router.push(this.fullPath(page));
    },
  },

  computed: {
    presentedPages() {
      const current = this.pagination.currentPage;
      const blockSize = this.blockSize;
      const total = (this.pagination.totalPages === 0) ? 0 : this.pagination.totalPages - 1;

      const startOfBlock = current - (current % blockSize);
      const endOfBlock = startOfBlock + blockSize;
      const complimentedEOB = (endOfBlock > (total + 1)) ? total + 1 : endOfBlock;
      return _.range(startOfBlock, complimentedEOB);
    },
  },

  beforeRouteUpdate(to, from, next) {
    next();
    this.beforeLoadPage();
  },

};
</script>
