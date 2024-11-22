package com.ddogdog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ddogdog.model.dto.DogWalker;
import com.ddogdog.model.dto.User;
import com.ddogdog.service.DogWalkerService;
import com.ddogdog.service.UserService;
import com.ddogdog.utill.JwtUtil;

@CrossOrigin("*")
@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;
    private final DogWalkerService dogWalkerService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil, DogWalkerService dogWalkerService) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
        this.dogWalkerService = dogWalkerService;
    }

    // 사용자 생성
    @PostMapping("")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody User user) {
        boolean result = userService.create(user);
        Map<String, String> response = new HashMap<>();

        if (result) {
            response.put("message", "User created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        response.put("error", "Failed to create user");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        User loginUser = userService.getUserById(user.getUserId());

        if (loginUser == null) {
            response.put("error", "User not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        if (loginUser.getPassword().equals(user.getPassword())) {
            String token = jwtUtil.createToken(loginUser.getUserId());
            response.put("access-token", token);
            response.put("message", "Login successful");
            response.put("region", loginUser.getRegion());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        response.put("error", "Invalid password");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // 모든 사용자 조회
    @GetMapping("")
    public ResponseEntity<?> getAllUser() {
        List<User> users = userService.getAllUser();

        if (users == null || users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No users found");
        }

        return ResponseEntity.ok(users);
    }

    // 사용자 ID로 조회
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable("userId") String userId) {
        User user = userService.getUserById(userId);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.ok(user);
    }

    // 사용자 정보 수정
    @PutMapping("")
    public ResponseEntity<Map<String, String>> updateUser(@ModelAttribute User user) {
        boolean result = userService.modifyUser(user);
        Map<String, String> response = new HashMap<>();

        if (result) {
            response.put("message", "User updated successfully");
            return ResponseEntity.ok(response);
        }

        response.put("error", "Failed to update user");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 도그워커 가입
    @PutMapping("/dogwalker")
    public ResponseEntity<Map<String, String>> updateDogWalker(@RequestBody User user) {
        boolean result = userService.modifyDogWalker(user);
        Map<String, String> response = new HashMap<>();

        if (result) {
        	DogWalker d = new DogWalker();
        	d.setUserId(user.getUserId());
        	dogWalkerService.createDogWalker(d);
            response.put("message", "Dog walker information updated successfully");
            return ResponseEntity.ok(response);
        }

        response.put("error", "Failed to update dog walker information");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 사용자 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Map<String, String>> deleteUser(@PathVariable("userId") String userId) {
        boolean result = userService.removeUser(userId);
        Map<String, String> response = new HashMap<>();

        if (result) {
            response.put("message", "User deleted successfully");
            return ResponseEntity.ok(response);
        }

        response.put("error", "Failed to delete user");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
