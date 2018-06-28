import Vue from 'vue';
import Vuex from 'vuex';

import axios from 'axios';
import qstr from 'query-string';

Vue.use(Vuex);

const setAxiosHeader = (param) => {
  const str = (param === null) ? null : `${param.token_type} ${param.access_token}`;
  axios.defaults.headers.common.Authorization = str;
};

const store = new Vuex.Store({
  state: {
    user: null,
  },
  getters: {
    user(state) {
      return state.user;
    },
  },
  mutations: {
    setuser(state, payload) {
      state.user = payload;
      localStorage.user = qstr.stringify(payload);
      setAxiosHeader(payload);
    },
  },
  actions: {
    setuser({ commit }, payload) {
      commit('setuser', payload);
    },
  },
});

export default store;
