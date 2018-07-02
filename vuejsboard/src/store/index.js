import Vue from 'vue';
import Vuex from 'vuex';
import qstr from 'query-string';

import { setAxiosHeader } from '../lib/axios-option';

Vue.use(Vuex);

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
