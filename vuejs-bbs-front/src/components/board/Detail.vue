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
      <template v-if="authorized">
        <a class="btn" @click="toModify">수정</a>&nbsp;
        <a class="btn red" @click="remove = true" v-if="!remove">삭제</a>
        <template v-else>
          <button class="grey" @click="remove = false">취소</button>&nbsp;
          <button class="red" @click="toRemove">삭제</button>
        </template>&nbsp;
      </template>
      <a class="btn grey" @click="toList">목록</a>
    </div>
    <div class="divider"></div>

    <!-- 댓글 -->
    <comments :id="detail.id" :comments="detail.comments" v-on:reload="loadComments"></comments>

  </div>
</template>

<script>
import qstr from 'query-string';
import axios from '../../libs/axios.custom';
import notification from '../../libs/notification';
import Comments from './Comments.vue';

export default {
  components: {
    Comments,
  },

  props: ['id'],

  async created() {
    const res = await axios.get(`/api/v1/board/${this.id}`);
    this.detail = res.data;
  },

  data: () => ({
    detail: {
      user: {},
    },
    remove: false,
  }),

  methods: {
    async loadComments() {
      const res = await axios.get(`/api/v1/board/${this.id}/comment`);
      this.detail.comments = res.data;
    },

    toModify() {
      const query = qstr.stringify(this.$route.query);
      this.$router.push(`/board/${this.id}/modify?${query}`);
    },

    async toRemove() {
      try {
        const res = await axios.delete(`/api/v1/board/${this.detail.id}`);
        notification.success(res, '게시물 삭제 성공', () => {
          this.$router.push('/');
        });
      } catch (err) {
        notification.error(err, '게시물 삭제 실패');
      }
    },

    toList() {
      this.$router.push({ path: '/board', query: this.$route.query });
    },
  },

  computed: {
    authorized() {
      return this.$store.getters.username === this.detail.user.username;
    },
  },
};
</script>
