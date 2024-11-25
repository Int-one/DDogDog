<template>
  <div id="app">
    <router-view />
    <!-- 하단 메뉴바 조건부 렌더링 -->
    <BottomNavBar v-if="showBottomNavBar" />
  </div>
</template>

<script setup>
import { useRoute } from "vue-router";
import BottomNavBar from "@/components/BottomNavBar.vue";

const route = useRoute();

// 하단 메뉴바를 숨길 경로를 정의
const hideBottomNavBarRoutes = ["/login", "/signup"];

// 현재 경로가 하단 메뉴바 숨김 경로인지 확인
const showBottomNavBar = !hideBottomNavBarRoutes.includes(route.path);
</script>

<style>
/* 스타일이 필요하면 추가 */
</style>


<!-- 
<template>
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