<template>
  <div>
    <div id="map" style="height: 500px; width: 100%;"></div>
    <button @click="drawPolyline">Draw Polyline</button>
  </div>
</template>

<script setup>
import { onMounted, ref } from "vue";

// 지도와 Polyline 관련 변수
let map;
const coordinates = ref([]); // Polyline 좌표 배열

// 지도 초기화
const initMap = () => {
  // 카카오 지도 객체 생성
  const mapContainer = document.getElementById("map"); // 지도를 표시할 div
  const mapOption = {
    center: new kakao.maps.LatLng(37.5665, 126.9780), // 초기 중심 (서울 광화문)
    level: 3, // 지도 확대 레벨
  };

  map = new kakao.maps.Map(mapContainer, mapOption);
};

// Polyline 그리기 함수
const drawPolyline = async () => {
  // 예제 좌표 데이터
  // coordinates.value = [
  //   { lat: 37.5665, lng: 126.9780 },
  //   { lat: 37.5668, lng: 126.9783 },
  //   { lat: 37.5672, lng: 126.9787 },
  //   { lat: 37.5675, lng: 126.9791 },
  //   { lat: 37.5678, lng: 126.9795 },
  // ];

  // 서버에서 데이터를 가져온다고 가정
  const response = await fetch("http://localhost:8081/api/walklog/1"); // 선택한 walklog의 id를 마지막에 넣어줘야 한다.
  // console.log(response.json())
  const data = await response.json();

  // WalkLog 데이터 중 하나를 선택 (샘플)
  const sampleWalk = data.walkPath.path; // 배열 형태라고 가정
  coordinates.value = sampleWalk

  // Polyline 좌표 변환
  const polylinePath = coordinates.value.map(
    (coord) => new kakao.maps.LatLng(coord.lat, coord.lng)
  );

  // Polyline 객체 생성
  const polyline = new kakao.maps.Polyline({
    path: polylinePath, // 선을 구성하는 좌표배열
    strokeWeight: 5, // 선 두께
    strokeColor: "#FF0000", // 선 색깔
    strokeOpacity: 0.8, // 선의 투명도
    strokeStyle: "solid", // 선 스타일
  });

  // Polyline 지도에 표시
  polyline.setMap(map);

  // 지도 중심을 Polyline의 시작점으로 이동
  if (polylinePath.length > 0) {
    map.setCenter(polylinePath[0]);
  }
};

onMounted(() => {
  // 카카오 지도 초기화
  initMap();
});
</script>






<!-- <template>
    <div>
      <div id="map" style="height: 500px; width: 100%;"></div>
      <button @click="loadWalkLog">Load Walk Log</button>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from "vue";
  
  // Google Maps 관련 변수
  let map;
  let polyline;
  const walkData = ref(); // 서버에서 가져온 경로 데이터
  
  // 지도 초기화
  const initMap = () => {
    map = new google.maps.Map(document.getElementById("map"), {
      center: { lat: 37.5665, lng: 126.9780 }, // 초기 중심 (서울)
      zoom: 15,
    });
  
    polyline = new google.maps.Polyline({
      geodesic: true,
      strokeColor: "#FF0000",
      strokeOpacity: 1.0,
      strokeWeight: 3,
      map: map,
    });
  };
  
  // WalkLog 데이터 로드 (예: 서버에서 API 호출)
  const loadWalkLog = async () => {
   
    // // 서버에서 데이터를 가져온다고 가정
    // const response = await fetch("/api/getWalkLog"); // 실제 API 엔드포인트로 교체
    // const data = await response.json();
  
    // // WalkLog 데이터 중 하나를 선택 (샘플)
    // const sampleWalk = data[0]; // 배열 형태라고 가정
  
    // // JSON 파싱
    // walkData.value = JSON.parse(sampleWalk.walk_path);

    walkData.value = [
    {"lat": 37.5665, "lng": 126.9780, "time": "2024-11-15 10:00:00"},
    {"lat": 37.5668, "lng": 126.9783, "time": "2024-11-15 10:05:00"},
    {"lat": 37.5672, "lng": 126.9787, "time": "2024-11-15 10:10:00"},
    {"lat": 37.5675, "lng": 126.9791, "time": "2024-11-15 10:15:00"},
    {"lat": 37.5678, "lng": 126.9795, "time": "2024-11-15 10:20:00"},
    {"lat": 37.5682, "lng": 126.9799, "time": "2024-11-15 10:25:00"},
    {"lat": 37.5685, "lng": 126.9803, "time": "2024-11-15 10:30:00"},
    {"lat": 37.5688, "lng": 126.9807, "time": "2024-11-15 10:35:00"},
    {"lat": 37.5690, "lng": 126.9810, "time": "2024-11-15 10:40:00"},
    {"lat": 37.5693, "lng": 126.9813, "time": "2024-11-15 10:45:00"},
    {"lat": 37.5695, "lng": 126.9817, "time": "2024-11-15 10:50:00"},
    {"lat": 37.5698, "lng": 126.9820, "time": "2024-11-15 10:55:00"},
    {"lat": 37.5700, "lng": 126.9825, "time": "2024-11-15 11:00:00"}
  ]
  
    // 경로를 지도에 그리기
    const pathCoordinates = walkData.value.map((p) => ({
      lat: p.lat,
      lng: p.lng,
    }));
  
    polyline.setPath(pathCoordinates);
  
    // 지도 중심을 경로의 시작점으로 이동
    if (pathCoordinates.length > 0) {
      map.setCenter(pathCoordinates[0]);
    }
  };
  
  onMounted(() => {
    // Google Maps 초기화
    initMap();
  });
  </script>
   -->