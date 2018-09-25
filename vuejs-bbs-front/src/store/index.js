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
    userDetail: null,
  },

  getters: {
    user(state) {
      return state.user;
    },

    username(state) {
      return (state.token) ? state.token.user_name : null;
    },

    userDetail(state) {
      return state.userDetail;
    },
  },

  mutations: {
    SET_USER(state, payload) {
      state.user = payload;
      state.token = payload ? jwtDecode(payload.access_token) : null;
      localStorage.user = qstr.stringify(payload);
      setAxiosHeader(payload);
    },

    SET_USER_DETAIL(state, payload) {
      state.userDetail = payload;
    },
  },

  actions: {
    SET_USER({ commit }, payload) {
      commit('SET_USER', payload);
    },

    SET_USER_DETAIL({ commit }, payload) {
      commit('SET_USER_DETAIL', payload);
    },
  },
});

export default store;
