package com.ddogdog.service;

import com.ddogdog.model.dto.Together;
import java.util.List;

public interface TogetherService {
    boolean createTogether(Together together);

    List<Together> getAllTogether();

    List<Together> getTogetherByTradeId(Long tradeId);

    List<Together> getTogetherByPetId(Integer petId);

    boolean deleteTogether(Long togetherId);
}
