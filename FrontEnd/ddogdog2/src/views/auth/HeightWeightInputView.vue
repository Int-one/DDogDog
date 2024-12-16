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
        신장과 체중을 입력해주세요📊
      </h2>

      <!-- 신장 및 체중 입력 필드 -->
      <div class="input-container">
        <!-- 신장 입력 -->
        <div class="square-input">
          <span class="value">{{ height || "--" }}</span>
          <span class="unit">cm</span>
          <input
            v-model.number="height"
            type="number"
            placeholder="신장"
            class="hidden-input"
            @wheel.prevent="adjustValue($event, 'height')"
          />
        </div>

        <!-- 체중 입력 -->
        <div class="square-input">
          <span class="value">{{ weight || "--" }}</span>
          <span class="unit">kg</span>
          <input
            v-model.number="weight"
            type="number"
            placeholder="체중"
            class="hidden-input"
            @wheel.prevent="adjustValue($event, 'weight')"
          />
        </div>
      </div>

      <!-- 다음 버튼 -->
      <button class="next-button" :disabled="!isValidHeight || !isValidWeight" @click="nextStep">
        다음
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useRouter } from "vue-router";
import { useSignupStore } from "@/stores/signup";

const height = ref("");
const weight = ref("");
const router = useRouter();
const signupStore = useSignupStore();

// 뒤로가기 버튼 동작
const goBack = () => {
  router.push("/signup/birth-gender");
};

// 다음 단계로 이동
const nextStep = () => {
  signupStore.setSignupData({ height: height.value, weight: weight.value });
  router.push("/signup/location");
};

// 신장과 체중 값 유효성 검사
const isValidHeight = computed(() => height.value >= 100 && height.value < 301);
const isValidWeight = computed(() => weight.value >= 30 && weight.value < 251);

// 마우스 휠로 값 조정
const adjustValue = (event, type) => {
  const increment = event.deltaY < 0 ? 1 : -1; // 휠 위로 올리면 증가, 아래로 내리면 감소
  if (type === "height") {
    const newValue = height.value + increment;
    height.value = Math.max(100, Math.min(newValue, 300)); // 범위: 100 ~ 300
  } else if (type === "weight") {
    const newValue = weight.value + increment;
    weight.value = Math.max(30, Math.min(newValue, 250)); // 범위: 30 ~ 250
  }
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

/* 입력 컨테이너 스타일 */
.input-container {
  display: flex;
  justify-content: space-between;
  width: 100%;
  max-width: 400px;
  gap: 10px; /* 신장과 체중 입력 필드 사이 간격 */
}

/* 정사각형 입력 필드 스타일 */
.square-input {
  position: relative;
  width: 100%;
  aspect-ratio: 1; /* 정사각형 유지 */
  border: 1px solid #ddd;
  border-radius: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f9f9f9;
  font-size: 2.5rem; /* 값 크기 키움 */
  font-weight: bold; /* 값 굵게 */
}

.value {
  display: inline-block;
  line-height: 1;
  text-align: right;
}

.unit {
  font-size: 1.5rem; /* 단위 크기 */
  font-weight: normal; /* 단위 굵기 줄임 */
  color: #666;
  margin-left: 5px; /* 값과 단위 간 간격 조정 */
}

/* 숨겨진 입력 필드 스타일 */
.hidden-input {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  cursor: pointer;
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
