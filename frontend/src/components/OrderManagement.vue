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

    >
      <template slot="name" slot-scope="row">{{row.value}}</template>
      <template slot="actions" slot-scope="row">
        <b-button size="sm" @click.stop="updateOrder(row.item)" class="mr-1">
          수정
        </b-button>
      </template>

    </b-table>


    <b-modal ref="updateModal" hide-footer title="메뉴 수정하기">
      <div class="d-block text-center">
        <b-row class="my-1">
          <b-col sm="3"><label v-mode="name" :for="name">메뉴 이름 :</label></b-col>
          <b-col sm="9">
            <b-form-input type="text" v-model="name"></b-form-input>
          </b-col>
        </b-row>
        <b-row class="my-1">
          <b-col sm="3"><label v-mode="price" :for="price">가격 :</label></b-col>
          <b-col sm="9">
            <b-form-input type="number" v-model="price"></b-form-input>
          </b-col>
        </b-row>
        <b-row class="my-1">
          <b-col sm="3">
            <span>판매 여부</span>
          </b-col>
          <b-col sm="9">
            <b-form-group>
              <b-form-radio-group id="radios" v-model="menu" name="radios">
                <b-form-radio value="true">판매 중</b-form-radio>
                <b-form-radio value="false">미판매</b-form-radio>
              </b-form-radio-group>
            </b-form-group>
          </b-col>
        </b-row>
        <!--<div v-if="!image">
          <b-row class="my-1">

            <b-col sm="3">
              <label v-mode="image" :for="image">사진 :</label>
            </b-col>
            <b-col sm="9">
              <b-form-file class="mt-auto" :id="image" v-model="image" :state="Boolean(image)" @change="onFileChange"
                           placeholder="이미지를 선택하세요"></b-form-file>
            </b-col>

          </b-row>

        </div>
        <div v-else>
          <b-col sm="12">
            <b-img :src="image" fluid alt="Fluid image"/>
          </b-col>
        </div>-->
        <!--
                <b-btn class="mt-auto" @click="removeImage">초기화</b-btn>
        -->


      </div>
      <b-btn class="mt-3" variant="outline" block @click.stop="onUpdate">수정</b-btn>
      <b-btn class="mt-3" variant="outline-danger" block @click.stop="hideUpdateModal">취소</b-btn>
    </b-modal>

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
      headers: { Authorization: 'Bearer '.concat(token) } };
    axios.get('http://192.168.0.32:8080/api/orders', auth).then((response) => {
      console.log(response);
      this.items = response.data;
    });
    console.log(this.items);
  },
  methods: {
    updateOrder(item){
      this.done = item.done;
      this.$refs.updateModal.show();

    },
  },
};

</script>

<style scoped>

</style>
