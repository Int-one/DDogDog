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
</style> -->



<template>
  <div class="signup-page">
    <!-- 상단 헤더 -->
    <header class="header">
      <button class="back-button" @click="goBack">
        <i class="fi fi-rr-angle-left"></i>
      </button>
    </header>

    <!-- 본문 내용 -->
    <div class="content">
      <h2 class="message">
        현재 위치 정보를 확인해주세요🌍
      </h2>

      <!-- 위치 가져오기 버튼 -->
      <button @click="fetchLocation" class="action-button">
        위치 정보 가져오기
      </button>

      <!-- 위치 정보 표시 -->
      <div v-if="location.address" class="location-info">
        <p class="location-text">{{ location.address }}</p>
        <div id="map" class="map"></div> <!-- 지도 추가 -->
        <div class="confirm-buttons-vertical">
          <button @click="fetchLocation" class="retry-button">아니에요</button>
          <button @click="confirmLocation" class="confirm-button">일치해요</button>
        </div>
      </div>

      <!-- 오류 메시지 표시 -->
      <div v-if="error" class="error">
        <p>오류: {{ error }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, watch, onMounted } from "vue";
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

// 지도 표시
const displayMap = () => {
  const container = document.getElementById("map");
  if (!container) return; // #map 요소가 렌더링되지 않은 경우 안전하게 종료

  const options = {
    center: new kakao.maps.LatLng(location.latitude, location.longitude),
    level: 3,
  };

  const map = new kakao.maps.Map(container, options);

  const marker = new kakao.maps.Marker({
    position: new kakao.maps.LatLng(location.latitude, location.longitude),
  });

  marker.setMap(map);
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

// Kakao 지도 표시를 위한 watch
watch(
  () => location.address,
  (newAddress) => {
    if (newAddress) {
      displayMap(); // 주소가 업데이트되면 지도 표시
    }
  }
);

// 뒤로가기 버튼 동작
const goBack = () => {
  router.push("/signup/height-weight");
};

// Kakao 지도 스크립트 로드
onMounted(() => {
  const script = document.createElement("script");
  script.src = "//dapi.kakao.com/v2/maps/sdk.js?appkey=YOUR_APP_KEY&libraries=services";
  script.async = true;
  document.head.appendChild(script);
});
</script>

<style scoped>
/* 전체 레이아웃 스타일 */
.signup-page {
  padding: 10px;
  font-family: Arial, sans-serif;
  background-color: #ffffff;
  min-height: 100vh;
}

/* 헤더 스타일 */
.header {
  position: relative;
  top: 40px;
  margin-bottom: 40px;
  display: flex;
  align-items: center;
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

.content {
  margin-left: 10px;
  margin-right: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 15px;
}

.message {
  font-size: 1.3rem;
  font-weight: bold;
  color: #333;
  text-align: left;
  width: 100%;
}

.action-button {
  width: 100%;
  max-width: 400px;
  padding: 10px;
  font-size: 1rem;
  font-weight: bold;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  text-align: center;
  transition: background-color 0.3s ease;
  background-color: #5eb75e;
  color: white;
}

.action-button:hover {
  background-color: #4ba64b;
}

.location-info {
  margin-top: 20px;
  font-size: 1rem;
  text-align: center;
}

.location-text {
  font-size: 1rem; /* 텍스트 크기 줄이기 */
  font-weight: normal;
  margin-bottom: 10px;
}

.map {
  width: 100%;
  max-width: 400px;
  height: 300px;
  margin-top: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.confirm-buttons-vertical {
  display: flex;
  flex-direction: column; /* 수직 정렬 */
  gap: 10px; /* 버튼 간 간격 */
  margin-top: 20px;
}

.retry-button {
  padding: 8px;
  text-align: center;
  border: 2px solid #5eb75e;
  background-color: white;
  color: #5eb75e;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
}

.retry-button:hover {
  background-color: #e7ffe7;
}

.confirm-button {
  padding: 8px;
  text-align: center;
  border: 2px solid #5eb75e;
  background-color: #5eb75e;
  color: white;
  border-radius: 12px;
  font-size: 1rem;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease-in-out;
}

.confirm-button:hover {
  background-color: #4ba64b;
}

.error {
  color: red;
  margin-top: 20px;
  text-align: center;
}
</style>

