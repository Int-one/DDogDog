// import { defineStore } from "pinia";
// import axios from "axios";

// export const usePetStore = defineStore("pet", {
//   state: () => ({
//     pets: [], // 사용자 반려견 목록
//   }),
//   actions: {
//     // 사용자 반려견 목록 가져오기
//     async fetchPets(userId, token) {
//       try {
//         const response = await axios.get(`http://localhost:8081/api/pet/${userId}`, {
//           headers: {
//             'access-token': `${token}`,
//           },
//         });
//         this.pets = response.data;
//       } catch (error) {
//         console.error("반려견 목록 가져오기 실패:", error);
//         throw error;
//       }
//     },

//     // 반려견 등록
//     async addPet(petData, token) {
//       try {
//         const response = await axios.post(`http://localhost:8081/api/pet`, petData, {
//           headers: {
//             Authorization: `Bearer ${token}`,
//           },
//         });
//         this.pets.push(response.data); // 새로 등록된 반려견을 목록에 추가
//       } catch (error) {
//         console.error("반려견 등록 실패:", error);
//         throw error;
//       }
//     },

//     // 반려견 삭제
//     async deletePet(petId, token) {
//       try {
//         await axios.delete(`http://localhost:8081/api/pet/${petId}`, {
//           headers: {
//             Authorization: `Bearer ${token}`,
//           },
//         });
//         this.pets = this.pets.filter((pet) => pet.petId !== petId); // 삭제된 반려견 제거
//       } catch (error) {
//         console.error("반려견 삭제 실패:", error);
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
        // 로컬스토리지에서 user_id 가져오기
        const userId = localStorage.getItem("user_id");
        if (!userId) {
          throw new Error("사용자 ID를 찾을 수 없습니다.");
        }

        const response = await axios.get(`http://localhost:8081/api/pet/${userId}`, {
          headers: {
            'access-token': `${localStorage.getItem("token")}`, // JWT 토큰 추가
          },
        });
        this.pets = response.data;
      } catch (error) {
        console.error("반려견 목록 가져오기 실패:", error);
        throw error;
      }
    },
  },
});
