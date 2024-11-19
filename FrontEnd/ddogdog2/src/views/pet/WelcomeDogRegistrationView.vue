<template>
  <div>
    <h1>반려견 등록 안내</h1>
    <p>반려견 정보를 등록하고 반려견과 함께한 산책시간을 기록하세요.</p>
    <button @click="goToPetRegistration">내 반려견 등록하러가기</button>
    <button @click="skipToDogWalkerIntro">건너뛰기</button>
  </div>
</template>

<script>
export default {
  methods: {
    goToPetRegistration() {
      this.$router.push("/pet-registration");
    },
    skipToDogWalkerIntro() {
      this.$router.push("/dog-walker-intro");
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
