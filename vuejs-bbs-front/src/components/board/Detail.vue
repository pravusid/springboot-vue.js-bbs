<template>
  <div class="container">
    <div class="section">
      <h3>{{ detail.title }}</h3>
    </div>
    <div class="divider"></div>
    <div class="section">
      <h5>
        작성자 : <span>{{ detail.user.name }}</span>&nbsp;&nbsp;
        <small>{{ detail.regdate }}</small>
      </h5>
    </div>
    <div class="divider"></div>
    <div class="section">
      <p v-html="detail.content"></p>
    </div>
    <div class="row right-align">
      <a class="btn" @click="modify">수정</a>&nbsp;
      <a class="btn red" @click="remove">삭제</a>&nbsp;
      <a class="btn grey" @click="toList">목록</a>
    </div>
    <div class="divider"></div>

    <!-- 댓글 -->
    <comments :id="detail.id" :comments="detail.comments" v-on:reload="loadComments"></comments>

  </div>
</template>

<script>
import axios from 'axios';
import Comments from './Comments.vue';

export default {
  components: {
    Comments,
  },

  created() {
    axios.get(`/api/v1/board/${this.$route.params.id}`).then((res) => {
      this.detail = res.data;
    });
  },

  data: () => ({
    detail: {
      user: {},
    },
  }),

  methods: {
    loadComments() {
      axios.get(`/api/v1/board/${this.$route.params.id}/comment`).then((res) => {
        this.detail.comments = res.data;
      });
    },

    modify() {

    },

    remove() {

    },

    toList() {

    },
  },
};
</script>
