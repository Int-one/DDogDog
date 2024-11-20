<template>
  <div class="walk-tracker">
    <h1>산책</h1>
    <div class="status">
      <p>현재 상태: {{ tracking ? '산책 중' : '산책 전' }}</p>
    </div>
    <button @click="toggleTracking" class="tracking-button">
      {{ tracking ? '종료' : '산책 시작' }}
    </button>
    <div v-if="walkData.walkPath.length > 0" class="path-info">
      <h2>경로 데이터</h2>
      <ul>
        <li v-for="(point, index) in walkData.walkPath" :key="index">
          {{ index + 1 }}. 
          위도: {{ point.lat }}, 
          경도: {{ point.lng }}, 
          시간: {{ point.time }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onUnmounted } from "vue";
import axios from "axios"; // Axios 임포트

const tracking = ref(false); // 추적 상태
const walkData = reactive({
  walkPath: [], // 경로 데이터를 저장할 배열
  startTime: null, // 시작 시간
  endTime: null, // 종료 시간
  total: 0, // 총 이동 거리 (예: 미터)
  dogWalking: false, // 강아지 산책 여부
  userId: "test@test.com", // 사용자 ID (예시)
  tradeId: null,
});
let trackingInterval = null; // setInterval ID

// API URL 설정
const apiUrl = "http://localhost:8081/api/walklog"; // API 엔드포인트

// 시간 포맷터 (YYYY-MM-DDTHH:mm:ss 형식)
const formatTime = (date) => {
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, "0");
  const day = String(date.getDate()).padStart(2, "0");
  const hours = String(date.getHours()).padStart(2, "0");
  const minutes = String(date.getMinutes()).padStart(2, "0");
  const seconds = String(date.getSeconds()).padStart(2, "0");
  return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
};

// 1초마다 위치를 기록
const startTracking = () => {
  if (!navigator.geolocation) {
    alert("Geolocation을 지원하지 않는 브라우저입니다.");
    return;
  }

  walkData.startTime = formatTime(new Date()); // 시작 시간 설정
  walkData.walkPath = []; // 경로 초기화

  trackingInterval = setInterval(() => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const { latitude: lat, longitude: lng } = position.coords;
        const time = formatTime(new Date()); // ISO 8601 형식으로 시간 저장
        walkData.walkPath.push({ lat, lng, time }); // 경로에 좌표와 시간 추가
        console.log("위치 저장:", { lat, lng, time });
      },
      (error) => {
        console.error("위치 추적 오류:", error);
      },
      {
        enableHighAccuracy: true, // GPS 데이터 정확도 최대화
        maximumAge: 0, // 캐시된 위치 무조건 무시
      }
    );
  }, 1000); // 1초 간격
};

// 위치 추적 중지
const stopTracking = async () => {
  if (trackingInterval !== null) {
    clearInterval(trackingInterval); // setInterval 중지
    trackingInterval = null;
  }

  if (walkData.walkPath.length > 0) {
    walkData.endTime = formatTime(new Date()); // 종료 시간 설정
    walkData.total = calculateTotalDistance(walkData.walkPath); // 총 거리 계산

    try {
      // API로 데이터 전송
      const response = await axios.post(apiUrl, walkData);
      console.log("API 전송 성공:", response.data);
      alert("산책 데이터를 성공적으로 저장했습니다.");
    } catch (error) {
      console.error("API 전송 오류:", error);
      alert("산책 데이터를 저장하는 중 문제가 발생했습니다.");
    }
  }
};

// 총 거리 계산
const calculateTotalDistance = (path) => {
  let totalDistance = 0;

  for (let i = 1; i < path.length; i++) {
    const prev = path[i - 1];
    const current = path[i];
    totalDistance += haversineDistance(prev.lat, prev.lng, current.lat, current.lng);
  }

  return totalDistance;
};

// 두 지점 간 거리 계산 (Haversine 공식)
const haversineDistance = (lat1, lng1, lat2, lng2) => {
  const R = 6371e3; // 지구 반지름 (미터)
  const toRadians = (degree) => (degree * Math.PI) / 180;

  const φ1 = toRadians(lat1);
  const φ2 = toRadians(lat2);
  const Δφ = toRadians(lat2 - lat1);
  const Δλ = toRadians(lng2 - lng1);

  const a = Math.sin(Δφ / 2) ** 2 + Math.cos(φ1) * Math.cos(φ2) * Math.sin(Δλ / 2) ** 2;
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

  return R * c; // 두 지점 간 거리 반환 (미터 단위)
};

// 추적 상태 토글
const toggleTracking = () => {
  if (tracking.value) {
    stopTracking();
    console.log("최종 산책 데이터:", walkData); // 콘솔에 최종 데이터 출력
  } else {
    startTracking();
  }
  tracking.value = !tracking.value;
};

// 컴포넌트 언마운트 시 추적 중지
onUnmounted(() => {
  stopTracking();
});
</script>

<style scoped>
.walk-tracker {
  font-family: Arial, sans-serif;
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
}

.status {
  margin-bottom: 20px;
  font-size: 18px;
}

.tracking-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 5px;
}

.tracking-button:hover {
  background-color: #0056b3;
}

.path-info {
  margin-top: 20px;
  text-align: left;
}

.path-info h2 {
  font-size: 20px;
  margin-bottom: 10px;
}

.path-info ul {
  list-style: none;
  padding: 0;
}

.path-info li {
  font-size: 16px;
  line-height: 1.5;
}
</style>



<!-- 초 뒤에 'N'Z가 있는 버전 -->
<!-- <template>
  <div class="walk-tracker">
    <h1>산책</h1>
    <div class="status">
      <p>현재 상태: {{ tracking ? '산책 중' : '산책 전' }}</p>
    </div>
    <button @click="toggleTracking" class="tracking-button">
      {{ tracking ? '종료' : '산책 시작' }}
    </button>
    <div v-if="walkData.walkPath.length > 0" class="path-info">
      <h2>경로 데이터</h2>
      <ul>
        <li v-for="(point, index) in walkData.walkPath" :key="index">
          {{ index + 1 }}. 
          위도: {{ point.lat }}, 
          경도: {{ point.lng }}, 
          시간: {{ point.time }}
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onUnmounted } from "vue";
import axios from "axios"; // Axios 임포트

const tracking = ref(false); // 추적 상태
const walkData = reactive({
  walkPath: [], // 경로 데이터를 저장할 배열
  startTime: null, // 시작 시간
  endTime: null, // 종료 시간
  total: 0, // 총 이동 거리 (예: 미터)
  dogWalking: false, // 강아지 산책 여부
  userId: "test@test.com", // 사용자 ID (예시)
  tradeId: null,
});
let trackingInterval = null; // setInterval ID

// API URL 설정
const apiUrl = "http://localhost:8081/api/walklog"; // API 엔드포인트

// 1초마다 위치를 기록
const startTracking = () => {
  if (!navigator.geolocation) {
    alert("Geolocation을 지원하지 않는 브라우저입니다.");
    return;
  }

  walkData.startTime = new Date().toISOString(); // 시작 시간 설정
  walkData.walkPath = []; // 경로 초기화

  trackingInterval = setInterval(() => {
    navigator.geolocation.getCurrentPosition(
      (position) => {
        const { latitude: lat, longitude: lng } = position.coords;
        const time = new Date().toISOString(); // ISO 8601 형식으로 현재 시간 저장
        walkData.walkPath.push({ lat, lng, time }); // 경로에 좌표와 시간 추가
        console.log("위치 저장:", { lat, lng, time });
      },
      (error) => {
        console.error("위치 추적 오류:", error);
      },
      {
        enableHighAccuracy: true, // GPS 데이터 정확도 최대화
        maximumAge: 0, // 캐시된 위치 무조건 무시
      }
    );
  }, 1000); // 1초 간격
};

// 위치 추적 중지
const stopTracking = async () => {
  if (trackingInterval !== null) {
    clearInterval(trackingInterval); // setInterval 중지
    trackingInterval = null;
  }

  if (walkData.walkPath.length > 0) {
    walkData.endTime = new Date().toISOString(); // 종료 시간 설정
    walkData.total = calculateTotalDistance(walkData.walkPath); // 총 거리 계산

    try {
      // API로 데이터 전송
      const response = await axios.post(apiUrl, walkData);
      console.log("API 전송 성공:", response.data);
      alert("산책 데이터를 성공적으로 저장했습니다.");
    } catch (error) {
      console.error("API 전송 오류:", error);
      alert("산책 데이터를 저장하는 중 문제가 발생했습니다.");
    }
  }
};

// 총 거리 계산
const calculateTotalDistance = (path) => {
  let totalDistance = 0;

  for (let i = 1; i < path.length; i++) {
    const prev = path[i - 1];
    const current = path[i];
    totalDistance += haversineDistance(prev.lat, prev.lng, current.lat, current.lng);
  }

  return totalDistance;
};

// 두 지점 간 거리 계산 (Haversine 공식)
const haversineDistance = (lat1, lng1, lat2, lng2) => {
  const R = 6371e3; // 지구 반지름 (미터)
  const toRadians = (degree) => (degree * Math.PI) / 180;

  const φ1 = toRadians(lat1);
  const φ2 = toRadians(lat2);
  const Δφ = toRadians(lat2 - lat1);
  const Δλ = toRadians(lng2 - lng1);

  const a = Math.sin(Δφ / 2) ** 2 + Math.cos(φ1) * Math.cos(φ2) * Math.sin(Δλ / 2) ** 2;
  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

  return R * c; // 두 지점 간 거리 반환 (미터 단위)
};

// 추적 상태 토글
const toggleTracking = () => {
  if (tracking.value) {
    stopTracking();
    console.log("최종 산책 데이터:", walkData); // 콘솔에 최종 데이터 출력
  } else {
    startTracking();
  }
  tracking.value = !tracking.value;
};

// 컴포넌트 언마운트 시 추적 중지
onUnmounted(() => {
  stopTracking();
});
</script>

<style scoped>
.walk-tracker {
  font-family: Arial, sans-serif;
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  text-align: center;
}

.status {
  margin-bottom: 20px;
  font-size: 18px;
}

.tracking-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 5px;
}

.tracking-button:hover {
  background-color: #0056b3;
}

.path-info {
  margin-top: 20px;
  text-align: left;
}

.path-info h2 {
  font-size: 20px;
  margin-bottom: 10px;
}

.path-info ul {
  list-style: none;
  padding: 0;
}

.path-info li {
  font-size: 16px;
  line-height: 1.5;
}
</style> -->
