<template>
  <div class="signup-page">
    <header class="header">
      <button class="back-button" @click="goBack">
        <i class="fi fi-rr-angle-left"></i>
      </button>
    </header>

    <div class="content">
      <h2 class="message">
        휴대폰 번호를 입력해주세요📱
      </h2>

      <input
        v-model="phoneNumber"
        type="tel"
        placeholder="휴대폰 번호를 입력하세요"
        class="input-field"
      />

      <button class="next-button" :disabled="!phoneNumber" @click="nextStep">
        다음
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useSignupStore } from "@/stores/signup";

const phoneNumber = ref("");
const router = useRouter();
const signupStore = useSignupStore();

const goBack = () => {
  router.push("/signup/nickname");
};

const nextStep = () => {
  signupStore.setSignupData({ phoneNumber: phoneNumber.value });
  router.push("/signup/birth-gender");
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