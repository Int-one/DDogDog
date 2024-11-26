<template>
  <div class="mypage">
    <!-- 상단 제목 -->
    <header class="page-header">
      <h1>마이페이지</h1>
    </header>

    <!-- 상단 사용자 정보 -->
    <section class="user-info">
      <div class="user-details">
        <button class="level-badge">Lv.1</button>
        <span class="nickname">{{ user.nickname }} 님</span>
        <span class="user-id">({{ user.userId }})</span>
      </div>
      <button class="edit-profile" @click="toggleProfileCard">[내 프로필 수정]</button>
      <div class="exp-bar">
        <div class="current-exp" :style="{ width: user.exp + '%' }"></div>
      </div>
    </section>

    <!-- 프로필 수정 카드 -->
    <div v-if="showProfileCard" class="profile-card">
      <h2>프로필 수정</h2>
      <table>
        <tbody>
          <tr>
            <td>이름</td>
            <td>{{ user.nickname }}</td>
          </tr>
          <tr>
            <td>이메일</td>
            <td>{{ user.userId }}</td>
          </tr>
          <tr>
            <td>생년월일</td>
            <td>{{ user.birth }}</td>
          </tr>
          <tr>
            <td>전화번호</td>
            <td>{{ user.phone }}</td>
          </tr>
          <tr>
            <td>성별</td>
            <td>{{ user.gender === "M" ? "남성" : "여성" }}</td>
          </tr>
          <tr>
            <td>키</td>
            <td>{{ user.height }} cm</td>
          </tr>
          <tr>
            <td>몸무게</td>
            <td>{{ user.weight }} kg</td>
          </tr>
          <tr>
            <td>지역</td>
            <td>{{ user.region }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- BPM 지표 -->
    <section class="bpm-section">
      <div class="bpm-circle">
        <p>{{ user.bpm }}</p>
        <span>BPM</span>
      </div>
    </section>

    <!-- 내 반려견 섹션 -->
    <section class="pet-section">
      <!-- <h2 style="margin-top: 0px; margin-bottom: 3px">내 반려견</h2> -->
      <div class="pet-list">
        <div v-for="pet in pets" :key="pet.pet_id" class="pet-card">
          <img :src="`http://localhost:8081/${pet.petPhoto}`" alt="반려견 사진" class="pet-photo" />
          <div class="pet-info">
            <p class="pet-name">{{ pet.petName }}</p>
            <p class="last-walk">{{ pet.endTime }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 도그워커 활동 -->
    <section class="dogwalker-section">
      <h2 style="margin-top: 10px; margin-bottom: 3px;">나의 도그워커 활동</h2>
      <div class="activity-stats">
        <div class="activity-card">
          <h3>해주세요</h3>
          <p class="count">{{ user.hireCnt }}</p>
        </div>
        <div class="activity-card">
          <h3>해드려요</h3>
          <p class="count">{{ user.hiredCnt }}</p>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { usePetStore } from "@/stores/pet";
import axios from "axios";

// Pinia 스토어 사용
const petStore = usePetStore();

// 사용자 정보 관리
const user = ref({
  userId: localStorage.getItem("user_id"),
  nickname: "",
  exp: 0,
  bpm: 0,
  hireCnt: 0,
  hiredCnt: 0,
  birth: "",
  phone: "",
  gender: "",
  height: 0,
  weight: 0,
  region: "",
});

// 반려견 정보 가져오기
const pets = petStore.pets;

// 프로필 수정 카드 상태
const showProfileCard = ref(false);

// 마이페이지 데이터 로드
const fetchUserData = async () => {
  try {
    const response = await axios.get(`http://localhost:8081/api/user/${user.value.userId}`, {
      headers: {
        "access-token": localStorage.getItem("token"),
      },
    });
    console.log(response.data)
    Object.assign(user.value, response.data); // 사용자 정보 업데이트
    console.log(user.value.hireCount)
  } catch (error) {
    console.error("사용자 정보를 가져오는 데 실패했습니다:", error);
  }
};

// 반려견 정보 로드
const fetchPets = async () => {
  await petStore.fetchPets();
};

// 프로필 수정 카드 토글
const toggleProfileCard = () => {
  showProfileCard.value = !showProfileCard.value;
};

// 데이터 로드
onMounted(async () => {
  await fetchUserData();
  await fetchPets();
});
</script>

<style scoped>
/* 공통 스타일 */
.mypage {
  padding: 20px;
  font-family: Arial, sans-serif;
  color: #333;
}

/* 제목 */
.page-header h1 {
  font-size: 1.8rem;
  font-weight: bold;
  color: #4caf50; /* 키 컬러 */
  margin-bottom: 20px;
}

/* 사용자 정보 */
.user-info {
  margin-bottom: 20px;
}

.user-details {
  display: flex;
  align-items: center;
  gap: 10px;
}

.level-badge {
  background: #4caf50;
  color: white;
  padding: 5px 10px;
  border-radius: 15px;
  font-size: 0.9rem;
  border: none;
}

.nickname {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
}

.user-id {
  font-size: 0.9rem;
  color: #777;
}

.edit-profile {
  font-size: 0.9rem;
  margin-top: 10px;
  background: none;
  border: none;
  color: #4caf50;
  cursor: pointer;
}

.edit-profile:hover {
  text-decoration: underline;
}

.exp-bar {
  position: relative;
  height: 20px;
  background: #e0e0e0;
  border-radius: 10px;
  overflow: hidden;
  margin-top: 5px;
}

.current-exp {
  height: 100%;
  background: #4caf50;
  transition: width 0.3s ease;
}

/* BPM 섹션 */
.bpm-section {
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 30px 0;
}

.bpm-circle {
  width: 140px;
  height: 140px;
  background: #f9f9f9;
  border: 6px solid #4caf50; /* 키 컬러 */
  border-radius: 50%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.bpm-circle p {
  font-size: 2.5rem;
  font-weight: bold;
  color: #4caf50; /* 키 컬러 */
  margin: 0;
}

.bpm-circle span {
  font-size: 0.9rem;
  color: #777;
}

/* 내 반려견 섹션 */
.pet-section h2 {
  font-size: 1.5rem;
  margin: 20px 0 10px;
  color: #4caf50; /* 키 컬러 */
}

.pet-list {
  display: flex;
  gap: 15px;
  flex-wrap: wrap;
}

.pet-card {
  background: #fff;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 140px;
}

.pet-photo {
  width: 100%;
  height: 100px;
  object-fit: cover;
  border-radius: 8px;
  margin-bottom: 10px;
}

.pet-name {
  font-size: 1rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 5px;
}

.last-walk {
  font-size: 0.8rem;
  color: #777;
}

/* 도그워커 활동 */
.dogwalker-section h2 {
  font-size: 1.5rem;
  margin: 20px 0 10px;
  color: #4caf50; /* 키 컬러 */
}

.activity-stats {
  display: flex;
  justify-content: space-between;
}

.activity-card {
  background: #fff;
  padding: 15px;
  padding-bottom: 5px;
  border: 1px solid #ddd;
  border-radius: 8px;
  text-align: center;
  width: 48%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.activity-card h3 {
  font-size: 1rem;
  margin-bottom: 5px;
  color: #333;
}

.activity-card .count {
  font-size: 1.5rem;
  font-weight: bold;
  color: #4caf50; /* 키 컬러 */
}
</style>
