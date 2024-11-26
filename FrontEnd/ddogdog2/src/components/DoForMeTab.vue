<template>
  <div class="do-for-me-tab">
    <div class="section-header">
      <h2 class="section-title">나의 '해주세요'</h2>
      <router-link to="/dogwalker/doforme/request" class="create-request-button-horizontal">
        +
      </router-link>
    </div>

    <div class="my-requests horizontal-scroll">
      <template v-if="myRequests.length > 0">
        <div
          v-for="trade in myRequests"
          :key="trade.tradeId"
          class="request-card-horizontal"
          @click="navigateToDetail(trade.tradeId)"
        >
          <div class="request-header">
            <span class="request-title">{{ trade.petNames.join(", ") }} 산책 해주세요!</span>
            <span class="request-id">#{{ trade.tradeId }}</span>
          </div>
          <p class="request-date">
            {{ formatDate(trade.tradeStartTime) }} ({{ calculateDuration(trade.tradeStartTime, trade.tradeEndTime) }})
          </p>
          <p class="request-tags">
            #{{ trade.largeDog ? "대형견" : "소형견" }} #{{ trade.petNames.length }}마리 #{{ trade.region }}
          </p>
          <div class="request-footer">
            <span class="request-author">{{ trade.nickname || "익명" }}</span>
            <span class="request-cost">{{ trade.cost }}원</span>
          </div>
        </div>
        <!-- '+ 버튼' -->
        <router-link
          to="/dogwalker/doforme/request"
          class="create-request-button-horizontal"
        >
          +
        </router-link>
      </template>

      <!-- 목록이 없을 때 대체 카드 -->
      <router-link
        v-else
        to="/dogwalker/doforme/request"
        class="request-card-horizontal create-request-card"
      >
        내 반려견 산책 해주세요
      </router-link>
    </div>
    <!--구분선-->
    <hr class="divider" />

    <h2 class="section-title">실시간 '해주세요'</h2>
    <div class="all-requests">
      <div
        v-for="trade in trades"
        :key="trade.tradeId"
        class="request-card"
        @click="navigateToDetail(trade.tradeId)"
      >
        <div class="request-header">
          <span class="request-title">{{ trade.petNames.join(", ") }} 산책 해주세요!</span>
          <span class="request-id">#{{ trade.tradeId }}</span>
        </div>
        <p class="request-date">
          {{ formatDate(trade.tradeStartTime) }} ({{ calculateDuration(trade.tradeStartTime, trade.tradeEndTime) }})
        </p>
        <p class="request-tags">
          #{{ trade.largeDog ? "대형견" : "소형견" }} #{{ trade.petNames.length }}마리 #{{ trade.region }}
        </p>
        <div class="request-footer">
          <span class="request-author">{{ trade.nickname || "익명" }}</span>
          <span class="request-cost">{{ trade.cost }}원</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import { useDoForMeStore } from "@/stores/doforme";

const store = useDoForMeStore();
const router = useRouter();

const myRequests = ref([]);
const trades = computed(()=>{
  return store.trades;
});

// 컴포넌트 마운트 시 데이터 필터링
// onMounted(() => {
//   if (!store.trades.length) {
//     store.fetchTrades().then(() => {
//       const userId = localStorage.getItem("user_id");
//       myRequests.value = store.trades.filter((trade) => trade.superId === userId);
//     });
//   } else {
//     const userId = localStorage.getItem("user_id");
//     myRequests.value = store.trades.filter((trade) => trade.superId === userId);
//   }
// });

// 상세 페이지로 이동
const navigateToDetail = (tradeId) => {
  router.push({
    name: "DoForMeDetail",
    params: { id: tradeId },
  });
};

// 날짜 포맷
const formatDate = (datetime) => {
  const date = new Date(datetime);
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, "0")}-${String(date.getDate()).padStart(
    2,
    "0"
  )} ${String(date.getHours()).padStart(2, "0")}:${String(date.getMinutes()).padStart(2, "0")}`;
};

// 기간 계산
const calculateDuration = (start, end) => {
  const startTime = new Date(start);
  const endTime = new Date(end);
  const diffMs = endTime - startTime;

  const hours = Math.floor(diffMs / (1000 * 60 * 60));
  const minutes = Math.floor((diffMs % (1000 * 60 * 60)) / (1000 * 60));

  if (minutes === 0) {
    return `${hours}h`;
  }
  return `${hours}h ${minutes}m`;
};
</script>

<style scoped>
.do-for-me-tab {
  padding: 20px;
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
}

/* 섹션 제목 */
.section-title {
  font-size: 1.4rem;
  color: #333;
  margin-bottom: 10px;
  margin-top: 10px;
}

/* 섹션 헤더 */
.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1px;
}

/* '+ 버튼' (가로 스크롤) */
.create-request-button-horizontal {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100px;
  height: 80px;
  background-color: #4ba64b;
  color: white;
  font-size: 1.5rem;
  font-weight: bold;
  border-radius: 8px;
  cursor: pointer;
  text-decoration: none;
}

.create-request-button-horizontal:hover {
  background-color: #3d8e3d;
}

/* 가로 스크롤 */
.horizontal-scroll {
  display: flex;
  gap: 15px;
  overflow-x: auto;
  padding-bottom: 10px;
}

/* 요청 카드 */
.request-card-horizontal {
  flex: 0 0 auto;
  width: 300px;
  background: #ffffff;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: transform 0.2s;
  margin-bottom: 5px;
}

.request-card-horizontal:hover {
  transform: scale(1.05);
}

/* 요청 카드 대체 버튼 */
.create-request-card {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1rem;
  font-weight: bold;
  background-color: #4ba64b;
  color: white;
  text-align: center;
  text-decoration: none;
}

/* 요청 카드 (세로) */
.request-card {
  background: #ffffff;
  padding: 15px;
  margin-bottom: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: transform 0.2s;
}

.request-card:hover {
  transform: scale(1.05);
}

.request-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.request-title {
  font-size: 1rem;
  font-weight: bold;
}

.request-id {
  font-size: 0.9rem;
  color: #999;
}

.request-date {
  font-size: 0.9rem;
  color: #666;
  margin: 5px 0;
}

.request-tags {
  font-size: 0.8rem;
  color: #666;
  margin-bottom: 10px;
}

.request-footer {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.request-author {
  font-size: 0.8rem;
  color: #999;
}

.request-cost {
  font-size: 0.9rem;
  font-weight: bold;
  color: #4ba64b;
}

.create-request-button-horizontal {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 30px;
  height: 30px;
  background-color: #4ba64b;
  color: white;
  font-size: 1.5rem;
  font-weight: bold;
  border-radius: 50%;
  text-decoration: none;
  cursor: pointer;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s;
}

.create-request-button-horizontal:hover {
  background-color: #3d8e3d;
}

/* 구분선 스타일 */
.divider { /* 추가된 부분 */
  border: none;
  border-top: 1px solid #ddd;
  margin: 20px 0;
}

</style>
