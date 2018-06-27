import Vue from 'vue';
import Vuex from 'vuex';

import axios from 'axios';
import qstr from 'querystring';

Vue.use(Vuex);

const setAxiosHeader = (param) => {
  if (param) {
    axios.defaults.headers.common.Authorization = `${param.token_type} ${param.access_token}`;
  }
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
