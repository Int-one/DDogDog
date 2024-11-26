<template>
  <div>
    <h1>이메일 입력</h1>
    <input v-model="email" type="email" placeholder="이메일" />
    <button :disabled="!email" @click="nextStep">다음</button>
    <router-link to="/signup">뒤로가기</router-link>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useSignupStore } from '@/stores/signup'

const email = ref('')
const router = useRouter()
const signupStore = useSignupStore()

const nextStep = () => {
  signupStore.setSignupData({ email: email.value }) // 이메일 저장
  router.push('/signup/password') // 비밀번호 입력 페이지로 이동
}
</script>

<style scoped>
/* 스타일 추가 */
</style>




<!-- <template>
  <div class="dogwalker-tab">
    <div class="header">
      <button class="main-button" @click="navigateToSignup">도그워커 가입하기</button>
    </div>

    <div class="profile-list">
      <h2>전체 도그워커 목록</h2>

      <div v-if="dogWalkers.length === 0">
        <p>도그워커 목록을 불러오는 중이거나 데이터가 없습니다.</p>
      </div>

      <div class="profiles">
        <div
          v-for="walker in dogWalkers"
          :key="walker.userId"
          class="profile-card"
        >
        
          <div class="profile-info">
            <h3>{{ walker.nickname }}</h3>
            <p>{{ walker.introduce || "소개 내용이 없습니다." }}</p>
            <p class="region">{{ walker.region }}</p>
            <button class="view-profile" @click="viewProfile(walker.userId)">프로필 보기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

// 상태 관리
const router = useRouter();
const dogWalkers = ref([]); // 전체 도그워커 목록
const defaultImage = "/assets/default-profile.png"; // 기본 이미지 경로

// API 호출로 도그워커 목록 가져오기
const fetchDogWalkers = async () => {
  try {
    const response = await axios.get("http://localhost:8081/api/dogwalker");
    console.log("도그워커 데이터:", response.data); // API 응답 확인
    dogWalkers.value = response.data; // 모든 도그워커 정보를 저장
  } catch (error) {
    console.error("도그워커 목록을 불러오지 못했습니다:", error);
  }
};

// 페이지 로드 시 데이터 호출
onMounted(() => {
  fetchDogWalkers();
});

// 네비게이션 함수
const navigateToSignup = () => {
  router.push("/dog-walker-signup");
};
const viewProfile = (userId) => {
  router.push(`/dogwalker/${userId}`);
};
</script>

<style scoped>
.dogwalker-tab {
  width: 360px;
  height: 780px;
  margin: 0 auto;
  padding: 10px;
  background-color: #f9f9f9;
  display: flex;
  flex-direction: column;
  overflow-y: auto;
}

.header {
  text-align: center;
  margin-bottom: 20px;
}

.main-button {
  padding: 15px;
  font-size: 18px;
  color: white;
  background-color: #4ba64b;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  width: 100%;
  max-width: 340px;
}

.profile-list h2 {
  font-size: 1.4rem;
  margin-bottom: 10px;
  color: #333;
  text-align: center;
}

.profiles {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  justify-content: center;
}

.profile-card {
  background: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 160px;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 15px;
}

.profile-image {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 10px;
}

.profile-info h3 {
  font-size: 1rem;
  margin-bottom: 8px;
  color: #555;
}

.profile-info p {
  font-size: 0.8rem;
  color: #777;
  text-align: center;
  margin-bottom: 5px;
}

.profile-info .region {
  font-size: 0.8rem;
  font-weight: bold;
  color: #333;
}

.view-profile {
  padding: 8px 10px;
  background-color: #4ba64b;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 0.85rem;
  cursor: pointer;
}
</style> -->
