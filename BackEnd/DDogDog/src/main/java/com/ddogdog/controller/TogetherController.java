package com.ddogdog.controller;

import com.ddogdog.model.dto.Together;
import com.ddogdog.service.TogetherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("api/together")
public class TogetherController {

    private final TogetherService togetherService;

    public TogetherController(TogetherService togetherService) {
        this.togetherService = togetherService;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> createTogether(@RequestBody Together together) {
        boolean result = togetherService.createTogether(together);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Together created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        response.put("error", "Failed to create Together");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<Together>> getAllTogether() {
        List<Together> togethers = togetherService.getAllTogether();
        return ResponseEntity.ok(togethers);
    }

    @GetMapping("/trade/{tradeId}")
    public ResponseEntity<List<Together>> getTogetherByTradeId(@PathVariable Long tradeId) {
        List<Together> togethers = togetherService.getTogetherByTradeId(tradeId);
        return ResponseEntity.ok(togethers);
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<Together>> getTogetherByPetId(@PathVariable Integer petId) {
        List<Together> togethers = togetherService.getTogetherByPetId(petId);
        return ResponseEntity.ok(togethers);
    }

    @DeleteMapping("/{togetherId}")
    public ResponseEntity<Map<String, String>> deleteTogether(@PathVariable Long togetherId) {
        boolean result = togetherService.deleteTogether(togetherId);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Together deleted successfully");
            return ResponseEntity.ok(response);
        }
        response.put("error", "Failed to delete Together");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
