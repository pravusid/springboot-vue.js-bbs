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
          <a id="reply" @click="toggle">대댓글</a>
          <a id="modify" @click="toggle">수정</a>
          <a id="remove" @click="remove">삭제</a>
        </div>
      </div>
    </div>

    <div :class="'col s' + (comment.replyDepth - 1)"></div>
    <div :class="'col s' + (13 - comment.replyDepth)">
      <div class="row">
        <!-- 대댓글 -->
        <div class="col s12" :class="{hide : !reply.isActive}">
          <textarea class="materialize-textarea col s10" v-model="reply.content"
              placeholder="대댓글을 입력하세요"></textarea>
          <button class="btn col s2" @click="toReply">대댓글작성</button>
        </div>
        <!-- 수정 -->
        <div class="col s12" :class="{hide : !modify.isActive}">
          <textarea class="materialize-textarea col s10" v-model="modify.content"></textarea>
          <button class="btn col s2" @click="toModify">댓글수정</button>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import axios from 'axios';
import notification from '../../lib/notification';

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

    toReply() {
      axios.post(`/api/v1/board/${this.id}/comment`, {
        content: this.reply.content,
        replyDepth: this.comment.replyDepth,
        replyOrder: this.comment.replyOrder,
      }).then((res) => {
        notification.success(res, '댓글등록 성공', () => {
          this.$emit('reload');
          this.reply.content = '';
          this.reply.isActive = false;
        });
      }).catch((err) => {
        notification.error(err, '댓글등록 실패!');
      });
    },

    toModify() {
      axios.put(`/api/v1/board/${this.id}/comment/${this.comment.id}`, {
        content: this.modify.content,
      }).then((res) => {
        notification.success(res, '댓글 수정 성공', () => {
          this.$emit('reload');
          this.modify.isActive = false;
        });
      }).catch((err) => {
        notification.error(err, '댓글 수정실패\n다시 시도해 주세요');
      });
    },

    remove() {

    },
  },
};
</script>
