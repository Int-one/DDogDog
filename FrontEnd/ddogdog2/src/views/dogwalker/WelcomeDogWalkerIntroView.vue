<template>
  <div class="dog-walker-intro-page">
    <!-- 본문 내용 -->
    <div class="content">
      <h1 class="title">도그워커 소개</h1>

      <!-- 중앙 이미지 -->
      <div class="image-container">
        <img src="@/asset/dog.png" alt="Dog" class="dog-image" />
      </div>

      <!-- 설명 -->
      <p class="description">
        도그워커 서비스에 대해 알아보세요.<br />
        반려견과 더 나은 시간을 만들어 드립니다. 🐾
      </p>

      <!-- 버튼 그룹 -->
      <div class="button-group">
        <button @click="goToDogWalkerSignup" class="action-button">도그워커 데뷔하기</button>
        <button @click="goToDogWalkerList" class="action-button">도그워커 서비스 이용하기</button>
        <button @click="goToMain" class="skip-button">건너뛰기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    goToDogWalkerSignup() {
      this.$router.push("/dog-walker-signup");
    },
    goToDogWalkerList() {
      this.$router.push("/dog-walker-list");
    },
    goToMain() {
      this.$router.push("/");
    },
  },
};
</script>

<style scoped>
/* 전체 페이지 스타일 */
.dog-walker-intro-page {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  padding: 20px;
  background-color: #ffffff;
  font-family: Arial, sans-serif;
}

/* 본문 내용 */
.content {
  max-width: 400px;
  text-align: center;
}

/* 제목 */
.title {
  font-size: 1.8rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px;
}

/* 이미지 컨테이너 */
.image-container {
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
}

.dog-image {
  width: 150px; /* 이미지 크기 */
  height: auto;
}

/* 설명 */
.description {
  font-size: 1rem;
  color: #666;
  line-height: 1.5;
  margin-bottom: 30px;
}

/* 버튼 그룹 */
.button-group {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

/* 액션 버튼 스타일 */
.action-button {
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

.action-button:hover {
  background-color: #4ba64b;
}

/* 건너뛰기 버튼 스타일 */
.skip-button {
  width: 100%;
  padding: 10px;
  background-color: white;
  color: #5eb75e;
  border: 2px solid #5eb75e;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
}

.skip-button:hover {
  background-color: #f1f1f1;
}
</style>
