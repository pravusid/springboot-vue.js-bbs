<template>
  <div>
    <nav class="teal">
      <div class="nav-wrapper container teal">
        <router-link to="/" class="brand-logo">IDPRAVUS</router-link>
        <ul id="nav-mobile" class="right hide-on-med-and-down">
          <li><router-link to="/board">게시판</router-link></li>
          <li><router-link to="/user">회원 목록</router-link></li>
          <li v-if="!user"><router-link to="/signup">회원 가입</router-link></li>
          <li v-if="!user"><router-link to="/login">로그인</router-link></li>
          <li v-if="user"><router-link to="/user/modify">정보수정</router-link></li>
          <li v-if="user"><router-link to="javascript:logout()">로그아웃</router-link></li>
          <li>{{this.user}}</li>
        </ul>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  data() {
    return {
      user: this.$store.getters.user,
    };
  },
};
</script>
