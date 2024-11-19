package com.ddogdog.controller;

import com.ddogdog.model.dto.Result;
import com.ddogdog.service.ResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("api/result")
public class ResultController {

    private final ResultService resultService;

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> createResult(@RequestBody Result result) {
        boolean isCreated = resultService.createResult(result);
        Map<String, String> response = new HashMap<>();
        if (isCreated) {
            response.put("message", "Result created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        response.put("error", "Failed to create Result");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{logId}")
    public ResponseEntity<?> getResultByLogId(@PathVariable Long logId) {
        Result result = resultService.getResultByLogId(logId);
        if (result == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Result not found");
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<List<Result>> getAllResults() {
        List<Result> results = resultService.getAllResults();
        return ResponseEntity.ok(results);
    }

    @PutMapping("/{logId}")
    public ResponseEntity<Map<String, String>> updateResult(@PathVariable Long logId, @RequestBody Result result) {
        result.setLogId(logId);
        boolean isUpdated = resultService.updateResult(result);
        Map<String, String> response = new HashMap<>();
        if (isUpdated) {
            response.put("message", "Result updated successfully");
            return ResponseEntity.ok(response);
        }
        response.put("error", "Failed to update Result");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<Map<String, String>> deleteResult(@PathVariable Long logId) {
        boolean isDeleted = resultService.deleteResult(logId);
        Map<String, String> response = new HashMap<>();
        if (isDeleted) {
            response.put("message", "Result deleted successfully");
            return ResponseEntity.ok(response);
        }
        response.put("error", "Failed to delete Result");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
