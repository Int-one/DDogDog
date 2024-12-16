<template>
  <div class="signup-page">
    <!-- 상단 헤더 -->
    <header class="header">
      <button class="back-button" @click="goBack">
        <i class="fi fi-rr-angle-left"></i>
      </button>
      <h2 class="header-title">회원가입</h2>
    </header>

    <!-- 로고 섹션 -->
    <div class="logo-container">
      <div class="logo-circle">
        <img src="@/asset/ddogdog.png" alt="Logo" class="logo" />
      </div>
      <h1 class="logo-title">DDogDog</h1>
    </div>

    <!-- 회원가입 약관 폼 -->
    <form class="signup-form" @submit.prevent="handleSignup">
      <!-- 약관 동의 -->
      <div class="form-group">
        <!-- 모두 동의 -->
        <div class="terms-section">
          <div class="custom-checkbox" @click="toggleAllAccepted">
            <i :class="allAccepted ? 'fi fi-br-checkbox' : 'fi fi-sr-square unchecked'"></i>
            <label><strong>모두 동의합니다.</strong></label>
          </div>
        </div>

        <!-- 필수 동의 -->
        <div class="terms-section">
          <div
            class="custom-checkbox"
            v-for="(term, index) in terms.required"
            :key="'required-' + index"
            @click="toggleIndividual(index, 'required')"
          >
            <i :class="term.checked ? 'fi fi-br-checkbox' : 'fi fi-sr-square unchecked'"></i>
            <label>{{ term.text }}</label>
          </div>
        </div>

        <!-- 선택 동의 -->
        <div class="terms-section">
          <div
            class="custom-checkbox"
            v-for="(term, index) in terms.optional"
            :key="'optional-' + index"
            @click="toggleIndividual(index, 'optional')"
          >
            <i :class="term.checked ? 'fi fi-br-checkbox' : 'fi fi-sr-square unchecked'"></i>
            <label>{{ term.text }}</label>
          </div>
        </div>
      </div>

      <!-- 회원가입 시작 버튼 -->
      <div class="button-group">
        <button
          type="submit"
          class="signup-button"
          :disabled="!canProceed"
        >
          똑똑한 산책 시작하기
        </button>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

// 뒤로 가기 함수
const goBack = () => {
  router.push("/login");
};

// 약관 데이터
const terms = ref({
  required: [
    { text: "서비스 이용 약관 동의 (필수)", checked: false },
    { text: "개인정보 수집 및 이용 동의 (필수)", checked: false },
  ],
  optional: [
    { text: "마케팅 정보 수신 동의 (선택)", checked: false },
    { text: "제3자 정보 제공 동의 (선택)", checked: false },
  ],
});

const allAccepted = ref(false);

// 필수 동의 항목이 모두 체크되었는지 확인
const canProceed = computed(() =>
  terms.value.required.every((term) => term.checked)
);

// 모두 동의 버튼 토글
const toggleAllAccepted = () => {
  allAccepted.value = !allAccepted.value;
  terms.value.required.forEach((term) => (term.checked = allAccepted.value));
  terms.value.optional.forEach((term) => (term.checked = allAccepted.value));
};

// 개별 항목 토글
const toggleIndividual = (index, type) => {
  terms.value[type][index].checked = !terms.value[type][index].checked;
  allAccepted.value =
    terms.value.required.every((term) => term.checked) &&
    terms.value.optional.every((term) => term.checked);
};

// 회원가입 시작 버튼 클릭 시
const handleSignup = () => {
  if (canProceed.value) {
    router.push("/signup/email"); // 이메일 입력 페이지로 이동
  } else {
    alert("필수 약관을 모두 동의해야 합니다.");
  }
};
</script>

<style scoped>
/* 전체 페이지 스타일 */
.signup-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  padding: 10px;
  background-color: #ffffff;
  font-family: Arial, sans-serif;
}

/* 헤더 스타일 */
.header {
  position: absolute;
  top: 40px; /* 조금 아래로 이동 */
  left: 10px;
  right: 0px;
  display: flex;
  align-items: center;
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
  color: #333; /* 검정색 */
  margin-top: 5px;
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

/* 회원가입 폼 */
.signup-form {
  width: 100%;
  max-width: 400px;
  padding: 30px;
  background: #ffffff;
  border-radius: 16px;
}

.form-group {
  margin-bottom: 15px;
}

.form-title {
  font-size: 1.5rem;
  font-weight: bold;
  color: #5eb75e;
  margin-bottom: 10px;
}


.custom-checkbox {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.custom-checkbox i {
  font-size: 1.5rem;
}

.custom-checkbox .unchecked {
  color: #ccc; /* 선택되지 않은 상태의 색상 */
}

.custom-checkbox .fi-br-checkbox {
  color: #5eb75e; /* 선택된 상태의 색상 */
}

.custom-checkbox label {
  font-size: 1rem;
  color: #333;
}

/* 버튼 섹션 */
.button-group {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.signup-button {
  width: 100%;
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

.signup-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.signup-button:not(:disabled):hover {
  background-color: #4ba64b;
}
.custom-checkbox label {
  font-size: 0.9rem; /* 글씨 크기를 기존보다 작게 조정 */
  color: #333;
}
</style>
