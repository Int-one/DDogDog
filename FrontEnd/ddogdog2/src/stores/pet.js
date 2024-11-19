
// import { defineStore } from "pinia";
// import axios from "axios";

// export const usePetStore = defineStore("pet", {
//   state: () => ({
//     pets: [], // 사용자 반려견 목록
//   }),
//   actions: {
//     // 사용자 반려견 목록 가져오기
//     async fetchPets() {
//       try {
//         // 로컬스토리지에서 user_id 가져오기
//         const userId = localStorage.getItem("user_id");
//         if (!userId) {
//           throw new Error("사용자 ID를 찾을 수 없습니다.");
//         }

//         const response = await axios.get(`http://localhost:8081/api/pet/${userId}`, {
//           headers: {
//             'access-token': `${localStorage.getItem("token")}`, // JWT 토큰 추가
//           },
//         });
//         this.pets = response.data;
//       } catch (error) {
//         console.error("반려견 목록 가져오기 실패:", error);
//         throw error;
//       }
//     },
//   },
// });

import { defineStore } from "pinia";
import axios from "axios";

export const usePetStore = defineStore("pet", {
  state: () => ({
    pets: [], // 사용자 반려견 목록
  }),
  actions: {
    // 사용자 반려견 목록 가져오기
    async fetchPets() {
      try {
        const userId = localStorage.getItem("user_id"); // 로컬스토리지에서 user_id 가져오기
        if (!userId) {
          throw new Error("사용자 ID를 찾을 수 없습니다.");
        }

        const response = await axios.get(`http://localhost:8081/api/pet/${userId}`, {
          headers: {
            'access-token': `${localStorage.getItem("token")}`, // JWT 토큰 추가
          },
        });
        this.pets = response.data; // 반려견 목록 업데이트
      } catch (error) {
        console.error("반려견 목록 가져오기 실패:", error);
        throw error;
      }
    },

    // 반려견 등록
    async addPet(petData) {
      try {
        const userId = localStorage.getItem("user_id"); // 로컬스토리지에서 user_id 가져오기
        if (!userId) {
          throw new Error("사용자 ID를 찾을 수 없습니다.");
        }
        // userId = "test@test.com"
        const token = localStorage.getItem("token"); // JWT 토큰 가져오기
        if (!token) {
          throw new Error("JWT 토큰을 찾을 수 없습니다.");
        }

        console.log(petData.petBirth)
        petData.petBirth += "T00:00:00"
        console.log(petData.petBirth)
        // 사용자 ID 포함한 반려견 데이터
        const petWithUserId = { ...petData, userId };
        
        console.log(petWithUserId)

        const response = await axios.post("http://localhost:8081/api/pet", petWithUserId, {
          headers: {
            'access-token': `${localStorage.getItem("token")}`,
            "Content-Type": "application/json",
          },
        });
        console.log("fetched")

        this.pets.push(response.data); // 새로 등록된 반려견을 목록에 추가
      } catch (error) {
        console.error("반려견 등록 실패:", error);
        throw error;
      }
    },
  },
});
