<template>
  <b-container fluid>
    <!-- User Interface controls -->
    <b-row>
      <b-col md="6" class="my-1">
        <b-form-group horizontal label="" class="mb-0">
          <b-input-group>
            <b-form-input v-model="filter" placeholder="검색어를 입력하세요"/>
            <b-input-group-append>
              <b-btn :disabled="!filter" @click="filter = ''">초기화</b-btn>
            </b-input-group-append>
          </b-input-group>
        </b-form-group>
      </b-col>
    </b-row>

    <b-table show-empty
             stacked="md"
             :items="items"
             :fields="fields"
             :current-page="currentPage"
             :per-page="perPage"
             :filter="filter"

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
          <button @click.stop="subMenu(item)"> - </button>
          <b-badge variant="primary" pill>{{item.quantity}}</b-badge>        <button  @click.stop="addMenu(item)"> + </button>
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
    <b-row>
      <b-col md="6" class="my-1">
        합계: {{sum}}원
      </b-col>
      <b-col md="6" class="my-1">
        <b-button size="sm" @click.stop="order" class="mr-1">
          주문하기
        </b-button>
      </b-col>
    </b-row>
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
      sum: 0,
      selected: [],
      productId: [],
      quantities: [],
      newArray: new Map(),
      filter: '',
      isActive: true,
      fields: [
        { key: 'add', label: '선택' },
        { key: 'name', label: '메뉴 이름', sortable: true, sortDirection: 'desc' },
        { key: 'price', label: '가격', sortable: true, class: 'text-center' },
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
    axios.get('http://192.168.0.32:8080/api/products/menu?page='.concat(0).concat('&size=').concat(100), auth).then((response) => {
      console.log(response);
      this.items = response.data;
    });
    console.log(this.items);
  },
  methods: {
    order() {
      for (let i = 0, len = this.selected.length; i < len; i++) {
        this.productId.push(this.selected[i].id.toString());
        this.quantities.push(this.selected[i].quantity.toString());
      }
      const token = localStorage.getItem('token');
      const auth = {
        headers: { Authorization: 'Bearer '.concat(token) } };
      axios.post('http://192.168.0.32:8080/api/orders/', {
        productId: this.productId,
        quantity: this.quantities,
      }, auth).then((response) => {
        if (response.status === 200) {
          alert('주문 완료');
          this.$router.push('myOrder');
        }
      }).catch((error) => {
        console.log(error);
      });
    },
    subMenu(item) {
      if (item.quantity > 0) {
        item.quantity--;
      }
      this.sum = 0;
      for (let i = 0; i < this.selected.length; i++ ) {
        this.sum += (this.selected[i].quantity * this.selected[i].price);
      }
    },
    addMenu(item) {
      item.quantity++;
      this.sum = 0;
      for (let i = 0; i < this.selected.length; i++ ) {
        this.sum += (this.selected[i].quantity * this.selected[i].price);
      }
    },
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
