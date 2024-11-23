<template>
  <div class="pets-container d-flex overflow-auto">
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
  <p class="text-muted">{{ pet.endTime }}</p>
</div>
</div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useWalkStore } from "@/stores/walk";
import { usePetStore } from "@/stores/pet";

const walkStore = useWalkStore();
const petStore = usePetStore();


const { onImageClick, pets } = defineProps({
  onImageClick: {
    type: Function,
    default: (pet) => console.log("기본 클릭 동작: ", pet),
  },
  pets: {
    type: Array,
  },
});



const handleImageClick = (pet) => {
  // Props로 전달된 함수 실행
  if (typeof onImageClick === "function") {
    onImageClick(pet.petId);
  } else {
    console.log(pet);
  }
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

.pet-image-container {
  width: 70px;
  height: 70px;
  border-radius: 50%;
  border: 2px solid transparent; /* 기본 테두리는 투명 */
  display: flex;
  justify-content: center;
  align-items: center;
}

.pet-image-container.selected {
  border-color: #007bff; /* 선택된 상태에서 테두리 강조 */
}

.pet-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 50%;
}

.pets-container::-webkit-scrollbar {
  display: none; /* 스크롤바 숨김 */
}
</style>
