<template>
  <div class="container walk-tracker">
    <!-- 반려견 섹션 -->
    <section class="pets-section my-4">
      <h2 class="text-center">산책 중 반려견</h2>
      <PetIcon :pets="petStore.together" :onImageClick="selectPet" :showEndTime="false" />
    </section>

    <!-- 세부 일정 작성 폼 -->
    <div class="detail-form mt-4 p-3 border rounded bg-light" v-if="selectedPet">
      <h3 class="text-center">{{ selectedPet?.petName }} 세부 일정 작성</h3>
      <div class="row text-center mb-3">
        <div class="col">
          <label class="form-label">소변 횟수</label>
          <div class="input-group mt-2 justify-content-center">
            <button class="btn btn-outline-secondary" @click="details.smallWalks = Math.max(0, details.smallWalks - 1)">-</button>
            <input type="number" class="form-control text-center" v-model.number="details.smallWalks" />
            <button class="btn btn-outline-secondary" @click="details.smallWalks++">+</button>
          </div>
        </div>
        <div class="col">
          <label class="form-label">대변 횟수</label>
          <div class="input-group mt-2 justify-content-center">
            <button class="btn btn-outline-secondary" @click="details.bigWalks = Math.max(0, details.bigWalks - 1)">-</button>
            <input type="number" class="form-control text-center" v-model.number="details.bigWalks" />
            <button class="btn btn-outline-secondary" @click="details.bigWalks++">+</button>
          </div>
        </div>
      </div>
      <div class="mb-3">
        <label for="notes" class="form-label">특이사항</label>
        <textarea id="notes" class="form-control" rows="4" v-model="details.notes"></textarea>
      </div>
    </div>

    <!-- 실시간 이동 거리 및 경과 시간 -->
    <div class="row text-center mt-4">
      <div class="col">
        <h4>실시간 이동 거리</h4>
        <p class="fs-3">{{ currentDistance }} km</p>
      </div>
      <div class="col">
        <h4>경과 시간</h4>
        <p class="fs-3">{{ formattedElapsedTime }}</p>
      </div>
    </div>

    <!-- 하단 버튼 -->
    <footer class="text-center my-4">
      <button @click="toggleTracking" class="btn btn-primary btn-lg w-100">
        {{ tracking ? "종료" : "산책 ㄱ" }}
      </button>
    </footer>
  </div>
</template>

<script setup>
import axios from "axios";
import PetIcon from "@/components/PetIcon.vue";
import router from "@/router";
import { ref, computed } from "vue";
import { usePetStore } from "@/stores/pet";
import { useWalkStore } from "@/stores/walk";

const petStore = usePetStore();
const walkStore = useWalkStore();
const walkData = walkStore.currentWalk;
let trackingInterval = null;
const apiUrl = "http://localhost:8081/api/walklog";

walkData.userId = "test@test.com";
const tracking = ref(false);
const elapsedTime = ref(0); // 타이머 경과 시간 (초 단위)
const timerInterval = ref(null); // 타이머 간격 ID

const details = ref({
  bigWalks: 0,
  smallWalks: 0,
  notes: "",
});

// 선택된 반려견
const selectedPet = computed(() =>
  petStore.together.find((pet) => pet.petId === petStore.goWith[0])
);

// 반려견 선택
const selectPet = (petId) => {
  petStore.goWith = [petId];
};

// 거리 변수 (누적)
const distance = ref(0); // 누적 이동 거리 (km)

// 경로 일부 구간 거리 계산
const calculateSegmentDistance = (lat1, lng1, lat2, lng2) => {
  const toRad = (value) => (value * Math.PI) / 180;
  const R = 6371; // 지구 반지름 (km)
  const dLat = toRad(lat2 - lat1);
  const dLng = toRad(lng2 - lng1);

  const a =
    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos(toRad(lat1)) *
      Math.cos(toRad(lat2)) *
      Math.sin(dLng / 2) *
      Math.sin(dLng / 2);
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  return Math.round(R * c * 1000) / 1000; // km 단위, 소수점 3째 자리 반올림
};

// 새 위치 처리
const handleNewPosition = (lat, lng, time) => {
  const path = walkData.walkPath;
  const newPoint = { lat, lng, time };

  if (path.length > 0) {
    const lastPoint = path[path.length - 1];
    const segmentDistance = calculateSegmentDistance(
      lastPoint.lat,
      lastPoint.lng,
      lat,
      lng
    );
    distance.value += segmentDistance; // 누적 거리 업데이트
  }
  path.push(newPoint); // 경로에 새 지점 추가
};

// 타이머 시작
const startTimer = () => {
  elapsedTime.value = 0; // 초기화
  timerInterval.value = setInterval(() => {
    elapsedTime.value++;
  }, 1000);
};

// 타이머 중지
const stopTimer = () => {
  clearInterval(timerInterval.value);
  timerInterval.value = null;
};

// 타이머 경과 시간 포맷팅
const formattedElapsedTime = computed(() => {
  const hours = Math.floor(elapsedTime.value / 3600).toString().padStart(2, "0");
  const minutes = Math.floor((elapsedTime.value % 3600) / 60)
    .toString()
    .padStart(2, "0");
  const seconds = (elapsedTime.value % 60).toString().padStart(2, "0");
  return `${hours}:${minutes}:${seconds}`;
});

// 산책 시작
const startTracking = () => {
  if (!navigator.geolocation) {
    alert("Geolocation을 지원하지 않는 브라우저입니다.");
    return;
  }

  walkData.startTime = formatTime(new Date());
  walkData.walkPath = [];
  distance.value = 0; // 초기 거리 초기화
  startTimer(); // 타이머 시작

  trackingInterval = setInterval(() => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const { latitude: lat, longitude: lng } = position.coords;
        const time = formatTime(new Date());
        handleNewPosition(lat, lng, time); // 새 위치 추가 및 거리 업데이트
      },
      (error) => {
        console.error("위치 추적 오류:", error);
      },
      {
        enableHighAccuracy: true,
        maximumAge: 0,
      }
    );
  }, 1000);
};

// 산책 종료
const stopTracking = async () => {
  if (trackingInterval !== null) {
    clearInterval(trackingInterval);
    trackingInterval = null;
  }
  stopTimer(); // 타이머 중지

  if (walkData.walkPath.length > 1) {
    walkData.endTime = formatTime(new Date());

    try {
      await axios.post(apiUrl, walkData);
      petStore.goWith = [];
      // alert(`산책 데이터를 성공적으로 저장했습니다. 이동 거리: ${distance.value} km`);
      router.replace({ name: "walklog" });
    } catch (error) {
      console.error("데이터 저장 실패:", error);
      alert("산책 데이터를 저장하는 중 문제가 발생했습니다.");
    }
  } else {
    alert("위치 기록이 부족합니다.");
  }
};

// 버튼 토글
const toggleTracking = () => {
  if (tracking.value) {
    stopTracking();
  } else {
    startTracking();
  }
  tracking.value = !tracking.value;
};

// 실시간 이동 거리
const currentDistance = computed(() => distance.value);

// 시간 포맷 함수
const formatTime = (date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");
  const seconds = String(date.getSeconds()).padStart(2, "0");
  return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
};
</script>

<style scoped>
.pets-container {
  gap: 1rem;
  overflow-x: auto;
  padding-bottom: 0.5rem;
}

.pet-item {
  flex: 0 0 auto;
  width: 80px;
  cursor: pointer;
}

.pet-image-container {
  width: 70px;
  height: 70px;
  border-radius: 50%;
  border: 2px solid transparent;
  display: flex;
  justify-content: center;
  align-items: center;
}

.pet-image-container.selected {
  border-color: #007bff;
}

.pet-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 50%;
}

.detail-form {
  max-width: 400px;
  margin: 0 auto;
}
</style>





<!-- <template>
  <div class="container walk-tracker">
    
    <section class="pets-section my-4">
      <h2 class="text-center">산책 중 반려견</h2>
      <PetIcon :pets="petStore.together" />
      <div class="border bg-light rounded p-3 text-center mt-3">
        산책 준비물 체크
      </div>
    </section>


    <div>
      <div v-for="mylog in walkStore.myWalkLogs">
        <p>{{ mylog.startTime }}</p>
        <p></p>
      </div>
    </div>
    

    <footer class="text-center my-4">
      <button
        @click="toggleTracking"
        class="btn btn-primary btn-lg w-100 rounded-pill"
      >
        {{ tracking ? '종료' : '산책 ㄱ' }}
      </button>
    </footer>
  </div>
</template>

<script setup>
import { ref, onUnmounted, onMounted } from "vue";
import axios from "axios";
import router from "@/router";
import { useWalkStore } from "@/stores/walk";
import { usePetStore } from "@/stores/pet";

const tracking = ref(false);
const selectedTab = ref("누적");
const walkStore = useWalkStore();
const petStore = usePetStore();
// const goWith = petStore.goWith // 선택된 반려견 ID 배열





import { computed } from "vue";
import PetIcon from "@/components/PetIcon.vue";

const now = new Date();

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

    const calculateDistance = (path) => {
      let distance = 0;
      for (let i = 1; i < path.length; i++) {
        const { lat: lat1, lng: lng1 } = path[i - 1];
        const { lat: lat2, lng: lng2 } = path[i];
        const toRad = (value) => (value * Math.PI) / 180;

        const R = 6371; // Earth radius in km
        const dLat = toRad(lat2 - lat1);
        const dLng = toRad(lng2 - lng1);
        const a =
          Math.sin(dLat / 2) * Math.sin(dLat / 2) +
          Math.cos(toRad(lat1)) *
            Math.cos(toRad(lat2)) *
            Math.sin(dLng / 2) *
            Math.sin(dLng / 2);
        const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        distance += R * c;
      }
      return distance;
    };

    return sum + calculateDistance(walk.walkPath);
  }, 0);
});

const totalWalkTime = computed(() => {
  // 필터링된 데이터의 총 시간 계산
  return filteredWalkLogs.value.reduce((sum, walk) => {
    const start = new Date(walk.startTime);
    const end = new Date(walk.endTime);
    const duration = (end - start) / 1000; // 초 단위
    return sum + duration;
  }, 0);
});

// 시간을 "hh:mm:ss" 형식으로 변환
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

const walkData = walkStore.currentWalk;
walkData.userId = "test@test.com";

// 반려견 선택/해제 함수
const togglePetSelection = (petId) => {
  const index = petStore.goWith.indexOf(petId);
  if (index === -1) {
    // 선택되지 않았다면 추가
    petStore.goWith.push(petId);
  } else {
    // 이미 선택된 경우 제거
    petStore.goWith.splice(index, 1);
  }
};

let trackingInterval = null;

const apiUrl = "http://localhost:8081/api/walklog";

const formatTime = (date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");
  const seconds = String(date.getSeconds()).padStart(2, "0");
  return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
};

const startTracking = () => {
  if (!navigator.geolocation) {
    alert("Geolocation을 지원하지 않는 브라우저입니다.");
    return;
  }

  walkData.startTime = formatTime(new Date());
  walkData.walkPath = [];
  walkData.goWith = petStore.goWith.value; // 선택된 반려견 정보 저장

  trackingInterval = setInterval(() => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const { latitude: lat, longitude: lng } = position.coords;
        const time = formatTime(new Date());
        walkData.walkPath.push({ lat, lng, time });
      },
      (error) => {
        console.error("위치 추적 오류:", error);
      },
      {
        enableHighAccuracy: true,
        maximumAge: 0,
      }
    );
  }, 1000);
};

const stopTracking = async () => {
  if (trackingInterval !== null) {
    clearInterval(trackingInterval);
    trackingInterval = null;
  }

  if (walkData.walkPath.length > 0) {
    walkData.endTime = formatTime(new Date());

    try {
      const response = await axios.post(apiUrl, walkData);
      alert("산책 데이터를 성공적으로 저장했습니다.");
      router.replace({ name: "main" });
    } catch (error) {
      alert("산책 데이터를 저장하는 중 문제가 발생했습니다.");
    }
  }
};

const toggleTracking = () => {
  if (tracking.value) {
    stopTracking();
  } else {
    startTracking();
  }
  tracking.value = !tracking.value;
};

onMounted(() => {
  walkStore.fetchMyWalkLogs();
})

onUnmounted(() => {
  // stopTracking();
});
</script>

<style scoped>
.record-section .border {
  font-size: 14px;
}

.pets-container {
  gap: 1rem;
  overflow-x: auto; /* 가로 스크롤 활성화 */
  padding-bottom: 0.5rem;
}

.pet-item {
  flex: 0 0 auto; /* 가로 방향으로만 확장 */
  width: 80px; /* 각 반려견 아이템의 고정 너비 */
  cursor: pointer; /* 클릭 가능 표시 */
}

.dog-image-container {
  width: 70px;
  height: 70px;
  border-radius: 50%;
  border: 2px solid transparent; /* 기본 테두리는 투명 */
  display: flex;
  justify-content: center;
  align-items: center;
}

.dog-image-container.selected {
  border-color: #007bff; /* 선택된 상태에서 테두리 강조 */
}

.dog-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 50%;
}

.pets-container::-webkit-scrollbar {
  display: none; /* 스크롤바 숨김 */
}
</style> -->
