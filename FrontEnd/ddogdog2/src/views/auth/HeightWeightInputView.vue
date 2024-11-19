<template>
  <div>
    <h1>신장 및 체중 입력</h1>
    <input v-model="height" placeholder="신장 (cm)" />
    <input v-model="weight" placeholder="체중 (kg)" />
    <button :disabled="!height || !weight" @click="nextStep">다음</button>
    <router-link to="/signup/birth-gender">뒤로가기</router-link>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useSignupStore } from '@/stores/signup'

const height = ref('')
const weight = ref('')
const router = useRouter()
const signupStore = useSignupStore()


const nextStep = () => {
  signupStore.setSignupData({ height: height.value, weight: weight.value }) // 신장 및 체중 저장
  router.push('/signup/location') // 위치 정보 입력 페이지로 이동
}
</script>

<style scoped>
/* 스타일 추가 */
</style>
