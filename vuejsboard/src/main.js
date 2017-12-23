// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import Router from 'vue-router';
import Vuex from 'vuex';

import App from './App.vue';

import routes from './routes';
import vuexStore from './store';

Vue.config.productionTip = false;

Vue.use(Router);
Vue.use(Vuex);

const router = new Router({
  mode: 'history',
  routes,
});

const store = new Vuex.Store({
  vuexStore,
});

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App },
});
