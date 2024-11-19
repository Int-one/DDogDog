package com.ddogdog.service;

import com.ddogdog.model.dao.ResultDao;
import com.ddogdog.model.dto.Result;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResultServiceImpl implements ResultService {

    private final ResultDao resultDao;

    public ResultServiceImpl(ResultDao resultDao) {
        this.resultDao = resultDao;
    }

    @Override
    public boolean createResult(Result result) {
        int resultCount = resultDao.insertResult(result);
        return resultCount > 0;
    }

    @Override
    public Result getResultByLogId(Long logId) {
        return resultDao.getResultByLogId(logId);
    }

    @Override
    public List<Result> getAllResults() {
        return resultDao.getAllResults();
    }

    @Override
    public boolean updateResult(Result result) {
        int resultCount = resultDao.updateResult(result);
        return resultCount > 0;
    }

    @Override
    public boolean deleteResult(Long logId) {
        int resultCount = resultDao.deleteResult(logId);
        return resultCount > 0;
    }
}
