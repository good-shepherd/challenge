// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import jQuery from 'jquery';
import 'bootstrap';
import 'bootstrap/dist/css/bootstrap.css';
import Vue from 'vue';
import BootstrapVue from 'bootstrap-vue';
import axios from 'axios';
import moment from 'moment';
import VueMomentJs from 'vue-momentjs';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import App from './App';
import router from './router';
import store from './store';

Vue.use(BootstrapVue, jQuery, VueMomentJs, moment);

Vue.config.productionTip = false;
Vue.prototype.$http = axios;
/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  // $http,
  components: { App },
  template: '<App/>',
});
