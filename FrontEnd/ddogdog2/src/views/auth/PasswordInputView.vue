<template>
  <div>
    <h1>비밀번호 입력</h1>
    <input v-model="password" type="password" placeholder="비밀번호" />
    <button :disabled="!password" @click="nextStep">다음</button>
    <router-link to="/signup/email">뒤로가기</router-link>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useSignupStore } from '@/stores/signup'

const password = ref('')
const router = useRouter()
const signupStore = useSignupStore()

const nextStep = () => {
  signupStore.setSignupData({ password: password.value }) // 비밀번호 저장
  router.push('/signup/nickname') // 닉네임 입력 페이지로 이동
}
</script>

<style scoped>
/* 스타일 추가 */
</style>
