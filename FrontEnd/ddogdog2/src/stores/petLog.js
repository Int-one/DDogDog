import { defineStore } from 'pinia';
import { ref, computed } from 'vue';

export const usePetLogStore = defineStore('petLogStore', () => {
  // State
  const petLogs = ref([]); // PetLog 데이터를 저장하는 배열

  // Actions
  const initializePetLogs = (pets) => {
    /**
     * 초기화: petStore.together에 있는 pet의 수만큼 초기 데이터를 생성
     * @param {Array} pets - 함께 산책한 pet 목록 (petStore.together에서 가져옴)
     */
    petLogs.value = pets.map((pet) => ({
      plog_id: null, // 서버에서 설정되므로 null로 초기화
      note: '',
      small: 0,
      big: 0,
      logId: null, // WalkLog의 log_id와 연결
      pet_id: pet.petId,
    }));
  };

  const updatePetLog = (petId, logData) => {
    /**
     * 특정 pet의 로그를 업데이트
     * @param {Number} petId - 업데이트할 pet의 ID
     * @param {Object} logData - 업데이트할 데이터 (note, small, big)
     */
    const index = petLogs.value.findIndex((log) => log.pet_id === petId);
    if (index !== -1) {
      petLogs.value[index] = {
        ...petLogs.value[index],
        ...logData,
      };
    }
  };

  const resetPetLogs = () => {
    /**
     * 모든 로그를 초기 상태로 리셋
     */
    petLogs.value = [];
  };

  const setLogId = (logId) => {
    /**
     * WalkLog의 log_id를 모든 PetLog에 설정
     * @param {Number|String} logId - WalkLog ID
     */
    const parsedLogId = Number(logId)
    petLogs.value.forEach((log) => {
      log.petId = log.pet_id;
      log.logId = parsedLogId;
    });
  };

  // Getters
  const getPetLogs = computed(() => petLogs.value);

  return {
    petLogs,
    initializePetLogs,
    updatePetLog,
    resetPetLogs,
    setLogId,
    getPetLogs,
  };
});
