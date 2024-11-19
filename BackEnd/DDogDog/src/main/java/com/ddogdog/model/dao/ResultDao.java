package com.ddogdog.model.dao;

import com.ddogdog.model.dto.Result;
import java.util.List;

public interface ResultDao {
    int insertResult(Result result);

    Result getResultByLogId(Long logId);

    List<Result> getAllResults();

    int updateResult(Result result);

    int deleteResult(Long logId);
}
