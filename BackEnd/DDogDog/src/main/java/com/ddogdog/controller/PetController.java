package com.ddogdog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ddogdog.model.dto.Pet;
import com.ddogdog.model.dto.User;
import com.ddogdog.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
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
    
    @GetMapping("/pets/{logId}")
    public ResponseEntity<List<Pet>> findPetByLogId(@PathVariable("logId") Long logId) {
    	try {
            List<Pet> pets = petService.findPetByLogId(logId);
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 펫 생성
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<String> createPet(
            @RequestParam("pet") String petJson,
            @RequestParam("file") MultipartFile file) {
        try {
            // JSON 데이터를 Pet 객체로 변환
            ObjectMapper objectMapper = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // ISO 8601 형식 활성화
                    .build();
            Pet pet = objectMapper.readValue(petJson, Pet.class);

            // ResourceLoader로 클래스패스 내의 특정 디렉토리 가져오기
//            Resource resource = resourceLoader.getResource("classpath:/static/petPhoto");
//            File uploadFolder = resource.getFile(); // 디렉토리 파일 객체
            String baseDir = System.getProperty("user.dir");
            String uploadDir = baseDir + "/src/main/resources/static/petPhoto/";
            File uploadFolder = new File(uploadDir);
            System.out.println(uploadFolder);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs(); // 디렉토리 생성
            }

            if (file != null && file.getSize() > 0) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                File destinationFile = new File(uploadFolder, fileName);
                file.transferTo(destinationFile);

                // 저장된 파일 경로를 Pet 객체에 설정
                pet.setPetPhoto("petPhoto/" + fileName); // 상대 경로로 저장
            }
//            System.out.println("before");
//            // 파일 저장
//            if (!file.isEmpty()) {
//            	System.out.println("fileExists");
//                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//                File destinationFile = new File(uploadFolder, fileName);
//                file.transferTo(destinationFile);
//                System.out.println("fileSaved");
//                // 저장된 파일 경로를 Pet 객체에 설정
//                pet.setPetPhoto("static/petPhoto/" + fileName); // 상대 경로로 저장
//            }

            // 펫 데이터 저장
            petService.createPet(pet);
            return ResponseEntity.status(HttpStatus.CREATED).body("Pet created successfully");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save file");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create pet");
        }
    }
//    @PostMapping
//    public ResponseEntity<String> createPet(@RequestBody Pet pet) {
//        try {
//            petService.createPet(pet);
//            return ResponseEntity.status(HttpStatus.CREATED).body("Pet created successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create pet");
//        }
//    }

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
