package com.ddogdog.model.dao;

import com.ddogdog.model.dto.Trade;
import java.util.List;

public interface TradeDao {
    int insertTrade(Trade trade);

    List<Trade> getAllTrades();

    Trade selectOne(Long tradeId);

    int updateTrade(Trade trade);

    int deleteTrade(Long tradeId);
}
