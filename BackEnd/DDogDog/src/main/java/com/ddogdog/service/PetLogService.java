package com.ddogdog.service;

import com.ddogdog.model.dto.PetLog;
import java.util.List;

public interface PetLogService {
    boolean createPetLogs(List<PetLog> petLogs);

    List<PetLog> getAllPetLogs();

    PetLog getPetLogById(Long plogId);

    List<PetLog> getPetLogsByLogId(Long logId);

    List<PetLog> getPetLogsByPetId(Integer petId);

    boolean updatePetLog(PetLog petLog);

    boolean deletePetLog(Long plogId);
}
