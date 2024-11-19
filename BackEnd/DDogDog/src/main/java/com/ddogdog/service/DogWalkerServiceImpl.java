package com.ddogdog.service;

import com.ddogdog.model.dao.DogWalkerDao;
import com.ddogdog.model.dto.DogWalker;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DogWalkerServiceImpl implements DogWalkerService {

    private final DogWalkerDao dogWalkerDao;

    public DogWalkerServiceImpl(DogWalkerDao dogWalkerDao) {
        this.dogWalkerDao = dogWalkerDao;
    }

    @Override
    public boolean createDogWalker(DogWalker dogWalker) {
        int result = dogWalkerDao.insertDogWalker(dogWalker);
        return result > 0;
    }

    @Override
    public DogWalker getDogWalkerByUserId(String userId) {
        return dogWalkerDao.getDogWalkerByUserId(userId);
    }

    @Override
    public List<DogWalker> getAllDogWalkers() {
        return dogWalkerDao.getAllDogWalkers();
    }

    @Override
    public boolean updateDogWalker(DogWalker dogWalker) {
        int result = dogWalkerDao.updateDogWalker(dogWalker);
        return result > 0;
    }

    @Override
    public boolean deleteDogWalker(String userId) {
        int result = dogWalkerDao.deleteDogWalker(userId);
        return result > 0;
    }
}
