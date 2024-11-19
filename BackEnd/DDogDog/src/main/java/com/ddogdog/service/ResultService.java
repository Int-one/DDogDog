package com.ddogdog.service;

import com.ddogdog.model.dto.Result;
import java.util.List;

public interface ResultService {
    boolean createResult(Result result);

    Result getResultByLogId(Long logId);

    List<Result> getAllResults();

    boolean updateResult(Result result);

    boolean deleteResult(Long logId);
}
