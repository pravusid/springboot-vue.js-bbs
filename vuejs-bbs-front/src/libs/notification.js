import vue from '../main';

export default {
  success(response, msg, block) {
    if (response.status === 200) {
      vue.$notify({
        group: 'noti',
        type: 'success',
        text: msg,
      });
      block();
    }
  },

  error(error, msg, block) {
    if (error.response.status === 500) {
      vue.$notify({
        group: 'noti',
        type: 'error',
        text: msg,
      });
      block();
    }
  },

  warn(msg) {
    vue.$notify({
      group: 'noti',
      type: 'warning',
      text: msg,
    });
  },
};
