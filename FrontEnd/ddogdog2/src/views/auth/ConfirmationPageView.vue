<template>
  <div>
    <h1>입력 정보 확인</h1>
    <p>이메일: {{ signupStore.email }}</p>
    <p>닉네임: {{ signupStore.nickname }}</p>
    <p>휴대폰 번호: {{ signupStore.phoneNumber }}</p>
    <p>생년월일: {{ signupStore.birthdate }}</p>
    <p>성별: {{ signupStore.gender }}</p>
    <p>신장: {{ signupStore.height }}</p>
    <p>체중: {{ signupStore.weight }}</p>
    <p>위치: {{ signupStore.location }}</p>
    <button @click="completeSignup">회원가입 완료</button>
  </div>
</template>

<script setup>
import { useSignupStore } from '@/stores/signup'
import { useRouter } from 'vue-router'

const signupStore = useSignupStore()
const router = useRouter()
const completeSignup = async () => {
  
  try {
    await signupStore.signup() // 회원가입 API 호출
    router.push('/main') // 메인 페이지로 이동
  } catch (error) {
    console.error('회원가입 완료 실패:', error)
  }
}
</script>

<style scoped>
/* 스타일 추가 */
</style>
