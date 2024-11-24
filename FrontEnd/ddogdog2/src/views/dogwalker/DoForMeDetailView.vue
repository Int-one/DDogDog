<template>
  <div class="doforme-detail" v-if="trade">
    <!-- 제목 -->
    <h1 class="title">
      [{{ trade.tradeId }}] {{ trade.petNames?.join(", ") || "반려견 정보 없음" }} 산책 해주세요!
    </h1>

    <!-- 거래 정보 -->
    <div class="info-section">
      <p><strong>작성자:</strong> {{ trade.nickname || "익명" }} ({{ trade.superId }})</p>
      <p><strong>지역:</strong> {{ trade.region }}</p>
      <p><strong>시작 시간:</strong> {{ formatDate(trade.tradeStartTime) }}</p>
      <p><strong>종료 시간:</strong> {{ formatDate(trade.tradeEndTime) }}</p>
      <p><strong>금액:</strong> {{ trade.cost }}원</p>
      <p><strong>요청 사항:</strong> {{ trade.detail }}</p>
      <p><strong>대형견 여부:</strong> {{ trade.largeDog ? "있음" : "없음" }}</p>
      <p><strong>상태:</strong> {{ trade.state === 0 ? "대기 중" : "완료" }}</p>
    </div>

    <!-- 반려견 정보 -->
    <div class="pet-section" v-if="pets.length">
      <h2>함께 가는 반려견</h2>
      <div v-for="(pet, index) in pets" :key="pet.petId" class="pet-card">
        <!-- 반려견 이름 버튼 -->
        <button class="pet-name-button" @click="togglePetDetail(index)">
          {{ pet.name }}
        </button>
        <!-- 반려견 상세 정보 -->
        <div class="pet-detail">
          <img :src="`http://localhost:8081/${pet.petPhoto}` || '/images/default-pet.jpg'" alt="반려견 프로필" class="pet-photo" />
          <p><strong>이름:</strong> {{ pet.petName }}</p>
          <p><strong>나이:</strong> {{ pet.petBirth }}살</p>
          <p><strong>성별:</strong> {{ pet.petGender }}</p>
          <p><strong>견종:</strong> {{ pet.petBreed }}</p>
          <p><strong>체급:</strong> {{ pet.petGroup }}</p>
          <p><strong>체중:</strong> {{ pet.petWeight }}kg</p>
          <p><strong>특이사항:</strong> {{ pet.ps || "없음" }}</p>
        </div>
      </div>
    </div>
    <div v-else>
      <p>함께 가는 반려견 정보가 없습니다.</p>
    </div>
  </div>

  <!-- 로딩 상태 -->
  <div v-else>
    <p>데이터를 불러오는 중입니다...</p>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import axios from "axios";

const trade = ref(null); // 거래 상세 데이터
const pets = ref([]); // 반려견 정보
const showPetDetails = ref([]); // 반려견 프로필 박스 활성화 상태
const route = useRoute(); // 라우터 정보 접근

// 거래 및 반려견 정보 로드
onMounted(async () => {
  try {
    // 거래 데이터 로드
    const response = await axios.get(`http://localhost:8081/api/trade/${route.params.id}`);
    trade.value = response.data;

    // 반려견 데이터 로드
    const petResponse = await axios.get(`http://localhost:8081/api/trade/pets/${route.params.id}`);
    pets.value = petResponse.data;
    console.log(pets.value[0].petPhoto)

    // 반려견 프로필 활성화 초기값 설정
    showPetDetails.value = pets.value.map(() => true);
  } catch (error) {
    console.error("데이터를 가져오는 데 실패했습니다:", error);
  }
});

// 반려견 프로필 토글
const togglePetDetail = (index) => {
  showPetDetails.value[index] = !showPetDetails.value[index];
};

// 날짜 포맷 함수
const formatDate = (datetime) => {
  const date = new Date(datetime);
  return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, "0")}-${String(date.getDate()).padStart(
    2,
    "0"
  )} ${String(date.getHours()).padStart(2, "0")}:${String(date.getMinutes()).padStart(2, "0")}`;
};
</script>

<style scoped>
.doforme-detail {
  padding: 20px;
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
  min-height: 100vh;
}

/* 제목 스타일 */
.title {
  font-size: 1.5rem;
  font-weight: bold;
  margin-bottom: 20px;
  color: #333;
}

/* 정보 섹션 스타일 */
.info-section p {
  font-size: 1rem;
  margin: 10px 0;
  color: #555;
}

/* 반려견 섹션 스타일 */
.pet-section {
  margin-top: 30px;
}

.pet-section h2 {
  font-size: 1.2rem;
  margin-bottom: 15px;
  color: #333;
}

/* 반려견 카드 */
.pet-card {
  margin-bottom: 20px;
}

.pet-name-button {
  display: block;
  width: 100%;
  text-align: left;
  background-color: #4ba64b;
  color: white;
  padding: 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
  margin-bottom: 10px;
  transition: background-color 0.3s;
}

.pet-name-button:hover {
  background-color: #3d8e3d;
}

.pet-detail {
  padding: 10px;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.pet-photo {
  width: 100%;
  max-height: 150px;
  object-fit: cover;
  border-radius: 5px;
  margin-bottom: 10px;
}

.pet-detail p {
  font-size: 0.9rem;
  margin: 5px 0;
  color: #555;
}
</style>
