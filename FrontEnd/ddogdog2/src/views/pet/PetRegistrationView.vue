<!-- <template>
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

      <button type="submit">등록</button>
    </form>
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
      },
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
      const petStore = usePetStore(); // 이름 변경 시 "PetStore"로 수정 가능
      const authStore = useAuthStore();
      const userId = authStore.user.email; // 로그인된 사용자 이메일

      try {
        await dogStore.addDog({ ...this.pet, userId }); // dogStore를 사용 중. 필요시 store 이름 수정.
        alert("반려견이 성공적으로 등록되었습니다!");
        this.$router.push("/dog-walker-intro");
      } catch (error) {
        console.error("반려견 등록 실패:", error);
      }
    },
  },
};
</script> -->
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
</style>
