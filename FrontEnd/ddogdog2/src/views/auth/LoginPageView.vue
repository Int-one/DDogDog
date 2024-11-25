<!-- 
<template>
  <div class="login-page">
    
    <div class="logo-container">
      <div class="logo-circle">
        <img src="@/asset/ddogdog.png" alt="Logo" class="logo" />
      </div>
      <h1 class="logo-title">DDogDog</h1>
    </div>

    <form class="login-form" @submit.prevent="handleLogin">
  
      <div class="form-group">
        <label for="userId" class="form-label">ID(e-mail)</label>
        <input
          type="text"
          id="userId"
          v-model="userId"
          placeholder="아이디를 입력하세요"
          class="form-input"
          required
        />
      </div>

     
      <div class="form-group">
        <label for="password" class="form-label">Password</label>
        <input
          type="password"
          id="password"
          v-model="password"
          placeholder="비밀번호를 입력하세요"
          class="form-input"
          required
        />
      </div>

     
      <div class="button-group">
        <button type="submit" class="login-button">로그인</button>
        <router-link to="/signup" class="signup-button">DDogDog 회원가입</router-link>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const userId = ref("");
const password = ref("");

// 로그인 처리 함수
const handleLogin = async () => {
  try {
    const response = await fetch("http://localhost:8081/api/login", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ userId: userId.value, password: password.value }),
    });

    if (!response.ok) throw new Error("로그인 실패");

    const data = await response.json();
    localStorage.setItem("user_id", data.userId);
    localStorage.setItem("token", data.token);
    alert("로그인 성공");
    router.push("/doforme");
  } catch (error) {
    console.error(error);
    alert("로그인에 실패했습니다.");
  }
};
</script> -->

<template>
  <div class="login-page">
    <!-- 로고 섹션 -->
    <div class="logo-container">
      <div class="logo-circle">
        <img src="@/asset/ddogdog.png" alt="Logo" class="logo" />
      </div>
      <h1 class="logo-title">DDogDog</h1>
    </div>

    <!-- 로그인 폼 -->
    <form class="login-form" @submit.prevent="handleLogin">
      <!-- 이메일 입력 -->
      <div class="form-group">
        <label for="email" class="form-label">이메일</label>
        <input
          type="email"
          id="email"
          v-model="email"
          placeholder="이메일을 입력하세요"
          class="form-input"
          required
        />
      </div>

      <!-- 비밀번호 입력 -->
      <div class="form-group">
        <label for="password" class="form-label">비밀번호</label>
        <input
          type="password"
          id="password"
          v-model="password"
          placeholder="비밀번호를 입력하세요"
          class="form-input"
          required
        />
      </div>

      <!-- 버튼 섹션 -->
      <div class="button-group">
        <button type="submit" class="login-button">로그인</button>
        <router-link to="/signup" class="signup-button">회원가입</router-link>
      </div>
    </form>
    <p v-if="errorMessage" class="error-message">{{ errorMessage }}</p>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useAuthStore } from "@/stores/auth"; // 인증 스토어 사용

const router = useRouter();
const authStore = useAuthStore();

const email = ref("");
const password = ref("");
const errorMessage = ref("");

// 로그인 처리 함수
const handleLogin = async () => {
  try {
    await authStore.login(email.value, password.value); // 스토어의 로그인 함수 호출
    router.push("/"); // 로그인 성공 시 메인 페이지로 이동
  } catch (error) {
    console.error("로그인 실패:", error);
    errorMessage.value = "로그인 실패. 이메일 또는 비밀번호를 확인하세요.";
  }
};
</script>
<style scoped>
/* 전체 페이지 스타일 */
.login-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  padding: 10px;
  background-color: #ffffff;
  font-family: Arial, sans-serif;
}

/* 로고 섹션 */
.logo-container {
  text-align: center;
}

.logo-circle {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 180px; 
  height: 180px; 
  border: 2px dashed #ccc;
  border-radius: 50%;
  margin: 0 auto;
}

.logo {
  width: 120px; 
  height: auto;
}

.logo-title {
  font-size: 2.2rem;
  font-weight: bold;
  color: #5eb75e;
  margin-top: 15px;
}

/* 로그인 폼 */
.login-form {
  width: 100%;
  max-width: 400px;
  padding: 30px;
  background: #ffffff;
  border-radius: 16px;
}

.form-group {
  margin-bottom: 15px;
}

.form-label {
  display: block;
  font-size: 0.9rem;
  margin-bottom: 1px;
  color: #5eb75e;
}

.form-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 12px;
  font-size: 1rem;
  box-sizing: border-box;
}

/* 버튼 스타일 */
.button-group {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.login-button {
  padding: 10px; 
  background-color: #5eb75e;
  color: white;
  border: none;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
}

.login-button:hover {
  background-color: #4ba64b;
}

.signup-button {
  padding: 8px; 
  text-align: center;
  border: 2px solid #5eb75e;
  background-color: white;
  color: #5eb75e;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: bold;
  text-decoration: none;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
}

.signup-button:hover {
  background-color: #f1f1f1;
}
</style>
