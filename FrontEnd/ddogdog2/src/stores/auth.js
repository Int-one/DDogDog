// import { ref, computed } from 'vue'
// import { defineStore } from 'pinia'

// export const useCounterStore = defineStore('counter', () => {
//   const count = ref(0)
//   const doubleCount = computed(() => count.value * 2)
//   function increment() {
//     count.value++
//   }

//   return { count, doubleCount, increment }
// })


// src/stores/auth.js
import { defineStore } from 'pinia'
import axios from 'axios'

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null,
    token: null,
  }),
  actions: {
    async login(email, password) {
      try {
        const response = await axios.post('/api/login', { email, password })
        this.user = response.data.user
        this.token = response.data.token
        localStorage.setItem('token', this.token) // JWT 저장
      } catch (error) {
        console.error('로그인 실패:', error)
        throw error // 에러를 호출자에게 전달
      }
    },
    async signup(userData) {
      try {
        await axios.post('/api/signup', userData)
      } catch (error) {
        console.error('회원가입 실패:', error)
        throw error // 에러를 호출자에게 전달
      }
    },
    logout() {
      this.user = null
      this.token = null
      localStorage.removeItem('token') // JWT 삭제
    },
  },
})

