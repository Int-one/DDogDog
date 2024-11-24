package com.ddogdog.service;

import com.ddogdog.model.dto.Pet;
import com.ddogdog.model.dto.Trade;
import java.util.List;
import java.util.Map;

public interface TradeService {
    boolean createTrade(Trade trade);

    List<Trade> getAllTrades();

    List<Trade> getAllWillTrades();
    
    List<Pet> getPetsByTradeId(Long tradeId);
    
    List<Trade> getTradesByCandId(String candId);

    Trade getTradeById(Long tradeId);
    
    Trade getLatestTradeById(Trade trade);

    boolean updateTrade(Trade trade);

    boolean deleteTrade(Long tradeId);
    
    public List<Map<String, Object>> getAllPleaseTrades();
}
