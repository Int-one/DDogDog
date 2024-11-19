package com.ddogdog.service;

import java.util.List;

import com.ddogdog.model.dto.User;

public interface UserService {
	public boolean create(User user);
	
	public List<User> getAllUser();
	
	public User getUserById(String userId);
	
	public boolean removeUser(String userId);
	
	public boolean modifyUser(User user);
	
	public boolean modifyDogWalker(User user);
}