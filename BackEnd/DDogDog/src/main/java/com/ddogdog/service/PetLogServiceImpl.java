package com.ddogdog.service;

import com.ddogdog.model.dao.PetLogDao;
import com.ddogdog.model.dto.PetLog;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PetLogServiceImpl implements PetLogService {

    private final PetLogDao petLogDao;

    public PetLogServiceImpl(PetLogDao petLogDao) {
        this.petLogDao = petLogDao;
    }

    @Override
    public boolean createPetLog(PetLog petLog) {
        int result = petLogDao.insertPetLog(petLog);
        return result > 0;
    }

    @Override
    public List<PetLog> getAllPetLogs() {
        return petLogDao.getAllPetLogs();
    }

    @Override
    public PetLog getPetLogById(Long plogId) {
        return petLogDao.getPetLogById(plogId);
    }

    @Override
    public List<PetLog> getPetLogsByLogId(Long logId) {
        return petLogDao.getPetLogsByLogId(logId);
    }

    @Override
    public List<PetLog> getPetLogsByPetId(Integer petId) {
        return petLogDao.getPetLogsByPetId(petId);
    }

    @Override
    public boolean updatePetLog(PetLog petLog) {
        int result = petLogDao.updatePetLog(petLog);
        return result > 0;
    }

    @Override
    public boolean deletePetLog(Long plogId) {
        int result = petLogDao.deletePetLog(plogId);
        return result > 0;
    }
}
