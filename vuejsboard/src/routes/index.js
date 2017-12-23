import List from '../components/board/List.vue';
import UserList from '../components/user/List.vue';
import Login from '../components/user/Login.vue';
import Signup from '../components/user/Signup.vue';
import UserDetail from '../components/user/Detail.vue';

export default [
  {
    path: '/',
    name: 'Board/List',
    component: List,
  },
  {
    path: '/board',
    name: 'Board/List',
    component: List,
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
