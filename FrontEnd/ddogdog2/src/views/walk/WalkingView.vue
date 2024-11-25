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
        <!-- 소변 횟수 -->
        <div class="col">
          <label class="form-label">소변 횟수</label>
          <div class="input-group mt-2 justify-content-center">
            <button class="btn btn-outline-secondary" @click="updateSmall(selectedDetails.small - 1)">-</button>
            <input type="number" class="form-control text-center" :value="selectedDetails.small" @input="updateSmall(Number($event.target.value))" />
            <button class="btn btn-outline-secondary" @click="updateSmall(selectedDetails.small + 1)">+</button>
          </div>
        </div>
        <!-- 대변 횟수 -->
        <div class="col">
          <label class="form-label">대변 횟수</label>
          <div class="input-group mt-2 justify-content-center">
            <button class="btn btn-outline-secondary" @click="updateBig(selectedDetails.big - 1)">-</button>
            <input type="number" class="form-control text-center" :value="selectedDetails.big" @input="updateBig(Number($event.target.value))" />
            <button class="btn btn-outline-secondary" @click="updateBig(selectedDetails.big + 1)">+</button>
          </div>
        </div>
      </div>
      <!-- 특이사항 -->
      <div class="mb-3">
        <label for="notes" class="form-label">특이사항</label>
        <textarea id="notes" class="form-control" rows="6" v-model="selectedDetails.notes"></textarea>
      </div>
    </div>

    <!-- 실시간 이동 거리 및 경과 시간 -->
    <div class="row text-center mt-4">
      <div class="col">
        <h5>실시간 이동 거리</h5>
        <p class="fs-3">{{ currentDistance }} km</p>
      </div>
      <div class="col">
        <h5>경과 시간</h5>
        <p class="fs-3">{{ formattedElapsedTime }}</p>
      </div>
    </div>

    <!-- 하단 버튼 -->
    <footer class="text-center my-4">
      <button @click="toggleTracking" class="btn btn-primary btn-lg w-100 rounded-pill">
        {{ tracking ? "종료" : "산책 시작" }}
      </button>
    </footer>
  </div>
</template>

<script setup>
import axios from "axios";
import { ref, computed, onMounted } from "vue";
import PetIcon from "@/components/PetIcon.vue";
import router from "@/router";
import { usePetStore } from "@/stores/pet";
import { useWalkStore } from "@/stores/walk";
import { usePetLogStore } from "@/stores/petLog";

const petStore = usePetStore();
const walkStore = useWalkStore();
const petLogStore = usePetLogStore();

const tracking = ref(false);
const elapsedTime = ref(0); // 타이머 경과 시간 (초 단위)
const distance = ref(0); // 누적 이동 거리
const timerInterval = ref(null);
const trackingInterval = ref(null);
const walkData = walkStore.currentWalk;
walkData.userId = "test@test.com";

// 선택된 반려견
const selectedPet = computed(() =>
  petStore.together.find((pet) => pet.petId === petStore.goWith[0])
);

// 선택된 반려견의 세부 일정 데이터
const selectedDetails = computed(() => {
  const petId = petStore.goWith[0];
  let log = petLogStore.petLogs.find((log) => log.pet_id === petId);
  if (!log) {
    log = { pet_id: petId, small: 0, big: 0, notes: "" };
    petLogStore.petLogs.push(log); // 초기 데이터 추가
  }
  return log;
});

// 반려견 선택
const selectPet = (petId) => {
  petStore.goWith = [petId];
};

// 소변 횟수 업데이트
const updateSmall = (count) => {
  const petId = selectedPet.value?.petId;
  if (petId) {
    const validCount = Math.max(0, count); // 음수 방지
    petLogStore.updatePetLog(petId, { small: validCount });
  }
};

// 대변 횟수 업데이트
const updateBig = (count) => {
  const petId = selectedPet.value?.petId;
  if (petId) {
    const validCount = Math.max(0, count); // 음수 방지
    petLogStore.updatePetLog(petId, { big: validCount });
  }
};

// 타이머 시작
const startTimer = () => {
  elapsedTime.value = 0;
  timerInterval.value = setInterval(() => {
    elapsedTime.value++;
  }, 1000);
};

// 타이머 중지
const stopTimer = () => {
  clearInterval(timerInterval.value);
  timerInterval.value = null;
};

// 경과 시간 포맷팅
const formattedElapsedTime = computed(() => {
  const hours = Math.floor(elapsedTime.value / 3600).toString().padStart(2, "0");
  const minutes = Math.floor((elapsedTime.value % 3600) / 60).toString().padStart(2, "0");
  const seconds = (elapsedTime.value % 60).toString().padStart(2, "0");
  return `${hours}:${minutes}:${seconds}`;
});

// 거리 계산
const calculateSegmentDistance = (lat1, lng1, lat2, lng2) => {
  const toRad = (value) => (value * Math.PI) / 180;
  const R = 6371;
  const dLat = toRad(lat2 - lat1);
  const dLng = toRad(lat2 - lat1);
  const a =
    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos(toRad(lat1)) *
      Math.cos(toRad(lat2)) *
      Math.sin(dLng / 2) *
      Math.sin(dLng / 2);
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  return Math.round(R * c * 1000) / 1000;
};

// 위치 추적
const handleNewPosition = (lat, lng, time) => {
  const path = walkData.walkPath;
  const newPoint = { lat, lng, time };
  if (path.length > 0) {
    const lastPoint = path[path.length - 1];
    distance.value += calculateSegmentDistance(lastPoint.lat, lastPoint.lng, lat, lng);
  }
  path.push(newPoint);
};

const currentDistance = computed(() => distance.value);

const formatTime = (date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");
  const seconds = String(date.getSeconds()).padStart(2, "0");
  return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
};

// 산책 시작
const startTracking = () => {
  if (!navigator.geolocation) {
    alert("Geolocation을 지원하지 않는 브라우저입니다.");
    return;
  }
  walkData.startTime = formatTime(new Date());
  walkData.walkPath = [];
  distance.value = 0;
  startTimer();
  trackingInterval.value = setInterval(() => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const { latitude, longitude } = position.coords;
        handleNewPosition(latitude, longitude, formatTime(new Date()));
      },
      (err) => console.error(err),
      { enableHighAccuracy: true, maximumAge: 0 }
    );
  }, 1000);
};

// 산책 종료
const stopTracking = async () => {
  clearInterval(trackingInterval.value);
  stopTimer();
  walkData.endTime = formatTime(new Date());
  try {
    await axios.post("http://localhost:8081/api/walklog", walkData, {
          headers: { "access-token": localStorage.getItem("token") },
        })
      .then((res) => {
        petLogStore.setLogId(res.data.logId);
      });
    await axios.post("http://localhost:8081/api/petlog", petLogStore.getPetLogs, {
          headers: { "access-token": localStorage.getItem("token") },
        })
    petLogStore.resetPetLogs();
    petStore.goWith = [];
    router.replace({ name: "walklog" });
  } catch (err) {
    console.error(err);
  }
};

// 버튼 토글
const toggleTracking = () => {
  if (tracking.value) stopTracking();
  else startTracking();
  tracking.value = !tracking.value;
};

onMounted(() => {
  petLogStore.initializePetLogs(petStore.together);
})
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
