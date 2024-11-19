package com.ddogdog.service;

import com.ddogdog.model.dao.WalkLogDao;
import com.ddogdog.model.dto.WalkLog;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class WalkLogServiceImpl implements WalkLogService {

    private final WalkLogDao walkLogDao;

    public WalkLogServiceImpl(WalkLogDao walkLogDao) {
        this.walkLogDao = walkLogDao;
    }

    @Override
    public boolean createWalkLog(WalkLog walkLog) {
        int result = walkLogDao.insertWalkLog(walkLog);
        return result > 0;
    }

    @Override
    public List<WalkLog> getAllWalkLogs() {
        return walkLogDao.getAllWalkLogs();
    }

    @Override
    public WalkLog getWalkLogById(Long logId) {
        return walkLogDao.getWalkLogById(logId);
    }

    @Override
    public List<WalkLog> getWalkLogsByUserId(String userId) {
        return walkLogDao.getWalkLogsByUserId(userId);
    }

    @Override
    public boolean updateWalkLog(WalkLog walkLog) {
        int result = walkLogDao.updateWalkLog(walkLog);
        return result > 0;
    }

    @Override
    public boolean deleteWalkLog(Long logId) {
        int result = walkLogDao.deleteWalkLog(logId);
        return result > 0;
    }
}
