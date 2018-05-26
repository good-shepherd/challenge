import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios';

Vue.use(Vuex);

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
    },
  },
  actions: {
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
