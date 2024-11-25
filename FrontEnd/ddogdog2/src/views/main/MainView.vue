<template>
  <div class="container mt-3">


    <header class="text-center my-3">
      <div class="btn-group" role="group" aria-label="탭 메뉴">
        <button
          type="button"
          class="btn btn-outline-primary"
          :class="{ active: selectedTab === '누적' }"
          @click="selectedTab = '누적'"
        >
          누적
        </button>
        <button
          type="button"
          class="btn btn-outline-primary"
          :class="{ active: selectedTab === '월간' }"
          @click="selectedTab = '월간'"
        >
          월간
        </button>
        <button
          type="button"
          class="btn btn-outline-primary"
          :class="{ active: selectedTab === '주간' }"
          @click="selectedTab = '주간'"
        >
          주간
        </button>
      </div>
    </header>
    <section class="record-section my-3">
      <div class="border border-primary rounded p-2 text-primary text-center">
        <div>산책 횟수: {{ totalWalks }}</div>
        <div>소비 칼로리: {{ totalCalories }} kcal</div>
        <div>산책 거리: {{ totalDistance.toFixed(2) }} km</div>
        <div>산책 시간: {{ totalWalkTimeFormatted }}</div>
      </div>
    </section>


    <!-- 상단 반려견 정보 Carousel -->
    <div v-if="dogs" class="dog-carousel-container">
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
                <h5 class="dog-name">{{ dog.petName || '이름 없음' }}</h5>
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

    <!-- 최근 산책 기록 스크롤 -->
    <div class="recent-logs">
      <h5 class="mb-3 text-center">지금 산책중인 사람들</h5>
      <div class="scroll-container">
        <div
          v-for="(log, index) in recentLogs"
          :key="index"
          class="scroll-item"
        >
          <div class="card text-center shadow-sm mx-2">
            <div class="card-body">
              <!-- <h6 class="card-title">산책 기록 {{ index + 1 }}</h6> -->
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
import { ref, computed, onMounted } from "vue";
import axios from "axios";
import { usePetStore } from "@/stores/pet";
import router from "@/router";
import { useWalkStore } from "@/stores/walk";

const petStore = usePetStore();
const walkStore = useWalkStore();
const now = new Date();
const dogs = ref([]);
const recentLogs = ref([]); // 최근 산책 기록

// API 호출 함수들
const fetchDogData = async () => {
  try {
    const response = await axios.get(`http://localhost:8081/api/pet/${localStorage.getItem('user_id')}`, {
          headers: { "access-token": localStorage.getItem("token") },
        }); // 반려견 데이터 API
    dogs.value = response.data;

    // 각 반려견의 마지막 산책 시간을 계산
    dogs.value.forEach((dog) => {
      calculateLastWalkTime(dog);
    });
    petStore.pets = dogs.value;
  } catch (error) {
    console.error("Failed to fetch dog data:", error);
  }
};

const fetchRecentLogs = async () => {
  try {
    const response = await axios.get("http://localhost:8081/api/walklog", {
          headers: { "access-token": localStorage.getItem("token") },
        });
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

// 상단 페이지 관련
const selectedTab = ref("누적");

const filteredWalkLogs = computed(() => {
  if (selectedTab.value === "누적") {
    return walkStore.myWalkLogs; // 전체 데이터
  }

  const daysAgo = selectedTab.value === "월간" ? 30 : 7;
  const startDate = new Date();
  startDate.setDate(now.getDate() - daysAgo);

  return walkStore.myWalkLogs.filter((walk) => {
    const walkDate = new Date(walk.startTime);
    return walkDate >= startDate && walkDate <= now;
  });
});

const totalWalks = computed(() => {
  // 필터링된 데이터의 총 개수
  return filteredWalkLogs.value.length;
});

const totalCalories = computed(() => {
  // 칼로리 소모 계산: 1km당 60kcal 기준
  const caloriesPerKm = 60;
  const calories = totalDistance.value * caloriesPerKm;

  // 소수 셋째자리에서 반올림
  return Math.round(calories * 100) / 100;
});

const totalDistance = computed(() => {
  // 필터링된 데이터의 총 거리 계산
  return filteredWalkLogs.value.reduce((sum, walk) => {
    if (!walk.walkPath || walk.walkPath.length < 2) return sum;

    return sum + walk.total;
  }, 0);
});

// const totalDistance = computed(() => {
//   // 필터링된 데이터의 총 거리 계산
//   return filteredWalkLogs.value.reduce((sum, walk) => {
//     if (!walk.walkPath || walk.walkPath.length < 2) return sum;

//     const calculateDistance = (path) => {
//       let distance = 0;
//       for (let i = 1; i < path.length; i++) {
//         const { lat: lat1, lng: lng1 } = path[i - 1];
//         const { lat: lat2, lng: lng2 } = path[i];
//         const toRad = (value) => (value * Math.PI) / 180;

//         const R = 6371; // Earth radius in km
//         const dLat = toRad(lat2 - lat1);
//         const dLng = toRad(lng2 - lng1);
//         const a =
//           Math.sin(dLat / 2) * Math.sin(dLat / 2) +
//           Math.cos(toRad(lat1)) *
//             Math.cos(toRad(lat2)) *
//             Math.sin(dLng / 2) *
//             Math.sin(dLng / 2);
//         const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//         distance += R * c;
//       }
//       return distance;
//     };

//     return sum + calculateDistance(walk.walkPath);
//   }, 0);
// });

const totalWalkTime = computed(() => {
  // 필터링된 데이터의 총 시간 계산
  return filteredWalkLogs.value.reduce((sum, walk) => {
    const start = new Date(walk.startTime);
    const end = new Date(walk.endTime);
    const duration = (end - start) / 1000; // 초 단위
    return sum + duration;
  }, 0);
});

const totalWalkTimeFormatted = computed(() =>
  formatDuration(totalWalkTime.value)
);

// 시간을 "hh:mm:ss" 형식으로 변환
const formatDuration = (seconds) => {
  const hrs = Math.floor(seconds / 3600);
  const mins = Math.floor((seconds % 3600) / 60);
  const secs = seconds % 60;
  return `${hrs.toString().padStart(2, "0")}:${mins
    .toString()
    .padStart(2, "0")}:${secs.toString().padStart(2, "0")}`;
};


// 페이지 로드 시 데이터 로드
onMounted(() => {
  // petStore.fetchPets();
  fetchDogData();
  fetchRecentLogs();
  walkStore.fetchMyWalkLogs();
});
</script>

<style scoped>
/* 메인 컨테이너 크기 조정 */
.container {
  max-width: 600px;
  margin: auto;
}

/* 반려견 Carousel 컨테이너 */
.dog-carousel-container {
  width: 100%;
  max-width: 360px;
  margin: auto;
}

/* 반려견 사진 스타일 */
.dog-image {
  width: 250px;
  height: 250px;
  object-fit: cover;
  border-radius: 50%; /* 항상 원형 유지 */
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

/* 반려견 카드 스타일 */
.dog-card {
  min-height: 300px; /* 고정 높이 설정으로 위치 움직임 방지 */
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
}

.card-body > p {
  margin-bottom: 0.5rem;
}

.dog-name {
  font-size: 1.2rem;
  font-weight: bold;
  margin-top: 10px;
  color: black;
}

.last-walk {
  font-size: 1rem;
  color: gray;
  margin-top: 5px;
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
  width: 90%; /* 아이템 크기 */
  max-width: 360px;
}

.scroll-container::-webkit-scrollbar {
  display: none; /* 스크롤바 숨김 */
}

/* 버튼 스타일 */
button.btn {
  width: 80%;
  margin: auto;
  display: block;
}

/* Carousel 배경색 제거 */
.carousel-item {
  background-color: transparent !important;
}

/* 상단 헤더 관련 */
header {
  margin-top: 1rem;
  margin-bottom: 1rem;
  text-align: center;
}

.btn-group .btn {
  margin-right: 0.5rem;
}

.btn-group .btn.active {
  background-color: #007bff;
  color: white;
  border-color: #007bff;
}

</style>
