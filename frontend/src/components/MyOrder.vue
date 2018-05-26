<template>
  <b-container fluid>
    <!-- User Interface controls -->


    <b-table show-empty
             stacked="md"
             :items="items"
             :current-page="currentPage"
             :per-page="perPage"

    >
      <template slot="add" slot-scope="row">


        <input type="checkbox" v-model="selected" :value="row.item" >


      </template>
      <template slot="name" slot-scope="row">{{row.value}}</template>


    </b-table>


    <b-list-group title="주문 목록">
      <b-list-group-item v-for="item in selected" :key="item.id" class="d-flex justify-content-between align-items-center">
        <b-col sm="8">
          {{item.name}}
        </b-col>
        <b-col sm="4">
          <button @click.stop="item.quantity--"> - </button>
          <b-badge variant="primary" pill>{{item.quantity}}</b-badge>        <button  @click.stop="item.quantity++"> + </button>
        </b-col>
      </b-list-group-item>
    </b-list-group>

    <!--<b-row :v-for="orderItem in selected">-->
      <!--<b-col md="9" class="my1">-->
        <!--{{orderItem.name}}-->
      <!--</b-col>-->
      <!--<b-col md="3" class="my1">-->
        <!--{{orderItem.num}}-->
      <!--</b-col>-->

    <!--</b-row>-->

  </b-container>

</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      items: [],
      id: '',
      name: '',
      price: '',
      image: '',
      quantity: 0,
      selected: [],
      productId: [],
      quantities: [],
      newArray: new Map(),
      isActive: true,
      fields: [
        { key: 'add', label: '선택' },
        { key: 'updatedTime', label: '주문일시', sortable: true, sortDirection: 'desc' },
        { key: 'sumOfPrice', label: '총 가격', sortable: true, class: 'text-center' },
        { key: 'done', label: '완료 여부', sortable: true, class: 'text-center' },
      ],
      currentPage: 1,
      perPage: 10,
      pageOptions: [5, 10, 15],
    };
  },
  created() {
    const token = localStorage.getItem('token');
    const auth = {
      headers: { Authorization: 'Bearer '.concat(token) } };
    axios.get('http://192.168.0.32:8080/api/orders/me', auth).then((response) => {
      console.log(response);
      this.items = response.data;
    });
    console.log(this.items);
  },
  methods: {
  },
};
/* const token = localStorage.getItem('token');
       const auth = {
         headers: { Authorization: 'Bearer '.concat(token) } };
       axios.get('http://192.168.0.32:8080/api/products/menu?page='.concat(0).concat('&size=').concat(100), auth).then((response) => {
         console.log(response);
         this.items = response.data;
       }); */
</script>

<style scoped>

</style>
