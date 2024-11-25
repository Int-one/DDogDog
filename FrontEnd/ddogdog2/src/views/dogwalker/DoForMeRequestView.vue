<template>
  <div class="do-for-me-request">
    <h1>해주세요 작성 페이지</h1>

    <div class="pet-selection">
      <h2>반려견 선택</h2>
      <div class="pet-list">
        <PetIcon :pets="petStore.pets" :onImageClick="togglePetSelection" />
      </div>
    </div>

    <div class="request-form">
      <h2>산책 요청 정보</h2>
      
      <label>산책 시작 시간</label>
      <input type="datetime-local" v-model="tradeStartTime" />

      <label>산책 종료 시간</label>
      <input type="datetime-local" v-model="tradeEndTime" />

      <label>희망 금액 (₩)</label>
      <input type="number" v-model="cost" placeholder="예: 10000" />

      <label>대형견이 있나요?</label>
      <div class="large-dog-options">
        <button
          :class="{ active: largeDog === true }"
          @click="setLargeDog(true)"
        >
          네
        </button>
        <button
          :class="{ active: largeDog === false }"
          @click="setLargeDog(false)"
        >
          아니오
        </button>
      </div>

      <label>지역</label>
      <div class="region-selection">
        <p class="current-region">{{ region }}</p>
        <button type="button" @click="toggleRegionOptions">
          타 지역에서 산책할까요?
        </button>
        <div v-if="showRegionOptions" class="region-dropdowns">
          <select v-model="selectedProvince">
            <option disabled value="">광역시/도</option>
            <option>서울</option>
            <option>경기</option>
            <option>부산</option>
          </select>
          <select v-model="selectedCity">
            <option disabled value="">시/군/구</option>
            <option>강남구</option>
            <option>서초구</option>
          </select>
          <select v-model="selectedDistrict">
            <option disabled value="">읍/면/동</option>
            <option>역삼동</option>
            <option>신사동</option>
          </select>
          <button class="apply-region-button" @click="applyRegion">
            적용
          </button>
        </div>
      </div>

      <label>요청 사항</label>
      <textarea v-model="detail" placeholder="추가 요청 사항을 입력해주세요"></textarea>
      
      <button class="submit-button" @click="openConfirmationModal">작성 완료</button>
    </div>

    <!-- 등록 확인 모달 -->
    <div v-if="showModal" class="modal-overlay">
      <div class="modal-content">
        <p>등록하시겠습니까?</p>
        <div class="modal-buttons">
          <button class="modal-confirm-btn" @click="submitRequest">네</button>
          <button class="modal-cancel-btn" @click="closeModal">아니오</button>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import PetIcon from "@/components/PetIcon.vue";
import { usePetStore } from "@/stores/pet";
import axios from "axios";

// Vue Router
const router = useRouter();

// Pinia 스토어 사용
const petStore = usePetStore();
const pets = ref([]);
const selectedPets = ref([]);
const region = ref("서울 강남구 역삼동"); // 사용자 기본 지역 정보
const showRegionOptions = ref(false);
const showModal = ref(false); // 모달 표시 여부

// 지역 선택 데이터
const selectedProvince = ref("");
const selectedCity = ref("");
const selectedDistrict = ref("");

// 폼 데이터
const tradeStartTime = ref("");
const tradeEndTime = ref("");
const cost = ref(0);
const largeDog = ref(null); // 대형견 여부
const detail = ref("");

// 반려견 목록 불러오기
onMounted(() => {
  if (!pets.value.length) {
    pets.value = petStore.pets; // 이미 로드된 데이터를 사용
  }
});

// 지역 옵션 활성화 토글
const toggleRegionOptions = () => {
  showRegionOptions.value = !showRegionOptions.value;
};

// 지역 정보 적용
const applyRegion = () => {
  if (selectedProvince.value && selectedCity.value && selectedDistrict.value) {
    region.value = `${selectedProvince.value} ${selectedCity.value} ${selectedDistrict.value}`;
    showRegionOptions.value = false;
  } else {
    alert("모든 지역 정보를 선택해주세요.");
  }
};

// 반려견 선택/해제 로직
const togglePetSelection = (petId) => {
  const index = petStore.goWith.indexOf(petId);
  if (index === -1) {
    petStore.goWith.push(petId); // 선택 추가
  } else {
    petStore.goWith.splice(index, 1); // 선택 해제
  }
};

// 대형견 여부 설정
const setLargeDog = (isLarge) => {
  largeDog.value = isLarge;
};

// 모달 열기/닫기
const openConfirmationModal = () => {
  showModal.value = true;
};
const closeModal = () => {
  showModal.value = false;
};

// 데이터 제출 로직
const submitRequest = async () => {
  const userId = localStorage.getItem("user_id");
  if (!userId) {
    alert("로그인이 필요합니다.");
    return;
  }

  const requestData = {
    kind: "해주세요",
    tradeStartTime: tradeStartTime.value,
    tradeEndTime: tradeEndTime.value,
    cost: cost.value,
    region: region.value,
    detail: detail.value,
    state: null,
    largeDog: largeDog.value, // 대형견 여부
    superId: userId,
    userId: null,
    pets: petStore.goWith, // 선택된 반려견 ID 배열
  };

  try {
    const response = await axios.post("http://localhost:8081/api/trade", requestData, {
      headers: {
        "access-token": localStorage.getItem("token"),
      },
    });
    alert("정상적으로 등록되었습니다.");
    console.log("응답 데이터:", response.data);

    // 요청 완료 후 초기화
    petStore.goWith = [];
    selectedPets.value = [];
    closeModal();

    // 작성 완료 후 이동
    router.push("/dogwalker"); // /dogwalker 경로로 이동
  } catch (error) {
    console.error("등록 실패:", error);
    alert("등록 중 오류가 발생했습니다.");
  }
};
</script>
<!-- <script setup>
import { ref, onMounted } from "vue";
import PetIcon from "@/components/PetIcon.vue";
import { usePetStore } from "@/stores/pet";
import axios from "axios";

// Pinia 스토어 사용
const petStore = usePetStore();
const pets = ref([]);
const selectedPets = ref([]);
const region = ref("서울 강남구 역삼동"); // 사용자 기본 지역 정보
const showRegionOptions = ref(false);
const showModal = ref(false); // 모달 표시 여부

// 지역 선택 데이터
const selectedProvince = ref("");
const selectedCity = ref("");
const selectedDistrict = ref("");

// 폼 데이터
const tradeStartTime = ref("");
const tradeEndTime = ref("");
const cost = ref(0);
const largeDog = ref(null); // 대형견 여부
const detail = ref("");

// 반려견 목록 불러오기
onMounted(() => {
  if (!pets.value.length) {
    pets.value = petStore.pets; // 이미 로드된 데이터를 사용
  }
});

// 지역 옵션 활성화 토글
const toggleRegionOptions = () => {
  showRegionOptions.value = !showRegionOptions.value;
};

// 지역 정보 적용
const applyRegion = () => {
  if (selectedProvince.value && selectedCity.value && selectedDistrict.value) {
    region.value = `${selectedProvince.value} ${selectedCity.value} ${selectedDistrict.value}`;
    showRegionOptions.value = false;
  } else {
    alert("모든 지역 정보를 선택해주세요.");
  }
};

// 반려견 선택/해제 로직
const togglePetSelection = (petId) => {
  const index = petStore.goWith.indexOf(petId);
  if (index === -1) {
    petStore.goWith.push(petId); // 선택 추가
  } else {
    petStore.goWith.splice(index, 1); // 선택 해제
  }
};

// 대형견 여부 설정
const setLargeDog = (isLarge) => {
  largeDog.value = isLarge;
};

// 모달 열기/닫기
const openConfirmationModal = () => {
  showModal.value = true;
};
const closeModal = () => {
  showModal.value = false;
};

// 데이터 제출 로직
const submitRequest = async () => {
  const userId = localStorage.getItem("user_id");
  if (!userId) {
    alert("로그인이 필요합니다.");
    return;
  }

  const requestData = {
    kind: "해주세요",
    tradeStartTime: tradeStartTime.value,
    tradeEndTime: tradeEndTime.value,
    cost: cost.value,
    region: region.value,
    detail: detail.value,
    state: null,
    largeDog: largeDog.value, // 대형견 여부
    superId: userId,
    userId: null,
    pets: petStore.goWith, // 선택된 반려견 ID 배열
  };

  try {
    const response = await axios.post("http://localhost:8081/api/trade", requestData, {
      headers: {
        "access-token": localStorage.getItem("token"),
      },
    });
    alert("정상적으로 등록되었습니다.");
    console.log("응답 데이터:", response.data);

    // 요청 완료 후 초기화
    petStore.goWith = [];
    selectedPets.value = [];
    closeModal();
  } catch (error) {
    console.error("등록 실패:", error);
    alert("등록 중 오류가 발생했습니다.");
  }
};
</script> -->

<style scoped>
.do-for-me-request {
  width: 360px;
  height: 780px;
  margin: 0 auto;
  padding: 10px;
  font-family: Arial, sans-serif;
  background: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  overflow-y: auto;
}

h1 {
  font-size: 1.5rem;
  color: #333;
  margin-bottom: 20px;
  text-align: center;
}

.pet-list {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 20px;
}

.request-form label {
  font-size: 0.9rem;
  margin-bottom: 5px;
  display: block;
  color: #555;
}

.request-form input,
.request-form select,
.request-form textarea {
  display: block;
  width: 100%;
  padding: 8px;
  margin-bottom: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.request-form button {
  width: 100%;
  background-color: #4ba64b;
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
  font-size: 16px;
  border-radius: 4px;
}

.apply-region-button {
  margin-top: 10px;
  width: 100%;
  background-color: #4ba64b;
  color: white;
  border: none;
  padding: 10px;
  cursor: pointer;
  font-size: 14px;
  border-radius: 4px;
}

.large-dog-options {
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
}

.large-dog-options button {
  flex: 1;
  margin: 0 5px;
  padding: 10px;
  font-size: 14px;
  background: #eee;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
}

.large-dog-options button.active {
  background: #4ba64b;
  color: white;
}

.submit-button {
  margin-bottom: 35px;
}

.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  width: 300px;
  padding: 20px;
  background: white;
  border-radius: 8px;
  text-align: center;
}

.modal-buttons {
  display: flex;
  justify-content: space-between;
}

.modal-buttons button {
  width: 45%;
  padding: 10px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.modal-confirm-btn {
  background-color: #4ba64b;
  color: white;
}

.modal-cancel-btn {
  background-color: #f44336;
  color: white;
}
</style>
  