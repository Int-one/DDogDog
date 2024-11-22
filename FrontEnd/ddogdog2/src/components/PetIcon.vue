<template>
  <div class="pets-container d-flex overflow-auto">
    <div
      v-for="dog in petStore.pets"
      :key="dog.id"
      class="pet-item text-center me-3"
    >
      <div
        class="rounded-circle mb-2 dog-image-container"
        :class="{ 'selected': goWith.includes(dog.petId) }"
      >
        <img
          :src="`http://localhost:8081/${dog.petPhoto}`"
          alt="반려견 사진"
          class="dog-image"
        />
        <!-- <img
          :src="`http://localhost:8081/${dog.petPhoto}`"
          alt="반려견 사진"
          class="dog-image"
          @click="togglePetSelection(dog.petId)"
        /> -->
      </div>
      <p class="text-muted">{{ dog.endTime }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onUnmounted, onMounted } from "vue";
import { useWalkStore } from "@/stores/walk";
import { usePetStore } from "@/stores/pet";

const selectedTab = ref("누적");
const walkStore = useWalkStore();
const petStore = usePetStore();
const goWith = ref([]); // 선택된 반려견 ID 배열





import { computed } from "vue";

const now = new Date();

const filteredWalkLogs = computed(() => {
  if (selectedTab.value === "누적") {
    return walkStore.myWalkLogs; // 전체 데이터
  }

  const daysAgo = selectedTab.value === "월간" ? 30 : 7;
  const startDate = new Date();
  startDate.setDate(now.getDate() - daysAgo);

  return walkStore.myWalkLogs.filter((walk) => {
    const walkDate = new Date(walk.startTime);
    return walkDate >= startDate && walkDate <= now;
  });
});

const fetchDogData = async () => {
  try {
    const response = await axios.get("http://localhost:8081/api/pet/test@test.com"); // 반려견 데이터 API
    dogs.value = response.data;

    // 각 반려견의 마지막 산책 시간을 계산
    dogs.value.forEach((dog) => {
      calculateLastWalkTime(dog);
    });
    petStore.pets = dogs.value;
  } catch (error) {
    console.error("Failed to fetch dog data:", error);
  }
};

const calculateLastWalkTime = (dog) => {
  if (!dog.endTime) {
    dog.endTime = "산책 가고 싶어요";
    return;
  }

  const lastWalkDate = new Date(dog.endTime);
  const now = new Date();
  const days = Math.floor((now - lastWalkDate) / (1000 * 60 * 60 * 24));

  if (days === 0) {
    dog.endTime = "마지막 산책 오늘";
  } else if (days === 1) {
    dog.endTime = "마지막 산책 1일 전";
  } else {
    dog.endTime = `마지막 산책 ${days}일 전`;
  }
};


const totalWalkTime = computed(() => {
  // 필터링된 데이터의 총 시간 계산
  return filteredWalkLogs.value.reduce((sum, walk) => {
    const start = new Date(walk.startTime);
    const end = new Date(walk.endTime);
    const duration = (end - start) / 1000; // 초 단위
    return sum + duration;
  }, 0);
});

// 시간을 "hh:mm:ss" 형식으로 변환
const totalWalkTimeFormatted = computed(() =>
  formatDuration(totalWalkTime.value)
);


// 시간을 "hh:mm:ss" 형식으로 변환
const formatDuration = (seconds) => {
  const hrs = Math.floor(seconds / 3600);
  const mins = Math.floor((seconds % 3600) / 60);
  const secs = seconds % 60;
  return `${hrs.toString().padStart(2, "0")}:${mins
    .toString()
    .padStart(2, "0")}:${secs.toString().padStart(2, "0")}`;
};


onMounted(() => {
  walkStore.fetchMyWalkLogs();
  petStore.fetchPets();
})

</script>

<style scoped>

.pets-container {
  gap: 1rem;
  overflow-x: auto; /* 가로 스크롤 활성화 */
  padding-bottom: 0.5rem;
}

.pet-item {
  flex: 0 0 auto; /* 가로 방향으로만 확장 */
  width: 80px; /* 각 반려견 아이템의 고정 너비 */
  cursor: pointer; /* 클릭 가능 표시 */
}

.dog-image-container {
  width: 70px;
  height: 70px;
  border-radius: 50%;
  border: 2px solid transparent; /* 기본 테두리는 투명 */
  display: flex;
  justify-content: center;
  align-items: center;
}

.dog-image-container.selected {
  border-color: #007bff; /* 선택된 상태에서 테두리 강조 */
}

.dog-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 50%;
}

.pets-container::-webkit-scrollbar {
  display: none; /* 스크롤바 숨김 */
}
</style>
