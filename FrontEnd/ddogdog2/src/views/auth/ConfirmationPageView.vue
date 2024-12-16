<!-- 
<template>
  <div class="signup-page">
   
    <header class="header">
      <button class="back-button" @click="goBack">
        <i class="fi fi-rr-angle-left"></i>
      </button>
    </header>

   
    <div class="content">
      <h2 class="message">
        입력 정보를 확인해주세요😎
      </h2>

     
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
        입력하신 정보를 확인해주세요😎
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
import { useAuthStore } from "@/stores/auth";
import { useRouter } from "vue-router";

const signupStore = useSignupStore();
const authStore = useAuthStore();
const router = useRouter();

// 회원가입 완료 처리
const completeSignup = async () => {
  try {
    // 회원가입 API 호출
    await signupStore.signup();

    // 회원가입 완료 후 자동 로그인
    const email = signupStore.email;
    const password = signupStore.password; // 비밀번호를 스토어에서 가져오거나 직접 전달
    await authStore.login(email, password);

    // 웰컴 페이지로 이동
    router.push("/welcome-dog");
  } catch (error) {
    console.error("회원가입 완료 실패:", error);
    alert("회원가입 중 오류가 발생했습니다. 다시 시도해주세요.");
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
