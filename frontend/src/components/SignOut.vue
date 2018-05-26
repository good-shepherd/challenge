<template>

</template>

<script>
export default {
  created() {
    this.$store.dispatch('LOGOUT').then(() => this.$router.push('/'));
  },
};
</script>

<style scoped>
</style>
