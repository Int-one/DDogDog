<!-- <template>
  <div class="dogwalker-tab">
    <div class="header">
      <button class="main-button" @click="navigateToSignup">도그워커 가입하기</button>
    </div>

    <div class="actions">
      <button class="sub-button" @click="navigateToDoForMe">해주세요 작성하기</button>
      <button class="sub-button" @click="navigateToDoForYou">해드려요 작성하기</button>
    </div>

    <div class="profile-list">
      <h2>우리 동네 도그워커</h2>

     
      <div class="profiles">
        <div
          v-for="walker in filteredDogWalkers"
          :key="walker.userId"
          class="profile-card"
        >
          <img :src="walker.photo || defaultImage" alt="프로필 사진" class="profile-image" />
          <div class="profile-info">
            <h3>{{ walker.name }}</h3>
            <p>{{ walker.introduce }}</p>
            <p class="region">{{ walker.region }}</p>
            <button class="view-profile" @click="viewProfile(walker.userId)">프로필 보기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

// 상태 관리
const router = useRouter();
const dogWalkers = ref([]); // 전체 도그워커 목록
const filteredDogWalkers = ref([]); // 필터링된 도그워커 목록
const defaultImage = "/assets/default-profile.png"; // 기본 이미지 경로

// 사용자 지역 정보 (로컬스토리지에서 가져오기)
const userRegion = localStorage.getItem("region") || "";

// API 호출로 도그워커 목록 가져오기
const fetchDogWalkers = async () => {
  try {
    const response = await axios.get("http://localhost:8081/api/user");
    console.log("도그워커 데이터:", response.data); // API 응답 확인
    dogWalkers.value = response.data.filter((walker) => walker.dogWalker === true);
    applyFilters(); // 필터링 실행
  } catch (error) {
    console.error("도그워커 목록을 불러오지 못했습니다:", error);
  }
};

// 필터 적용 함수
const applyFilters = () => {
  let results = dogWalkers.value;

  // 사용자 지역 정보와 일치하는 도그워커 필터링
  if (userRegion) {
    results = results.filter((walker) => walker.region.includes(userRegion));
  }

  filteredDogWalkers.value = results;
  console.log("필터링된 도그워커 목록:", filteredDogWalkers.value); // 필터링 확인
};

// 페이지 로드 시 데이터 호출
onMounted(() => {
  fetchDogWalkers();
});

// 네비게이션 함수
const navigateToSignup = () => {
  router.push("/dog-walker-signup");
};
const navigateToDoForMe = () => {
  router.push("/dogwalker/doforme/request");
};
const navigateToDoForYou = () => {
  router.push("/dogwalker/doforyou");
};
const viewProfile = (userId) => {
  router.push(`/dogwalker/${userId}`);
};
</script>

<style scoped>
.dogwalker-tab {
  width: 360px;
  height: 780px;
  margin: 0 auto;
  padding: 10px;
  background-color: #f9f9f9;
  display: flex;
  flex-direction: column;
  overflow-y: auto;
}

.header {
  text-align: center;
  margin-bottom: 20px;
}

.main-button {
  padding: 15px;
  font-size: 18px;
  color: white;
  background-color: #4ba64b;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  width: 100%;
  max-width: 340px;
}

.actions {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 20px;
  align-items: center;
}

.sub-button {
  padding: 12px;
  font-size: 16px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  width: 100%;
  max-width: 340px;
}

.profiles {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  justify-content: center;
}

.profile-card {
  background: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 160px;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 15px;
}

.profile-image {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 10px;
}

.profile-info h3 {
  font-size: 1rem;
  margin-bottom: 8px;
  color: #555;
}

.profile-info p {
  font-size: 0.8rem;
  color: #777;
  text-align: center;
  margin-bottom: 5px;
}

.profile-info .region {
  font-size: 0.8rem;
  font-weight: bold;
  color: #333;
}

.view-profile {
  padding: 8px 10px;
  background-color: #4ba64b;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 0.85rem;
  cursor: pointer;
}
</style> -->


<template>
  <div class="dogwalker-tab">
    <div class="header">
      <button class="main-button" @click="navigateToSignup">도그워커 가입하기</button>
    </div>

    <div class="actions">
      <button class="sub-button" @click="navigateToDoForMe">해주세요 작성하기</button>
      <button class="sub-button" @click="navigateToDoForYou">해드려요 작성하기</button>
    </div>

    <div class="profile-list">
      <h2>우리 동네 도그워커</h2>
      <!-- 검색 기능 -->
      <div class="search-bar-container">
        <input
          type="text"
          v-model="searchQuery"
          placeholder="지역명을 입력하세요 (예: 강남구)"
          class="search-bar"
        />
        <button @click="filterDogWalkers" class="search-button">검색</button>
      </div>

      <!-- 내 지역만 보기 버튼 -->
      <button
        class="toggle-button"
        :class="{ active: filterByRegion }"
        @click="toggleFilterByRegion"
      >
        {{ filterByRegion ? "내 지역 보기: ON" : "내 지역 보기: OFF" }}
      </button>

      <!-- 도그워커 목록 -->
      <div class="profiles">
        <div
          v-for="walker in filteredDogWalkers"
          :key="walker.userId"
          class="profile-card"
        >
          <img :src="walker.photo || defaultImage" alt="프로필 사진" class="profile-image" />
          <div class="profile-info">
            <h3>{{ walker.name }}</h3>
            <p>{{ walker.introduce }}</p>
            <p class="region">{{ walker.region }}</p>
            <button class="view-profile" @click="viewProfile(walker.userId)">프로필 보기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";

// 상태 관리
const router = useRouter();
const dogWalkers = ref([]);
const filteredDogWalkers = ref([]);
const searchQuery = ref("");
const filterByRegion = ref(false);
const defaultImage = "/assets/default-profile.png"; // 기본 이미지 경로

// 사용자 지역 정보 (로컬스토리지에서 가져오기)
const userRegion = localStorage.getItem("region") || "";

// API 호출로 도그워커 목록 가져오기
const fetchDogWalkers = async () => {
  try {
    const response = await axios.get("http://localhost:8081/api/user", {
          headers: { "access-token": localStorage.getItem("token") },
        });
    dogWalkers.value = response.data.filter((walker) => walker.dogWalker === true);
    applyFilters();
  } catch (error) {
    console.error("도그워커 목록을 불러오지 못했습니다:", error);
  }
};

// 필터 적용 함수
const applyFilters = () => {
  let results = dogWalkers.value;

  // '내 지역만 보기' 활성화 시 필터링
  if (filterByRegion.value) {
    results = results.filter((walker) => walker.region.includes(userRegion));
  }

  // 검색어 필터링
  if (searchQuery.value) {
    results = results.filter((walker) =>
      walker.region.toLowerCase().includes(searchQuery.value.toLowerCase())
    );
  }

  filteredDogWalkers.value = results;
};

// 검색 버튼 동작
const filterDogWalkers = () => {
  applyFilters();
};

// '내 지역만 보기' 토글
const toggleFilterByRegion = () => {
  filterByRegion.value = !filterByRegion.value;
  applyFilters();
};

// 페이지 로드 시 데이터 호출
onMounted(() => {
  fetchDogWalkers();
});

// 네비게이션 함수
const navigateToSignup = () => {
  router.push("/dog-walker-signup");
};
const navigateToDoForMe = () => {
  router.push("/dogwalker/doforme/request");
};
const navigateToDoForYou = () => {
  router.push("/dogwalker/doforyou");
};
const viewProfile = (userId) => {
  router.push(`/dogwalker/${userId}`);
};
</script>

<style scoped>
.dogwalker-tab {
  width: 360px;
  height: 780px;
  margin: 0 auto;
  padding: 10px;
  background-color: #f9f9f9;
  display: flex;
  flex-direction: column;
  overflow-y: auto;
}

.header {
  text-align: center;
  margin-bottom: 20px;
}

.main-button {
  padding: 15px;
  font-size: 18px;
  color: white;
  background-color: #4ba64b;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  width: 100%;
  max-width: 340px;
}

.actions {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-bottom: 20px;
  align-items: center;
}

.sub-button {
  padding: 12px;
  font-size: 16px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  width: 100%;
  max-width: 340px;
}

.profile-list h2 {
  font-size: 1.4rem;
  margin-bottom: 10px;
  color: #333;
  text-align: center;
}

.search-bar-container {
  display: flex;
  gap: 10px;
  margin-bottom: 15px;
}

.search-bar {
  flex: 1;
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.search-button {
  padding: 10px 15px;
  font-size: 14px;
  background-color: #4ba64b;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.toggle-button {
  width: 100%;
  padding: 10px;
  font-size: 14px;
  margin-bottom: 20px;
  background-color: #ddd;
  color: #555;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  text-align: center;
}

.toggle-button.active {
  background-color: #4ba64b;
  color: white;
}

.profiles {
  display: flex;
  flex-wrap: wrap;
  gap: 15px;
  justify-content: center;
}

.profile-card {
  background: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 160px;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 15px;
}

.profile-image {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 10px;
}

.profile-info h3 {
  font-size: 1rem;
  margin-bottom: 8px;
  color: #555;
}

.profile-info p {
  font-size: 0.8rem;
  color: #777;
  text-align: center;
  margin-bottom: 5px;
}

.profile-info .region {
  font-size: 0.8rem;
  font-weight: bold;
  color: #333;
}

.view-profile {
  padding: 8px 10px;
  background-color: #4ba64b;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 0.85rem;
  cursor: pointer;
}
</style>