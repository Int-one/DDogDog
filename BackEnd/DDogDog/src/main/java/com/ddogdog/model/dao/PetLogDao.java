package com.ddogdog.model.dao;

import com.ddogdog.model.dto.PetLog;
import java.util.List;

public interface PetLogDao {
    int insertPetLog(PetLog petLog);

    List<PetLog> getAllPetLogs();

    PetLog getPetLogById(Long plogId);

    List<PetLog> getPetLogsByLogId(Long logId);

    List<PetLog> getPetLogsByPetId(Integer petId);

    int updatePetLog(PetLog petLog);

    int deletePetLog(Long plogId);
}
