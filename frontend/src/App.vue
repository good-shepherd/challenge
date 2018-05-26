<template>
  <div id="app">
    <nav class="topnav">
      <a href="#">
        <router-link to="/">Midas Cafe</router-link>
      </a>

      <div v-if="this.$store.state.token !== 0">
        <a href="#">
          <router-link to="order">예약 주문</router-link>
        </a>
      </div>
      <div v-if="this.$store.state.token !== 0">
        <a href="#">
          <router-link to="myOrder">나의 예약</router-link>
        </a>
      </div>
      <div v-if="this.$store.state.token !== 0">
        <a href="#">
          <router-link to="monthlyOrder">월별 예약</router-link>
        </a>
      </div>
      <div v-if="this.$store.state.token !== 0  && (this.$store.state.role == 'ROLE_MASTER' || this.$store.state.role == 'ROLE_ADMIN' )">
        <a href="#">
          <router-link to="orderManagement">예약 관리</router-link>
        </a>
      </div>
      <div v-if="this.$store.state.token !== 0  && (this.$store.state.role == 'ROLE_MASTER' || this.$store.state.role == 'ROLE_ADMIN' )">
        <a href="#">
          <router-link to="menu">메뉴관리</router-link>
        </a>
      </div>
      <div v-if="this.$store.state.token !== 0  && (this.$store.state.role == 'ROLE_MASTER' || this.$store.state.role == 'ROLE_ADMIN' )">
        <a href="#">
          <router-link to="users">회원관리</router-link>
        </a>
      </div>

      <div v-if="this.$store.state.token === 0">
        <a href="#">
          <router-link to="signin">로그인</router-link>
        </a>
      </div>
      <div v-else>
        <a href="#">
          <router-link to="signout">로그아웃</router-link>
        </a>
      </div>
      <div v-if="this.$store.state.token === 0">
      <a href="#">
        <router-link to="signup">가입하기</router-link>
      </a>
      </div>

    </nav>
    <div class="main">
      <router-view/>
    </div>
  </div>
</template>

<script>
export default {
  name: 'App',
  computed: {
    useremail() {
      const email = this.$store.state.useremail;
      if (email !== '') {
        return email;
      }
      return '로그인하세요';
    },
  },
  method: {
    onClickLogout() {
      // LOGOUT 변이 실행 후 리다이렉트

    },
  },
};
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
  }

  .topnav {
    overflow: hidden;
    background-color: #333;

    position: -webkit-sticky; /* Safari */
    position: sticky;
    text-align: right;
  }

  .topnav a {
    float: left;
    color: #f2f2f2;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
    font-size: 17px;
  }

  .topnav a:hover {
    background-color: #ddd;
    color: black;
  }

  .topnav a.active {
    background-color: #4CAF50;
    color: white;
  }

  .sidenav {
    height: 100%;
    width: 200px;
    position: fixed;
    z-index: 1;
    left: 0;
    background-color: #111;
    overflow-x: hidden;
    padding-top: 20px;
  }

  .sidenav a {
    padding: 6px 6px 6px 32px;
    text-decoration: none;
    font-size: 25px;
    color: #818181;
    display: block;
  }

  .sidenav a:hover {
    color: #f1f1f1;
  }

  .main {
    padding: 20px;
  }

  @media screen and (max-height: 450px) {
    .sidenav {
      padding-top: 15px;
    }

    .sidenav a {
      font-size: 18px;
    }
  }
</style>
