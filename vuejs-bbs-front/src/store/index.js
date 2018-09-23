import Vue from 'vue';
import Vuex from 'vuex';
import qstr from 'query-string';
import jwtDecode from 'jsonwebtoken/decode';

import { setAxiosHeader } from '../lib/axios-option';

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    user: null,
    token: null,
  },
  getters: {
    user(state) {
      return state.user;
    },
    username(state) {
      return state.token.user_name;
    },
  },
  mutations: {
    SET_USER(state, payload) {
      state.user = payload;
      state.token = payload ? jwtDecode(payload.access_token) : null;
      localStorage.user = qstr.stringify(payload);
      setAxiosHeader(payload);
    },
  },
  actions: {
    SET_USER({ commit }, payload) {
      commit('SET_USER', payload);
    },
  },
});

export default store;
