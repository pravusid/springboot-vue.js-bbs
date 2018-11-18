// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import Notifications from 'vue-notification';
import VueScrollTo from 'vue-scrollto';

import App from './App.vue';

import router from './routes';
import store from './store';

Vue.use(Notifications);
Vue.use(VueScrollTo);

Vue.config.productionTip = false;

/* eslint-disable no-new */
export default new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app');
