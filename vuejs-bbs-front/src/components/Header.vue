<template>
  <div>
    <nav class="teal">
      <div class="nav-wrapper container teal">
        <router-link to="/" class="brand-logo">IDPRAVUS</router-link>
        <ul id="nav-mobile" class="right hide-on-med-and-down">
          <li><router-link to="/board?page=0">게시판</router-link></li>
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
import qstr from 'query-string';
import axios from '../libs/axios.custom';

export default {
  async created() {
    await this.getUserDetails();
  },

  data: () => ({
    loggedIn: false,
    params: {
      response_type: 'token',
      client_id: 'vueclient',
      redirect_uri: `${process.env.VUE_APP_ORIGIN}/login?success`,
    },
    originHost: process.env.VUE_APP_ORIGIN,
  }),

  methods: {
    async login() {
      const url = `${process.env.VUE_APP_API}/oauth/authorize?${qstr.stringify(this.params)}`;
      const options = 'width=600, height=600';
      const popup = window.open(url, 'auth', options);

      const param = await this.popupWatcher(popup, this.originHost);
      await this.$store.commit('setUser', param);
      await this.getUserDetails();
    },

    popupWatcher(popup, exitUrl) {
      const parseUrl = document.createElement('a');
      parseUrl.href = exitUrl;

      return new Promise((resolve, reject) => {
        const polling = setInterval(() => {
          if (!popup || popup.closed || popup === undefined) {
            clearInterval(polling);
            reject(new Error('로그인 창 종료됨'));
          }

          try {
            if (popup.location.host === parseUrl.host) {
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
        }, 250);
      });
    },

    async getUserDetails() {
      try {
        const username = this.$store.getters.username;
        if (username === null) return;

        const res = await axios.get(`/api/v1/user/${username}`);
        if (res.status === 200) {
          this.$store.commit('setUserDetail', res.data);
          this.loggedIn = true;
        }
      } catch (err) {
        if (err.response.status === 401) {
          this.logout();
        }
      }
    },

    logout() {
      this.loggedIn = false;
      this.$store.commit('setUser', null);
      this.$store.commit('setUserDetail', null);
      this.$router.push('/');
    },
  },

};
</script>
