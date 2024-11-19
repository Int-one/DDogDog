<template>
  <div>
    <h1>생년월일 및 성별 입력</h1>
    <input v-model="birthdate" placeholder="YYYYMMDD" @input="detectLength"/>
    <div>
      <label><input type="radio" v-model="gender" value=1 /> 남자</label>
      <label><input type="radio" v-model="gender" value=0 /> 여자</label>
    </div>
    <button :disabled="!birthdate || !gender" @click="nextStep">다음</button>
    <router-link to="/signup/phone-number">뒤로가기</router-link>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useSignupStore } from '@/stores/signup'

const birthdate = ref('')
const gender = ref()
const router = useRouter()
const signupStore = useSignupStore()

const nextStep = () => {

  signupStore.setSignupData({ birthdate: birthdate.value, gender: gender.value }) // 생년월일 및 성별 저장
  router.push('/signup/height-weight') // 신장 및 체중 입력 페이지로 이동
}

const detectLength = () => {
    // 입력 문자열이 8자리인지 확인
    if (birthdate.value.length !== 8) {
        console.log("Not match!!!")
    }
}
</script>

<style scoped>
/* 스타일 추가 */
</style>
