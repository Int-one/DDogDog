package com.ddogdog.controller;

import com.ddogdog.model.dto.DogWalker;
import com.ddogdog.service.DogWalkerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("api/dogwalker")
public class DogWalkerController {

    private final DogWalkerService dogWalkerService;

    public DogWalkerController(DogWalkerService dogWalkerService) {
        this.dogWalkerService = dogWalkerService;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> createDogWalker(@RequestBody DogWalker dogWalker) {
        boolean result = dogWalkerService.createDogWalker(dogWalker);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "DogWalker created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        response.put("error", "Failed to create DogWalker");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getDogWalkerByUserId(@PathVariable String userId) {
        DogWalker dogWalker = dogWalkerService.getDogWalkerByUserId(userId);
        if (dogWalker == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("DogWalker not found");
        }
        return ResponseEntity.ok(dogWalker);
    }

    @GetMapping("")
    public ResponseEntity<List<DogWalker>> getAllDogWalkers() {
        List<DogWalker> dogWalkers = dogWalkerService.getAllDogWalkers();
        return ResponseEntity.ok(dogWalkers);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Map<String, String>> updateDogWalker(@PathVariable String userId, @RequestBody DogWalker dogWalker) {
        dogWalker.setUserId(userId);
        boolean result = dogWalkerService.updateDogWalker(dogWalker);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "DogWalker updated successfully");
            return ResponseEntity.ok(response);
        }
        response.put("error", "Failed to update DogWalker");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, String>> deleteDogWalker(@PathVariable String userId) {
        boolean result = dogWalkerService.deleteDogWalker(userId);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "DogWalker deleted successfully");
            return ResponseEntity.ok(response);
        }
        response.put("error", "Failed to delete DogWalker");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
