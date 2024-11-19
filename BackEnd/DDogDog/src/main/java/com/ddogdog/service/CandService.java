package com.ddogdog.service;

import com.ddogdog.model.dto.Cand;
import java.util.List;

public interface CandService {
    boolean createCand(Cand cand);

    List<Cand> getAllCands();

    List<Cand> getCandsByTradeId(Long tradeId);

    List<Cand> getCandsByUserId(String userId);

    boolean deleteCand(Long tradeId, String userId);
}
