<template>
    <div>
      <h1>도그워커 가입</h1>
      <ul>
        <li v-for="(item, index) in terms" :key="index">
          <input type="checkbox" v-model="checkedItems[index]" />
          {{ item }}
        </li>
      </ul>
      <button :disabled="!allChecked" @click="confirmTerms">모두 확인했어요</button>
    </div>
  </template>
  
  <script>
  import { ref, computed } from "vue";
  import { useRouter } from "vue-router";
  import { useAuthStore } from "@/stores/auth";
  
  export default {
    setup() {
      const router = useRouter();
      const authStore = useAuthStore();
      const terms = ref([
        "주의사항 1: ...",
        "주의사항 2: ...",
        "주의사항 3: ...",
        "주의사항 4: ...",
        "주의사항 5: ...",
      ]);
  
      const checkedItems = ref(Array(terms.value.length).fill(false));
  
      const allChecked = computed(() =>
        checkedItems.value.every((checked) => checked)
      );
  
      const confirmTerms = async () => {
        const userId = localStorage.getItem("user_id");
        if (!userId) {
          alert("사용자 정보가 없습니다. 다시 로그인해주세요.");
          return;
        }
        try {
          await authStore.updateDogWalkerStatus(userId, true);
          alert("도그워커로 가입되었습니다!");
          router.push("/dog-walker-profile"); // 도그워커 프로필 작성 페이지로 이동
        } catch (error) {
          console.error("도그워커 가입 실패:", error);
          alert("도그워커 가입에 실패했습니다. 다시 시도해주세요.");
        }
      };
  
      return {
        terms,
        checkedItems,
        allChecked,
        confirmTerms,
      };
    },
  };
  </script>
  
  <style>
  button:disabled {
    background-color: #ccc;
    cursor: not-allowed;
  }
  </style>
  