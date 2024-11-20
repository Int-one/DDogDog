<template>
  <div class="container mt-3">
    <!-- 상단 반려견 정보 -->
    <div v-if="dog" class="card text-center shadow-sm mb-3">
      <img :src="`http://localhost:8081/${dog.petPhoto}`" alt="반려견 사진" class="card-img-top dog-image" />
      <div class="card-body">
        <h5 class="card-title">{{ dog.name }}</h5>
        <p class="card-text">{{ lastWalkText }}</p>
        <button class="btn btn-primary btn-sm" @click="goForAWalk">산책 가기</button>
      </div>
    </div>

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

const dog = ref(null); // 반려견 데이터
const recentLogs = ref([]); // 최근 산책 기록
const lastWalkText = ref("");

// API 호출 함수들
const fetchDogData = async () => {
  try {
    const userId = localStorage.getItem("user_id");
    const response = await axios.get("http://localhost:8081/api/pet/test@test.com"); // 반려견 데이터 API
    // const response = await axios.get(`http://localhost:8081/api/pet/${userId}`); // 실사용 시
    dog.value = response.data[0];
    calculateLastWalkTime(dog.value.endTime);
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
const calculateLastWalkTime = (lastWalkTime) => {
  const lastWalkDate = new Date(lastWalkTime);
  const now = new Date();
  const difference = now - lastWalkDate; // 밀리초 차이
  const days = Math.floor(difference / (1000 * 60 * 60 * 24));
  console.log(lastWalkTime)
  if(lastWalkTime == null) {
    lastWalkText.value = "산책 가고 싶어요"
  } else if (days === 0) {
    lastWalkText.value = "마지막 산책 오늘";
  } else if (days === 1) {
    lastWalkText.value = "마지막 산책 1일 전";
  } else {
    lastWalkText.value = `마지막 산책 ${days}일 전`;
  }
};

// 소요 시간을 계산
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
  fetchDogData();
  fetchRecentLogs();
});
</script>

<style scoped>
.dog-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 50%;
  margin: auto;
}

.card {
  border: none;
  border-radius: 10px;
}

.card-body {
  text-align: center;
}

.scroll-container {
  display: flex;
  overflow-x: auto; /* 가로 스크롤 활성화 */
  gap: 16px; /* 아이템 간격 */
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




<!-- <template>
  <div class="container mt-3">
    
    <div v-if="dog" class="card text-center shadow-sm mb-3">
      <img :src="dog.imageUrl" alt="반려견 사진" class="card-img-top dog-image" />
      <div class="card-body">
        <h5 class="card-title">{{ dog.name }}</h5>
        <p class="card-text">마지막 산책 {{ lastWalkText }}</p>
        <button class="btn btn-primary btn-sm" @click="goForAWalk">산책 가기</button>
      </div>
    </div>

    <div>
      <h5 class="mb-3">실시간 산책 인증</h5>
      <div class="carousel-container">
        <div
          class="carousel-track"
          :style="trackStyle"
        >
          <div
            v-for="(log, index) in recentLogs"
            :key="index"
            class="carousel-item"
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
        
        <button class="carousel-control-prev" @click="prevSlide">
          <span class="carousel-control-prev-icon" aria-hidden="true"></span>
          <span class="visually-hidden">이전</span>
        </button>
        <button class="carousel-control-next" @click="nextSlide">
          <span class="carousel-control-next-icon" aria-hidden="true"></span>
          <span class="visually-hidden">다음</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from "vue";
import axios from "axios";

const dog = ref(null); // 반려견 데이터
const recentLogs = ref([]); // 최근 산책 기록
const currentIndex = ref(0); // 현재 활성화된 슬라이드 인덱스
const lastWalkText = ref("");

// API 호출 함수들
const fetchDogData = async () => {
  try {
    const response = await axios.get("http://localhost:8081/api/pet/levidixon@yahoo.com"); // 반려견 데이터 API
    dog.value = response.data[0];
    calculateLastWalkTime(dog.value.endTime);
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
const calculateLastWalkTime = (lastWalkTime) => {
  const lastWalkDate = new Date(lastWalkTime);
  const now = new Date();
  const difference = now - lastWalkDate; // 밀리초 차이
  const days = Math.floor(difference / (1000 * 60 * 60 * 24));

  if (days === 0) {
    lastWalkText.value = "오늘";
  } else if (days === 1) {
    lastWalkText.value = "1일 전";
  } else {
    lastWalkText.value = `${days}일 전`;
  }
};

// 소요 시간을 계산
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

// 이전 슬라이드로 이동
const prevSlide = () => {
  currentIndex.value =
    currentIndex.value === 0 ? recentLogs.value.length - 1 : currentIndex.value - 1;
};

// 다음 슬라이드로 이동
const nextSlide = () => {
  currentIndex.value =
    currentIndex.value === recentLogs.value.length - 1 ? 0 : currentIndex.value + 1;
};

// 슬라이드 트랙 스타일 계산
const trackStyle = computed(() => {
  return {
    transform: `translateX(-${currentIndex.value * 100}%)`,
    transition: "transform 0.5s ease-in-out",
  };
});

// 페이지 로드 시 데이터 로드
onMounted(() => {
  fetchDogData();
  fetchRecentLogs();
});
</script>

<style scoped>
.dog-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  border-radius: 50%;
  margin: auto;
}

.card {
  border: none;
  border-radius: 10px;
}

.card-body {
  text-align: center;
}

.carousel-container {
  position: relative;
  overflow: hidden;
  width: 100%;
  height: 200px; /* 모바일 기준 높이 설정 */
}

.carousel-track {
  display: flex;
  transition: transform 0.5s ease-in-out;
  width: 100%;
}

.carousel-item {
  flex: 0 0 100%; /* 각 아이템이 화면의 100%를 차지 */
  box-sizing: border-box;
  display: flex;
  justify-content: center;
  align-items: center;
}

.carousel-control-prev,
.carousel-control-next {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  z-index: 1;
  background: none;
  border: none;
  font-size: 1.5rem;
}

.carousel-control-prev {
  left: 10px;
}

.carousel-control-next {
  right: 10px;
}
</style> -->
