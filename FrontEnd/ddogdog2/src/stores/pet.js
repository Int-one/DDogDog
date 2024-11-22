
import { defineStore } from "pinia";
import axios from "axios";

export const usePetStore = defineStore("pet", {
  state: () => ({
    pets: [], // 사용자 반려견 목록
    goWith: [],
  }),
  actions: {
    // 사용자 반려견 목록 가져오기
    async fetchPets() {
      try {
        const userId = localStorage.getItem("user_id"); // 로컬스토리지에서 user_id 가져오기
        // const userId = "test@test.com";
        if (!userId) {
          throw new Error("사용자 ID를 찾을 수 없습니다.");
        }

        const response = await axios.get(`http://localhost:8081/api/pet/${userId}`, {
          headers: {
            'access-token': `${localStorage.getItem("token")}`, // JWT 토큰 추가
          },
        });
        this.pets = response.data; // 반려견 목록 업데이트

        this.pets.forEach((pet) => {
          this.calculateLastWalkTime(pet);
        });
        console.log(this.pets)
      } catch (error) {
        console.error("반려견 목록 가져오기 실패:", error);
        throw error;
      }
    },


    calculateLastWalkTime(dog) {
      if (!dog.endTime) {
        dog.endTime = "산책 가고 싶어요";
        return;
      }
    
      const lastWalkDate = new Date(dog.endTime);
      const now = new Date();
      const days = Math.floor((now - lastWalkDate) / (1000 * 60 * 60 * 24));
    
      if (days === 0) {
        dog.endTime = "마지막 산책 오늘";
      } else if (days === 1) {
        dog.endTime = "마지막 산책 1일 전";
      } else {
        dog.endTime = `마지막 산책 ${days}일 전`;
      }
    },

    // 반려견 등록(수정 전)
    // async addPet(petData) {
    //   try {
    //     const userId = localStorage.getItem("user_id"); // 로컬스토리지에서 user_id 가져오기
    //     if (!userId) {
    //       throw new Error("사용자 ID를 찾을 수 없습니다.");
    //     }
    //     // userId = "test@test.com"
    //     const token = localStorage.getItem("token"); // JWT 토큰 가져오기
    //     if (!token) {
    //       throw new Error("JWT 토큰을 찾을 수 없습니다.");
    //     }

    //     console.log(petData.petBirth)
    //     petData.petBirth += "T00:00:00"
    //     console.log(petData.petBirth)
    //     // 사용자 ID 포함한 반려견 데이터
    //     const petWithUserId = { ...petData, userId };
        
    //     console.log(petWithUserId)

    //     const response = await axios.post("http://localhost:8081/api/pet", petWithUserId, {
    //       headers: {
    //         'access-token': `${localStorage.getItem("token")}`,
    //         "Content-Type": "application/json",
    //       },
    //     });
    //     console.log("fetched")

    //     this.pets.push(response.data); // 새로 등록된 반려견을 목록에 추가
    //   } catch (error) {
    //     console.error("반려견 등록 실패:", error);
    //     throw error;
    //   }
    // },
    async addPet(petData) {
      try {
        // 사용자 ID 가져오기
        const userId = localStorage.getItem("user_id");
        if (!userId) {
          throw new Error("사용자 ID를 찾을 수 없습니다.");
        }
    
        
    
      
    
        // FormData 생성
        const formData = new FormData();
        formData.append("pet", JSON.stringify(petData)); // JSON으로 직렬화하여 추가
        formData.append("file", petData.petPhoto); // 파일 추가
       
    
        console.log("FormData 확인:");
        for (let pair of formData.entries()) {
          console.log(`${pair[0]}: ${pair[1]}`);
        }
    
        // API 호출
        const response = await axios.post("http://localhost:8081/api/pet", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            "access-token": localStorage.getItem("token"),
          },
        });
    
        this.pets.push(response.data); // 새로 등록된 반려견 추가
      } catch (error) {
        console.error("반려견 등록 실패:", error);
        throw error;
      }
    },
    

  },
});
