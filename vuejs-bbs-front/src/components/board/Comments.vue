<template>
  <div id="comments">
    <div class="section">
      <h5>
        댓글&nbsp;<small>(<span>{{ numberOfComments }}</span>)</small>
      </h5>
      <template v-for="comment in comments">
        <comment :key="comment.id" :id="id" :comment="comment"
            v-on:reload="reloadRequest"></comment>
      </template>
    </div>

    <div class="section row" id="new-comment">
      <textarea class="materialize-textarea col s10" v-model="comment.content"></textarea>
      <a class="btn col s2" @click="writeComment">댓글작성</a>
    </div>
  </div>
</template>

<script>
import axios from '../../libs/axios.custom';
import notification from '../../libs/notification';
import Comment from './Comment.vue';

export default {
  props: {
    id: Number,
    comments: Array,
  },

  components: {
    Comment,
  },

  data: () => ({
    comment: {},
  }),

  methods: {
    async writeComment() {
      try {
        if (this.comment.content === undefined || this.comment.content === '') {
          notification.warn('내용을 입력해 주세요');
          return;
        }
        const res = await axios.post(`/api/v1/board/${this.id}/comment`, this.comment);
        notification.success(res, '댓글이 등록되었습니다', () => {
          this.$el.scrollTop = this.$el.scrollHeight;
          this.reloadRequest();
          this.$scrollTo('#new-comment');
          this.comment = {};
        });
      } catch (err) {
        notification.error(err, '댓글 등록중 오류 발생');
      }
    },

    reloadRequest() {
      this.$emit('reload');
    },
  },

  computed: {
    numberOfComments() {
      return this.comments ? this.comments.length : 0;
    },
  },
};
</script>
