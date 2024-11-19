import { defineStore } from 'pinia'
import axios from 'axios'

export const useSignupStore = defineStore('signup', {
  state: () => ({
    email: '',
    password: '',
    nickname: '',
    phoneNumber: '',
    birthdate: '',
    gender: '',
    height: '',
    weight: '',
    location: ''
  }),
  actions: {
    // 사용자 입력 데이터를 설정하는 메서드 추가
    setSignupData(data) {
      Object.assign(this, data) // 전달받은 데이터를 상태에 병합
    },

    formatDate(input) {
    
      // 연도, 월, 일 추출
      const year = input.substring(0, 4);
      const month = input.substring(4, 6);
      const day = input.substring(6, 8);
      
      // 형식화된 문자열 생성
      return `${year}-${month}-${day}T00:00:00`;
    },
    
    // 회원가입 API 호출
    async signup() {
      try {
        const birth = this.formatDate(this.birthdate)
        const userData = {
          userId: this.email,
          password: this.password,
          nickname: this.nickname,
          birth: birth,
          phone: this.phoneNumber,
          height: +this.height,
          weight: +this.weight,
          gender: +this.gender,
          region: this.location
        };


        await axios.post('http://localhost:8081/api/user', userData)
      } catch (error) {
        console.error('회원가입 실패:', error)
        throw error // 에러를 호출자에게 전달
      }
    },
    
    // 사용자 입력 데이터 초기화
    clearSignupData() {
      this.email = ''
      this.password = ''
      this.nickname = ''
      this.phoneNumber = ''
      this.birthdate = ''
      this.gender = ''
      this.height = ''
      this.weight = ''
      this.location = ''
    }
  }
})
