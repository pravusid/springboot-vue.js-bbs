import Vue from 'vue';
import Vuex from 'vuex';

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
    },
  },
  actions: {
    setuser({ commit }, payload) {
      commit('setuser', payload);
    },
  },
});

export default store;
