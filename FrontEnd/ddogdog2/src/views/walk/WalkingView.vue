<template>
  <div class="container walk-tracker">
    <!-- 상단 헤더
    <header class="text-center my-3">
      <h1>산책</h1>
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
    </header> -->

    <!-- 산책 기록 -->
    <!-- <section class="record-section my-3">
      <h2 class="text-center">산책기록</h2>
      <div class="border border-primary rounded p-2 text-primary text-center">
        산책 횟수 / 소비칼로리 / 산책 거리 / 산책 시간
      </div>
    </section> -->

    <!-- <section class="record-section my-3">
      <h2 class="text-center">산책기록</h2>
      <div class="border border-primary rounded p-2 text-primary text-center">
        <div>산책 횟수: {{ totalWalks }}</div>
        <div>소비 칼로리: {{ totalCalories }} kcal</div>
        <div>산책 거리: {{ totalDistance.toFixed(2) }} km</div>
        <div>산책 시간: {{ totalWalkTimeFormatted }}</div>
      </div>
    </section> -->


    <!-- 반려견 섹션 -->
    <section class="pets-section my-4">
      <h2 class="text-center">산책 대기 중 반려견</h2>
      <!-- <div class="pets-container d-flex overflow-auto">
        <div
          v-for="dog in petStore.pets"
          :key="dog.id"
          class="pet-item text-center me-3"
        >
          <div
            class="rounded-circle mb-2 dog-image-container"
            :class="{ 'selected': goWith.includes(dog.petId) }"
          >
            <img
              :src="`http://localhost:8081/${dog.petPhoto}`"
              alt="반려견 사진"
              class="dog-image"
              @click="togglePetSelection(dog.petId)"
            />
          </div>
          <p class="text-muted">{{ dog.endTime }}</p>
        </div>
      </div> -->
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
    

    <!-- 하단 버튼 -->
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
</style>
