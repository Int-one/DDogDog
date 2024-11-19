package com.ddogdog.service;

import com.ddogdog.model.dao.TogetherDao;
import com.ddogdog.model.dto.Together;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TogetherServiceImpl implements TogetherService {

    private final TogetherDao togetherDao;

    public TogetherServiceImpl(TogetherDao togetherDao) {
        this.togetherDao = togetherDao;
    }

    @Override
    public boolean createTogether(Together together) {
        int result = togetherDao.insertTogether(together);
        return result > 0;
    }

    @Override
    public List<Together> getAllTogether() {
        return togetherDao.getAllTogether();
    }

    @Override
    public List<Together> getTogetherByTradeId(Long tradeId) {
        return togetherDao.getTogetherByTradeId(tradeId);
    }

    @Override
    public List<Together> getTogetherByPetId(Integer petId) {
        return togetherDao.getTogetherByPetId(petId);
    }

    @Override
    public boolean deleteTogether(Long togetherId) {
        int result = togetherDao.deleteTogether(togetherId);
        return result > 0;
    }
}
