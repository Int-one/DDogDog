<!-- 

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

      
      <label>반려견 대표사진</label>
      <input type="file" @change="onFileChange" />

      <button type="submit">등록</button>
    </form>
  </div>
</template>

<script>
import { reactive, ref } from "vue";
import { usePetStore } from "@/stores/pet";
import axios from "axios";

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
      userId:localStorage.getItem("user_id"),
    });

    const petPhoto = ref(null);

    const setGender = (gender) => {
      pet.petGender = gender;
    };

    const setCastration = (value) => {
      pet.castration = value;
    };

    const setGroup = (group) => {
      pet.group = group;
    };

    const onFileChange = (event) => {
      petPhoto.value = event.target.files[0];
    };

    const registerPet = async () => {
      try {
        if (!pet.petName || !pet.petBirth || pet.petGender === null || pet.castration === null) {
          alert("모든 필수 항목을 입력해주세요.");
          return;
        }
        pet.petBirth += "T00:00:00";
        // await petStore.addPet({pet});
        await add();
        alert("반려견이 성공적으로 등록되었습니다!");
      } catch (error) {
        console.error("반려견 등록 실패:", error);
        alert("반려견 등록에 실패했습니다. 다시 시도해주세요.");
      }
    };



    const add = () => {
      try {
        // 사용자 ID 가져오기
        const userId = localStorage.getItem("user_id");
        if (!userId) {
          throw new Error("사용자 ID를 찾을 수 없습니다.");
        }
    
        // FormData 생성
        const formData = new FormData();
        formData.append("pet", JSON.stringify(pet)); // JSON으로 직렬화하여 추가
        formData.append("file", petPhoto.value); // 파일 추가
       
    
        console.log("FormData 확인:");
        for (let pair of formData.entries()) {
          console.log(`${pair[0]}: ${pair[1]}`);
        }
    
        // API 호출
        const response = axios.post("http://localhost:8081/api/pet", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            "access-token": localStorage.getItem("token"),
          },
        });
    
        // this.pets.push(response.data); // 새로 등록된 반려견 추가
      } catch (error) {
        console.error("반려견 등록 실패:", error);
        throw error;
      }
    };


    return {
      pet,
      petPhoto,
      setGender,
      setCastration,
      setGroup,
      onFileChange,
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


 -->

 <template>
  <div class="registration-page">
    <!-- 상단 헤더 -->
    <header class="header">
      <button class="back-button" @click="goBack">
        <i class="fi fi-rr-angle-left"></i>
      </button>
    </header>

    <!-- 본문 내용 -->
    <div class="content">
      <!-- 설명 -->
      <h1 class="title">반려견 정보를 입력해주세요🐾</h1>
      <p class="description">
        나중에 수정할 수 있어요<br />
      </p>

      
      <form class="registration-form" @submit.prevent="registerPet">
        <!-- 이름 -->
        <label for="petName" class="form-label">이름</label>
        <input v-model="pet.petName" id="petName" type="text" class="form-input" placeholder="반려견 이름을 입력하세요" required />

        <!-- 생년월일 -->
        <label for="petBirth" class="form-label">생년월일</label>
        <input v-model="pet.petBirth" id="petBirth" type="date" class="form-input" required />

        <!-- 성별 -->
        <label class="form-label">성별</label>
        <div class="button-group">
          <button type="button" @click="setGender(true)" :class="{ active: pet.petGender === true }" class="toggle-button">남아</button>
          <button type="button" @click="setGender(false)" :class="{ active: pet.petGender === false }" class="toggle-button">여아</button>
        </div>

        <!-- 중성화 여부 -->
        <label class="form-label">중성화 여부</label>
        <div class="button-group">
          <button type="button" @click="setCastration(true)" :class="{ active: pet.castration === true }" class="toggle-button">Yes</button>
          <button type="button" @click="setCastration(false)" :class="{ active: pet.castration === false }" class="toggle-button">No</button>
        </div>

        <!-- 체급 -->
        <label class="form-label">체급</label>
        <div class="button-group">
          <button type="button" @click="setGroup('소형')" :class="{ active: pet.group === '소형' }" class="toggle-button">소형</button>
          <button type="button" @click="setGroup('중형')" :class="{ active: pet.group === '중형' }" class="toggle-button">중형</button>
          <button type="button" @click="setGroup('대형')" :class="{ active: pet.group === '대형' }" class="toggle-button">대형</button>
        </div>

        <!-- 견종 -->
        <label for="breed" class="form-label">견종</label>
        <input v-model="pet.breed" id="breed" type="text" class="form-input" placeholder="견종을 입력하세요" required />

        <!-- 몸무게 -->
        <label for="petWeight" class="form-label">몸무게</label>
        <input v-model="pet.petWeight" id="petWeight" type="number" class="form-input" placeholder="몸무게를 입력하세요 (kg)" />

        <!-- 특이사항 -->
        <label for="ps" class="form-label">특이사항 (최대 50자)</label>
        <textarea v-model="pet.ps" id="ps" class="form-textarea" maxlength="50" placeholder="특이사항을 입력하세요"></textarea>

        <!-- 사진 업로드 -->
        <label for="petPhoto" class="form-label">반려견 대표사진</label>
        <input type="file" id="petPhoto" @change="onFileChange" class="form-input-file" />

        <!-- 등록 버튼 -->
        <button type="submit" class="action-button">등록</button>
      </form>
    </div>
  </div>
</template>

<script>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { usePetStore } from "@/stores/pet";
import axios from "axios";

export default {
  setup() {
    const petStore = usePetStore();
    const router = useRouter();

    const pet = reactive({
      petName: "",
      petBirth: "",
      petGender: null,
      castration: null,
      group: "",
      breed: "",
      petWeight: null,
      ps: "",
      userId: localStorage.getItem("user_id"),
    });

    const petPhoto = ref(null);

    const setGender = (gender) => {
      pet.petGender = gender;
    };

    const setCastration = (value) => {
      pet.castration = value;
    };

    const setGroup = (group) => {
      pet.group = group;
    };

    const onFileChange = (event) => {
      petPhoto.value = event.target.files[0];
    };

    const registerPet = async () => {
      try {
        if (!pet.petName || !pet.petBirth || pet.petGender === null || pet.castration === null) {
          alert("모든 필수 항목을 입력해주세요.");
          return;
        }
        pet.petBirth += "T00:00:00";
        await add();
        alert("반려견이 성공적으로 등록되었습니다!");
      } catch (error) {
        console.error("반려견 등록 실패:", error);
        alert("반려견 등록에 실패했습니다. 다시 시도해주세요.");
      }
    };

    const add = async () => {
      try {
        const formData = new FormData();
        formData.append("pet", JSON.stringify(pet));
        formData.append("file", petPhoto.value);

        await axios.post("http://localhost:8081/api/pet", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            "access-token": localStorage.getItem("token"),
          },
        });
      } catch (error) {
        console.error("반려견 등록 실패:", error);
        throw error;
      }
    };

    const goBack = () => {
      router.push("/welcome-dog"); // 뒤로가기 클릭 시 /welcome-dog 경로로 이동
    };

    return {
      pet,
      petPhoto,
      setGender,
      setCastration,
      setGroup,
      onFileChange,
      registerPet,
      goBack,
    };
  },
};
</script>


<style scoped>
/* 전체 페이지 스타일 */
.registration-page {
  padding: 10px;
  background-color: #ffffff;
  font-family: Arial, sans-serif;
  height: 100vh;
  overflow-y: auto;
}

/* 헤더 스타일 */
.header {
  position: relative;
  top: 40px; /* 조금 아래로 이동 */
  right: 0px;
  display: flex;
  align-items: center;
  margin-bottom: 40px; /* 헤더와 본문 간 간격 추가 */
  gap: 
  0px;
}

.back-button {
  font-size: 1.2rem;
  background: none;
  border: none;
  color: #333; 
  cursor: pointer;
  display: flex;
  align-items: center;
}

.back-button i {
  font-size: 1.2rem;
}

/* 본문 내용 */
.content {
  max-width: 400px;
  width: 100%;
  margin: 0 auto;
}

/* 설명 텍스트 */
.description {
  font-size: 1rem;
  color: #666;
  text-align: center;
  margin-bottom: 20px;
  text-align: left;
  margin-left: 5px; 
}

/* 제목 */
.title {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
  text-align: left; 
  margin-top: 60px;
  margin-left: 5px;
  width: 100%; 
}

/* 폼 스타일 */
.registration-form {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.form-label {
  font-size: 1rem;
  font-weight: bold;
  color: #555;
}

.form-input,
.form-textarea,
.form-input-file {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
}

.form-textarea {
  resize: none;
}

/* 버튼 그룹 */
.button-group {
  display: flex;
  gap: 10px;
}

.toggle-button {
  flex: 1;
  padding: 10px;
  background-color: #f1f1f1;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  cursor: pointer;
}

.toggle-button.active {
  background-color: #5eb75e;
  color: white;
}

.toggle-button:hover {
  background-color: #e1e1e1;
}

/* 등록 버튼 */
.action-button {
  padding: 10px;
  background-color: #5eb75e;
  color: white;
  border: none;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  margin-bottom: 100px;
}

.action-button:hover {
  background-color: #4ba64b;
}
</style>
