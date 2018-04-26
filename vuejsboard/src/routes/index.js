import BoardList from '../components/board/List.vue';
import BoardWrite from '../components/board/Write.vue';
import UserList from '../components/user/List.vue';
import Login from '../components/user/Login.vue';
import Signup from '../components/user/Signup.vue';
import UserDetail from '../components/user/Detail.vue';

export default [
  {
    path: '/',
    name: 'Board/List',
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
    path: '/login',
    name: 'User/Login',
    component: Login,
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
];
