package com.ddogdog.service;

import com.ddogdog.model.dto.DogWalker;
import java.util.List;

public interface DogWalkerService {
    boolean createDogWalker(DogWalker dogWalker);

    DogWalker getDogWalkerByUserId(String userId);

    List<DogWalker> getAllDogWalkers();

    boolean updateDogWalker(DogWalker dogWalker);

    boolean deleteDogWalker(String userId);
}
