<template>
  <div id="app">
    <router-view />  <!-- 현재 라우트에 따라 컴포넌트를 렌더링 -->
  </div>
</template>

<script setup>
// 필요에 따라 전역 설정이나 컴포넌트를 여기에서 추가할 수 있습니다.
</script>

<style>
/* 전역 스타일 설정 */
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
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