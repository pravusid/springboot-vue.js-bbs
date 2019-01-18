import Vue from 'vue';
import Router from 'vue-router';

import auth from '../libs/authentication';

import BoardList from '../components/board/List.vue';
import BoardDetail from '../components/board/Detail.vue';
import BoardWrite from '../components/board/Write.vue';
import BoardModify from '../components/board/Modify.vue';
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
      redirect: '/board',
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
      beforeEnter: auth.isAuthenticated,
    },
    {
      path: '/board/:id',
      name: 'Board/Detail',
      component: BoardDetail,
      props: true,
    },
    {
      path: '/board/:id/modify',
      name: 'Board/Modify',
      component: BoardModify,
      props: true,
      beforeEnter: auth.isAuthenticated,
    },
    {
      path: '/user',
      name: 'User/List',
      component: UserList,
      beforeEnter: auth.isAuthenticated,
    },
    {
      path: '/user/modify',
      name: 'User/Modify',
      component: UserDetail,
      beforeEnter: auth.isAuthenticated,
    },
    {
      path: '/signup',
      name: 'User/Signup',
      component: Signup,
    },
  ],
});

export default router;
