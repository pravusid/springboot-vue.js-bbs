<template>
  <div>
    <nav class="teal">
      <div class="nav-wrapper container teal">
        <router-link to="/" class="brand-logo">IDPRAVUS</router-link>
        <ul id="nav-mobile" class="right hide-on-med-and-down">
          <li><router-link to="/board">게시판</router-link></li>
          <li><router-link to="/user">회원 목록</router-link></li>
          <li v-if="!loggedIn"><router-link to="/signup">회원 가입</router-link></li>
          <li v-if="!loggedIn"><a @click="login">로그인</a></li>
          <li v-if="loggedIn"><router-link to="/user/modify">정보수정</router-link></li>
          <li v-if="loggedIn"><router-link to="javascript:logout()">로그아웃</router-link></li>
        </ul>
      </div>
    </nav>
  </div>
</template>

<script>
import qstr from 'querystring';
import axios from 'axios';

export default {
  data() {
    return {
      loggedIn: false,
      params: {
        response_type: 'token',
        client_id: 'vueclient',
        redirect_uri: 'http://localhost:3000',
      },
    };
  },
  computed: {
    redirectUri() {
      return this.params.redirect_uri.replace('http://', '').replace('https://', '');
    },
  },
  methods: {
    login() {
      const url = `http://localhost:8080/oauth/authorize?${qstr.stringify(this.params)}`;
      const options = {
        width: 600,
        height: 500,
      };

      const popup = window.open(url, 'auth', qstr.stringify(options, ','));

      this.popupWatcher(popup).then((param) => {
        this.$store.dispatch('setuser', param);
        this.loggedIn = true;
        axios.defaults.headers.common.Authorization = `bearer ${param.access_token}`;
      });
    },
    popupWatcher(window) {
      return new Promise((resolve, reject) => {
        const polling = setInterval(() => {
          if (!window || window.closed || window.closed === undefined) {
            clearInterval(polling);
            reject(new Error('로그인 윈도우 종료'));
          }
          if (window.location.host === this.redirectUri) {
            const hash = qstr.parse(window.location.hash.substring(1).replace(/[/$]/, ''));
            if (hash.error) {
              reject(new Error(hash.error));
            } else {
              resolve(hash);
            }
            clearInterval(polling);
            window.close();
          }
        }, 500);
      });
    },
  },
};
</script>
