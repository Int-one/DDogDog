<!-- <template>
  <div class="bottom-nav-bar">
    <router-link
      v-for="menu in menus"
      :key="menu.name"
      :to="menu.to"
      class="menu-item"
      :class="{ active: $route.path === menu.to }"
    >
      <i :class="menu.icon"></i>
      <span>{{ menu.name }}</span>
    </router-link>
  </div>
</template>

<script setup>
const menus = [
  { name: "메인", to: "/", icon: "fi fi-rr-home" },
  { name: "커뮤니티", to: "/board", icon: "fi fi-rs-comment" },
  { name: "산책", to: "/walk", icon: "fi fi-brands-telegram" },
  { name: "도그워커", to: "/dogwalker", icon: "fi fi-sr-paw" },
  { name: "MY", to: "/my", icon: "fi fi-rr-user" },
];
</script>

<style scoped>
.router-link-active,
.router-link-exact-active {
  background-color: transparent !important; /* 배경색 제거 */
}

.bottom-nav-bar {
  display: flex;
  justify-content: space-around;
  align-items: center;
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 60px;
  background-color: #fff;
  border-top: 1px solid #ddd;
}

.menu-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-decoration: none;
  font-size: 12px;
  color: #000;
}

.menu-item i {
  font-size: 20px;
}

.active {
  color: #4BA64B;
}
</style> -->

<template>
  <nav class="bottom-nav">
    <router-link
      v-for="menu in menus"
      :key="menu.name"
      :to="menu.to"
      class="menu-item"
      :class="{ active: $route.path === menu.to }"
    >
      <i :class="menu.icon"></i>
      <span>{{ menu.name }}</span>
    </router-link>
  </nav>
</template>

<script setup>
import { ref } from "vue";

const menus = ref([
  { name: "Home", to: "/", icon: "fi fi-rr-home" },
  { name: "Community", to: "/board", icon: "fi fi-rs-comment" },
  { name: "Walk", to: "/walk", icon: "fi fi-brands-telegram" },
  { name: "Dog Walker", to: "/dogwalker", icon: "fi fi-sr-paw" },
  { name: "My Page", to: "/my", icon: "fi fi-rr-user" },
]);
</script>

<style scoped>
.bottom-nav {
  position: fixed;
  bottom: 0;
  width: 100%;
  display: flex;
  justify-content: space-around; /* 메뉴 간 같은 간격 */
  align-items: center; /* 세로 가운데 정렬 */
  background-color: #fff; /* 메뉴바 배경색 */
  border-top: 1px solid #ddd; /* 상단 경계선 */
  height: 60px; /* 메뉴바 높이 */
  z-index: 1000;
}

.menu-item {
  flex: 1; /* 모든 메뉴가 같은 너비 */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-decoration: none;
  color: #666; /* 기본 텍스트 색상 */
}

.menu-item i {
  font-size: 20px; /* 아이콘 크기 */
  /* margin-bottom: 1px; 아이콘과 텍스트 간격 */
}

.menu-item span {
  font-size: 12px; /* 텍스트 크기 */
}

.menu-item.active {
  color: #4BA64B; /* 활성화된 메뉴 색상 */
}
</style>
