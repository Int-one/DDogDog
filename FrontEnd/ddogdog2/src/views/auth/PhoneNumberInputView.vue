<template>
  <div>
    <h1>휴대폰 번호 입력</h1>
    <input v-model="phoneNumber" placeholder="휴대폰 번호" />
    <button :disabled="!phoneNumber" @click="nextStep">다음</button>
    <router-link to="/signup/nickname">뒤로가기</router-link>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useSignupStore } from '@/stores/signup'


const phoneNumber = ref('')
const router = useRouter()
const signupStore = useSignupStore()

const nextStep = () => {
  signupStore.setSignupData({ phoneNumber: phoneNumber.value }) // 전화번호 저장
  router.push('/signup/birth-gender') // 생년월일 및 성별 입력 페이지로 이동
}
</script>

<style scoped>
/* 스타일 추가 */
</style>
