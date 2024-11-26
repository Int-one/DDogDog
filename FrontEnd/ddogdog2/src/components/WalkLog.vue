<template>
    <div class="walk-log-view">
      <!-- 상단 반려견 아이콘 섹션 -->
      <div class="section">
        <h2 class="m-0">산책 일지</h2>
        <!-- PetIcon에 showEndTime="false" 추가 -->
         <h4 class="m-0">함께한 친구들</h4>
        <PetIcon :pets="petStore.together" :onImageClick="handlePetClick" :showEndTime="false" />
      </div>
  
      <!-- 경로 지도 섹션 -->
      <div class="section">
        <h2>산책 경로</h2>
        <div id="map" style="height: 350px; width: 100%;"></div>
      </div>
  
      <!-- 산책 데이터 섹션 -->
      <div class="section">
        <div class="log-details-row">
          <div class="log-item">
            <h3>총 시간</h3>
            <p>{{ formattedTime }}</p>
          </div>
          <div class="log-item">
            <h3>총 거리</h3>
            <p>{{ formattedDistance }} km</p>
          </div>
          <div class="log-item">
            <h3>소비 칼로리</h3>
            <p>{{ formattedCalories }} kcal</p>
          </div>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { computed, onMounted } from "vue";
  import PetIcon from "@/components/PetIcon.vue"; // PetIcon 컴포넌트 import
  import { usePetStore } from "@/stores/pet";
  import { useWalkStore } from "@/stores/walk";
  
  const petStore = usePetStore();
  const walkStore = useWalkStore();

  const walkData = walkStore.currentWalk;
    
  // 칼로리 계산 함수
  const calculateCalories = (distanceInKm) => {
    return Math.round(distanceInKm * 60 * 1000) / 1000; // 1km당 60kcal, 소수 셋째 자리 반올림
  };
  
  // 총 시간 계산 함수
  const calculateTotalTime = (start, end) => {
    if (!start || !end) return "N/A";
    const diffInSeconds = (new Date(end) - new Date(start)) / 1000;
    const hours = Math.floor(diffInSeconds / 3600);
    const minutes = Math.floor((diffInSeconds % 3600) / 60);
    return `${hours}시간 ${minutes}분`;
  };
  
  // 계산된 데이터
  const formattedTime = computed(() => calculateTotalTime(walkData.startTime, walkData.endTime));
  const formattedDistance = computed(() => walkData.total.toFixed(3));
  const formattedCalories = computed(() => calculateCalories(walkData.total));
  
  // 지도 초기화
  let map;
  const initMap = () => {
    const mapContainer = document.getElementById("map");
    const mapOption = {
      center: new kakao.maps.LatLng(37.5665, 126.9780),
      level: 3,
    };
    map = new kakao.maps.Map(mapContainer, mapOption);
  
    if (walkData.walkPath.length > 1) {
      const path = walkData.walkPath.map(
        (point) => new kakao.maps.LatLng(point.lat, point.lng)
      );
      new kakao.maps.Polyline({
        map,
        path,
        strokeWeight: 5,
        strokeColor: "#FF0000",
        strokeOpacity: 0.7,
      });
      map.setBounds(new kakao.maps.LatLngBounds().extend(path[0]).extend(path[path.length - 1]));
    }
  };
  
  // Mount 시 데이터 로드
  onMounted(() => {
    petStore.fetchPets();
    initMap();
  });
  </script>
  
  <style scoped>
  .walk-log-view {
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
    padding: 1rem;
  }
  
  .section {
    display: flex;
    flex-direction: column;
    gap: 1rem;
  }
  
  .section h2 {
    font-size: 1.2rem;
    font-weight: bold;
    margin-bottom: 0.5rem;
  }
  
  .pet-icons {
    display: flex;
    gap: 1rem;
    justify-content: flex-start;
    overflow-x: auto;
  }
  
  .log-details-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    background-color: #f8f9fa;
    padding: 1rem;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  }
  
  .log-item {
    flex: 1;
    text-align: center;
  }
  
  .log-item h3 {
    font-size: 1rem;
    font-weight: bold;
    margin-bottom: 0.5rem;
  }
  
  .log-item p {
    font-size: 0.9rem;
    color: #666;
  }
  </style>
  


// [
//   { "lat": 37.500622, "lng": 127.036456, "time": "2024-11-16 08:00:00" },
//   { "lat": 37.500430, "lng": 127.035900, "time": "2024-11-16 08:01:00" },
//   { "lat": 37.500210, "lng": 127.035300, "time": "2024-11-16 08:02:00" },
//   { "lat": 37.499980, "lng": 127.034700, "time": "2024-11-16 08:03:00" },
//   { "lat": 37.499750, "lng": 127.034100, "time": "2024-11-16 08:04:00" },
//   { "lat": 37.499520, "lng": 127.033500, "time": "2024-11-16 08:05:00" },
//   { "lat": 37.499300, "lng": 127.032900, "time": "2024-11-16 08:06:00" },
//   { "lat": 37.499080, "lng": 127.032300, "time": "2024-11-16 08:07:00" },
//   { "lat": 37.498850, "lng": 127.031700, "time": "2024-11-16 08:08:00" },
//   { "lat": 37.498620, "lng": 127.031100, "time": "2024-11-16 08:09:00" },
//   { "lat": 37.498400, "lng": 127.030500, "time": "2024-11-16 08:10:00" },
//   { "lat": 37.498180, "lng": 127.029900, "time": "2024-11-16 08:11:00" },
//   { "lat": 37.497960, "lng": 127.029300, "time": "2024-11-16 08:12:00" },
//   { "lat": 37.497780, "lng": 127.028800, "time": "2024-11-16 08:13:00" },
//   { "lat": 37.497690, "lng": 127.028200, "time": "2024-11-16 08:14:00" },
//   { "lat": 37.497650, "lng": 127.027700, "time": "2024-11-16 08:15:00" },
//   { "lat": 37.497642, "lng": 127.027621, "time": "2024-11-16 08:16:00" }
// ],