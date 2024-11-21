<template>
  <div>
    <h1>도그워커 프로필 작성</h1>
    <form @submit.prevent="submitProfile">
      <label>산책 가능 시간대</label>
      <div>
        <label><input type="checkbox" v-model="profile.weekdayAm" /> 평일 오전</label>
        <label><input type="checkbox" v-model="profile.weekdayPm" /> 평일 오후</label>
        <label><input type="checkbox" v-model="profile.weekendAm" /> 주말 오전</label>
        <label><input type="checkbox" v-model="profile.weekendPm" /> 주말 오후</label>
        <label><input type="checkbox" v-model="profile.all" /> 무관</label>
      </div>

      <label>산책 가능 시간</label>
      <select v-model="profile.walkTime" required>
        <option value="0">1시간 미만</option>
        <option value="1">1-2시간</option>
        <option value="2">2-3시간</option>
        <option value="3">3시간 이상</option>
      </select>

      <label>대형견 산책 가능 여부</label>
      <div>
        <button type="button" @click="setHandle(true)" :class="{ active: profile.handle }">Yes</button>
        <button type="button" @click="setHandle(false)" :class="{ active: !profile.handle }">No</button>
      </div>

      <label>자기소개</label>
      <textarea v-model="profile.introduce" maxlength="50"></textarea>

      <button type="submit">프로필 작성 완료</button>
    </form>
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

    return {
      profile,
      setHandle,
      submitProfile,
    };
  },
};
</script>

<style>
.active {
  background-color: #007bff;
  color: white;
}
</style>
