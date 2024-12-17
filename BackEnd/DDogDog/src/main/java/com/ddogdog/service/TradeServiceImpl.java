package com.ddogdog.service;

import com.ddogdog.model.dao.TradeDao;
import com.ddogdog.model.dto.Pet;
import com.ddogdog.model.dto.Trade;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public List<Trade> getAllWillTrades(String region) {
    	return tradeDao.getAllWillTrades(region);
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
    
    public List<Map<String, Object>> getAllPleaseTrades(String region) {
        // MyBatis 쿼리 결과 가져오기
        List<Map<String, Object>> trades = tradeDao.getAllPleaseTrades(region);

        // 키 이름을 camelCase로 변환하고 petNames를 JSON 문자열에서 List<String>으로 변환
        return trades.stream()
            .map(trade -> {
                Map<String, Object> camelCaseTrade = convertKeysToCamelCase(trade);
                if (camelCaseTrade.containsKey("petNames")) {
                    try {
                        Object petNamesObj = camelCaseTrade.get("petNames");
                        if (petNamesObj instanceof String) {
                            // JSON 문자열을 List<String>으로 변환
                            String petNamesJson = (String) petNamesObj;
                            List<String> petNames = objectMapper.readValue(petNamesJson, new TypeReference<List<String>>() {});
                            camelCaseTrade.put("petNames", petNames);
                        }
                    } catch (Exception e) {
                        // 변환 실패 시 빈 배열로 초기화
                        camelCaseTrade.put("petNames", List.of());
                        e.printStackTrace(); // 디버깅용
                    }
                }
                return camelCaseTrade;
            })
            .collect(Collectors.toList());
    }
    
    @Override
    public List<Pet> getPetsByTradeId(Long tradeId) {
    	return tradeDao.getPetsByTradeId(tradeId);
    }
    
    @Override
    public List<Trade> getTradesByCandId(String candId) {
    	return tradeDao.getTradesByCandId(candId);
    }
    

	/**
	 * snake_case 키를 camelCase로 변환
	 */
	private Map<String, Object> convertKeysToCamelCase(Map<String, Object> snakeCaseMap) {
	    return snakeCaseMap.entrySet().stream()
	        .collect(Collectors.toMap(
	            entry -> toCamelCase(entry.getKey()), // 키 변환
	            Map.Entry::getValue // 값 그대로
	        ));
	}
	
	/**
	 * snake_case를 camelCase로 변환하는 메서드
	 */
	private String toCamelCase(String snakeCase) {
	    StringBuilder result = new StringBuilder();
	    boolean nextUpperCase = false;
	    for (char c : snakeCase.toCharArray()) {
	        if (c == '_') {
	            nextUpperCase = true;
	        } else if (nextUpperCase) {
	            result.append(Character.toUpperCase(c));
	            nextUpperCase = false;
	        } else {
	            result.append(c);
	        }
	    }
	    return result.toString();
	}
}
