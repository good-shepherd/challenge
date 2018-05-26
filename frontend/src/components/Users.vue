<template>
  <b-container fluid>
    <b-row>
    </b-row>
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
      <b-col md="6" class="my-1">
        <b-form-group horizontal label="정렬" class="mb-0">
          <b-input-group>
            <b-form-select v-model="sortBy" :options="sortOptions">
              <option slot="first" :value="null">-- none --</option>
            </b-form-select>
            <b-form-select :disabled="!sortBy" v-model="sortDesc" slot="append">
              <option :value="false">오름차순</option>
              <option :value="true">내림차순</option>
            </b-form-select>
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
             :sort-by.sync="sortBy"
             :sort-desc.sync="sortDesc"
             :sort-direction="sortDirection"
             @filtered="onFiltered"
    >
      <template slot="name" slot-scope="row">{{row.value}}</template>
      <template slot="action" slot-scope="row">
        <b-button size="sm" @click.stop="info(row.item, $event.target)" class="mr-1">
          수정하기
        </b-button>

      </template>
    </b-table>

    <b-row>
      <b-col md="6" class="my-1">
        <b-pagination :total-rows="totalRows" :per-page="perPage" v-model="currentPage" class="my-0"/>
      </b-col>
      <!--<b-col md="6" class="my-1">-->
        <!--<b-btn @click.stop="showAddModal" variant="primary">식단 추가하기</b-btn>-->
      <!--</b-col>-->
    </b-row>

    <!-- Info modal -->
    <b-modal id="modalInfo" @hide="resetModal" :title="modalInfo.title" ok-only>
      <pre>{{ modalInfo.content }}</pre>

      <b-row class="my-1">
        <b-col sm="3"><label v-mode="point" :for="point">포인트 :</label></b-col>
        <b-col sm="9">
          <b-form-input :id="point" type="number" value="point" v-model="point"></b-form-input>
        </b-col>
      </b-row>
      <b-row class="my-1">
        <b-col sm="3"><label v-mode="role" :for="role" >권한 :</label></b-col>
        <b-col sm="9">
          <b-form-select v-model="role" :options="options" class="mb-3" />
        </b-col>
      </b-row>

    </b-modal>
  </b-container>
</template>

<script>
import axios from 'axios';


const items = [];

export default {
  created() {
    const token = localStorage.getItem('token');
    console.log(token);
    const auth = {
      headers: { Authorization: 'Bearer '.concat(token) },
    };
    console.log(auth);
    axios.get('http://192.168.0.32:8080/api/users?page='.concat(0).concat('&size=').concat(100), auth).then((response) => {
      console.log(response);
      this.items = response.data;
    });
    console.log(this.items);
  },
  name: 'Users',
  data() {
    return {
      items,
      id: '',
      name: '',
      role: '',
      birthDay: '',
      email: '',
      point: '',
      fields: [
        { key: 'name', label: '이름', sortable: true, sortDirection: 'desc' },
        { key: 'role', label: '역할', sortable: true, class: 'text-center' },
        { key: 'email', label: '이메일', sortable: true, class: 'text-center' },
        { key: 'birthdate', label: '생일', sortable: true, class: 'text-center' },
        { key: 'point', label: '포인트', sortable: true },
        { key: 'action', label: '수정하기' },
      ],
      options: [
        { value: 'user', text: '회원' },
        { value: 'admin', text: '관리자' },
      ],
      currentPage: 1,
      perPage: 10,
      totalRows: items.length,
      pageOptions: [5, 10, 15],
      sortBy: null,
      sortDesc: false,
      sortDirection: 'asc',
      filter: null,
      modalInfo: { title: '', content: '' },
      types: [{ key: 'name', type: 'text' },
        { key: 'price', type: 'text' },
        { key: 'kcal', type: 'text' },
        { key: 'mealDate', type: 'date' },

      ],
    };
  },


  methods: {
    info(item, button) {
      this.name = item.name;
      this.id = item.id;
      this.birthDay = item.birthDay;
      this.point = item.point;
      this.email = item.email;
      this.role = item.role;
      this.modalInfo.content = item.id;
      this.$root.$emit('bv::show::modal', 'modalInfo', button);
    },
    resetModal() {
      this.modalInfo.title = '';
      this.modalInfo.content = '';
    },
    onFiltered(filteredItems) {
      // Trigger pagination to update the number of buttons/pages due to filtering
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
    showAddModal() {
      this.name = '';
      this.price = '';
      this.kcal = '';
      this.mealDate = '';
      this.image = '';
      this.$refs.myModalRef.show();
    },
    hideAddModal() {
      this.name = '';
      this.price = '';
      this.kcal = '';
      this.mealDate = '';
      this.image = '';
      this.$refs.myModalRef.hide();
    },
    onSubmit() {
      console.log(this.name);
      axios.post('/api/post', {
        name: this.name,
        role: this.role,
      }).then((response) => {
        if (response.status === 200) {
          alert('수정완료');
        } else if (response.status === 401) {
          alert('입력 값을 확인하세요');
        }
      }).catch((error) => {
        console.log(error);
      });
      this.hideAddModal();
    },
  }
  ,
}
;
</script>


<style scoped>

</style>
