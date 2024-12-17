package com.ddogdog.model.dao;

import com.ddogdog.model.dto.Pet;
import com.ddogdog.model.dto.Trade;
import java.util.List;
import java.util.Map;

public interface TradeDao {
    int insertTrade(Trade trade);

    List<Trade> getAllTrades();
    
    List<Trade> getAllWillTrades(String region);
    
    List<Pet> getPetsByTradeId(Long tradeId);
    
    List<Trade> getTradesByCandId(String candId);

    Trade getTradeById(Long tradeId);
    
    Trade getLatestTradeById(Trade trade);

    int updateTrade(Trade trade);

    int deleteTrade(Long tradeId);
    
    List<Map<String, Object>> getAllPleaseTrades(String region);
}
