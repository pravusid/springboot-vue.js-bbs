import Vue from '../main';

export default {
  success(response, msg, block) {
    if (response.status === 200) {
      Vue.$notify({
        group: 'noti',
        type: 'success',
        text: msg,
      });
      block();
    }
  },

  error(error, msg, block) {
    if (error.response.status === 500) {
      Vue.$notify({
        group: 'noti',
        type: 'error',
        text: msg,
      });
      block();
    }
  },

  warn(msg) {
    Vue.$notify({
      group: 'noti',
      type: 'warning',
      text: msg,
    });
  },
};
