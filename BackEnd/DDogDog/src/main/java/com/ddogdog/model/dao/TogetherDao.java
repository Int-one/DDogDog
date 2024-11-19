package com.ddogdog.model.dao;

import com.ddogdog.model.dto.Together;
import java.util.List;

public interface TogetherDao {
    int insertTogether(Together together);

    List<Together> getAllTogether();

    List<Together> getTogetherByTradeId(Long tradeId);

    List<Together> getTogetherByPetId(Integer petId);

    int deleteTogether(Long togetherId);
}
