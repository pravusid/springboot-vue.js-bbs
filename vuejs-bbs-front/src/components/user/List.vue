<template>
  <div class="container">
    <div class="row">
      <h1>회원 목록</h1>
    </div>
    <div class="row">
      <table>
        <thead>
        <tr>
          <th>아이디</th>
          <th>이름</th>
          <th>이메일</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="user in users" v-bind:key="user.id">
          <td>{{ user.username }}</td>
          <td>{{ user.name }}</td>
          <td>{{ user.email }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  beforeMount() {
    axios.get('/api/v1/user').then((res) => {
      this.users = res.data;
    }).catch((err) => {
      if (err.response.status === 401) {
        this.$notify({
          group: 'noti',
          type: 'error',
          text: '접근권한이 없습니다',
        });
        this.$router.go(-1);
      }
    });
  },
  data: () => ({
    users: [],
  }),
};
</script>
