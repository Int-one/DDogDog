package com.ddogdog.service;

import com.ddogdog.model.dao.TradeDao;
import com.ddogdog.model.dto.Pet;
import com.ddogdog.model.dto.Trade;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeDao tradeDao;
    private final ObjectMapper objectMapper;

    public TradeServiceImpl(TradeDao tradeDao, ObjectMapper objectMapper) {
        this.tradeDao = tradeDao;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean createTrade(Trade trade) {
        int result = tradeDao.insertTrade(trade);
        return result > 0;
    }

    @Override
    public List<Trade> getAllTrades() {
        return tradeDao.getAllTrades();
    }
    
    @Override
    public List<Trade> getAllWillTrades() {
    	return tradeDao.getAllWillTrades();
    }

    @Override
    public Trade getTradeById(Long tradeId) {
        return tradeDao.getTradeById(tradeId);
    }
    
    @Override
    public Trade getLatestTradeById(Trade trade) {
    	return tradeDao.getLatestTradeById(trade);
    }

    @Override
    public boolean updateTrade(Trade trade) {
        int result = tradeDao.updateTrade(trade);
        return result > 0;
    }

    @Override
    public boolean deleteTrade(Long tradeId) {
        int result = tradeDao.deleteTrade(tradeId);
        return result > 0;
    }
    
    @Override
    public List<Map<String, Object>> getAllPleaseTrades() {
        // MyBatis 쿼리 결과 가져오기
        List<Map<String, Object>> trades = tradeDao.getAllPleaseTrades();

        // petNames를 JSON 문자열에서 배열로 변환
        for (Map<String, Object> trade : trades) {
            if (trade.containsKey("petNames")) {
                try {
                    // petNames 값이 문자열인 경우 배열로 파싱
                    String petNamesJson = (String) trade.get("petNames");
                    List<String> petNames = objectMapper.readValue(petNamesJson, new TypeReference<List<String>>() {});
                    trade.put("petNames", petNames);
                } catch (Exception e) {
                    // 오류 발생 시 빈 배열로 초기화
                    trade.put("petNames", List.of());
                }
            }
        }
        return trades;
    }
    
    @Override
    public List<Pet> getPetsByTradeId(Long tradeId) {
    	return tradeDao.getPetsByTradeId(tradeId);
    }
    
    @Override
    public List<Trade> getTradesByCandId(String candId) {
    	return tradeDao.getTradesByCandId(candId);
    }
}
