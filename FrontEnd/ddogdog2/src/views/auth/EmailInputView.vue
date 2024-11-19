<template>
  <div>
    <h1>이메일 입력</h1>
    <input v-model="email" type="email" placeholder="이메일" />
    <button :disabled="!email" @click="nextStep">다음</button>
    <router-link to="/signup">뒤로가기</router-link>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useSignupStore } from '@/stores/signup'

const email = ref('')
const router = useRouter()
const signupStore = useSignupStore()

const nextStep = () => {
  signupStore.setSignupData({ email: email.value }) // 이메일 저장
  router.push('/signup/password') // 비밀번호 입력 페이지로 이동
}
</script>

<style scoped>
/* 스타일 추가 */
</style>
