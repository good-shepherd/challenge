<template>
  <b-container fluid>
    <!-- User Interface controls -->
    <b-row>
      <b-col sm="5">
        <b-button @click="prevMonth"> 이전</b-button>
      </b-col>
      <b-col sm="2">
        {{this.year}}년 {{this.month}}월 구매내역
      </b-col>
      <b-col sm="5">
        <b-button @click="nextMonth"> 다음</b-button>
      </b-col>
    </b-row>

    <b-table show-empty
             stacked="md"
             :items="items"
             :current-page="currentPage"
             :per-page="perPage"

    >
      <template slot="name" slot-scope="row">{{row.value}}</template>

    </b-table>

  </b-container>
</template>

<script>
import axios from 'axios';
import moment from 'moment';

export default {
  created() {
    this.month = 5;
    this.year = 2018;
    const token = localStorage.getItem('token');
    const auth = {
      headers: { Authorization: 'Bearer '.concat(token) },
    };
    axios.get('http://192.168.0.32:8080/api/users/'.concat(this.$store.state.id).concat('/orders/').concat(this.year).concat('/')
      .concat(this.month), auth).then((response) => {
      console.log(response);
      this.items = response.data;
    });
    console.log(this.items);
  },
  data() {
    return {
      items: [],
      month: 0,
      year: 0,
    };
  },
  methods: {
    prevMonth() {
      if(this.month>1){
        this.month--;

      }else{
        this.year--;
        this.month = 12;
      }
      const token = localStorage.getItem('token');
      const auth = {
        headers: { Authorization: 'Bearer '.concat(token) },
      };
      axios.get('http://192.168.0.32:8080/api/users/'.concat(this.$store.state.id).concat('/orders/').concat(this.year).concat('/')
        .concat(this.month), auth).then((response) => {
        console.log(response);
        this.items = response.data;
      });
    },
    nextMonth(){
      if(this.month>11){
        this.year++;
        this.month=1;
      }else{
        this.month++;
      }
      const token = localStorage.getItem('token');
      const auth = {
        headers: { Authorization: 'Bearer '.concat(token) },
      };
      axios.get('http://192.168.0.32:8080/api/users/'.concat(this.$store.state.id).concat('/orders/').concat(this.year).concat('/')
        .concat(this.month), auth).then((response) => {
        console.log(response);
        this.items = response.data;
      });
    }
  },
};
</script>

<style scoped>

</style>
