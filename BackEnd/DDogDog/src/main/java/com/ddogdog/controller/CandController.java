package com.ddogdog.controller;

import com.ddogdog.model.dto.Cand;
import com.ddogdog.service.CandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("api/cand")
@CrossOrigin("*")
public class CandController {

    private final CandService candService;

    public CandController(CandService candService) {
        this.candService = candService;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> createCand(@RequestBody Cand cand) {
        boolean result = candService.createCand(cand);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Cand created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        response.put("error", "Failed to create Cand");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<Cand>> getAllCands() {
        List<Cand> cands = candService.getAllCands();
        return ResponseEntity.ok(cands);
    }

    @GetMapping("/trade/{tradeId}")
    public ResponseEntity<List<Cand>> getCandsByTradeId(@PathVariable Long tradeId) {
        List<Cand> cands = candService.getCandsByTradeId(tradeId);
        return ResponseEntity.ok(cands);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Cand>> getCandsByUserId(@PathVariable String userId) {
        List<Cand> cands = candService.getCandsByUserId(userId);
        return ResponseEntity.ok(cands);
    }

    @DeleteMapping("")
    public ResponseEntity<Map<String, String>> deleteCand(@RequestParam Long tradeId, @RequestParam String userId) {
        boolean result = candService.deleteCand(tradeId, userId);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Cand deleted successfully");
            return ResponseEntity.ok(response);
        }
        response.put("error", "Failed to delete Cand");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
