<template>
  <div class="container mt-3">
    <!-- 상단 반려견 정보 -->
    <div v-if="dogs" class="row">
      <!-- 반려견 카드 -->
      <div v-for="dog in dogs" :key="dog.id" class="col-4 mb-3">
        <div class="text-center shadow-sm h-100">
          <img :src="`http://localhost:8081/${dog.petPhoto}`" alt="반려견 사진" class="dog-image" />
          <div class="card-body">
            <h5 class="card-title dog-name">{{ dog.name }}</h5>
            <p class="card-text dog-walk-text">{{ calculateLastWalkTime(dog.lastWalkTime) }}</p>
          </div>
        </div>
      </div>
    </div>
    <button class="btn btn-primary btn-sm mt-3" @click="goForAWalk">산책 가기</button>

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
import { usePetStore } from '@/stores/pet';

const petStore = usePetStore();
const dogs = ref([]);
const recentLogs = ref([]);

// API 호출 함수들
const fetchDogData = async () => {
  try {
    const userId = localStorage.getItem("user_id");
    const response = await axios.get(`http://localhost:8081/api/pet/${userId}`);
    dogs.value = response.data;
  } catch (error) {
    console.error("Failed to fetch dog data:", error);
  }
};

const fetchRecentLogs = async () => {
  try {
    const response = await axios.get("http://localhost:8081/api/walklog");
    recentLogs.value = response.data.map((log) => (log.walk_path
      ? { ...log, walk_path: JSON.parse(log.walk_path) }
      : log));
  } catch (error) {
    console.error("Failed to fetch recent logs:", error);
  }
};

// 마지막 산책 시간을 계산
const calculateLastWalkTime = (lastWalkTime) => {
  if (!lastWalkTime) {
    return "산책 가고 싶어요";
  }
  const lastWalkDate = new Date(lastWalkTime);
  const now = new Date();
  const days = Math.floor((now - lastWalkDate) / (1000 * 60 * 60 * 24));
  return days === 0 ? "마지막 산책 오늘" : `마지막 산책 ${days}일 전`;
};

// 산책 시간 계산
const calculateDuration = (startTime, endTime) => {
  const start = new Date(startTime);
  const end = new Date(endTime);
  const duration = Math.floor((end - start) / (1000 * 60)); // 분 단위 계산
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
/* 반려견 사진 동적 크기 조정 */
.dog-image {
  width: 70px;
  height: 70px;
  object-fit: cover;
  border-radius: 50%;
  margin: auto;

  /* 동적 크기 조정 */
  @media (min-width: 576px) {
    width: 120px;
    height: 120px;
  }

  @media (min-width: 768px) {
    width: 140px;
    height: 140px;
  }

  @media (min-width: 992px) {
    width: 160px;
    height: 160px;
  }
}

/* 텍스트 크기 동적 조정 */
.dog-name {
  font-size: 1rem;

  @media (min-width: 576px) {
    font-size: 1.2rem;
  }

  @media (min-width: 768px) {
    font-size: 1.4rem;
  }

  @media (min-width: 992px) {
    font-size: 1.6rem;
  }
}

.dog-walk-text {
  font-size: 0.8rem;

  @media (min-width: 576px) {
    font-size: 1rem;
  }

  @media (min-width: 768px) {
    font-size: 1.2rem;
  }

  @media (min-width: 992px) {
    font-size: 1.4rem;
  }
}

/* 스크롤 컨테이너 스타일 */
.scroll-container {
  display: flex;
  overflow-x: auto; /* 가로 스크롤 활성화 */
  gap: 16px;
  padding: 10px 0;
  scroll-behavior: smooth; /* 스크롤 시 부드러운 움직임 */
}

.scroll-item {
  flex: 0 0 auto; /* 각 아이템이 스크롤 영역에 맞게 배치 */
  width: 80%; /* 모바일 화면에서 적당한 크기로 설정 */
  max-width: 300px; /* 최대 너비 제한 */
}

.scroll-container::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera */
}
</style>