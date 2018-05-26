<template>
  <div id="signup">
    <img src="@/assets/logo.png"/>
    <div id="signup-box">
      <div class="form-group">
        <label>이름</label>
        <input type="text" class="form-control" v-model="name"/>
      </div>
      <div class="form-group">
        <label>이메일</label>
        <input class="form-control" v-model="email"/>
        <span v-show="errorMessage.email!==''" class="validation_warn">
          {{errorMessage.email}}</span>
      </div>
      <div class="form-group">
        <label>비밀번호</label>
        <input type="password" class="form-control" v-model="password"/>
        <span v-show="errorMessage.password!==''" class="validation_warn">
          {{errorMessage.password}}</span>
      </div>
      <div class="form-group">
        <label>생년월일</label>
        <input type="date" class="form-control" v-model="birthDay"/>
        <span v-show="errorMessage.birthDay!==''" class="validation_warn">
          {{errorMessage.birthDay}}</span>
      </div>

      <div class="form-group">
        <button class="btn btn-default" @click="_signUp">가입</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      email: '',
      password: '',
      name: '',
      birthDay: '',
      errorMessage: Object,
    };
  },
  watch: {
    email() {
      const EMAIL_REGEX = RegExp('[^@ ]+@[^@ ]+\\.[^@ ]+');
      if (!EMAIL_REGEX.test(this.email)) {
        this.errorMessage.email = '이메일 형식이 아닙니다.';
        return;
      }
      this.errorMessage.email = '';

      if (this.email.length < 3 || this.email.length > 40) {
        this.errorMessage.email = '반드시 최소값 3과(와) 최대값 40 사이의 크기이어야 합니다.';
        return;
      }
      this.errorMessage.email = '';
    },
    password() {
      if (this.password.length < 6 || this.password.length > 20) {
        this.errorMessage.password = '반드시 최소값 6과(와) 최대값 20 사이의 크기이어야 합니다.';
      } else {
        this.errorMessage.password = '';
      }
    },
    birthDay() {
      if (this.birthDay.length < 6 || this.birthDay.length > 10) {
        this.errorMessage.birthDay = '유효하지 않은 날짜 형식입니다.';
      } else {
        this.errorMessage.birthDay = '';
      }
    },
  },
  methods: {
    validation() {
      const validation = Object.keys(this.errorMessage).every(x => this.errorMessage[x] === '');
      if (!validation) {
        alert('유효성 검사를 진행하세요.');
        return false;
      }
      if (this.name.length === 0 || this.password.length === 0 || this.email.length === 0) {
        alert('form을 채워주세요.');
        return false;
      }
      return true;
    },
    _signUp() {
      if (!this.validation()) {
        return;
      }
      axios.post('/api/auth/signup', {
        email: this.email,
        name: this.name,
        birthDay: this.birthDay,
        password: this.password,
      }).then((response) => {
        if (response.status === 201) {
          alert('회원가입 성공');
          this.$router.push('check');
        }
      }).catch((error) => {
        console.log(error);
      });
  },
  },
};
</script>

<style scoped>
  #signup{
    text-align: center
  }
  #signup-box{
    border: 1px solid;
    padding: 10px;
    box-shadow: 2px 3px #888888;
    margin: auto;
    width:300px;
  }
  .validation_warn{
    color:red;
  }
</style>
