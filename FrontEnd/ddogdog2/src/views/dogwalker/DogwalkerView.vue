<template>
  <div class="dogwalker-view">
    <nav class="tabs">
      <button
        v-for="tab in tabs"
        :key="tab.name"
        :class="{ active: activeTab === tab.name }"
        @click="setActiveTab(tab.name)"
      >
        {{ tab.label }}
      </button>
    </nav>
    <div class="tab-content">
      <component :is="activeTabComponent" />
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import DogWalkerTab from "@/components/DogWalkerTab.vue"; // '도그워커' 탭 컴포넌트
import DoForMeTab from "@/components/DoForMeTab.vue"; // '해주세요' 탭 컴포넌트
import DoForYouTab from "@/components/DoForYouTab.vue"; // '해드려요' 탭 컴포넌트

const tabs = [
  { name: "dogwalker", label: "도그워커", component: DogWalkerTab },
  { name: "doforme", label: "해주세요", component: DoForMeTab },
  { name: "doforyou", label: "해드려요", component: DoForYouTab },
];

const activeTab = ref(tabs[0].name);

const activeTabComponent = computed(() => {
  return tabs.find((tab) => tab.name === activeTab.value)?.component || null;
});

const setActiveTab = (tabName) => {
  activeTab.value = tabName;
};
</script>

<style scoped>
.dogwalker-view {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.tabs {
  display: flex;
  justify-content: space-around;
  background-color: #f9f9f9;
  border-bottom: 1px solid #ddd;
  padding: 10px 0;
}

.tabs button {
  flex: 1;
  padding: 10px 0;
  font-size: 16px;
  color: #999;
  background: none;
  border: none;
  cursor: pointer;
}

.tabs button.active {
  color: #000;
  font-weight: bold;
  border-bottom: 2px solid #000;
}

.tab-content {
  flex: 1;
  overflow-y: auto;
  padding: 10px;
}
</style>
