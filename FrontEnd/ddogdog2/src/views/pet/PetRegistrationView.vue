<!-- 
<template>
  <div>
    <h1>반려견 등록</h1>
    <form @submit.prevent="registerPet">
      <label>이름</label>
      <input v-model="pet.name" type="text" required />

      <label>나이</label>
      <input v-model="pet.birth" type="date" />

      <label>성별</label>
      <button type="button" @click="setGender('남아')">남아</button>
      <button type="button" @click="setGender('여아')">여아</button>

      <label>중성화 여부</label>
      <button type="button" @click="setCastration(true)">Yes</button>
      <button type="button" @click="setCastration(false)">No</button>

      <label>체급</label>
      <button type="button" @click="setGroup('소형')">소형</button>
      <button type="button" @click="setGroup('중형')">중형</button>
      <button type="button" @click="setGroup('대형')">대형</button>

      <label>견종</label>
      <input v-model="pet.breed" type="text" />

      <label>몸무게</label>
      <input v-model="pet.weight" type="number" />

      <label>특이사항</label>
      <textarea v-model="pet.note" maxlength="50"></textarea>

      <button type="submit">등록</button>
    </form>

    <div v-if="showModal">
      <p>추가로 등록할 반려견이 있나요?</p>
      <button @click="addAnotherPet">추가 등록하기</button>
      <button @click="skipToDogWalkerIntro">이제 됐어요</button>
    </div>
  </div>
</template>

<script>
import { usePetStore } from "@/stores/pet";
import { useAuthStore } from "@/stores/auth";

export default {
  data() {
    return {
      pet: {
        name: "",
        birth: "",
        gender: "",
        castration: false,
        group: "",
        breed: "",
        weight: null,
        note: "",
      },
      showModal: false,
    };
  },
  methods: {
    setGender(gender) {
      this.pet.gender = gender;
    },
    setCastration(value) {
      this.pet.castration = value;
    },
    setGroup(group) {
      this.pet.group = group;
    },
    async registerPet() {
      const petStore = usePetStore();
      const authStore = useAuthStore();
      const userId = authStore.user.email;

      try {
        await petStore.addPet({ ...this.pet, userId });
        this.showModal = true;
      } catch (error) {
        console.error("반려견 등록 실패:", error);
      }
    },
    addAnotherPet() {
      this.pet = {
        name: "",
        birth: "",
        gender: "",
        castration: false,
        group: "",
        breed: "",
        weight: null,
        note: "",
      };
      this.showModal = false;
    },
    skipToDogWalkerIntro() {
      this.$router.push("/dog-walker-intro");
    },
  },
};
</script>

<style scoped>
h1 {
  text-align: center;
}
form {
  display: flex;
  flex-direction: column;
}
button {
  margin: 5px;
  padding: 10px 15px;
}
</style> -->
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
  </div>
</template>

<script>
import { reactive } from "vue";
import { usePetStore } from "@/stores/pet";

export default {
  setup() {
    const petStore = usePetStore();

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
      } catch (error) {
        console.error("반려견 등록 실패:", error);
        alert("반려견 등록에 실패했습니다. 다시 시도해주세요.");
      }
    };

    return {
      pet,
      setGender,
      setCastration,
      setGroup,
      registerPet,
    };
  },
};
</script>

<style>
.active {
  background-color: #007bff;
  color: white;
}
</style>
