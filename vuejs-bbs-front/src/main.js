import Vue from 'vue';

import './plugins';

import router from './router';
import store from './store';
import App from './App.vue';

Vue.config.productionTip = false;

/* eslint-disable no-new */
const app = new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app');

export default app;
