<template>
  <div class="do-for-me-tab">
    <h2>해주세요</h2>
    <button @click="navigateToRequestPage">산책 해주세요</button>
  </div>
</template>

<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const navigateToRequestPage = () => {
  router.push("/dogwalker/doforme/request");
};
</script>

<style scoped>
.do-for-me-tab {
  padding: 16px;
}
</style>
