<template>
  <div class="profile-page">
    <!-- 상단 헤더 -->
    <header class="header">
      <button class="back-button" @click="goBack">
        <i class="fi fi-rr-angle-left"></i>
      </button>
      <h2 class="header-title">도그워커 프로필 작성</h2>
    </header>

    <!-- 본문 내용 -->
    <div class="content">
      <form class="profile-form" @submit.prevent="submitProfile">
        <!-- 산책 가능 시간대 -->
        <label class="form-label">산책 가능 시간대</label>
        <div class="checkbox-group">
          <label class="checkbox-label">
            <input type="checkbox" v-model="profile.weekdayAm" />
            평일 오전
          </label>
          <label class="checkbox-label">
            <input type="checkbox" v-model="profile.weekdayPm" />
            평일 오후
          </label>
          <label class="checkbox-label">
            <input type="checkbox" v-model="profile.weekendAm" />
            주말 오전
          </label>
          <label class="checkbox-label">
            <input type="checkbox" v-model="profile.weekendPm" />
            주말 오후
          </label>
          <label class="checkbox-label">
            <input type="checkbox" v-model="profile.all" />
            무관
          </label>
        </div>

        <!-- 산책 가능 시간 -->
        <label class="form-label">산책 가능 시간</label>
        <select v-model="profile.time" class="form-select" required>
          <option value="0">1시간 미만</option>
          <option value="1">1-2시간</option>
          <option value="2">2-3시간</option>
          <option value="3">3시간 이상</option>
        </select>

        <!-- 대형견 산책 가능 여부 -->
        <label class="form-label">대형견 산책 가능 여부</label>
        <div class="button-group">
          <button
            type="button"
            @click="setHandle(true)"
            :class="{ active: profile.handle }"
            class="toggle-button"
          >
            Yes
          </button>
          <button
            type="button"
            @click="setHandle(false)"
            :class="{ active: !profile.handle }"
            class="toggle-button"
          >
            No
          </button>
        </div>

        <!-- 자기소개 -->
        <label class="form-label">자기소개</label>
        <textarea
          v-model="profile.introduce"
          class="form-textarea"
          maxlength="50"
          placeholder="간단한 자기소개를 작성해주세요 (50자 이내)"
        ></textarea>

        <!-- 제출 버튼 -->
        <button type="submit" class="action-button">프로필 작성 완료</button>
      </form>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

export default {
  setup() {
    const profile = reactive({
      weekdayAm: false,
      weekdayPm: false,
      weekendAm: false,
      weekendPm: false,
      all: false,
      time: null,
      handle: null,
      introduce: "",
    });

    const router = useRouter();

    const setHandle = (value) => {
      profile.handle = value;
    };

    const submitProfile = async () => {
      try {
        const userId = localStorage.getItem("user_id");
        if (!userId) {
          throw new Error("로그인된 사용자 정보를 찾을 수 없습니다.");
        }

        const profileData = {
          userId,
          weekdayAm: profile.weekdayAm,
          weekdayPm: profile.weekdayPm,
          weekendAm: profile.weekendAm,
          weekendPm: profile.weekendPm,
          all: profile.all,
          ableTime: profile.time,
          handle: profile.handle,
          introduce: profile.introduce,
        };

        await axios.put("http://localhost:8081/api/dogwalker", profileData, {
          headers: {
            "Content-Type": "application/json",
          },
        });

        alert(`도그워커 프로필이 성공적으로 업데이트되었습니다.`);
        router.push("/dogwalker-list");
      } catch (error) {
        console.error("프로필 작성 실패:", error);
        alert("프로필 작성 중 문제가 발생했습니다. 다시 시도해주세요.");
      }
    };

    const goBack = () => {
      router.push("/dog-walker-intro"); // 뒤로가기 클릭 시 이동할 페이지
    };

    return {
      profile,
      setHandle,
      submitProfile,
      goBack,
    };
  },
};
</script>

<style scoped>
/* 전체 페이지 스타일 */
.profile-page {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  padding: 20px;
  background-color: #ffffff;
  font-family: Arial, sans-serif;
  overflow-y: auto;
}

/* 헤더 스타일 */
.header {
  width: 100%;
  display: flex;
  align-items: center;
  margin-bottom: 20px;
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
  font-size: 1.5rem;
}

.header-title {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
  margin-left: 10px;
}

/* 본문 내용 */
.content {
  max-width: 400px;
  width: 100%;
  text-align: center;
}

/* 폼 스타일 */
.profile-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 라벨 스타일 */
.form-label {
  font-size: 1rem;
  font-weight: bold;
  color: #555;
  text-align: left;
}

/* 체크박스 그룹 */
.checkbox-group {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.checkbox-label {
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 1rem;
  color: #333;
}

/* 셀렉트 박스 */
.form-select {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  outline: none;
}

/* 토글 버튼 */
.button-group {
  display: flex;
  gap: 10px;
}

.toggle-button {
  flex: 1;
  padding: 10px;
  background-color: #f1f1f1;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  cursor: pointer;
}

.toggle-button.active {
  background-color: #5eb75e;
  color: white;
}

.toggle-button:hover {
  background-color: #e1e1e1;
}

/* 텍스트 에어리어 */
.form-textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  resize: none;
}

/* 제출 버튼 */
.action-button {
  padding: 10px;
  background-color: #5eb75e;
  color: white;
  border: none;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
}

.action-button:hover {
  background-color: #4ba64b;
}
</style>
