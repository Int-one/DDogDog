package com.ddogdog.controller;

import com.ddogdog.model.dto.Photo;
import com.ddogdog.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("api/photo")
@CrossOrigin("*")
public class PhotoController {

    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping("")
    public ResponseEntity<Map<String, String>> createPhoto(@RequestBody Photo photo) {
        boolean result = photoService.createPhoto(photo);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Photo created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        response.put("error", "Failed to create photo");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @GetMapping("")
    public ResponseEntity<List<Photo>> getAllPhotos() {
        List<Photo> photos = photoService.getAllPhotos();
        return ResponseEntity.ok(photos);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Photo>> getPhotosByUserId(@PathVariable String userId) {
        List<Photo> photos = photoService.getPhotosByUserId(userId);
        return ResponseEntity.ok(photos);
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<?> getPhotoById(@PathVariable Long photoId) {
        Photo photo = photoService.getPhotoById(photoId);
        if (photo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Photo not found");
        }
        return ResponseEntity.ok(photo);
    }

    @PutMapping("/{photoId}")
    public ResponseEntity<Map<String, String>> updatePhoto(@PathVariable Long photoId, @RequestBody Photo photo) {
        photo.setPhotoId(photoId);
        boolean result = photoService.updatePhoto(photo);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Photo updated successfully");
            return ResponseEntity.ok(response);
        }
        response.put("error", "Failed to update photo");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<Map<String, String>> deletePhoto(@PathVariable Long photoId) {
        boolean result = photoService.deletePhoto(photoId);
        Map<String, String> response = new HashMap<>();
        if (result) {
            response.put("message", "Photo deleted successfully");
            return ResponseEntity.ok(response);
        }
        response.put("error", "Failed to delete photo");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
