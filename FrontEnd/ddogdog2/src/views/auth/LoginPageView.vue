
<template>
  <div>
    <h1>로그인</h1>
    <form @submit.prevent="handleLogin">
      <label for="email">이메일:</label>
      <input v-model="email" type="email" id="email" required />

      <label for="password">비밀번호:</label>
      <input v-model="password" type="password" id="password" required />

      <button type="submit">로그인</button>
    </form>
    <p v-if="errorMessage">{{ errorMessage }}</p>
  </div>
</template>

<script>
import { useAuthStore } from "@/stores/auth";

export default {
  data() {
    return {
      email: "",
      password: "",
      errorMessage: null,
    };
  },
  methods: {
    async handleLogin() {
      const authStore = useAuthStore();
      try {
        await authStore.login(this.email, this.password);
        this.$router.push("/main"); // 메인 페이지로 이동
      } catch (error) {
        this.errorMessage = "로그인 실패. 이메일 또는 비밀번호를 확인하세요.";
      }
    },
  },
};
</script>

<!-- <template>
  <div>
    <h1>로그인</h1>
    <form @submit.prevent="handleLogin">
      <label>이메일</label>
      <input v-model="email" type="email" required />
      <label>비밀번호</label>
      <input v-model="password" type="password" required />
      <button type="submit">로그인</button>
    </form>
  </div>
</template>

<script>
import { useAuthStore } from "@/stores/auth";

export default {
  data() {
    return {
      email: "",
      password: "",
    };
  },
  methods: {
    async handleLogin() {
      const authStore = useAuthStore();
      try {
        await authStore.login(this.email, this.password);
        alert("로그인에 성공했습니다!");
        this.$router.push('/main'); // 메인 페이지로 이동
      } catch (error) {
        alert("로그인에 실패했습니다. 이메일과 비밀번호를 확인해주세요.");
        console.error("로그인 실패:", error);
      }
    },
  },
};
</script> -->

<!-- <template>
  <div>
    <h1>로그인</h1>
    <form @submit.prevent="handleLogin">
      <label>이메일</label>
      <input v-model="email" type="email" required />
      <label>비밀번호</label>
      <input v-model="password" type="password" required />
      <button type="submit">로그인</button>
    </form>
  </div>
</template>

<script>
import { useAuthStore } from "@/stores/auth";
import { usePetStore } from "@/stores/pet";

export default {
  data() {
    return {
      email: "",
      password: "",
    };
  },
  methods: {
    async handleLogin() {
      const authStore = useAuthStore();
      const petStore = usePetStore();
      try {
        await authStore.login(this.email, this.password);
        alert("로그인에 성공했습니다!");

        // 반려견 등록 여부 확인
        await petStore.fetchPets();
        if (petStore.pets.length === 0) {
          this.$router.push("/welcome-dog");
        } else {
          this.$router.push("/main");
        }
      } catch (error) {
        alert("로그인에 실패했습니다. 이메일과 비밀번호를 확인해주세요.");
        console.error("로그인 실패:", error);
      }
    },
  },
};
</script> -->
