<template>
    <div>
      <h1>도그워커 프로필 작성</h1>
      <form @submit.prevent="submitProfile">
        <label>소개글</label>
        <textarea v-model="profile.introduction" required></textarea>
  
        <label>서비스 가능 지역</label>
        <input v-model="profile.serviceArea" type="text" required />
  
        <button type="submit">프로필 저장</button>
      </form>
    </div>
  </template>
  
  <script>
  import { reactive } from "vue";
  
  export default {
    setup() {
      const profile = reactive({
        introduction: "",
        serviceArea: "",
      });
  
      const submitProfile = () => {
        console.log("프로필 저장:", profile);
        // API 요청 로직 추가
      };
  
      return {
        profile,
        submitProfile,
      };
    },
  };
  </script>
  