<template>
  <div class="container">
    <div class="row">
      <h1>회원 가입</h1>
    </div>
    <div class="row">
      <div class="row">
        <div class="input-field col s12">
          <input id="username" v-model="user.username" type="text" class="validate">
          <label for="username">사용자 아이디</label>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s12">
          <input id="password" v-model="user.password" type="password" class="validate">
          <label for="password">비밀번호</label>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s12">
          <input id="passwordre" v-model="user.confirmpassword" type="password" class="validate">
          <label for="password">비밀번호확인</label>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s12">
          <input id="name" v-model="user.name" type="text" class="validate">
          <label for="name">이름</label>
        </div>
      </div>
      <div class="row">
        <div class="input-field col s12">
          <input id="email" v-model="user.email" type="email" class="validate">
          <label for="email">이메일</label>
        </div>
      </div>
      <button class="btn waves-effect waves-light" @click="signup">회원가입
        <i class="material-icons right">send</i>
      </button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {

  data: () => ({
    user: {
      username: null,
      password: null,
      confirmpassword: null,
      name: null,
      email: null,
    },
  }),

  methods: {
    validate() {

    },

    signup() {
      axios.post('/api/v1/user/signup', this.user)
        .then((res) => {
          if (res.status === 200) {
            this.$notify({
              group: 'noti',
              type: 'success',
              title: '',
              text: '가입성공',
            });
            this.$router.push('/');
          }
        }).catch(() => {
          console.log('가입실패');
        });
    },
  },

};
</script>
