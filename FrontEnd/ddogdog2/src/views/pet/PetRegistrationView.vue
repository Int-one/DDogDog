<template>
  <div>
    <h1>반려견 등록</h1>
    <form @submit.prevent="registerPet">
      <label>이름</label>
      <input v-model="pet.petName" type="text" required />

      <label>생년월일</label>
      <input v-model="pet.petBirth" type="date" required />

      <label>성별</label>
      <button type="button" @click="setGender(true)" :class="{ active: pet.petGender === true }">남아</button>
      <button type="button" @click="setGender(false)" :class="{ active: pet.petGender === false }">여아</button>

      <label>중성화 여부</label>
      <button type="button" @click="setCastration(true)" :class="{ active: pet.castration === true }">Yes</button>
      <button type="button" @click="setCastration(false)" :class="{ active: pet.castration === false }">No</button>

      <label>체급</label>
      <button type="button" @click="setGroup('소형')" :class="{ active: pet.group === '소형' }">소형</button>
      <button type="button" @click="setGroup('중형')" :class="{ active: pet.group === '중형' }">중형</button>
      <button type="button" @click="setGroup('대형')" :class="{ active: pet.group === '대형' }">대형</button>

      <label>견종</label>
      <input v-model="pet.breed" type="text" required />

      <label>몸무게</label>
      <input v-model="pet.petWeight" type="number" />

      <label>특이사항 (최대 50자)</label>
      <textarea v-model="pet.ps" maxlength="50"></textarea>

      <button type="submit">등록</button>
    </form>

    <!-- Modal -->
    <div v-if="showModal" class="modal">
      <p>추가로 등록할 반려견이 있나요?</p>
      <button @click="addAnotherPet">추가 등록하기</button>
      <button @click="goToDogWalkerIntro">이제 됐어요</button>
    </div>
  </div>
</template>

<script>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { usePetStore } from "@/stores/pet";

export default {
  setup() {
    const petStore = usePetStore();
    const router = useRouter();
    const showModal = ref(false);

    const pet = reactive({
      petName: "",
      petBirth: "",
      petGender: null,
      castration: null,
      group: "",
      breed: "",
      petWeight: null,
      ps: "",
    });

    const setGender = (gender) => {
      pet.petGender = gender;
    };

    const setCastration = (value) => {
      pet.castration = value;
    };

    const setGroup = (group) => {
      pet.group = group;
    };

    const registerPet = async () => {
      try {
        await petStore.addPet(pet);
        alert("반려견이 성공적으로 등록되었습니다!");
        showModal.value = true; // Modal 표시
      } catch (error) {
        console.error("반려견 등록 실패:", error);
        alert("반려견 등록에 실패했습니다. 다시 시도해주세요.");
      }
    };

    const addAnotherPet = () => {
      showModal.value = false;
      Object.assign(pet, {
        petName: "",
        petBirth: "",
        petGender: null,
        castration: null,
        group: "",
        breed: "",
        petWeight: null,
        ps: "",
      });
    };

    const goToDogWalkerIntro = () => {
      showModal.value = false;
      router.push("/dog-walker-intro");
    };

    return {
      pet,
      showModal,
      setGender,
      setCastration,
      setGroup,
      registerPet,
      addAnotherPet,
      goToDogWalkerIntro,
    };
  },
};
</script>

<style>
.active {
  background-color: #007bff;
  color: white;
}
.modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  padding: 20px;
  border: 1px solid #ccc;
  z-index: 1000;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}
</style>
