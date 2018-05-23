import Vue from 'vue';
import Router from 'vue-router';
import SignUp from '@/components/SignUp';
import SignIn from '@/components/SignIn';
import HelloWorld from '@/components/HelloWorld';
import Blog from '@/components/Blog';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: HelloWorld,
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
      path: '/blog',
      name: 'Blog',
      component: Blog,
    },
  ],
});
