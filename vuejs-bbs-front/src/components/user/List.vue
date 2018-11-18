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
import axios from '../../libs/axios.custom';

export default {
  async created() {
    const res = await axios.get('/api/v1/user');
    this.users = res.data;
  },

  data: () => ({
    users: [],
  }),
};
</script>
