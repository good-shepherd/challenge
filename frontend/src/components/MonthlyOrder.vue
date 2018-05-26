<template>
  <b-container fluid>
    <!-- User Interface controls -->
    <b-row>
      <b-col sm="5">
        <b-button @click="this.month"> 이전</b-button>
      </b-col>
      <b-col sm="2">
        {{this.year}}년 {{this.month}}월 구매내역
      </b-col>
      <b-col sm="5">
        <b-button @click="this.month++"> 다음</b-button>
      </b-col>
    </b-row>

    <b-table show-empty
             stacked="md"
             :items="items"
             :current-page="currentPage"
             :per-page="perPage"

    >
      <template slot="add" slot-scope="row">


        <input type="checkbox" v-model="selected" :value="row.item">


      </template>
      <template slot="name" slot-scope="row">{{row.value}}</template>


    </b-table>


    <b-list-group title="주문 목록">
      <b-list-group-item v-for="item in selected" :key="item.id"
                         class="d-flex justify-content-between align-items-center">
        <b-col sm="8">
          {{item.name}}
        </b-col>
        <b-col sm="4">
          <button @click.stop="item.quantity--"> -</button>
          <b-badge variant="primary" pill>{{item.quantity}}</b-badge>
          <button @click.stop="item.quantity++"> +</button>
        </b-col>
      </b-list-group-item>
    </b-list-group>
  </b-container>
</template>

<script>
import axios from 'axios';

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

    },
    nextMonth(){

    }
  },
};
</script>

<style scoped>

</style>
