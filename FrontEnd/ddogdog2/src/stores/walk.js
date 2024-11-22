import { defineStore } from "pinia";
import { ref, reactive } from "vue";
import axios from "axios";

export const useWalkStore = defineStore("walk", () => {
  const walkLogs = ref([]); // 모든 산책 기록을 저장
  const myWalkLogs = ref([]); // 나의 산책 기록을 저장
  const currentWalk = reactive({
    walkPath: [], // 현재 산책 경로
    startTime: null, // 산책 시작 시간
    endTime: null, // 산책 종료 시간
    total: 0, // 총 거리 (예: 미터)
    dogWalking: false, // 강아지 산책 여부
    userId: "", // 사용자 ID
    tradeId: null, // 거래 ID (nullable)
  });

  const apiUrl = "http://localhost:8081/api/walklog"; // API 엔드포인트

  const calcTime = (time) => {
    const t = new Date(time);
    const period = t < 12 ? '오전' : '오후';
    const formattedHours = t % 12 || 12; // 0시를 12시로 변환
    const formattedMinutes = t.toString().padStart(2, '0'); // 두 자리로 변환
    return `${period} ${formattedHours}시 ${formattedMinutes}분`
  }

  // 새로운 산책 기록 추가
  const startWalk = (userId, dogWalking = false) => {
    currentWalk.startTime = new Date().toISOString(); // 시작 시간 저장
    currentWalk.endTime = null; // 종료 시간 초기화
    currentWalk.walkPath = []; // 경로 초기화
    currentWalk.total = 0; // 총 거리 초기화
    currentWalk.dogWalking = dogWalking; // 강아지 산책 여부 설정
    currentWalk.userId = userId; // 사용자 ID 설정
    currentWalk.tradeId = null; // 거래 ID 초기화
  };

  // 현재 산책에 위치 추가
  const addPathPoint = (latitude, longitude) => {
    const datetime = new Date().toISOString();
    currentWalk.walkPath.push({ latitude, longitude, datetime });
    console.log("경로 추가:", { latitude, longitude, datetime });
  };

  // 산책 종료 및 데이터 전송
  const endWalk = async () => {
    currentWalk.endTime = new Date().toISOString(); // 종료 시간 저장

    // API로 데이터 전송
    try {
      const response = await axios.post(apiUrl, { ...currentWalk });
      console.log("산책 데이터 저장 성공:", response.data);
      // 저장된 데이터를 walkLogs에 추가
      walkLogs.value.push({ ...currentWalk });
      resetCurrentWalk(); // 현재 산책 데이터 초기화
    } catch (error) {
      console.error("산책 데이터 저장 실패:", error);
    }
  };

  // 현재 산책 데이터 초기화
  const resetCurrentWalk = () => {
    currentWalk.walkPath = [];
    currentWalk.startTime = null;
    currentWalk.endTime = null;
    currentWalk.total = 0;
    currentWalk.dogWalking = false;
    currentWalk.userId = "";
    currentWalk.tradeId = null;
  };

  // 모든 산책 기록 불러오기
  const fetchWalkLogs = async () => {
    try {
      const response = await axios.get(apiUrl);
      walkLogs.value = response.data;
      console.log("산책 기록 불러오기 성공:", walkLogs.value);
    } catch (error) {
      console.error("산책 기록 불러오기 실패:", error);
    }
  };

  // 유저의 산책 기록 불러오기
  const fetchMyWalkLogs = async() => {
    try {
      const response = await axios.get(`${apiUrl}/${localStorage.getItem('user_id')}`);
      myWalkLogs.value = response.data
      console.log(myWalkLogs.value);
    } catch (e) {
      console.error("Failed", e);
    }
  };

  return {
    walkLogs,
    myWalkLogs,
    currentWalk,
    calcTime,
    startWalk,
    addPathPoint,
    endWalk,
    fetchWalkLogs,
    fetchMyWalkLogs,
  };
});
