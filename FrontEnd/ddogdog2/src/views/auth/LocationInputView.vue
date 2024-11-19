<template>
  <div>
    <h1>위치 정보 입력</h1>
    <p>현재 위치가 맞습니까?</p>
    <button @click="confirmLocation">맞아요</button>
    <button @click="declineLocation">아니에요</button>
    <router-link to="/signup/height-weight">뒤로가기</router-link>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { useSignupStore } from '@/stores/signup'

const router = useRouter()
const signupStore = useSignupStore()

const confirmLocation = () => {
  // 위치 정보를 확인하고 저장
  signupStore.setSignupData({ location: '현재 위치' }) // 임시 위치 저장
  router.push('/signup/confirmation') // 입력 정보 확인 페이지로 이동
}

const declineLocation = () => {
  // 위치 정보 거부 처리
}
</script>

<style scoped>
/* 스타일 추가 */
</style>
