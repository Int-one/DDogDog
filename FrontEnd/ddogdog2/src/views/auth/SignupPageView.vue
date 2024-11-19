<template>
  <div>
    <h1>회원가입</h1>
    <form @submit.prevent="handleSignup">
      <div>
        <h2>약관 동의</h2>
        <input type="checkbox" v-model="termsAccepted" />
        <label>모두 동의합니다</label>
      </div>
      <button :disabled="!termsAccepted">회원가입 시작</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const termsAccepted = ref(false)
const router = useRouter()

const handleSignup = () => {
  router.push('/signup/email') // 이메일 입력 페이지 이동
}
</script>

<style scoped>
/* 스타일 추가 */
</style>
