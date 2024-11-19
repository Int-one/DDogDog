package com.ddogdog.controller;

import com.ddogdog.model.dto.WalkLog;
import com.ddogdog.service.WalkLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("api/walklog")
public class WalkLogController {

    private final WalkLogService walkLogService;

    public WalkLogController(WalkLogService walkLogService) {
        this.walkLogService = walkLogService;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> createWalkLog(@RequestBody WalkLog walkLog) {
        boolean result = walkLogService.createWalkLog(walkLog);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "WalkLog created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        response.put("error", "Failed to create WalkLog");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<WalkLog>> getAllWalkLogs() {
        List<WalkLog> walkLogs = walkLogService.getAllWalkLogs();
        System.out.println(walkLogs.get(0).getWalkPath());
        return ResponseEntity.ok(walkLogs);
    }

    @GetMapping("/{logId}")
    public ResponseEntity<?> getWalkLogById(@PathVariable Long logId) {
        WalkLog walkLog = walkLogService.getWalkLogById(logId);
        if (walkLog == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("WalkLog not found");
        }
        return ResponseEntity.ok(walkLog);
    }
    
    @GetMapping("/latest/{userId}")
    public ResponseEntity<?> getLatestWalkLogById(@PathVariable String userId) {
    	WalkLog walkLog = walkLogService.getLatestWalkLogById(userId);
    	if (walkLog == null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("WalkLog not found");
    	}
    	return ResponseEntity.ok(walkLog);
    }

    @PutMapping("/{logId}")
    public ResponseEntity<Map<String, String>> updateWalkLog(@PathVariable Long logId, @RequestBody WalkLog walkLog) {
        walkLog.setLogId(logId);
        boolean result = walkLogService.updateWalkLog(walkLog);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "WalkLog updated successfully");
            return ResponseEntity.ok(response);
        }
        response.put("error", "Failed to update WalkLog");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{logId}")
    public ResponseEntity<Map<String, String>> deleteWalkLog(@PathVariable Long logId) {
        boolean result = walkLogService.deleteWalkLog(logId);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "WalkLog deleted successfully");
            return ResponseEntity.ok(response);
        }
        response.put("error", "Failed to delete WalkLog");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
