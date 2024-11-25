<template>
  <div class="container walk-tracker">
    <!-- 반려견 섹션 -->
    <section class="pets-section my-4">
      <h2 class="text-center">산책 대기 중 반려견</h2>
      <PetIcon :pets="petStore.pets" :onImageClick="togglePetSelection" />
      <div class="border bg-light rounded p-3 text-center mt-3">
        <div class="d-flex justify-content-center mt-2">
          <label class="form-check-label me-3">
            <input type="checkbox" class="form-check-input"> 리드줄
          </label>
          <label class="form-check-label me-3">
            <input type="checkbox" class="form-check-input"> 배변봉투
          </label>
          <label class="form-check-label">
            <input type="checkbox" class="form-check-input"> 간식
          </label>
        </div>
      </div>

    </section>

    <!-- 산책 기록 -->
    <div class="logs border rounded p-3 mb-5 overflow-auto">
      <div
        v-for="mylog in walkStore.myWalkLogs"
        :key="mylog.startTime"
        class="log-item mb-3"
      >
      {{ walkedTime(mylog) }}
        <p class="mb-1">{{ mylog.date }}</p>
        <div class="d-flex justify-content-between small">
          <span class="mb-1 text-muted small">{{ mylog.started }} ~ {{ mylog.ended }}</span>
          <span>{{ mylog.time }}</span>
        </div>
        <div class="d-flex justify-content-between small">
          <span>{{ mylog.total  }} km</span>
          <span>{{ mylog.cal }} kcal</span>
        </div>
      </div>
    </div>


    <!-- 하단 버튼 -->
    <footer class="text-center my-4">
      <button
        @click.prevent="() => {
          petStore.updateTogether();
          if(petStore.goWith.length > 0)
            petStore.goWith = [petStore.goWith[0]];
          router.push({name: 'walking'})
          }"
        class="btn btn-primary btn-lg w-100 rounded-pill"
      >
        <!-- {{ tracking ? '종료' : '산책 하러가기' }} -->
          산책 가기
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

const walkedTime = (mylog) => {
  const start = new Date(mylog.startTime);
  const end = new Date(mylog.endTime);
  const duration = (end - start) / 1000;
  const time = formatDuration(duration);
  const startHours = start.getHours();
  const startMinutes = start.getMinutes();
  const endHours = end.getHours();
  const endMinutes = end.getMinutes();

  const speriod = startHours < 12 ? '오전' : '오후';
  const sformattedHours = startHours % 12 || 12; // 0시를 12시로 변환
  const sformattedMinutes = startMinutes.toString().padStart(2, '0'); // 두 자리로 변환
  mylog.started = `${speriod} ${sformattedHours}시 ${sformattedMinutes}분`

  const eperiod = endHours < 12 ? '오전' : '오후';
  const eformattedHours = endHours % 12 || 12; // 0시를 12시로 변환
  const eformattedMinutes = endMinutes.toString().padStart(2, '0'); // 두 자리로 변환
  mylog.ended = `${eperiod} ${eformattedHours}시 ${eformattedMinutes}분`
  mylog.time = time;

  const year = start.getFullYear();
  const month = (start.getMonth() + 1).toString().padStart(2, '0'); // 월은 0부터 시작하므로 +1
  const day = start.getDate().toString().padStart(2, '0');

  mylog.date = `${year}.${month}.${day}`;
  
  const cal = mylog.total * 60;

  // 소수 셋째자리에서 반올림
  mylog.cal = Math.round(cal * 100) / 100;
}


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

onMounted(() => {
  walkStore.fetchMyWalkLogs();
  petStore.goWith = [];
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

.logs {
  height: 300px; /* 고정 높이 */
}
</style>