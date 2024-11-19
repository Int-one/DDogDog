package com.ddogdog.model.dao;

import com.ddogdog.model.dto.Cand;
import java.util.List;

public interface CandDao {
    int insertCand(Cand cand);

    List<Cand> getAllCands();

    List<Cand> getCandsByTradeId(Long tradeId);

    List<Cand> getCandsByUserId(String userId);

    int deleteCand(Long tradeId, String userId);
}
