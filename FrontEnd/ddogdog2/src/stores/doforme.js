import { defineStore } from "pinia";
import axios from "axios";

export const useDoForMeStore = defineStore("doforme", {
  state: () => ({
    trades: [], // '해주세요' 거래 데이터
  }),
  actions: {
    async fetchTrades() {
      try {
        const response = await axios.get("http://localhost:8081/api/trade/please", {
          headers: { "access-token": localStorage.getItem("token") },
        });
        this.trades = response.data;
      } catch (error) {
        console.error("거래 데이터를 가져오는 데 실패했습니다:", error);
      }
    },
  },
});
