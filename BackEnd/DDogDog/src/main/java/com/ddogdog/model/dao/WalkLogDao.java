package com.ddogdog.model.dao;

import com.ddogdog.model.dto.WalkLog;
import java.util.List;

public interface WalkLogDao {
    int insertWalkLog(WalkLog walkLog);

    List<WalkLog> getAllWalkLogs();

    WalkLog getWalkLogById(Long logId);

    List<WalkLog> getWalkLogsByUserId(String userId);

    int updateWalkLog(WalkLog walkLog);

    int deleteWalkLog(Long logId);
}
