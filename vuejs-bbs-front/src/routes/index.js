import Vue from 'vue';
import Router from 'vue-router';

import BoardList from '../components/board/List.vue';
import BoardWrite from '../components/board/Write.vue';
import UserList from '../components/user/List.vue';
import Signup from '../components/user/Signup.vue';
import UserDetail from '../components/user/Detail.vue';

Vue.use(Router);

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: BoardList,
    },
    {
      path: '/board',
      name: 'Board/List',
      component: BoardList,
    },
    {
      path: '/board/write',
      name: 'Board/Write',
      component: BoardWrite,
    },
    {
      path: '/user',
      name: 'User/List',
      component: UserList,
    },
    {
      path: '/signup',
      name: 'User/Signup',
      component: Signup,
    },
    {
      path: '/user/modify',
      name: 'User/Modify',
      component: UserDetail,
    },
  ],
});

export default router;
