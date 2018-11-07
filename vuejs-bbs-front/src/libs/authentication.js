import Vue from '../main';

export default {
  isAuthenticated: (to, from, next) => {
    if (Vue.$store.getters.user === null) {
      Vue.$notify({
        group: 'noti',
        type: 'error',
        text: '접근권한이 없습니다',
      });
    } else {
      next();
    }
  },
};
