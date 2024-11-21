<template>
  <div>
    <h1>도그워커 목록</h1>

    <!-- 검색창 -->
    <div>
      <input v-model="searchKeyword" type="text" placeholder="도그워커 검색" />
      <button @click="filterList">검색</button>
    </div>

    <!-- 필터 -->
    <div>
      <label>지역</label>
      <select v-model="filters.location">
        <option value="">전체</option>
        <option value="서울">서울</option>
        <option value="부산">부산</option>
        <option value="대구">대구</option>
        <!-- 필요한 지역 추가 -->
      </select>

      <label>시간대</label>
      <select v-model="filters.time">
        <option value="">전체</option>
        <option value="weekdayAm">평일 오전</option>
        <option value="weekdayPm">평일 오후</option>
        <option value="weekendAm">주말 오전</option>
        <option value="weekendPm">주말 오후</option>
        <option value="all">무관</option>
      </select>

      <label>대형견 가능 여부</label>
      <select v-model="filters.handleLargeDogs">
        <option value="">전체</option>
        <option value="true">가능</option>
        <option value="false">불가능</option>
      </select>

      <button @click="filterList">필터 적용</button>
    </div>

    <!-- 도그워커 카드 목록 -->
    <div class="dogwalker-list">
      <div 
        v-for="dogwalker in filteredDogWalkers" 
        :key="dogwalker.id" 
        class="dogwalker-card"
        @click="viewProfile(dogwalker.id)"
      >
        <img :src="dogwalker.profilePhoto || '/default-profile.png'" alt="프로필 사진" />
        <h3>{{ dogwalker.nickname }}</h3>
        <p>레벨: {{ dogwalker.level }}</p>
        <p>산책 가능 시간대: {{ dogwalker.availableTimes }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import { ref, computed } from "vue";
import axios from "axios";

export default {
  setup() {
    const dogWalkers = ref([]);
    const searchKeyword = ref("");
    const filters = ref({
      location: "",
      time: "",
      handleLargeDogs: "",
    });

    // 필터된 도그워커 목록
    const filteredDogWalkers = computed(() => {
      return dogWalkers.value.filter((walker) => {
        const matchesSearch = !searchKeyword.value || walker.nickname.includes(searchKeyword.value);
        const matchesLocation = !filters.value.location || walker.location === filters.value.location;
        const matchesTime = !filters.value.time || walker.availableTimes.includes(filters.value.time);
        const matchesHandleLargeDogs =
          !filters.value.handleLargeDogs ||
          String(walker.handleLargeDogs) === filters.value.handleLargeDogs;

        return matchesSearch && matchesLocation && matchesTime && matchesHandleLargeDogs;
      });
    });

    const fetchDogWalkers = async () => {
      try {
        const response = await axios.get("http://localhost:8081/api/dogwalker");
        dogWalkers.value = response.data;
      } catch (error) {
        console.error("도그워커 목록을 가져오는 데 실패했습니다.", error);
      }
    };

    const filterList = () => {
      console.log("필터 적용됨:", filters.value);
    };

    const viewProfile = (id) => {
      // 프로필 상세 페이지로 이동
      window.location.href = `/dogwalker/${id}`;
    };

    fetchDogWalkers();

    return {
      dogWalkers,
      searchKeyword,
      filters,
      filteredDogWalkers,
      filterList,
      viewProfile,
    };
  },
};
</script>

<style>
.dogwalker-list {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
}

.dogwalker-card {
  border: 1px solid #ddd;
  padding: 16px;
  border-radius: 8px;
  cursor: pointer;
  transition: box-shadow 0.2s ease;
}

.dogwalker-card:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.dogwalker-card img {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
}
</style>
