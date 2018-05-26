<template>
  <div id="signin">
    <img src="@/assets/logo.png"/>
    <div id="signin-box">
      <form @submit.prevent="onSubmit(email, password)">
        <div>
          <input type="text" v-model="email" placeholder="Email Address">

        </div>
        <div>
          <input type="password" v-model="password" placeholder="Password">

        </div>
        <div>
          <input type="submit" value="Login">

        </div>
      </form>
    </div>
  </div>
</template>

<script>

import axios from 'axios';

export default {
  name: 'sign-in',
  data() {
    return {
      email: '',
      password: '',
    };
  },
  methods: {
    onSubmit(email, password) {
      axios.post('/api/auth/signin', {
        userEmail: this.email,
        password: this.password,
      }).then((response) => {
        if (response.status === 200) {
          alert('로그인 성공');
          this.$store.commit('setToken', response.data.accessToken);
          this.$store.dispatch('getUserInfo');
        } else if (response.status === 401) {
          alert('회원 정보를 확인하세요.');
        }
      }).catch((error) => {
        console.log(error);
      });
    },
  },
};
</script>

<style scoped>
  #signin{
    text-align: center
  }
  #signin-box{
    border: 1px solid;
    padding: 10px;
    box-shadow: 2px 3px #888888;
    margin: auto;
    width:300px;
  }
</style>
