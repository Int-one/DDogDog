<!-- <template>
  <div id="app">
    <router-view />  
    <BottomNavBar />
  </div>
</template>

<script setup>
// 필요에 따라 전역 설정이나 컴포넌트를 여기에서 추가할 수 있습니다.
import BottomNavBar from "@/components/BottomNavBar.vue";

</script>

<style>
/* 전역 스타일 설정 */
/* #app {
  position: relative;
  width: 360px;
  height: 780px;
  margin: 0 auto;
  background-color: #f9f9f9;
  overflow: hidden;
} */

#app {
  max-width: 360px;
  height: 100vh;
  margin: 0 auto;
  background-color: #f9f9f9;
  overflow: hidden;
}
/* 페이지 전체 스크롤바 숨기기 */
body {
  overflow-y: scroll; /* 세로 스크롤 기능 유지 */
  -ms-overflow-style: none; /* IE 및 Edge */
  scrollbar-width: none; /* Firefox */
}

body::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera */
}
</style> -->

<template>
  <div id="app">
    <router-view /> <!-- 현재 라우트에 따라 컴포넌트를 렌더링 -->
    <!-- 특정 경로에서만 하단 메뉴바를 숨기기 -->
    <BottomNavBar v-if="showBottomNavBar" />
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import { useRoute } from "vue-router";
import BottomNavBar from "@/components/BottomNavBar.vue";

const route = useRoute();

// 하단 메뉴바를 숨길 경로를 정의
const hideBottomNavBarRoutes = ["/login", "/signup"]; // 하단 메뉴바를 숨길 페이지 경로
const showBottomNavBar = ref(!hideBottomNavBarRoutes.includes(route.path));

// 라우터 경로 변경을 감지하여 상태 업데이트
watch(() => route.path, (newPath) => {
  showBottomNavBar.value = !hideBottomNavBarRoutes.includes(newPath);
});
</script>

<style>
/* 전역 스타일 설정 */
#app {
  max-width: 360px;
  height: 100vh;
  margin: 0 auto;
  background-color: #f9f9f9;
  overflow: hidden;
}

/* 페이지 전체 스크롤바 숨기기 */
body {
  overflow-y: scroll; /* 세로 스크롤 기능 유지 */
  -ms-overflow-style: none; /* IE 및 Edge */
  scrollbar-width: none; /* Firefox */
}

body::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera */
}
</style>
