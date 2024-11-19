package com.ddogdog.service;

import com.ddogdog.model.dto.Trade;
import java.util.List;

public interface TradeService {
    boolean createTrade(Trade trade);

    List<Trade> getAllTrades();

    Trade getTradeById(Long tradeId);

    boolean updateTrade(Trade trade);

    boolean deleteTrade(Long tradeId);
}
