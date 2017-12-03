import Vue from 'vue';
import Router from 'vue-router';
import List from '../components/board/List.vue';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Board/List',
      component: List,
    },
  ],
});
