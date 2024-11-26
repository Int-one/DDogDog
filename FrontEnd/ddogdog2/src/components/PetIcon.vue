<template>
  <div class="pets-container d-flex overflow-auto"
  :style="{ height: showEndTime ? '150px' : '80px' }">
    <div
      v-for="pet in pets"
      :key="pet.petId"
      class="pet-item text-center me-3"
    >
      <div
        class="rounded-circle mb-2 pet-image-container"
        :class="{ 'selected': petStore.goWith.includes(pet.petId) }"
      >
        <img
          :src="`http://localhost:8081/${pet.petPhoto}`"
          alt="반려견 사진"
          class="pet-image"
          @click="handleImageClick(pet)"
        />
      </div>
      <!-- showEndTime가 true일 때만 endTime을 표시 -->
      <p v-if="showEndTime" class="text-muted">{{ pet.endTime }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useWalkStore } from "@/stores/walk";
import { usePetStore } from "@/stores/pet";

const walkStore = useWalkStore();
const petStore = usePetStore();

// Props
const { onImageClick, pets, showEndTime } = defineProps({
  onImageClick: {
    type: Function,
    default: (pet) => console.log("기본 클릭 동작: ", pet),
  },
  pets: {
    type: Array,
    required: true,
  },
  showEndTime: {
    type: Boolean,
    default: true, // 기본값으로 endTime 표시
  },
});

// 반려견 클릭 이벤트 핸들러
const handleImageClick = (pet) => {
  if (typeof onImageClick === "function") {
    onImageClick(pet.petId);
  } else {
    console.log(pet);
  }
};

onMounted(() => {
  walkStore.fetchMyWalkLogs();
  petStore.fetchPets();
});
</script>

<style scoped>
/* 기존 스타일 그대로 유지 */
.pets-container {
  gap: 1rem;
  overflow-x: auto;
  padding-bottom: 0.5rem;
  height: 150px;
}

.pet-item {
  flex: 0 0 auto;
  width: 80px;
  cursor: pointer;
}

.pet-image-container {
  width: 70px;
  height: 70px;
  border-radius: 50%;
  border: 3.5px solid transparent;
  display: flex;
  justify-content: center;
  align-items: center;
}

.pet-image-container.selected {
  border-color: #4ba64b;
}

.pet-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 50%;
}

.pets-container::-webkit-scrollbar {
  display: none;
}
</style>
