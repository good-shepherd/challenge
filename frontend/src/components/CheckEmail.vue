<template>
  <b-jumbotron lead="회원가입을 위한 이메일이 발송되었습니다. 이메일로 가셔서 인증하기 버튼을 눌러주세요." >
    <b-btn variant="primary" href="#" router-link to="/signin">확인</b-btn>
  </b-jumbotron>
</template>

<script>

export default {
  name: 'CheckEmail',
  methods: {
    close() {
    },
  },
};
</script>

<style scoped>

</style>
