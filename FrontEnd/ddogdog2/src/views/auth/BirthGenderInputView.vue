<!-- <template>
  <div>
    <h1>생년월일 및 성별 입력</h1>
    <input v-model="birthdate" placeholder="YYYYMMDD" @input="detectLength"/>
    <div>
      <label><input type="radio" v-model="gender" value=1 /> 남자</label>
      <label><input type="radio" v-model="gender" value=0 /> 여자</label>
    </div>
    <button :disabled="!birthdate || !gender" @click="nextStep">다음</button>
    <router-link to="/signup/phone-number">뒤로가기</router-link>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useSignupStore } from '@/stores/signup'

const birthdate = ref('')
const gender = ref()
const router = useRouter()
const signupStore = useSignupStore()

const nextStep = () => {

  signupStore.setSignupData({ birthdate: birthdate.value, gender: gender.value }) // 생년월일 및 성별 저장
  router.push('/signup/height-weight') // 신장 및 체중 입력 페이지로 이동
}

const detectLength = () => {
    // 입력 문자열이 8자리인지 확인
    if (birthdate.value.length !== 8) {
        console.log("Not match!!!")
    }
}
</script>

<style scoped>
/* 스타일 추가 */
</style> -->

<template>
  <div class="signup-page">
    <!-- 상단 헤더 -->
    <header class="header">
      <button class="back-button" @click="goBack">
        <i class="fi fi-rr-angle-left"></i>
      </button>
    </header>

    <!-- 본문 내용 -->
    <div class="content">
      <h2 class="message">
        생년월일과 성별을 입력해주세요🎂
      </h2>

      <!-- 생년월일 입력 필드 -->
      <input
        v-model="birthdate"
        type="text"
        placeholder="YYYYMMDD"
        class="input-field"
        @input="detectLength"
      />

      <!-- 성별 선택 버튼 -->
      <div class="gender-buttons">
        <button
          class="gender-button"
          :class="{ active: gender === '1' }"
          @click="selectGender('1')"
        >
          남성
        </button>
        <button
          class="gender-button"
          :class="{ active: gender === '0' }"
          @click="selectGender('0')"
        >
          여성
        </button>
      </div>

      <!-- 다음 버튼 -->
      <button class="next-button" :disabled="!birthdate || !gender" @click="nextStep">
        다음
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useSignupStore } from "@/stores/signup";

const birthdate = ref("");
const gender = ref(null);
const router = useRouter();
const signupStore = useSignupStore();

// 뒤로가기 버튼 동작
const goBack = () => {
  router.push("/signup/phone-number");
};

// 다음 단계로 이동
const nextStep = () => {
  signupStore.setSignupData({ birthdate: birthdate.value, gender: gender.value });
  router.push("/signup/height-weight");
};

// 생년월일 입력값이 8자리 초과하지 않도록 제한
const detectLength = () => {
  if (birthdate.value.length > 8) {
    birthdate.value = birthdate.value.slice(0, 8);
  }
};

// 성별 선택 버튼 클릭 시 동작
const selectGender = (selectedGender) => {
  gender.value = selectedGender;
};
</script>

<style scoped>
/* 전체 레이아웃 스타일 */
.signup-page {
  padding: 10px;
  font-family: Arial, sans-serif;
  background-color: #ffffff;
  min-height: 100vh;
}

/* 헤더 스타일 */
.header {
  position: relative;
  top: 40px;
  margin-bottom: 40px;
  display: flex;
  align-items: center;
}

.back-button {
  font-size: 1.2rem;
  background: none;
  border: none;
  color: #333;
  cursor: pointer;
  display: flex;
  align-items: center;
}

.content {
  margin-left: 10px;
  margin-right: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
}

.message {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
  text-align: left;
  margin-top: 30px;
  width: 100%;
}

.input-field {
  width: 100%;
  max-width: 400px;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  outline: none;
}

/* 성별 버튼 스타일 */
.gender-buttons {
  display: flex;
  justify-content: space-between;
  width: 100%;
  max-width: 400px;
  gap: 10px;
}

.gender-button {
  flex: 1;
  padding: 10px;
  background-color: #f5f5f5;
  color: #333;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  text-align: center;
  transition: background-color 0.3s ease, color 0.3s ease;
}

.gender-button:hover {
  background-color: #e0e0e0;
}

.gender-button.active {
  background-color: #5eb75e;
  color: white;
  border-color: #5eb75e;
}

/* 다음 버튼 스타일 */
.next-button {
  width: 100%;
  max-width: 400px;
  padding: 10px;
  background-color: #5eb75e;
  color: white;
  border: none;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.next-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.next-button:not(:disabled):hover {
  background-color: #4ba64b;
}
</style>

