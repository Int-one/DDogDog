<!-- <template>
  <div>
    <h1>위치 정보 입력</h1>
    <button @click="fetchLocation" class="location-button">
      위치 정보 가져오기
    </button>

    <div v-if="location.address" class="location-info">
      <p>현재 위치: {{ location.address }}</p>
      <p>이 위치가 맞습니까?</p>
      <button @click="confirmLocation" class="confirm-button">맞아요</button>
      <button @click="fetchLocation" class="retry-button">아니에요</button>
    </div>

    <div v-if="error" class="error">
      <p>오류: {{ error }}</p>
    </div>

    <router-link to="/signup/height-weight" class="back-link">뒤로가기</router-link>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { useSignupStore } from "@/stores/signup";

const router = useRouter();
const signupStore = useSignupStore();

const location = reactive({
  latitude: null,
  longitude: null,
  address: null, // 변환된 주소
});
const error = ref(null);

// Kakao 지도 API를 사용하여 위도/경도를 주소로 변환
const convertToAddress = async (lat, lng) => {
  if (!window.kakao || !window.kakao.maps) {
    error.value = "Kakao 지도 API가 로드되지 않았습니다.";
    return;
  }

  const geocoder = new kakao.maps.services.Geocoder();
  const coord = new kakao.maps.LatLng(lat, lng);

  return new Promise((resolve, reject) => {
    geocoder.coord2Address(coord.getLng(), coord.getLat(), (result, status) => {
      if (status === kakao.maps.services.Status.OK) {
        const roadAddress = result[0]?.road_address?.address_name; // 도로명 주소
        const jibunAddress = result[0]?.address?.address_name; // 지번 주소
        resolve(roadAddress || jibunAddress || "주소를 가져올 수 없습니다.");
      } else {
        reject("주소 변환 실패: " + status);
      }
    });
  });
};

// 위치 정보 가져오기
const fetchLocation = async () => {
  error.value = null; // 이전 오류 초기화

  if (!navigator.geolocation) {
    error.value = "브라우저가 위치 정보를 지원하지 않습니다.";
    return;
  }

  navigator.geolocation.getCurrentPosition(
    async (position) => {
      location.latitude = position.coords.latitude;
      location.longitude = position.coords.longitude;

      try {
        location.address = await convertToAddress(location.latitude, location.longitude);
      } catch (e) {
        error.value = e;
      }
    },
    (err) => {
      error.value = "위치 정보를 가져올 수 없습니다: " + err.message;
    },
    {
      enableHighAccuracy: true,
      timeout: 10000,
      maximumAge: 0,
    }
  );
};

// 위치 확인
const confirmLocation = () => {
  if (location.address) {
    // 위치 정보를 저장
    signupStore.setSignupData({ location: location.address });
    router.push("/signup/confirmation"); // 입력 정보 확인 페이지로 이동
  } else {
    alert("위치 정보를 확인할 수 없습니다.");
  }
};
</script>

<style scoped>
.location-button,
.confirm-button,
.retry-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 5px;
  margin: 10px;
}

.location-button:hover,
.confirm-button:hover,
.retry-button:hover {
  background-color: #0056b3;
}

.location-info {
  margin-top: 20px;
  font-size: 18px;
}

.error {
  color: red;
  margin-top: 20px;
}

.back-link {
  display: inline-block;
  margin-top: 20px;
  color: #007bff;
  text-decoration: none;
  font-size: 16px;
}

.back-link:hover {
  text-decoration: underline;
}
</style> -->
<template>
  <div>
    <h1>위치 정보 입력</h1>
    <button @click="fetchLocation" class="location-button">
      위치 정보 가져오기
    </button>

    <div v-if="location.address" class="location-info">
      <p>현재 위치: {{ location.address }}</p>
      <p>이 위치가 맞습니까?</p>
      <button @click="confirmLocation" class="confirm-button">맞아요</button>
      <button @click="fetchLocation" class="retry-button">아니에요</button>
    </div>

    <div v-if="error" class="error">
      <p>오류: {{ error }}</p>
    </div>

    <router-link to="/signup/height-weight" class="back-link">뒤로가기</router-link>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { useSignupStore } from "@/stores/signup";

const router = useRouter();
const signupStore = useSignupStore();

const location = reactive({
  latitude: null,
  longitude: null,
  address: null, // 전체 주소
  shortAddress: null, // 읍/면/동까지의 주소
});
const error = ref(null);

// Kakao 지도 API를 사용하여 위도/경도를 주소로 변환
const convertToAddress = async (lat, lng) => {
  if (!window.kakao || !window.kakao.maps) {
    error.value = "Kakao 지도 API가 로드되지 않았습니다.";
    return;
  }

  const geocoder = new kakao.maps.services.Geocoder();
  const coord = new kakao.maps.LatLng(lat, lng);

  return new Promise((resolve, reject) => {
    // geocoder.coord2Address(coord.getLng(), coord.getLat(), (result, status) => {
      geocoder.coord2Address(127.2352, 37.64657, (result, status) => {
      if (status === kakao.maps.services.Status.OK) {
        const roadAddress = result[0]?.road_address?.address_name; // 도로명 주소
        const jibunAddress = result[0]?.address?.address_name; // 지번 주소
        resolve(roadAddress || jibunAddress || "주소를 가져올 수 없습니다.");
      } else {
        reject("주소 변환 실패: " + status);
      }
    });
  });
};

// 주소에서 읍/면/동까지만 추출
const extractShortAddress = (fullAddress) => {
  const parts = fullAddress.split(" ");
  if (parts.length >= 3) {
    return parts.slice(0, 3).join(" "); // 시/도, 시/군/구, 읍/면/동까지만 결합
  }
  return fullAddress; // 기본적으로 전체 주소 반환
};

// 위치 정보 가져오기
const fetchLocation = async () => {
  error.value = null; // 이전 오류 초기화

  if (!navigator.geolocation) {
    error.value = "브라우저가 위치 정보를 지원하지 않습니다.";
    return;
  }

  navigator.geolocation.getCurrentPosition(
    async (position) => {
      location.latitude = position.coords.latitude;
      location.longitude = position.coords.longitude;

      try {
        const fullAddress = await convertToAddress(location.latitude, location.longitude);
        location.address = fullAddress;
        location.shortAddress = extractShortAddress(fullAddress); // 읍/면/동 추출
      } catch (e) {
        error.value = e;
      }
    },
    (err) => {
      error.value = "위치 정보를 가져올 수 없습니다: " + err.message;
    },
    {
      enableHighAccuracy: true,
      timeout: 10000,
      maximumAge: 0,
    }
  );
};

// 위치 확인
const confirmLocation = () => {
  if (location.shortAddress) {
    // 읍/면/동까지만 저장
    signupStore.setSignupData({ location: location.shortAddress });
    router.push("/signup/confirmation"); // 입력 정보 확인 페이지로 이동
  } else {
    alert("위치 정보를 확인할 수 없습니다.");
  }
};
</script>

<style scoped>
.location-button,
.confirm-button,
.retry-button {
  background-color: #007bff;
  color: white;
  border: none;
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
  border-radius: 5px;
  margin: 10px;
}

.location-button:hover,
.confirm-button:hover,
.retry-button:hover {
  background-color: #0056b3;
}

.location-info {
  margin-top: 20px;
  font-size: 18px;
}

.error {
  color: red;
  margin-top: 20px;
}

.back-link {
  display: inline-block;
  margin-top: 20px;
  color: #007bff;
  text-decoration: none;
  font-size: 16px;
}

.back-link:hover {
  text-decoration: underline;
}
</style>
