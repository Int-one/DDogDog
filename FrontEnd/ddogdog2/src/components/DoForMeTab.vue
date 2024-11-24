<!-- <template>
  <div class="do-for-me-tab">
    <h2 class="section-title">내가 작성한 '해주세요'</h2>
    <div class="my-requests">
      <div
        v-if="myRequests.length"
        class="horizontal-scroll"
      >
        <div v-for="request in myRequests" :key="request.id" class="request-card-horizontal">
          <h3 class="request-title">{{ request.detail }}</h3>
          <p class="request-info">비용: <span>{{ request.cost }}원</span></p>
          <p class="request-info">지역: <span>{{ request.region }}</span></p>
        </div>
      </div>
      <div v-else class="no-data">
        <p>아직 작성된 '해주세요'가 없습니다.</p>
      </div>
    </div>

    <h2 class="section-title">모든 '해주세요'</h2>
    <div class="all-requests">
      <div v-for="request in allRequests" :key="request.id" class="request-card">
        <h3 class="request-title">{{ request.detail }}</h3>
        <p class="request-info">비용: <span>{{ request.cost }}원</span></p>
        <p class="request-info">지역: <span>{{ request.region }}</span></p>
      </div>
    </div>

    <router-link to="/dogwalker/doforme/request" class="request-button">
      새로운 '해주세요' 작성하기
    </router-link>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const myRequests = ref([]);
const allRequests = ref([]);

onMounted(async () => {
  const userId = localStorage.getItem("user_id");
  try {
    const response = await axios.get("http://localhost:8081/api/trade", {
      headers: { "access-token": localStorage.getItem("token") },
    });
    allRequests.value = response.data;
    myRequests.value = response.data.filter((req) => req.superId === userId);
  } catch (error) {
    console.error("데이터 로드 실패:", error);
  }
});
</script>

<style scoped>
/* 전체 컨테이너 */
.do-for-me-tab {
  padding: 20px;
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
  min-height: 100vh;
}

/* 섹션 제목 */
.section-title {
  font-size: 1.2rem;
  color: #333;
  margin-bottom: 15px;
}

/* 가로 스크롤 섹션 */
.my-requests {
  margin-bottom: 30px;
}

.horizontal-scroll {
  display: flex;
  gap: 15px;
  overflow-x: auto;
  padding-bottom: 10px;
  scrollbar-width: thin; /* Firefox */
}

.horizontal-scroll::-webkit-scrollbar {
  height: 6px; /* 가로 스크롤바 높이 */
}

.horizontal-scroll::-webkit-scrollbar-thumb {
  background-color: #4ba64b;
  border-radius: 4px;
}

.horizontal-scroll::-webkit-scrollbar-track {
  background-color: #ddd;
}

/* 가로 카드 디자인 */
.request-card-horizontal {
  flex: 0 0 auto;
  width: 200px;
  background: #ffffff;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.request-card-horizontal h3 {
  font-size: 0.9rem;
  margin-bottom: 10px;
  color: #333;
}

.request-card-horizontal p {
  font-size: 0.8rem;
  margin: 5px 0;
  color: #666;
}

.request-card-horizontal .request-info span {
  font-weight: bold;
  color: #4ba64b;
}

/* 세로 스크롤 섹션 */
.all-requests {
  margin-bottom: 30px;
}

.request-card {
  background: #ffffff;
  padding: 15px;
  margin-bottom: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.request-card h3 {
  font-size: 0.9rem;
  margin-bottom: 10px;
  color: #333;
}

.request-card p {
  font-size: 0.8rem;
  margin: 5px 0;
  color: #666;
}

.request-card .request-info span {
  font-weight: bold;
  color: #4ba64b;
}

/* 버튼 디자인 */
.request-button {
  display: block;
  text-align: center;
  background-color: #4ba64b;
  color: white;
  padding: 10px;
  border-radius: 8px;
  text-decoration: none;
  font-size: 1rem;
  font-weight: bold;
  margin-top: 20px;
  transition: background-color 0.3s;
}

.request-button:hover {
  background-color: #3d8e3d;
}

/* "데이터 없음" 메시지 */
.no-data {
  text-align: center;
  color: #666;
  font-size: 0.9rem;
}
</style> -->
<template>
  <div class="do-for-me-tab">
    <h2 class="section-title">나의 '해주세요'</h2>
    <div class="my-requests">
      <div
        v-if="myRequests.length"
        class="horizontal-scroll"
      >
        <div v-for="request in myRequests" :key="request.id" class="request-card-horizontal">
          <h3 class="request-title">{{ request.detail }}</h3>
          <p class="request-info">비용: <span>{{ request.cost }}원</span></p>
          <p class="request-info">지역: <span>{{ request.region }}</span></p>
        </div>
      </div>
      <div v-else class="no-data">
        <p>아직 작성된 '해주세요'가 없습니다.</p>
      </div>
    </div>

    <!-- '내 반려견 산책 해주세요' 버튼 -->
    <router-link to="/dogwalker/doforme/request" class="create-request-button">
      내 반려견 산책 해주세요
    </router-link>

    <h2 class="section-title">실시간 '해주세요'</h2>
    <div class="all-requests">
      <div v-for="request in allRequests" :key="request.id" class="request-card">
        <h3 class="request-title">{{ request.detail }}</h3>
        <p class="request-info">비용: <span>{{ request.cost }}원</span></p>
        <p class="request-info">지역: <span>{{ request.region }}</span></p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";

const myRequests = ref([]);
const allRequests = ref([]);

onMounted(async () => {
  const userId = localStorage.getItem("user_id");
  try {
    const response = await axios.get("http://localhost:8081/api/trade", {
      headers: { "access-token": localStorage.getItem("token") },
    });
    allRequests.value = response.data;
    myRequests.value = response.data.filter((req) => req.superId === userId);
  } catch (error) {
    console.error("데이터 로드 실패:", error);
  }
});
</script>

<style scoped>
/* 전체 컨테이너 */
.do-for-me-tab {
  padding: 20px;
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
  min-height: 100vh;
}

/* 섹션 제목 */
.section-title {
  font-size: 1.2rem;
  color: #333;
  margin-bottom: 15px;
}

/* 가로 스크롤 섹션 */
.my-requests {
  margin-bottom: 20px;
}

.horizontal-scroll {
  display: flex;
  gap: 15px;
  overflow-x: auto;
  padding-bottom: 10px;
  scrollbar-width: thin; /* Firefox */
}

.horizontal-scroll::-webkit-scrollbar {
  height: 6px; /* 가로 스크롤바 높이 */
}

.horizontal-scroll::-webkit-scrollbar-thumb {
  background-color: #4ba64b;
  border-radius: 4px;
}

.horizontal-scroll::-webkit-scrollbar-track {
  background-color: #ddd;
}

/* 가로 카드 디자인 */
.request-card-horizontal {
  flex: 0 0 auto;
  width: 200px;
  background: #ffffff;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.request-card-horizontal h3 {
  font-size: 0.9rem;
  margin-bottom: 10px;
  color: #333;
}

.request-card-horizontal p {
  font-size: 0.8rem;
  margin: 5px 0;
  color: #666;
}

.request-card-horizontal .request-info span {
  font-weight: bold;
  color: #4ba64b;
}

/* 세로 스크롤 섹션 */
.all-requests {
  margin-top: 30px;
}

.request-card {
  background: #ffffff;
  padding: 15px;
  margin-bottom: 15px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.request-card h3 {
  font-size: 0.9rem;
  margin-bottom: 10px;
  color: #333;
}

.request-card p {
  font-size: 0.8rem;
  margin: 5px 0;
  color: #666;
}

.request-card .request-info span {
  font-weight: bold;
  color: #4ba64b;
}

/* '내 반려견 산책 해주세요' 버튼 */
.create-request-button {
  display: block;
  text-align: center;
  background-color: #4ba64b;
  color: white;
  padding: 12px;
  border-radius: 8px;
  text-decoration: none;
  font-size: 1rem;
  font-weight: bold;
  margin-top: 20px;
  margin-bottom: 30px;
  transition: background-color 0.3s;
}

.create-request-button:hover {
  background-color: #3d8e3d;
}

/* "데이터 없음" 메시지 */
.no-data {
  text-align: center;
  color: #666;
  font-size: 0.9rem;
}
</style>
