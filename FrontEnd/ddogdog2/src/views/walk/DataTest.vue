<template>
  <div>
    <h1>test</h1>
    <button @click="getData">GetData</button>
    <p v-for="log in logs">{{ log }}</p>
  </div>
</template>

<script setup>
  import { ref } from 'vue'

  const logs = ref()

  const getData = async () => {
    // 서버에서 데이터를 가져온다고 가정
    const response = await fetch("http://localhost:8081/api/walklog/1"); // 실제 API 엔드포인트로 교체
    // console.log(response.json())
    const data = await response.json();

    // WalkLog 데이터 중 하나를 선택 (샘플)
    const sampleWalk = data.walkPath.path; // 배열 형태라고 가정
    logs.value = sampleWalk
    // JSON 파싱
    // walkData.value = JSON.parse(sampleWalk.walk_path);
    console.log(sampleWalk)
  }
</script>

<style scoped>

</style>