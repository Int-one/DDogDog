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
import { ref, computed, onMounted } from "vue";
import DogWalkerTab from "@/components/DogWalkerTab.vue"; // '도그워커' 탭 컴포넌트
import DoForMeTab from "@/components/DoForMeTab.vue"; // '해주세요' 탭 컴포넌트
import DoForYouTab from "@/components/DoForYouTab.vue"; // '해드려요' 탭 컴포넌트
import { useDoForMeStore } from "@/stores/doforme";
import axios from "axios";

const myRequests = ref([]);
const store = useDoForMeStore();
const tabs = [
  // { name: "dogwalker", label: "도그워커", component: DogWalkerTab },
  { name: "doforme", label: "해주세요", component: DoForMeTab },
  { name: "doforyou", label: "해드려요", component: DoForYouTab },
];

const activeTab = ref(tabs[0].name);
const dogWalkers = ref([]);
const filteredDogWalkers = ref([]);
const searchQuery = ref("");
const filterByRegion = ref(false);

const activeTabComponent = computed(() => {
  return tabs.find((tab) => tab.name === activeTab.value)?.component || null;
});

const setActiveTab = (tabName) => {
  activeTab.value = tabName;
};

const fetchDogWalkers = async () => {
  try {
    const response = await axios.get("http://localhost:8081/api/user", {
          headers: { "access-token": localStorage.getItem("token") },
        });
    dogWalkers.value = response.data.filter((walker) => walker.dogWalker === true);
    applyFilters();
  } catch (error) {
    console.error("도그워커 목록을 불러오지 못했습니다:", error);
  }
};

const applyFilters = () => {
  let results = dogWalkers.value;

  // '내 지역만 보기' 활성화 시 필터링
  if (filterByRegion.value) {
    results = results.filter((walker) => walker.region.includes(userRegion));
  }

  // 검색어 필터링
  if (searchQuery.value) {
    results = results.filter((walker) =>
      walker.region.toLowerCase().includes(searchQuery.value.toLowerCase())
    );
  }

  filteredDogWalkers.value = results;
};


onMounted(() => {
  fetchDogWalkers();
  if (!store.trades.length) {
    store.fetchTrades().then(() => {
      const userId = localStorage.getItem("user_id");
      myRequests.value = store.trades.filter((trade) => trade.superId === userId);
    });
  } else {
    const userId = localStorage.getItem("user_id");
    myRequests.value = store.trades.filter((trade) => trade.superId === userId);
  }
});
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
