import Vue from 'vue';
import Vuex from 'vuex';
import qstr from 'query-string';
import jwtDecode from 'jsonwebtoken/decode';

import { setHeader } from '../libs/axios.custom';

Vue.use(Vuex);

const debug = process.env.VUE_APP_ENV === 'development';

export default new Vuex.Store({
  state: {
    user: null,
    token: null,
    userDetail: null,
  },

  getters: {
    user(state) {
      return state.user;
    },

    username(state) {
      return state.token ? state.token.user_name : null;
    },

    userDetail(state) {
      return state.userDetail;
    },
  },

  mutations: {
    setUser(state, payload) {
      state.user = payload;
      state.token = payload ? jwtDecode(payload.access_token) : null;
      localStorage.user = qstr.stringify(payload);
      setHeader(payload);
    },

    setUserDetail(state, payload) {
      state.userDetail = payload;
    },
  },

  actions: {
    setUser({ commit }, payload) {
      commit('setUser', payload);
    },

    setUserDetail({ commit }, payload) {
      commit('setUserDetail', payload);
    },
  },

  strict: debug,
});
