package com.ddogdog.service;

import com.ddogdog.model.dto.WalkLog;
import java.util.List;

public interface WalkLogService {
    boolean createWalkLog(WalkLog walkLog);

    List<WalkLog> getAllWalkLogs();

    WalkLog getWalkLogById(Long logId);

    List<WalkLog> getWalkLogsByUserId(String userId);

    boolean updateWalkLog(WalkLog walkLog);

    boolean deleteWalkLog(Long logId);
}
