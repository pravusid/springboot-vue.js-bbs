<template>
  <div class="container">
    <div class="row">
      <h1>게시물 작성</h1>
    </div>
    <div class="row">
      <div class="input-field">
        <i class="material-icons prefix">account_circle</i>
        <input id="name" name="name" type="text" class="validate"
          :value="name" placeholder="작성자" disabled>
      </div>
    </div>
    <div class="row">
      <div class="input-field">
        <i class="material-icons prefix">subject</i>
        <input id="title" name="title" type="text" class="validate"
          v-model="title" placeholder="제목">
      </div>
    </div>
    <div class="row">
      <div class="col s12">
        <quill-editor v-model="content"
            ref="myQuillEditor"
            :options="editorOption"
            @blur="onEditorBlur($event)"
            @focus="onEditorFocus($event)"
            @ready="onEditorReady($event)">
        </quill-editor>
      </div>
    </div>
    <div class="row center">
      <button class="btn" @click="write">작성 완료</button>&nbsp;&nbsp;
      <button class="btn grey" @click="cancel">취소</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { quillEditor } from 'vue-quill-editor';
import 'quill/dist/quill.core.css';
import 'quill/dist/quill.snow.css';
import notification from '../../lib/notification';

export default {
  components: {
    quillEditor,
  },

  created() {
    this.name = this.$store.getters.userDetail.name;
  },

  data: () => ({
    name: '',
    title: '',
    content: '',
    editorOption: {
      placeholder: '여기에 내용을 입력해주세요',
      theme: 'snow',
    },
    actions: [],
  }),

  methods: {
    write() {
      axios.post('/api/v1/board', {
        name: this.name,
        title: this.title,
        content: this.content,
      }).then((res) => {
        notification.success(res, '게시물이 등록되었습니다', () => {
          this.$router.push('/');
        });
      }).catch((err) => {
        notification.error(err, '게시물 등록 실패!\n다시 시도해주세요');
      });
    },

    cancel() {
      this.$router.go(-1);
    },

    onEditorBlur(quill) {
      this.actions.push(quill);
    },

    onEditorFocus(quill) {
      this.actions.push(quill);
    },

    onEditorReady(quill) {
      this.actions.push(quill);
    },

    onEditorChange({ quill, html }) {
      this.content = html;
      this.actions.push(quill);
    },
  },

  computed: {
    editor() {
      return this.$refs.myQuillEditor.quill;
    },
  },
};
</script>

<style scoped>
.quill-editor {
  border: none;
  height: auto;
}
</style>
