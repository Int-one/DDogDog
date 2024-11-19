package com.ddogdog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ddogdog.model.dto.Pet;
import com.ddogdog.model.dto.User;
import com.ddogdog.service.PetService;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/pet")
public class PetController {

    @Autowired
    private PetService petService;

    // 사용자 ID로 펫 조회
    @GetMapping("/{userId}")
    public ResponseEntity<List<Pet>> getPetByUserId(@PathVariable("userId") String userId) {
        try {
            List<Pet> pets = petService.getPetByUser(userId);
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 펫 ID로 펫 조회
    @GetMapping("/pet/{petId}")
    public ResponseEntity<Pet> getPetById(@PathVariable Integer petId) {
        try {
            Pet pet = petService.getPetById(petId);
            if (pet != null) {
                return ResponseEntity.ok(pet);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 펫 생성
    @PostMapping
    public ResponseEntity<String> createPet(@RequestBody Pet pet) {
        try {
            petService.createPet(pet);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pet created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create pet");
        }
    }

    // 펫 정보 업데이트
    @PutMapping
    public ResponseEntity<String> updatePet(@RequestBody Pet pet) {
        try {
            petService.updatePet(pet);
            return ResponseEntity.ok("Pet updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update pet");
        }
    }

    // 펫 삭제
    @DeleteMapping("/{petId}")
    public ResponseEntity<String> deletePet(@PathVariable Integer petId) {
        try {
            petService.deletePet(petId);
            return ResponseEntity.ok("Pet deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete pet");
        }
    }
}
