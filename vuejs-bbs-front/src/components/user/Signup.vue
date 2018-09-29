<template>
  <div class="container">
    <div class="row">
      <h1>회원 가입</h1>
    </div>
    <div class="row">
      <div class="row">
        <div class="input-field col s12">
          <input id="username" v-model="user.username"
            type="text" class="validate" placeholder="사용자 아이디">
        </div>
      </div>
      <div class="row">
        <div class="input-field col s12">
          <input id="password" v-model="user.password" type="password"
            class="validate" placeholder="비밀번호">
        </div>
      </div>
      <div class="row">
        <div class="input-field col s12">
          <input id="passwordre" v-model="user.confirmpassword" type="password"
            class="validate" placeholder="비밀번호 확인">
        </div>
      </div>
      <div class="row">
        <div class="input-field col s12">
          <input id="name" v-model="user.name" type="text" class="validate"
            placeholder="이름">
        </div>
      </div>
      <div class="row">
        <div class="input-field col s12">
          <input id="email" v-model="user.email" type="email" class="validate"
            placeholder="이메일">
        </div>
      </div>
      <a class="btn waves-effect waves-light" @click="signup">회원가입
        <i class="material-icons right">send</i>
      </a>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import notification from '../../lib/notification';

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
    signup() {
      axios.post('/api/v1/user/signup', this.user)
        .then((res) => {
          notification.success(res, '가입성공', () => {
            this.$router.push('/');
          });
        }).catch((err) => {
          err.response.data.errors.forEach((error) => {
            this.$notify({
              group: 'noti',
              type: 'error',
              duration: 6000,
              text: error.defaultMessage,
            });
          });
        });
    },
  },
};
</script>
