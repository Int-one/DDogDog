package com.ddogdog.service;

import com.ddogdog.model.dao.TradeDao;
import com.ddogdog.model.dto.Trade;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeDao tradeDao;

    public TradeServiceImpl(TradeDao tradeDao) {
        this.tradeDao = tradeDao;
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
}
