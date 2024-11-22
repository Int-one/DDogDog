
import { defineStore } from 'pinia';
import axios from 'axios';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null, // 사용자 정보
    token: null, // JWT 토큰
  }),
  actions: {
    // 로그인 액션
    async login(userId, password) {
      try {
        // API 호출로 로그인 처리
        const response = await axios.post('http://localhost:8081/api/user/login', { userId, password });

        // 사용자 정보와 토큰 저장
        this.token = response.data['access-token'];
        const region = response.data['region']; // 사용자 지역 정보
        const tokens = this.token.split('.');
        const user_id = JSON.parse(atob(tokens[1]))['userId']
        console.log(user_id)
        console.log(this.token)
        
        // 로컬 스토리지에 JWT 저장
        localStorage.setItem('token', this.token);
        localStorage.setItem('user_id', user_id);
        localStorage.setItem('region', region);
        


        // Axios 헤더에 토큰 설정
        axios.defaults.headers.common['Authorization'] = `Bearer ${this.token}`;
      } catch (error) {
        console.error('로그인 실패:', error);
        throw error; // 에러를 호출자에게 전달
      }
    },

    // 로그아웃 액션
    logout() {
      this.user = null;
      this.token = null;

      // 로컬 스토리지에서 JWT 제거
      localStorage.removeItem('token');

      // Axios 헤더에서 Authorization 제거
      delete axios.defaults.headers.common['Authorization'];
    },

    // 저장된 토큰으로 사용자 인증 상태 초기화
    initializeAuth() {
      const savedToken = localStorage.getItem('token');
      if (savedToken) {
        this.token = savedToken;
        axios.defaults.headers.common['Authorization'] = `Bearer ${savedToken}`;

        // API 호출로 사용자 정보 갱신
        this.fetchUser();
      }
    },

    // 사용자 정보 갱신
    async fetchUser() {
      try {
        const response = await axios.get('http://localhost:8081/api/user/me'); // 사용자 정보를 가져오는 엔드포인트
        this.user = response.data;
      } catch (error) {
        console.error('사용자 정보를 불러오지 못했습니다:', error);
        this.logout(); // 토큰이 유효하지 않으면 로그아웃 처리
      }
    },

    async updateDogWalkerStatus(userId, dogWalkerStatus) {
      try {
        console.log({userId, dogWalker: dogWalkerStatus})
        const response = await axios.put("http://localhost:8081/api/user/dogwalker", {
          userId,
          dogWalker: dogWalkerStatus,
        });
        console.log("도그워커 상태 업데이트 성공:", response.data);
      } catch (error) {
        console.error("도그워커 상태 업데이트 실패:", error);
        throw error;
      }
    },
  },
});


