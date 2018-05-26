import Vue from 'vue';
import Router from 'vue-router';
import SignUp from '@/components/SignUp';
import SignOut from '@/components/SignOut';
import SignIn from '@/components/SignIn';
import Home from '@/components/Home';
import Users from '@/components/Users';
import Check from '@/components/CheckEmail';
import Menu from '@/components/Menu';

Vue.use(Router);
export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home,
    },
    {
      path: '/signup',
      name: 'SignUp',
      component: SignUp,
    },
    {
      path: '/signin',
      name: 'SignIn',
      component: SignIn,
    },
    {
      path: '/menu',
      name: 'Menu',
      component: Menu,
    },
    {
      path: '/users',
      name: 'Users',
      component: Users,
    },
    {
      path: '/check',
      name: 'Check',
      component: Check,
    },
    {
      path: '/signOut',
      name: 'SignOut',
      component: SignOut,
    },

  ],
});
