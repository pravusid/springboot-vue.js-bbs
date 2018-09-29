<template>
  <div id="comments">
    <div class="section">
      <h5>
        댓글&nbsp;<small>(<span>{{ numberOfComments }}</span>)</small>
      </h5>

      <div :id="comment.id" class="row" v-for="comment in comments" :key="comment.id">
        <div :class="'col s' + comment.replyDepth"></div>
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

              <a href="javascript:" class="replyBtn" th:value="${comment.id}">대댓글</a>

              <a href="javascript:" class="modBtn" th:value="${comment.id}">수정</a>
              <form class="inline" th:id="|del${comment.id}|" method="post">
                <input type="hidden" name="_method" value="delete">
                <input type="hidden" name="id" th:value="${comment.id}">
                <input type="hidden" name="query" th:value="${query}">
                <a href="javascript:" class="deleteBtn" th:value="${comment.id}">삭제</a>
              </form>

            </div>
          </div>
        </div>
        <div :class="'col s' + comment.replyDepth"></div>
        <div :class="'col s' + (13 - comment.replyDepth)">
          <div class="row">
            <!-- 수정 -->
            <div :class="'col s12 hide'">
              <form method="post">
                <input type="hidden" name="_method" value="put">
                <input type="hidden" name="id" th:value="${comment.id}">
                <input type="hidden" name="query" th:value="${query}">
                <textarea class="materialize-textarea col s10" name="content"
                    th:text="${comment.content}"></textarea>
                <button class="btn col s2">댓글수정</button>
              </form>
            </div>
            <!-- 대댓글 -->
            <div class="col s12 hide">
              <form method="post" th:action="|/board/${detail.id}/comment|">
                <input type="hidden" name="query" th:value="${query}">
                <input type="hidden" name="replyRoot" th:value="${comment.replyRoot}">
                <input type="hidden" name="replyDepth" th:value="${comment.replyDepth}">
                <input type="hidden" name="replyOrder" th:value="${comment.replyOrder}">
                <textarea class="materialize-textarea col s10" name="content"
                    placeholder="대댓글을 입력하세요"></textarea>
                <a class="btn col s2">대댓글작성</a>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="section row">
      <textarea class="materialize-textarea col s10" v-model="comment.content"></textarea>
      <a class="btn col s2" @click="writeComment">댓글작성</a>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  props: {
    id: Number,
    comments: Array,
  },

  data: () => ({
    comment: {},
  }),

  methods: {
    writeComment() {
      axios.post(`/api/v1/board/${this.id}/comment`, this.comment)
        .then((res) => {
          if (res.status === 200) {
            this.$notify({
              group: 'noti',
              type: 'success',
              text: '댓글이 등록되었습니다.',
            });
          }
          // FIXME: 댓글만 받아서 갱신
          this.$emit('reload');
          this.comment = {};
        }).catch(() => {
          this.$notify({
            group: 'noti',
            type: 'error',
            text: '댓글 등록중 오류 발생',
          });
        });
    },
  },

  computed: {
    numberOfComments() {
      return this.comments ? this.comments.length : 0;
    },
  },
};
</script>
