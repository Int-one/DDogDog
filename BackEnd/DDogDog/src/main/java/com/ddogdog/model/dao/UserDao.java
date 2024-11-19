package com.ddogdog.model.dao;

import java.util.List;

import com.ddogdog.model.dto.User;

public interface UserDao {
	public int insertUser(User user);
	
	public List<User> getAllUser();
	
	public User selectOne(String userId);
	
	public int deleteUser(String userId);
	
	public int updateUser(User user);
}
