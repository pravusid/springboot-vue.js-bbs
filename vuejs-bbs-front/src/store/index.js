import Vue from 'vue';
import Vuex from 'vuex';
import qstr from 'query-string';
import jwtDecode from 'jsonwebtoken/decode';

import { setHeader } from '../libs/axios.custom';

Vue.use(Vuex);

const debug = process.env.VUE_APP_ENV === 'development';

const initUser = (store) => {
  const { user } = localStorage;
  if (user) {
    store.commit('setUser', qstr.parse(user));
  }
};

const store = new Vuex.Store({
  plugins: [initUser],

  state: {
    authentication: null,
    token: null,
    userDetail: null,
  },

  getters: {
    username(state) {
      return state.token ? state.token.user_name : null;
    },
  },

  mutations: {
    setUser(state, payload) {
      state.authentication = payload;
      state.token = payload ? jwtDecode(payload.access_token) : null;
      localStorage.user = qstr.stringify(payload);
      setHeader(payload);
    },

    setUserDetail(state, payload) {
      state.userDetail = payload;
    },
  },

  actions: {
  },

  strict: debug,
});

export default store;
