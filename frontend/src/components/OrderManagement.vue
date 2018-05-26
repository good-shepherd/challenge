<template>
  <b-container fluid>
    <!-- User Interface controls -->

    <b-table show-empty
             stacked="md"
             :items="items"
             :fields="fields"
             :current-page="currentPage"
             :per-page="perPage"

    >
      <template slot="name" slot-scope="row">{{row.value}}</template>
      <template slot="done" slot-scope="row">{{row.value?'완료':'미완료'}}</template>

      <template slot="actions" slot-scope="row">
        <b-button size="sm" @click.stop="onUpdate(row.item)" class="mr-1">
          완료
        </b-button>
      </template>

    </b-table>
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
        { key: 'customerName', label: '고객명', sortable: true, sortDirection: 'desc' },
        { key: 'updatedTime', label: '주문일시', sortable: true, sortDirection: 'desc' },
        { key: 'sumOfPrice', label: '총 가격', sortable: true, class: 'text-center' },
        { key: 'done', label: '완료 여부', sortable: true, class: 'text-center' },
        { key: 'actions', label: '관리' },
      ],
      currentPage: 1,
      perPage: 10,
      pageOptions: [5, 10, 15],
    };
  },
  created() {
    const token = localStorage.getItem('token');
    const auth = {
      headers: { Authorization: 'Bearer '.concat(token) },
    };
    axios.get('http://192.168.0.32:8080/api/orders', auth).then((response) => {
      console.log(response);
      this.items = response.data;
    });
    console.log(this.items);
  },
  methods: {
    onUpdate(item) {
      const token = localStorage.getItem('token');
      console.log(token);
      const auth = {
        headers: { Authorization: 'Bearer '.concat(token) },
      };
      console.log(auth);
      axios.post('http://192.168.0.32:8080/api/orders/'.concat(item.orderId), auth).then((response) => {
        if (response.status === 200) {
          alert('수정 완료');
        } else if (response.status === 401) {
          alert('입력 값을 확인하세요');
        }
      }).catch((error) => {
        console.log(error);
      });
      axios.get('http://192.168.0.32:8080/api/orders', auth).then((response) => {
        console.log(response);
        this.items = response.data;
      });
      this.$refs.updateModal.hide();
    },
  },
};


</script>

<style scoped>

</style>
