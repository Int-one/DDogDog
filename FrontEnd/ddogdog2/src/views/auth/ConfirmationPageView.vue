<!-- <template>
  <div>
    <h1>입력 정보 확인</h1>
    <p>이메일: {{ signupStore.email }}</p>
    <p>닉네임: {{ signupStore.nickname }}</p>
    <p>휴대폰 번호: {{ signupStore.phoneNumber }}</p>
    <p>생년월일: {{ signupStore.birthdate }}</p>
    <p>성별: {{ signupStore.gender }}</p>
    <p>신장: {{ signupStore.height }}</p>
    <p>체중: {{ signupStore.weight }}</p>
    <p>위치: {{ signupStore.location }}</p>
    <button @click="completeSignup">회원가입 완료</button>
  </div>
</template>

<script setup>
import { useSignupStore } from '@/stores/signup'
import { useRouter } from 'vue-router'

const signupStore = useSignupStore()
const router = useRouter()
const completeSignup = async () => {
  
  try {
    await signupStore.signup() // 회원가입 API 호출
    router.push('/') // 메인 페이지로 이동
  } catch (error) {
    console.error('회원가입 완료 실패:', error)
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
        입력 정보를 확인해주세요😎
      </h2>

      <!-- 테이블 컨테이너 -->
      <div class="info-container">
        <table class="info-table">
          <tbody>
            <tr>
              <th>이메일</th>
              <td>{{ signupStore.email }}</td>
            </tr>
            <tr>
              <th>닉네임</th>
              <td>{{ signupStore.nickname }}</td>
            </tr>
            <tr>
              <th>휴대폰 번호</th>
              <td>{{ signupStore.phoneNumber }}</td>
            </tr>
            <tr>
              <th>생년월일</th>
              <td>{{ signupStore.birthdate }}</td>
            </tr>
            <tr>
              <th>성별</th>
              <td>{{ signupStore.gender === 1 ? "남성" : "여성" }}</td>
            </tr>
            <tr>
              <th>신장</th>
              <td>{{ signupStore.height }} cm</td>
            </tr>
            <tr>
              <th>체중</th>
              <td>{{ signupStore.weight }} kg</td>
            </tr>
            <tr>
              <th>위치</th>
              <td>{{ signupStore.location }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <!-- 회원가입 완료 버튼 -->
      <button @click="completeSignup" class="action-button">회원가입 완료</button>
    </div>
  </div>
</template>

<script setup>
import { useSignupStore } from "@/stores/signup";
import { useRouter } from "vue-router";

const signupStore = useSignupStore();
const router = useRouter();

// 회원가입 완료 처리
const completeSignup = async () => {
  try {
    await signupStore.signup(); // 회원가입 API 호출
    router.push("/login"); 
  } catch (error) {
    console.error("회원가입 완료 실패:", error);
  }
};

// 뒤로가기 버튼 동작
const goBack = () => {
  router.push("/signup/location");
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
  text-align: center;
  margin-bottom: 20px;
  width: 100%;
}

/* 테이블 컨테이너 스타일 */
.info-container {
  width: 100%;
  max-width: 400px;
  padding: 20px;
  background-color: #f9f9f9; /* 밝은 회색 배경 */
  border: 1px solid #ddd; /* 회색 테두리 */
  border-radius: 12px; /* 둥근 모서리 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 부드러운 그림자 */
}

/* 테이블 스타일 */
.info-table {
  width: 100%;
  border-collapse: collapse;
  font-size: 1rem;
}

.info-table th,
.info-table td {
  padding: 10px 0;
  text-align: left;
}

.info-table th {
  font-weight: bold;
  color: #555;
  width: 40%;
}

.info-table td {
  color: #333;
}

/* 회원가입 완료 버튼 */
.action-button {
  width: 100%;
  max-width: 400px;
  padding: 10px;
  font-size: 1rem;
  font-weight: bold;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  text-align: center;
  transition: background-color 0.3s ease;
  background-color: #5eb75e;
  color: white;
}

.action-button:hover {
  background-color: #4ba64b;
}
</style>
