<template>
  <div>
    <h1>닉네임 입력</h1>
    <input v-model="nickname" placeholder="닉네임" />
    <button :disabled="!nickname" @click="nextStep">다음</button>
    <router-link to="/signup/password">뒤로가기</router-link>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useSignupStore } from '@/stores/signup'


const nickname = ref('')
const router = useRouter()
const signupStore = useSignupStore()

const nextStep = () => {
  signupStore.setSignupData({ nickname: nickname.value }) // 닉네임 저장
  router.push('/signup/phone-number') // 전화번호 입력 페이지로 이동
}
</script>

<style scoped>
/* 스타일 추가 */
</style>
