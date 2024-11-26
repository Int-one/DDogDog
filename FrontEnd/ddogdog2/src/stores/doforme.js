import { defineStore } from "pinia";
import axios from "axios";

export const useDoForMeStore = defineStore("doforme", {
  state: () => ({
    trades: [], // '해주세요' 거래 데이터
    cands: [],
  }),
  actions: {
    async fetchTrades() {
      try {
        console.log(localStorage.getItem('region'))
        const response = await axios.get(`http://localhost:8081/api/trade/please/${localStorage.getItem('region')}`, {
          headers: { "access-token": localStorage.getItem("token") },
        });
        this.trades = response.data;
      } catch (error) {
        console.error("거래 데이터를 가져오는 데 실패했습니다:", error);
      }
    },
    async fetchCands(tradeId) {
      try {
        const response = await axios.get(`http://localhost:8081/api/cand/trade/${tradeId}`, {
          headers: { "access-token": localStorage.getItem("token") },
        });
        this.cands = response.data;
      } catch (error) {
        console.error("거래 데이터를 가져오는 데 실패했습니다:", error);
      }
    },
  },
});
