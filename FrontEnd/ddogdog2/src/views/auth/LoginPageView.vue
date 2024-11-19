<template>
  <div>
    <h1>로그인</h1>
    <input v-model="email" type="email" placeholder="이메일" />
    <input v-model="password" type="password" placeholder="비밀번호" />
    <button @click="handleLogin">로그인</button>
    <router-link to="/signup">회원가입</router-link>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { useRouter } from 'vue-router'

const email = ref('')
const password = ref('')
const authStore = useAuthStore()
const router = useRouter()

const handleLogin = async () => {
  await authStore.login(email.value, password.value)
  if (authStore.user) {
    router.push('/main') // 메인 페이지로 이동
  }
}
</script>

<style scoped>
/* 스타일 추가 */
</style>
