import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from "@/stores/auth";
import { usePetStore } from "@/stores/pet";

import LoginPageView from '../views/auth/LoginPageView.vue';
import SignupPageView from '../views/auth/SignupPageView.vue';
import EmailInputView from '../views/auth/EmailInputView.vue';
import PasswordInputView from '../views/auth/PasswordInputView.vue';
import NicknameInputView from '../views/auth/NicknameInputView.vue';
import PhoneNumberInputView from '../views/auth/PhoneNumberInputView.vue';
import BirthGenderInputView from '../views/auth/BirthGenderInputView.vue';
import HeightWeightInputView from '../views/auth/HeightWeightInputView.vue';
import LocationInputView from '../views/auth/LocationInputView.vue';
import ConfirmationPageView from '../views/auth/ConfirmationPageView.vue';
import MainView from '../views/main/MainView.vue';
import WelcomeDogRegistrationView from '../views/pet/WelcomeDogRegistrationView.vue';
import PetRegistrationView from '../views/pet/PetRegistrationView.vue';
import WelcomeDogWalkerIntroView from '../views/dogwalker/WelcomeDogWalkerIntroView.vue';
import WalkTrack from '@/views/walk/WalkTrack.vue';
import WalkLog from '@/views/walk/WalkLog.vue';
import DataTest from '@/views/walk/DataTest.vue';

import DogWalkerSignupView from '@/views/dogwalker/DogWalkerSignupView.vue'; // 추가
import DogWalkerProfileView from '@/views/dogwalker/DogWalkerProfileView.vue'; // 추가
import DogWalkerDetailView from '@/views/dogwalker/DogWalkerDetailView.vue';
import DogWalkerListView from '@/views/dogwalker/DogWalkerListView.vue'; // 추가
import TestView from '../views/TestView.vue';

const routes = [
  { path: '/', component: LoginPageView }, // 로그인 페이지
  { path: '/signup', component: SignupPageView }, // 회원가입 페이지
  { path: '/signup/email', component: EmailInputView }, // 이메일 입력 페이지
  { path: '/signup/password', component: PasswordInputView }, // 비밀번호 입력 페이지
  { path: '/signup/nickname', component: NicknameInputView }, // 닉네임 입력 페이지
  { path: '/signup/phone-number', component: PhoneNumberInputView }, // 전화번호 입력 페이지
  { path: '/signup/birth-gender', component: BirthGenderInputView }, // 생년월일 및 성별 입력 페이지
  { path: '/signup/height-weight', component: HeightWeightInputView }, // 신장 및 체중 입력 페이지
  { path: '/signup/location', component: LocationInputView }, // 위치 정보 입력 페이지
  { path: '/signup/confirmation', component: ConfirmationPageView }, // 입력 정보 확인 페이지
  { path: '/main', name: 'main', component: MainView }, // 메인 페이지
  { path: '/welcome-dog', component: WelcomeDogRegistrationView }, // 웰컴 반려견 등록 페이지
  { path: '/pet-registration', component: PetRegistrationView }, // 반려견 등록 페이지
  { path: '/walk', component: WalkTrack },
  { path: '/walklog', component: WalkLog   },
  { path: '/walklogdata', component: DataTest},
  { path: "/dog-walker-signup", component: DogWalkerSignupView }, // 도그워커 가입 페이지 추가
  { path: "/dog-walker-profile", component: DogWalkerProfileView }, // 도그워커 프로필 작성 페이지 추가
  { path: "/test", name: 'test', component: TestView},
  { path: '/dog-walker-intro', component: WelcomeDogWalkerIntroView }, // 웰컴 도그워커 소개 페이지
  { path: "/dog-walker-signup", component: DogWalkerSignupView }, // 도그워커 가입 페이지
  { path: "/dog-walker-profile", component: DogWalkerProfileView }, // 도그워커 프로필 작성 페이지 
  { path: "/dogwalker/:id", component: DogWalkerDetailView }, // 특정 도그워커 상세 페이지
  { path: '/dogwalker-list', component: DogWalkerListView }, // 도그워커 리스트 페이지 추가
];




const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach(async (to, from, next) => {
  const authStore = useAuthStore();
  const petStore = usePetStore();

  // 로그인 상태 초기화
  if (!authStore.token) {
    authStore.initializeAuth();
  }

  // 로그인 상태 확인
  // if (!authStore.token && to.path !== '/') {
  //   alert("로그인이 필요합니다.");
  //   return next('/'); // 로그인 페이지로 리디렉션
  // }

  // 메인 페이지로 이동할 때 반려견 등록 여부 확인
  
  if (to.path === '/main') {
    try {
      await petStore.fetchPets();
      if (petStore.pets.length === 0) {
        alert("등록된 반려견이 없습니다. 반려견을 등록해주세요.");
        return next('/welcome-dog');
      }
    } catch (error) {
      console.error("반려견 데이터 확인 실패:", error);
      alert("문제가 발생했습니다. 다시 시도해주세요.");
      return next('/');
    }
  }

   // 도그워커 관련 페이지 접근 제어
   if (to.path === "/dog-walker-profile" || to.path === "/dog-walker-signup") {
    const userId = localStorage.getItem("user_id");
    if (!userId) {
      alert("로그인이 필요합니다.");
      return next("/");
    }

    // // 추가로 도그워커 상태를 확인하려면 API 요청 또는 authStore에서 확인
    // const isDogWalker = authStore.user?.dogWalker;
    // if (to.path === "/dog-walker-profile" && !isDogWalker) {
    //   alert("도그워커가 아닙니다. 도그워커 가입을 진행하세요.");
    //   return next("/dog-walker-signup");
    // }
  }

  next(); // 기본 이동
});

export default router;
