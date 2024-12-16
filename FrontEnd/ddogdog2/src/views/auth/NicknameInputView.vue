<!-- <template>
  <div>
    <h1>닉네임 입력</h1>
    <input v-model="nickname" placeholder="닉네임" />
    <button :disabled="!nickname" @click="nextStep">다음</button>
    <router-link to="/signup/password">뒤로가기</router-link>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useSignupStore } from '@/stores/signup'


const nickname = ref('')
const router = useRouter()
const signupStore = useSignupStore()

const nextStep = () => {
  signupStore.setSignupData({ nickname: nickname.value }) // 닉네임 저장
  router.push('/signup/phone-number') // 전화번호 입력 페이지로 이동
}
</script>

<style scoped>
/* 스타일 추가 */
</style> -->

<template>
  <div class="signup-page">
    <header class="header">
      <button class="back-button" @click="goBack">
        <i class="fi fi-rr-angle-left"></i>
      </button>
    </header>

    <div class="content">
      <h2 class="message">
        닉네임을 입력해주세요😊
      </h2>

      <input
        v-model="nickname"
        type="text"
        placeholder="닉네임을 입력하세요"
        class="input-field"
      />

      <button class="next-button" :disabled="!nickname" @click="nextStep">
        다음
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useSignupStore } from "@/stores/signup";

const nickname = ref("");
const router = useRouter();
const signupStore = useSignupStore();

const goBack = () => {
  router.push("/signup/password");
};

const nextStep = () => {
  signupStore.setSignupData({ nickname: nickname.value });
  router.push("/signup/phone-number");
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
  top: 40px; /* 조금 아래로 이동 */
  right: 0px;
  display: flex;
  align-items: center;
  margin-bottom: 40px; /* 헤더와 본문 간 간격 추가 */
  gap: 
  0px;
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

.back-button i {
  font-size: 1.2rem;
}

.header-title {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
}

/* 본문 스타일 */
.content {
  margin-left: 10px;
  margin-right: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
}

/* 메시지 스타일 */
.message {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
  text-align: left; 
  margin-top: 30px;
  width: 100%; 
  
}

/* 입력 필드 스타일 */
.input-field {
  width: 100%;
  max-width: 400px;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ddd;
  border-radius: 8px;
  outline: none;
}

/* 버튼 스타일 */
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
