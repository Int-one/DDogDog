<template>
  <div>
    <h1>도그워커 리스트</h1>
    <!-- 지역 선택 필터 -->
    <select v-model="selectedRegion" @change="fetchDogWalkers">
      <option v-for="region in regions" :key="region" :value="region">
        {{ region }}
      </option>
    </select>

    <!-- 도그워커 목록 -->
    <ul>
      <li v-for="dogWalker in dogWalkers" :key="dogWalker.id">
        {{ dogWalker.name }} ({{ dogWalker.region }})
      </li>
    </ul>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      dogWalkers: [], // 도그워커 목록
      selectedRegion: "", // 선택된 지역
      regions: ["서울", "부산", "대구", "인천", "광주"], // 지역 목록 (예제)
    };
  },
  created() {
    // 로컬스토리지에서 사용자 지역 정보를 가져와 초기화
    const userRegion = localStorage.getItem("user_region");
    this.selectedRegion = userRegion || this.regions[0]; // 사용자 지역이 없으면 기본값 설정
    this.fetchDogWalkers(); // 초기 도그워커 목록 불러오기
  },
  methods: {
    async fetchDogWalkers() {
      try {
        // 선택된 지역을 기준으로 도그워커 목록 요청
        const response = await axios.get("http://localhost:8081/api/dogwalkers", {
          params: { region: this.selectedRegion },
        });
        this.dogWalkers = response.data;
      } catch (error) {
        console.error("도그워커 목록 불러오기 실패:", error);
      }
    },
  },
};
</script>
