<template>
  <div>
    <h1>도그워커 상세 프로필</h1>

    <div v-if="dogWalker">
      <img :src="dogWalker.profilePhoto" alt="프로필 사진" class="profile-photo" />

      <h2>{{ dogWalker.nickname }} (Level {{ dogWalker.level }})</h2>
      <p><strong>산책 가능 시간대:</strong> {{ dogWalker.walkAvailableTimes.join(", ") }}</p>
      <p><strong>산책 가능 시간:</strong> {{ formatWalkTime(dogWalker.walkDuration) }}</p>
      <p><strong>대형견 가능 여부:</strong> {{ dogWalker.largeDogAvailable ? "가능" : "불가능" }}</p>
      <p><strong>기본 금액:</strong> {{ dogWalker.basePrice }}원</p>
      <p><strong>자기소개:</strong> {{ dogWalker.introduction || "소개 없음" }}</p>
      <p><strong>마지막 산책 시간:</strong> {{ dogWalker.lastWalkTime || "정보 없음" }}</p>
      <p><strong>매너 점수:</strong> {{ dogWalker.tailBpm }} bpm</p>
      <p><strong>총 산책 횟수:</strong> {{ dogWalker.totalWalkCount }}회</p>
      <p><strong>고용된 횟수:</strong> {{ dogWalker.hiredCount }}회</p>
      <h3>리뷰</h3>
      <ul>
        <li v-for="review in dogWalker.reviews" :key="review.id">
          {{ review.content }} ({{ review.rating }}점)
        </li>
      </ul>

      <button v-if="isMyProfile" @click="editProfile">프로필 수정</button>
    </div>

    <div v-else>
      <p>도그워커 정보를 불러오는 중입니다...</p>
    </div>
  </div>
</template>

<script>
import { ref, onMounted, computed } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";

export default {
  setup() {
    const route = useRoute();
    const router = useRouter();
    const dogWalker = ref(null);
    const isMyProfile = computed(() => {
      const userId = localStorage.getItem("user_id");
      return dogWalker.value?.userId === userId;
    });

    const fetchDogWalkerDetails = async () => {
      try {
        const { id } = route.params;
        const response = await axios.get(`http://localhost:8081/api/dogwalker/${id}`);
        dogWalker.value = response.data;
      } catch (error) {
        console.error("도그워커 정보를 불러오지 못했습니다:", error);
        alert("도그워커 정보를 가져오는 데 실패했습니다.");
        router.push("/dogwalker/list");
      }
    };

    const editProfile = () => {
      router.push("/dogwalker/profile");
    };

    const formatWalkTime = (duration) => {
      switch (duration) {
        case 0:
          return "1시간 미만";
        case 1:
          return "1~2시간";
        case 2:
          return "2~3시간";
        case 3:
          return "3시간 이상";
        default:
          return "정보 없음";
      }
    };

    onMounted(() => {
      fetchDogWalkerDetails();
    });

    return {
      dogWalker,
      isMyProfile,
      editProfile,
      formatWalkTime,
    };
  },
};
</script>

<style>
.profile-photo {
  width: 150px;
  height: 150px;
  border-radius: 50%;
  object-fit: cover;
  margin-bottom: 20px;
}
</style>
