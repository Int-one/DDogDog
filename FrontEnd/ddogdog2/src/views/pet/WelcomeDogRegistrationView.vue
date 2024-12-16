<!-- 
<template>
  <div class="intro-page">
    
    <div class="content">
      <h1 class="title">환영해요🥳</h1>
      <p class="description">
        
        내 반려견을 등록하고 <br> 함께한 산책을 기록하세요. 🐾
      </p>

      
      <div class="button-group">
        <button @click="goToPetRegistration" class="action-button">내 반려견 등록하러가기</button>
        <button @click="skipToDogWalkerIntro" class="skip-button">건너뛰기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    // 반려견 등록 페이지로 이동
    goToPetRegistration() {
      this.$router.push("/pet-registration");
    },
    // 도그워커 소개 페이지로 건너뛰기
    skipToDogWalkerIntro() {
      this.$router.push("/dog-walker-intro");
    },
  },
};
</script>

<style scoped>
/* 전체 페이지 스타일 */
.intro-page {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  padding: 20px;
  background-color: #ffffff;
  font-family: Arial, sans-serif;
}

/* 본문 내용 스타일 */
.content {
  text-align: center;
  max-width: 400px;
  margin: 0 auto;
}

.title {
  font-size: 1.8rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px;
}

.description {
  font-size: 1rem;
  color: #666;
  line-height: 1.5;
  margin-bottom: 30px;
}

/* 버튼 그룹 스타일 */
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
</style> -->
<template>
  <div class="intro-page">
    <!-- 본문 내용 -->
    <div class="content">
      <h1 class="title">환영해요🥳</h1>
      
      <!-- 이미지 추가 -->
      <div class="image-container">
        <img src="@/asset/dog.png" alt="Dog Image" class="dog-image" />
      </div>

      <p class="description">
        내 반려견을 등록하고 <br /> 
        함께한 산책을 기록하세요🐾
      </p>

      <!-- 버튼 섹션 -->
      <div class="button-group">
        <button @click="goToPetRegistration" class="action-button">내 반려견 등록하러가기</button>
        <button @click="skipToDogWalkerIntro" class="skip-button">건너뛰기</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    // 반려견 등록 페이지로 이동
    goToPetRegistration() {
      this.$router.push("/pet-registration");
    },
    // 도그워커 소개 페이지로 건너뛰기
    skipToDogWalkerIntro() {
      this.$router.push("/dog-walker-intro");
    },
  },
};
</script>

<style scoped>
/* 전체 페이지 스타일 */
.intro-page {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100vh;
  padding: 20px;
  background-color: #ffffff;
  font-family: Arial, sans-serif;
}

/* 본문 내용 스타일 */
.content {
  text-align: center;
  max-width: 400px;
  margin: 0 auto;
}

.title {
  font-size: 1.8rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px;
}

/* 이미지 컨테이너 스타일 */
.image-container {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-bottom: 20px;
}

.dog-image {
  width: 300px; /* 이미지 크기 크게 변경 */
  height: auto;
  border-radius: 12px; /* 이미지 모서리를 둥글게 */
}

.description {
  font-size: 1rem;
  color: #666;
  line-height: 1.5;
  margin-bottom: 30px;
}

/* 버튼 그룹 스타일 */
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
