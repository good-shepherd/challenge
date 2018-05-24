<template>
  <b-container fluid>
    <!-- User Interface controls -->
    <b-row>
      <b-col md="6" class="my-1">
        <b-form-group horizontal label="" class="mb-0">
          <b-input-group>
            <b-form-input v-model="filter" placeholder="검색어를 입력하세요" />
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
      <template slot="isActive" slot-scope="row">{{row.value?'판매중입니다':'매진되었습니다'}}</template>
      <template slot="actions" slot-scope="row">
        <b-button size="sm" @click.stop="info(row.item, row.index, $event.target)" class="mr-1">
          자세히보기
        </b-button>
        <b-button size="sm" @click.stop="row.toggleDetails">
          {{ row.detailsShowing ? 'Hide' : 'Show' }} Details
        </b-button>
      </template>
      <template slot="row-details" slot-scope="row">
        <b-card>
          <ul>
            <li v-for="(value, key) in row.item" :key="key">{{ key }}: {{ value}}</li>
          </ul>
        </b-card>
      </template>
    </b-table>

    <b-row>
      <b-col md="6" class="my-1">
        <b-pagination :total-rows="totalRows" :per-page="perPage" v-model="currentPage" class="my-0" />
      </b-col>
      <b-col md="6" class="my-1">
        <b-btn @click.stop="showAddModal" variant="primary">Show Modal</b-btn>
      </b-col>
    </b-row>

    <!-- Info modal -->
    <b-modal id="modalInfo" @hide="resetModal" :title="modalInfo.title" ok-only>
      <pre>{{ modalInfo.content }}</pre>
    </b-modal>
    <!-- Add modal -->
    <b-modal ref="myModalRef" hide-footer title="식단 추가하기">
      <div class="d-block text-center">
        <b-row class="my-1" v-for="type in types" :key="type">
          <b-col sm="3"><label :for="`type-${type}`">Type {{ type.type }}:</label></b-col>
          <b-col sm="9"><b-form-input :id="`type-${type.type}`" :type="type.type"></b-form-input></b-col>
        </b-row>
      </div>
      <b-btn class="mt-3" variant="outline-danger" block @click.stop="hideAddModal">Close Me</b-btn>
    </b-modal>
  </b-container>
</template>

<script>
const items = [
  { isActive: true, price: 4000, kcal: 100, name: '짜장면' },
  { isActive: false, price: 5000, kcal: 200, name: '짬뽕' },
  { isActive: true, price: 12000, kcal: 300, name: '탕수육' },
  { isActive: false, price: 20000, kcal: 400, name: '라조기' },
  { isActive: true, price: 5500, kcal: 500, name: '울면' },
  { isActive: true, price: 15000, kcal: 600, name: '꿔바로우' },
  { isActive: false, price: 6000, kcal: 700, name: '소룡포' },
  { isActive: true, price: 300000, kcal: 800, name: '개구리다리튀김' },
  { isActive: true, price: 40000, kcal: 900, name: '라조육' },
  { isActive: false, price: 7000, kcal: 1000, name: '삼선짜장' },
  { isActive: true, price: 8000, kcal: 1100, name: '사천짜장' },

];

export default {
  name: 'Table',
  data() {
    return {
      items,
      fields: [
        { key: 'name', label: '식단명', sortable: true, sortDirection: 'desc' },
        { key: 'price', label: '가격', sortable: true, class: 'text-center' },
        { key: 'kcal', label: '칼로리', sortable: true, class: 'text-center' },
        { key: 'isActive', label: '판매여부' },
        { key: 'actions', label: 'Actions' },
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
        { key: 'mealDate', type: 'text' },
        { key: 'isActive', type: 'text' },

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
    info(item, index, button) {
      this.modalInfo.title = `${index} 번째 메뉴`;
      this.modalInfo.content = JSON.stringify(item, null, 2);
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
      this.$refs.myModalRef.show();
    },
    hideAddModal() {
      this.$refs.myModalRef.hide();
    },
  },
};
</script>


<style scoped>

</style>
