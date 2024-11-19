<template>
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
    // 서버에서 데이터를 가져온다고 가정
    const response = await fetch("/api/getWalkLog"); // 실제 API 엔드포인트로 교체
    const data = await response.json();
  
    // WalkLog 데이터 중 하나를 선택 (샘플)
    const sampleWalk = data[0]; // 배열 형태라고 가정
  
    // JSON 파싱
    walkData.value = JSON.parse(sampleWalk.walk_path);

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
    const pathCoordinates = walkData.value.path.map((p) => ({
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
  