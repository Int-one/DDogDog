
<template>
  <div class="doforme-detail" v-if="trade">
    <!-- 상단 헤더 -->
    <header class="header">
      <button class="back-button" @click="goBack">
        <i class="fi fi-br-angle-left"></i>
      </button>
      <h1 class="header-title">거래 상세 정보</h1>
    </header>

    <!-- 거래 카드 -->
    <div class="trade-info-card">
      <div class="trade-info-header">
        <h2 class="trade-title">
          {{ trade.petNames?.join(", ") || "반려견 정보 없음" }} 산책 해주세요!
        </h2>
        <span class="trade-id">#{{ trade.tradeId }}</span>
        <div
          :class="[ 'status-badge', {
              recruiting: trade.state === 0,
              matching: trade.state === 1,
              walking: trade.state === 2,
              completed: trade.state === 3,
          }]"
        >
          {{
            trade.state === 0
              ? "모집 중"
              : trade.state === 1
              ? "매칭"
              : trade.state === 2
              ? "산책 중"
              : "완료"
          }}
        </div>
      </div>
      <table>
        <tr>
          <td>작성자</td>
          <td>{{ trade.nickname || "익명" }} ({{ trade.superId }})</td>
        </tr>
        <tr>
          <td>지역</td>
          <td>{{ trade.region }}</td>
        </tr>
        <tr>
          <td>시작 시간</td>
          <td>{{ formatDate(trade.tradeStartTime) }}</td>
        </tr>
        <tr>
          <td>종료 시간</td>
          <td>{{ formatDate(trade.tradeEndTime) }}</td>
        </tr>
        <tr>
          <td>금액</td>
          <td>{{ trade.cost }}원</td>
        </tr>
        <tr>
          <td>요청 사항</td>
          <td>{{ trade.detail }}</td>
        </tr>
        <tr>
          <td>대형견 여부</td>
          <td>{{ trade.largeDog ? "있음" : "없음" }}</td>
        </tr>
      </table>
    </div>

    <!-- 함께 가는 반려견 -->
    <div class="pet-section" v-if="pets.length">
      <div class="pet-photo-container">
        <div
          v-for="(pet, index) in pets"
          :key="pet.petId"
          class="pet-photo-wrapper"
          :class="{ active: selectedPet === pet }"
          @click="showPetDetail(index)"
        >
          <img
            :src="`http://localhost:8081/${pet.petPhoto}` || '/images/default-pet.jpg'"
            alt="반려견 프로필"
            class="pet-photo-button"
          />
        </div>
      </div>
    </div>

    <!-- 반려견 상세 정보 -->
    <div v-if="selectedPet" class="selected-pet-detail">
      <button class="close-button" @click="closePetDetail"></button>
      <h2 class="pet-name">{{ selectedPet.petName }}</h2>
      <table>
        <tbody>
          <tr>
            <td>나이</td>
            <td>만 {{ calculateAge(selectedPet.petBirth) }}살</td>
          </tr>
          <tr>
            <td>성별</td>
            <td>{{ selectedPet.petGender ? "남아" : "여아" }} ({{ selectedPet.castration ? "중성화" : "중성화X" }})</td>
          </tr>
          <tr>
            <td>견종</td>
            <td>{{ selectedPet.breed }}</td>
          </tr>
          <tr>
            <td>체급</td>
            <td>{{ selectedPet.group }} / {{ selectedPet.petWeight }}kg</td>
          </tr>
          <tr>
            <td>특이사항</td>
            <td>{{ selectedPet.ps || "없음" }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { useDoForMeStore } from "@/stores/doforme";
import axios from "axios";

const trade = ref(null); // 거래 데이터
const pets = ref([]); // 반려견 데이터
const selectedPet = ref(null); // 선택된 반려견 데이터
const route = useRoute();
const router = useRouter();
const doForMeStore = useDoForMeStore();

// 데이터 로드
onMounted(async () => {
  try {
    // Pinia Store에서 거래 데이터 로드
    await doForMeStore.fetchTrades();
    trade.value = doForMeStore.trades.find((item) => item.tradeId === Number(route.params.id));

    // 반려견 데이터 로드
    const petResponse = await axios.get(
      `http://localhost:8081/api/trade/pets/${route.params.id}`
    );
    pets.value = petResponse.data;
  } catch (error) {
    console.error("데이터 로드 실패:", error);
  }
});

// 반려견 상세 정보 표시
const showPetDetail = (index) => {
  if (selectedPet.value === pets.value[index]) {
    selectedPet.value = null; // 다시 클릭 시 닫기
  } else {
    selectedPet.value = pets.value[index]; // 반려견 정보 열기
  }
};

// 반려견 상세 정보 닫기
const closePetDetail = () => {
  selectedPet.value = null;
};

// 날짜 포맷 함수
const formatDate = (datetime) => {
  const date = new Date(datetime);
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(
    2,
    "0"
  )}-${String(date.getDate()).padStart(2, "0")} ${String(
    date.getHours()
  ).padStart(2, "0")}:${String(date.getMinutes()).padStart(2, "0")}`;
};

// 나이 계산 함수
const calculateAge = (birthDateString) => {
  const birthDate = new Date(birthDateString);
  const today = new Date();
  let age = today.getFullYear() - birthDate.getFullYear();
  const m = today.getMonth() - birthDate.getMonth();
  if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
    age--;
  }
  return age;
};

// 뒤로가기 함수
const goBack = () => {
  router.back();
};
</script>




<style scoped>
/* 전체 스타일 */
.doforme-detail {
  padding: 15px;
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
  width: 360px;
  height: 780px;
  margin: 0 auto;
  overflow-y: auto;
  border-radius: 8px;
}

/* 상단 헤더 */
.header {
  display: flex;
  align-items: center;
  gap: 5px;
  margin-bottom: 15px;
}

.back-button {
  background: none;
  border: none;
  color: #333;
  font-size: 1.5rem;
  cursor: pointer;
}

.header-title {
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
  margin-top: 5px;
}

/* 거래 카드 */
.trade-info-card {
  margin-bottom: 15px;
  padding: 15px;
  border-radius: 8px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.trade-info-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.trade-title {
  font-size: 0.9rem;
  font-weight: bold;
  color: #333;
}

.trade-id {
  font-size: 0.7rem;
  color: #888;
  margin-bottom: 7px;
}

/* 상태 배지 */
.status-badge {
  padding: 5px 10px;
  font-size: 0.7rem;
  color: #fff;
  border-radius: 50px;
  text-align: center;
}

.status-badge.recruiting {
  background-color: #fdd835; /* 연노란색 */
}

.status-badge.matching {
  background-color: #e57373; /* 파스텔분홍색 */
}

.status-badge.walking {
  background-color: #81c784; /* 파스텔연두색 */
}

.status-badge.completed {
  background-color: #64b5f6; /* 파스텔 하늘색 */
}

table {
  width: 100%;
  border-collapse: collapse;
}

td {
  font-size: 0.8rem;
  padding: 5px 0;
}

td:first-child {
  font-weight: bold;
  color: #555;
  width: 30%;
}

td:last-child {
  color: #333;
}

/* 반려견 섹션 */
.pet-photo-container {
  display: flex;
  gap: 8px;
  margin-top: 10px;
}

.pet-photo-wrapper {
  width: 70px;
  height: 70px;
  border: 2px solid transparent;
  border-radius: 50%;
  transition: border-color 0.3s;
}

.pet-photo-wrapper.active {
  border-color: #81c784;
}

.pet-photo-button {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 50%;
  cursor: pointer;
}

/* 반려견 상세 정보 */
.selected-pet-detail {
  margin-top: 15px;
  padding: 15px;
  background: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: left;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 1.2rem;
  color: #555;
  cursor: pointer;
}

.close-button:hover {
  color: #888;
}

.pet-name {
  font-size: 1rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 10px;
}

.selected-pet-detail table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

.selected-pet-detail td {
  font-size: 0.8rem;
  padding: 5px 0;
}

.selected-pet-detail td:first-child {
  text-align: left; /* 첫 번째 열도 좌측 정렬 */
  font-weight: bold; /* 첫 번째 열 텍스트 강조 */
  color: #555;
  width: 40%; /* 첫 번째 열 너비 조정 */
}

.selected-pet-detail td:last-child {
  text-align: left; /* 두 번째 열 좌측 정렬 */
  color: #333;
}
</style>
