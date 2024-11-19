package com.ddogdog.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddogdog.model.dto.User;
import com.ddogdog.service.UserService;

@RestController
@RequestMapping("api/user")
@CrossOrigin
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("")
	public ResponseEntity<?> createUser(@RequestBody User user) {
		System.out.println(user.toString());
		boolean result = userService.create(user);
		if(result)
			return ResponseEntity.ok().build();
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("")
	public ResponseEntity<?> getAllUser() {
		System.out.println("Here");
		List<User> list = userService.getAllUser();
		
		if(list == null || list.size() == 0) {
			return ResponseEntity.internalServerError().build();
		}
		
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> getUserById(@PathVariable("userId") String userId) {
		User user = userService.getUserById(userId);
		if(user == null)
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("")
	public ResponseEntity<?> updateUser(@ModelAttribute User user) {
		boolean result = userService.modifyUser(user);
		
		if(result)
			return ResponseEntity.ok().build();
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") String userId) {
		boolean result = userService.removeUser(userId);
		if(result)
			return ResponseEntity.ok().build();
		return ResponseEntity.badRequest().build();
	}
}
