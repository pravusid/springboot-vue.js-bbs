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
          <li v-if="loggedIn"><a @click="logout">로그아웃</a></li>
        </ul>
      </div>
    </nav>
  </div>
</template>

<script>
import qstr from 'querystring';

export default {
  data() {
    return {
      loggedIn: false,
      params: {
        response_type: 'token',
        client_id: 'vueclient',
        redirect_uri: 'http://localhost:3000/login?success',
      },
      originHost: 'localhost:3000',
    };
  },
  mounted() {
    const { user } = localStorage;
    if (user) {
      this.$store.dispatch('setuser', qstr.parse(user));
      this.loggedIn = true;
    }
  },
  methods: {
    login() {
      const url = `http://localhost:8080/oauth/authorize?${qstr.stringify(this.params)}`;
      const options = {
        width: 600,
        height: 600,
      };

      const popup = window.open(url, 'auth', qstr.stringify(options, ','));

      this.popupWatcher(popup, this.originHost).then((param) => {
        this.loggedIn = true;
        this.$store.dispatch('setuser', param);
      });
    },
    popupWatcher(popup, exitUrl) {
      return new Promise((resolve, reject) => {
        const polling = setInterval(() => {
          if (!popup || popup.closed || popup === undefined) {
            clearInterval(polling);
            reject(new Error('로그인 윈도우 종료'));
          }
          try {
            if (popup.location.host === exitUrl) {
              const hash = qstr.parse(popup.location.hash.substring(1));
              if (hash.error) {
                reject(new Error(hash.error));
              } else {
                resolve(hash);
              }
              clearInterval(polling);
              popup.close();
            }
          } catch (error) {
            // cross origin frame exception
          }
        }, 500);
      });
    },
    logout() {
      this.loggedIn = false;
      this.$store.dispatch('setuser', null);
    },
  },
};
</script>
