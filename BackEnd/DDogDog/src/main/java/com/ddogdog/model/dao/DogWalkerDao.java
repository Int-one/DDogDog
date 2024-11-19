package com.ddogdog.model.dao;

import com.ddogdog.model.dto.DogWalker;
import java.util.List;

public interface DogWalkerDao {
    int insertDogWalker(DogWalker dogWalker);

    DogWalker getDogWalkerByUserId(String userId);

    List<DogWalker> getAllDogWalkers();

    int updateDogWalker(DogWalker dogWalker);

    int deleteDogWalker(String userId);
}
