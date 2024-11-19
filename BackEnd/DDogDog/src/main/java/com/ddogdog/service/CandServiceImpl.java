package com.ddogdog.service;

import com.ddogdog.model.dao.CandDao;
import com.ddogdog.model.dto.Cand;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CandServiceImpl implements CandService {

    private final CandDao candDao;

    public CandServiceImpl(CandDao candDao) {
        this.candDao = candDao;
    }

    @Override
    public boolean createCand(Cand cand) {
        int result = candDao.insertCand(cand);
        return result > 0;
    }

    @Override
    public List<Cand> getAllCands() {
        return candDao.getAllCands();
    }

    @Override
    public List<Cand> getCandsByTradeId(Long tradeId) {
        return candDao.getCandsByTradeId(tradeId);
    }

    @Override
    public List<Cand> getCandsByUserId(String userId) {
        return candDao.getCandsByUserId(userId);
    }

    @Override
    public boolean deleteCand(Long tradeId, String userId) {
        int result = candDao.deleteCand(tradeId, userId);
        return result > 0;
    }
}
