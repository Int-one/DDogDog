<template>
  <div>
    <h1>도그워커 소개</h1>
    <p>도그워커 서비스에 대해 알아보세요.</p>
    <button @click="goToDogWalkerSignup">도그워커 데뷔하기</button>
    <button @click="goToDogWalkerList">도그워커 서비스 이용하기</button>
    <button @click="goToMain">건너뛰기</button>
  </div>
</template>

<script>
export default {
  methods: {
    goToDogWalkerSignup() {
      this.$router.push("/dog-walker-signup");
    },
    goToDogWalkerList() {
      this.$router.push("/dog-walker-list");
    },
    goToMain() {
      this.$router.push("/");
    },
  },
};
</script>

<style scoped>
h1 {
  text-align: center;
  margin-bottom: 20px;
}
button {
  margin: 10px;
  padding: 10px 20px;
}
</style>
