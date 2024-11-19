<template>
    <div class="walk-tracker">
      <h1>산책</h1>
      <div class="status">
        <p>현재 상태: {{ tracking ? '산책 중' : '산책 전' }}</p>
      </div>
      <button @click="toggleTracking" class="tracking-button">
        {{ tracking ? '종료' : '산책 시작' }}
      </button>
      <div v-if="path.length > 0" class="path-info">
        <h2>경로 데이터</h2>
        <ul>
          <li v-for="(point, index) in path" :key="index">
            {{ index + 1 }}. 
            위도: {{ point.latitude }}, 
            경도: {{ point.longitude }}, 
            시간: {{ point.datetime }}
          </li>
        </ul>
      </div>
    </div>
  </template>
  
  <script setup>
  import { reactive, ref, onUnmounted } from "vue";
  
  const tracking = ref(false); // 추적 상태
  const path = reactive([]); // 경로 데이터를 저장할 배열
  let trackingInterval = null; // setInterval ID
  
  // 1초마다 위치를 기록
  const startTracking = () => {
    if (!navigator.geolocation) {
      alert("Geolocation을 지원하지 않는 브라우저입니다.");
      return;
    }
  
    trackingInterval = setInterval(() => {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords;
          const datetime = new Date().toISOString(); // ISO 8601 형식으로 현재 시간 저장
          path.push({ latitude, longitude, datetime }); // 경로에 좌표와 시간 추가
          console.log("위치 저장:", { latitude, longitude, datetime });
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
  const stopTracking = () => {
    if (trackingInterval !== null) {
      clearInterval(trackingInterval); // setInterval 중지
      trackingInterval = null;
    }
  };
  
  // 추적 상태 토글
  const toggleTracking = () => {
    if (tracking.value) {
      // 종료 시 처리
      stopTracking();
      alert("산책이 종료되었습니다.");
      console.log("최종 경로 데이터:", path); // 콘솔에 최종 데이터 출력
    } else {
      path.length = 0; // 경로 초기화
      startTracking(); // 추적 시작
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
  