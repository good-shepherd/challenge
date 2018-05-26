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
      <template slot="menu" slot-scope="row">{{row.value?'판매중':'미판매'}}</template>
      <template slot="actions" slot-scope="row">
   <!--     <b-button size="sm" @click.stop="info(row.item,  $event.target)" class="mr-1">
          자세히보기
        </b-button>-->
        <b-button size="sm" @click.stop="updateProduct(row.item)" class="mr-1">
          수정
        </b-button>
        <b-button size="sm" @click.stop="infos(row.item)" class="mr-1">
          삭제
        </b-button>
      </template>
    </b-table>

    <b-row>
      <b-col md="6" class="my-1">
        <b-pagination :total-rows="totalRows" :per-page="perPage" v-model="currentPage" class="my-0"/>
      </b-col>
      <b-col md="6" class="my-1">
        <b-btn @click.stop="showAddModal" variant="primary">메뉴 추가하기</b-btn>
      </b-col>
    </b-row>

    <!-- Del modal -->
    <b-modal ref="deleteCheck" hide-footer title="메뉴 추가하기">
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
        <div v-if="!image">
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
        </div>
        <b-btn class="mt-auto" @click="removeImage">초기화</b-btn>



      </div>
      <b-btn class="mt-3" variant="outline" block @click.stop="onSubmit">등록</b-btn>
      <b-btn class="mt-3" variant="outline-danger" block @click.stop="hideAddModal">취소</b-btn>
    </b-modal>

    <!-- Info modal -->
    <b-modal id="modalInfo" @hide="resetModal" :title="modalInfo.title" ok-only>
      <pre>{{ modalInfo.content }}</pre>
    </b-modal>
    <!-- Delete modal -->
    <b-modal ref="deleteModal" hide-footer title="메뉴 삭제">
      정말 삭제하시겠습니까?
      <b-btn class="mt-3" variant="outline" block @click.stop="deleteProduct">삭제</b-btn>
      <b-btn class="mt-3" variant="outline-danger" block @click.stop="hideAddModal">취소</b-btn>
    </b-modal>
    <!-- Update modal -->
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
        <div v-if="!image">
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
        </div>-
        <b-btn class="mt-auto" @click="removeImage">초기화</b-btn>



      </div>
      <b-btn class="mt-3" variant="outline" block @click.stop="onUpdate">수정</b-btn>
      <b-btn class="mt-3" variant="outline-danger" block @click.stop="hideUpdateModal">취소</b-btn>
    </b-modal>
    <!-- Add modal -->
    <b-modal ref="myModalRef" hide-footer title="메뉴 추가하기">
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
        <div v-if="!image">
          <b-row class="my-1">

            <b-col sm="3">
              <label v-mode="image" :for="image">사진 :</label>
            </b-col>
            <b-col sm="9">
              <b-form-file enctype="multipart/form-data" class="mt-auto" :id="image" v-model="image" :state="Boolean(image)" @change="onFileChange"
                           placeholder="이미지를 선택하세요"></b-form-file>
            </b-col>

          </b-row>

        </div>
        <div v-else>
          <b-col sm="12">
            <b-img :src="image" fluid alt="Fluid image"/>
          </b-col>
        </div>
        <b-btn class="mt-auto" @click="removeImage">초기화</b-btn>


      </div>
      <b-btn class="mt-3" variant="outline" block @click.stop="onSubmit">등록</b-btn>
      <b-btn class="mt-3" variant="outline-danger" block @click.stop="hideAddModal">취소</b-btn>
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
    axios.get('http://192.168.0.32:8080/api/products?page='.concat(0).concat('&size=').concat(100), auth).then((response) => {
      console.log(response);
      this.items = response.data;
    });
    console.log(this.items);
  },
  name: 'Menu',
  data() {
    return {
      items,
      id: '',
      name: '',
      price: '',
      image: '',
      menu: false,
      isActive: true,
      fields: [
        { key: 'name', label: '메뉴 이름', sortable: true, sortDirection: 'desc' },
        { key: 'price', label: '가격', sortable: true, class: 'text-center' },
        { key: 'menu', label: '판매여부' },
        { key: 'actions', label: '관리' },
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

      ],
    };
  },
  computed: {
    sortOptions() {
      // Create an options list from our fields
      return this.fields
        .filter(f => f.sortable)
        .map(f => ({ text: f.label, value: f.key }));
    },
  },
  methods: {
    info(item, button) {
      this.id = item.id;
      this.name = item.name;
      this.price = item.price;
      this.$root.$emit('bv::show::modal', 'modalInfo', button);
    },
    infos(item, button) {
      this.id = item.id;
      this.name = item.name;
      this.price = item.price;
      this.$refs.deleteModal.show();
    },
    updateProduct(item) {
      this.id = item.id;
      this.name = item.name;
      this.price = item.price;
      this.menu = item.menu;
      this.$refs.updateModal.show();
    },
    deleteProduct() {
      const token = localStorage.getItem('token');
      console.log(this.id);
      const auth = {
        headers: { 'Authorization': 'Bearer '.concat(token) },
      };
      axios.delete('http://192.168.0.32:8080/api/products/'.concat(this.id), auth).then((response) => {
        if (response.status === 200) {
          alert('삭제 완료');
        } else if (response.status === 401) {
          alert('네트워크 오류 발생');
        }
      }).catch((error) => {
        console.log(error);
      });
      axios.get('http://192.168.0.32:8080/api/products?page='.concat(0).concat('&size=').concat(100), auth).then((response) => {
        console.log(response);
        this.items = response.data;
      });
      this.$refs.deleteModal.hide();
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
      this.image = '';
      this.menu = false;

      this.$refs.myModalRef.show();
    },
    hideAddModal() {
      this.name = '';
      this.price = '';
      this.image = '';
      this.menu = false;
      this.$refs.myModalRef.hide();
    },
    hideUpdateModal() {
      this.name = '';
      this.price = '';
      this.image = '';
      this.menu = false;
      this.$refs.updateModal.hide();
    },
    onSubmit() {
      console.log(this.name);
      const data = new FormData();
      data.append('name', this.name);
      data.append('price', this.price);
      data.append('image', this.image);
      const token = localStorage.getItem('token');
      console.log(token);

      const auth = {
        headers: { 'Authorization': 'Bearer '.concat(token), 'Content-Type': 'application/x-www-form-urlencoded' },
      };
      console.log(auth);

      axios.post('http://192.168.0.32:8080/api/products', data, auth ).then((response) => {
        if (response.status === 200) {
          alert('등록 완료');
        } else if (response.status === 401) {
          alert('입력 값을 확인하세요');
        }
      }).catch((error) => {
        console.log(error);
      });
      const data2 = new FormData();
      data2.append('image', this.image);
      axios.post('http://192.168.0.32:8080/api/products/66', data, auth ).then((response) => {
        if (response.status === 200) {
          alert('등록 완료');
        } else if (response.status === 401) {
          alert('입력 값을 확인하세요');
        }
      }).catch((error) => {
        console.log(error);
      });

      this.hideAddModal();
    },
    onUpdate() {
      const data = new FormData();
      data.append('name', this.name);
      data.append('price', this.price);
      data.append('menu', this.menu)
      // data.append('image', this.image);
      const token = localStorage.getItem('token');
      console.log(token);
      const auth = {
        headers: { 'Authorization': 'Bearer '.concat(token) },
      };
      console.log(auth);
      axios.post('http://192.168.0.32:8080/api/products/'.concat(this.id), data, auth).then((response) => {
        if (response.status === 200) {
          alert('수정 완료');
        } else if (response.status === 401) {
          alert('입력 값을 확인하세요');
        }
      }).catch((error) => {
        console.log(error);
      });
      this.hideUpdateModal();
    },
    onFileChange(e) {
      const files = e.target.files || e.dataTransfer.files;
      if (!files.length) {
        return;
      }

      this.createImage(files[0]);
    },
    createImage(file) {
      const image = new Image();
      const reader = new FileReader();
      const vm = this;

      reader.onload = (e) => {
        vm.image = e.target.result;
      };
      reader.readAsDataURL(file);
    },
    removeImage() {
      this.image = '';
    },
  },
};
</script>


<style scoped>

</style>
