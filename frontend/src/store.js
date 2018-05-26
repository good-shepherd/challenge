import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios';

Vue.use(Vuex);
let initialState = {
  token: 0,
  id: 0,
  name: '',
  email: '',
  role: '',
}

const state = Vue.util.extend({}, initialState)
const store = new Vuex.Store({
  state: {
    token: 0,
    id: 0,
    name: '',
    email: '',
    role: '',
  },
  mutations: {
    setToken(state, payload) {
      state.token = payload;
    },
    setUser(state, payload) {
      state.id = payload.id;
      state.username = payload.name;
      state.email = payload.email;
      state.role = payload.role;

    },
    LOGOUT (state) {
      // 토큰 정보 삭제
      state.token = 0;
    },
  },
  actions: {
    LOGOUT ({commit}) {
      // HTTP 요청 헤더값 제거
      axios.defaults.headers.common['Authorization'] = undefined
      commit('LOGOUT')
    },
    clear(state) {
      state.token = 0;
      state.id = 0;
      state.name = '';
      state.email = '';
      state.role = '';
    },
    getUserInfo({ commit, state }) {
      axios.get('/api/users/me', {
        headers: {
          Authorization: `Bearer ${state.token}`,
        },
      }).then((response) => {
        console.log(response);
        commit('setUser', response.data);
      });
    },
  },
});

export default store;
