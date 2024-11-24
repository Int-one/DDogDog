<template>
    <div class="walk-log-view">
      <!-- 상단 반려견 아이콘 섹션 -->
      <div class="section">
        <h2>산책 일지 기록</h2>
        <div class="pet-icons">
          <PetIcon :pets="pets" :onImageClick="handlePetClick" />
        </div>
      </div>
  
      <!-- 경로 지도 섹션 -->
      <div class="section">
        <h2>산책 경로</h2>
        <div class="map-placeholder">경로 지도</div>
      </div>
  
      <!-- 산책 데이터 섹션 -->
      <div class="section">
        <div class="log-details">
          <p>시작시간: {{ startTime }}</p>
          <p>종료시간: {{ endTime }}</p>
          <p>총시간: {{ totalTime }}</p>
          <p>이동거리: {{ distance }} km</p>
          <p>분류: {{ category }}</p>
          <p v-if="category === '대리산책'">도그워커 ID: {{ dogWalkerId }}</p>
          <p v-else>사용자 닉네임: {{ userNickname }}</p>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from "vue";
  import PetIcon from "@/components/PetIcon.vue"; // PetIcon 컴포넌트 import
  import { useWalkStore } from "@/stores/walk";
  
  const walkStore = useWalkStore();
  
  const pets = ref([]);
  const startTime = ref("10:00 AM");
  const endTime = ref("11:00 AM");
  const totalTime = ref("1시간");
  const distance = ref(3.5); // km
  const category = ref("대리산책"); // "대리산책" 또는 "개인산책"
  const dogWalkerId = ref("walker123");
  const userNickname = ref("홍길동");
  
  const handlePetClick = (petId) => {
    console.log("클릭한 Pet ID:", petId);
  };
  
  // Mount 시 데이터 로드
  onMounted(() => {
    walkStore.fetchMyWalkLogs().then((data) => {
      pets.value = data.pets;
      startTime.value = data.startTime;
      endTime.value = data.endTime;
      totalTime.value = data.totalTime;
      distance.value = data.distance;
      category.value = data.category;
      dogWalkerId.value = data.dogWalkerId;
      userNickname.value = data.userNickname;
    });
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
  
  .map-placeholder {
    height: 200px;
    background-color: #e0e0e0;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #666;
    font-size: 1.2rem;
  }
  
  .log-details {
    background-color: #f8f9fa;
    padding: 1rem;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    font-size: 0.9rem;
    line-height: 1.5;
  }
  </style>
  