package com.ddogdog.controller;

import com.ddogdog.model.dto.PetLog;
import com.ddogdog.service.PetLogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("api/petlog")
public class PetLogController {

    private final PetLogService petLogService;

    public PetLogController(PetLogService petLogService) {
        this.petLogService = petLogService;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> createPetLog(@RequestBody PetLog petLog) {
        boolean result = petLogService.createPetLog(petLog);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "PetLog created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        response.put("error", "Failed to create PetLog");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<PetLog>> getAllPetLogs() {
        List<PetLog> petLogs = petLogService.getAllPetLogs();
        return ResponseEntity.ok(petLogs);
    }

    @GetMapping("/{plogId}")
    public ResponseEntity<?> getPetLogById(@PathVariable Long plogId) {
        PetLog petLog = petLogService.getPetLogById(plogId);
        if (petLog == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("PetLog not found");
        }
        return ResponseEntity.ok(petLog);
    }

    @PutMapping("/{plogId}")
    public ResponseEntity<Map<String, String>> updatePetLog(@PathVariable Long plogId, @RequestBody PetLog petLog) {
        petLog.setPlogId(plogId);
        boolean result = petLogService.updatePetLog(petLog);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "PetLog updated successfully");
            return ResponseEntity.ok(response);
        }
        response.put("error", "Failed to update PetLog");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{plogId}")
    public ResponseEntity<Map<String, String>> deletePetLog(@PathVariable Long plogId) {
        boolean result = petLogService.deletePetLog(plogId);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "PetLog deleted successfully");
            return ResponseEntity.ok(response);
        }
        response.put("error", "Failed to delete PetLog");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
