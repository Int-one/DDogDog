import { createRouter, createWebHistory } from 'vue-router'


import LoginPageView from '../views/auth/LoginPageView.vue'
import SignupPageView from '../views/auth/SignupPageView.vue'
import EmailInputView from '../views/auth/EmailInputView.vue'
import PasswordInputView from '../views/auth/PasswordInputView.vue'
import NicknameInputView from '../views/auth/NicknameInputView.vue'
import PhoneNumberInputView from '../views/auth/PhoneNumberInputView.vue'
import BirthGenderInputView from '../views/auth/BirthGenderInputView.vue'
import HeightWeightInputView from '../views/auth/HeightWeightInputView.vue'
import LocationInputView from '../views/auth/LocationInputView.vue'
import ConfirmationPageView from '../views/auth/ConfirmationPageView.vue'
import MainView from '../views/main/MainView.vue'

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
  { path: '/main', component: MainView }, // 메인 페이지
  ]

const router = createRouter({
  history: createWebHistory(),
  routes,
})
export default router
