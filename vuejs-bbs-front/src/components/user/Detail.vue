<template>
  <div class="container">
    <div class="row">
      <h1>회원 정보 수정</h1>
    </div>
    <div class="row">
      <table>
        <thead>
        <tr>
          <th>아이디</th>
          <th>비밀번호</th>
          <th>이름</th>
          <th>이메일</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>
            <input type="text" name="username" :value="detail.username" disabled>
          </td>
          <td>
            <input type="password" name="password" v-model="detail.password">
          </td>
          <td>
            <input type="text" name="name" v-model="detail.name">
          </td>
          <td>
            <input type="email" name="email" v-model="detail.email">
          </td>
        </tr>
        </tbody>
      </table>
    </div>
    <div class="row center">
      <a class="btn" @click="modify">정보 수정</a>
    </div>
  </div>
</template>

<script>
import axios from '../../libs/axios.custom';
import notification from '../../libs/notification';

export default {
  async created() {
    const username = this.$store.getters.username;
    const res = await axios.get(`/api/v1/user/${username}`);
    this.detail = res.data;
  },

  data: () => ({
    detail: {},
  }),

  methods: {
    async modify() {
      try {
        const username = this.$store.getters.username;
        if (this.detail.password) this.detail.password = '';
        const res = await axios.put(`/api/v1/user/${username}`, this.detail);
        notification.success(res, '회원정보가 수정되었습니다', () => {
          this.$router.push('/user');
        });
      } catch (err) {
        notification.error(err, '회원정보 수정중 오류 발생');
      }
    },
  },
};
</script>
