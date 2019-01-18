import vue from '../main';
import store from '../store';

export default {
  isAuthenticated: (to, from, next) => {
    if (store.state.authentication === null) {
      if (vue) {
        vue.$notify({
          group: 'noti',
          type: 'error',
          text: '접근권한이 없습니다',
        });
      }
      return;
    }
    next();
  },
};
