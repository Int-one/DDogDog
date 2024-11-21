<template>
  <div class="container mt-3">
    <!-- 상단 반려견 정보 Carousel -->
    <div v-if="dogs">
      <h5 class="mb-3 text-center">반려견 정보</h5>
      <div id="dogCarousel" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner">
          <div
            v-for="(dog, index) in dogs"
            :key="dog.id"
            class="carousel-item"
            :class="{ active: index === 0 }"
          >
            <div class="dog-card">
              <img
                :src="`http://localhost:8081/${dog.petPhoto}`"
                alt="반려견 사진"
                class="dog-image"
              />
              <div class="mt-3 text-center">
                <h5 class="dog-name">{{ dog.name }}</h5>
                <p class="last-walk">{{ dog.endTime }}</p>
              </div>
            </div>
          </div>
        </div>
        <!-- Carousel Controls -->
        <button
          class="carousel-control-prev"
          type="button"
          data-bs-target="#dogCarousel"
          data-bs-slide="prev"
        >
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Previous</span>
        </button>
        <button
          class="carousel-control-next"
          type="button"
          data-bs-target="#dogCarousel"
          data-bs-slide="next"
        >
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">Next</span>
        </button>
      </div>
    </div>

    <button class="btn btn-primary btn-sm mt-3" @click="router.push({name: 'walk'})">산책 가기</button>

    <!-- 최근 산책 기록 스크롤 -->
    <div>
      <h5 class="mb-3">실시간 산책 인증</h5>
      <div class="scroll-container">
        <div
          v-for="(log, index) in recentLogs"
          :key="index"
          class="scroll-item"
        >
          <div class="card text-center shadow-sm mx-2">
            <div class="card-body">
              <h6 class="card-title">산책 기록 {{ index + 1 }}</h6>
              <p class="card-text">거리: {{ log.total }} km</p>
              <p class="card-text">시간: {{ calculateDuration(log.startTime, log.endTime) }}</p>
              <p class="card-text">날짜: {{ formatDate(log.startTime) }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { usePetStore } from "@/stores/pet";
import router from "@/router";

const petStore = usePetStore();

const dogs = ref([]);
const recentLogs = ref([]); // 최근 산책 기록

// API 호출 함수들
const fetchDogData = async () => {
  try {
    const userId = localStorage.getItem("user_id");
    const response = await axios.get("http://localhost:8081/api/pet/test@test.com"); // 반려견 데이터 API
    dogs.value = response.data;

    // 각 반려견의 마지막 산책 시간을 계산
    dogs.value.forEach((dog) => {
      calculateLastWalkTime(dog);
    });
  } catch (error) {
    console.error("Failed to fetch dog data:", error);
  }
};

const fetchRecentLogs = async () => {
  try {
    const response = await axios.get("http://localhost:8081/api/walklog");
    recentLogs.value = response.data.map((log) => ({
      ...log,
      walk_path: typeof log.walk_path === "string" ? JSON.parse(log.walk_path) : log.walk_path,
    }));
  } catch (error) {
    console.error("Failed to fetch recent logs:", error);
  }
};

// 마지막 산책 시간을 계산
const calculateLastWalkTime = (dog) => {
  if (!dog.endTime) {
    dog.endTime = "산책 가고 싶어요";
    return;
  }

  const lastWalkDate = new Date(dog.endTime);
  const now = new Date();
  const days = Math.floor((now - lastWalkDate) / (1000 * 60 * 60 * 24));

  if (days === 0) {
    dog.endTime = "마지막 산책 오늘";
  } else if (days === 1) {
    dog.endTime = "마지막 산책 1일 전";
  } else {
    dog.endTime = `마지막 산책 ${days}일 전`;
  }
};

// 산책 시간 계산
const calculateDuration = (startTime, endTime) => {
  const start = new Date(startTime);
  const end = new Date(endTime);
  const duration = Math.floor((end - start) / (1000 * 60)); // 분 단위로 계산
  return `${duration}분`;
};

// 날짜 형식 변환
const formatDate = (dateString) => {
  const date = new Date(dateString);
  return date.toLocaleDateString("ko-KR", {
    year: "numeric",
    month: "long",
    day: "numeric",
  });
};

// 페이지 로드 시 데이터 로드
onMounted(() => {
  petStore.fetchPets();
  fetchDogData();
  fetchRecentLogs();
});
</script>

<style scoped>
/* 반려견 사진 스타일 */
.dog-image {
  width: 100%;
  max-width: 300px;
  height: auto;
  object-fit: cover;
  border-radius: 50%;
}

.dog-card {
  min-height: 400px; /* 고정 높이 설정으로 위치 움직임 방지 */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
}

.dog-name {
  font-size: 1.5rem;
  font-weight: bold;
}

.last-walk {
  font-size: 1rem;
  color: gray;
}

/* Carousel 배경색 제거 */
.carousel-item {
  background-color: transparent !important;
}

/* 스크롤 컨테이너 스타일 */
.scroll-container {
  display: flex;
  overflow-x: auto; /* 가로 스크롤 활성화 */
  gap: 16px;
  padding: 10px 0;
  scroll-behavior: smooth;
}

.scroll-item {
  flex: 0 0 auto;
  width: 80%;
  max-width: 300px;
}

.scroll-container::-webkit-scrollbar {
  display: none;
}
</style>
