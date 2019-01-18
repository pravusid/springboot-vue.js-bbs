<template>
  <div class="row">
    <div :class="'col s' + (comment.replyDepth - 1)"></div>
    <div :class="'col s' + (13 - comment.replyDepth)">
      <div class="card">
        <div class="card-content">
          <span class="card-title">
            <span>{{ comment.user.name }}</span>&nbsp;&nbsp;
            <small>{{ comment.regdate }}</small>
          </span>
          <p v-html="comment.content">댓글</p>
        </div>
        <div class="card-action right-align">
          <a id="reply" v-if="authenticated" @click="toggle" :style="{ cursor: 'pointer'}">대댓글</a>
          <template v-if="authorized">
            <a id="modify" @click="toggle" :style="{ cursor: 'pointer'}">수정</a>
            <a id="remove" @click="remove = true" v-if="!remove"
              :style="{ cursor: 'pointer'}">삭제</a>
            <template v-else>
              <button class="grey" @click="remove = false">취소</button>&nbsp;
              <button class="red" @click="toRemove">삭제</button>
            </template>
          </template>
        </div>
      </div>
    </div>
    <div :class="'col s' + (comment.replyDepth - 1)"></div>
    <div :class="'col s' + (13 - comment.replyDepth)">
      <div class="row">
        <!-- 대댓글 -->
        <div class="col s12" :class="{hide : !reply.isActive}">
          <textarea
            class="materialize-textarea col s10"
            v-model="reply.content"
            placeholder="대댓글을 입력하세요"
          ></textarea>
          <a class="btn col s2" @click="toReply">대댓글작성</a>
        </div>
        <!-- 수정 -->
        <div class="col s12" :class="{hide : !modify.isActive}">
          <textarea class="materialize-textarea col s10" v-model="modify.content"></textarea>
          <a class="btn col s2" @click="toModify">댓글수정</a>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from '../../libs/axios.custom';
import notification from '../../libs/notification';

export default {
  props: {
    id: Number,
    comment: Object,
  },

  created() {
    this.modify.content = this.comment.content;
  },

  data: () => ({
    reply: {
      isActive: false,
    },
    modify: {
      isActive: false,
    },
    remove: false,
  }),

  methods: {
    toggle(event) {
      const reply = this.reply;
      const modify = this.modify;
      const activateOnlyOne = (origin, target) => {
        if (origin.isActive === true) {
          target.isActive = false;
        }
      };

      if (event.target.id === 'reply') {
        reply.isActive = !reply.isActive;
        activateOnlyOne(reply, modify);
      } else if (event.target.id === 'modify') {
        modify.isActive = !modify.isActive;
        activateOnlyOne(modify, reply);
      }
    },

    async toReply() {
      try {
        const res = await axios.post(`/api/v1/board/${this.id}/comment`, {
          content: this.reply.content,
          replyDepth: this.comment.replyDepth,
          replyOrder: this.comment.replyOrder,
        });
        notification.success(res, '댓글등록 성공', () => {
          this.$emit('reload');
          this.reply.content = '';
          this.reply.isActive = false;
        });
      } catch (err) {
        notification.error(err, '댓글등록 실패!');
      }
    },

    async toModify() {
      try {
        const res = await axios.put(`/api/v1/board/${this.id}/comment/${this.comment.id}`, {
          content: this.modify.content,
        });
        notification.success(res, '댓글 수정 성공', () => {
          this.$emit('reload');
          this.modify.isActive = false;
        });
      } catch (err) {
        notification.error(err, '댓글 수정실패\n다시 시도해 주세요');
      }
    },

    async toRemove() {
      try {
        const res = await axios.delete(`/api/v1/board/${this.id}/comment/${this.comment.id}`);
        notification.success(res, '댓글 삭제 성공', () => {
          this.$emit('reload');
        });
      } catch (err) {
        notification.error(err, '댓글 삭제 실패\n다시 시도해 주세요');
      }
    },
  },

  computed: {
    authenticated() {
      return this.$store.state.authentication !== null;
    },

    authorized() {
      return this.$store.getters.username === this.comment.user.username;
    },
  },
};
</script>
