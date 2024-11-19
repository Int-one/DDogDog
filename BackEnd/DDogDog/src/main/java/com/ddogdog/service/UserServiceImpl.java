package com.ddogdog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ddogdog.model.dao.UserDao;
import com.ddogdog.model.dto.User;

@Service
public class UserServiceImpl implements UserService {
	
	private UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean create(User user) {
		int result = userDao.insertUser(user);
		return result != 0;
	}

	@Override
	public List<User> getAllUser() {
		return userDao.getAllUser();
	}

	@Override
	public User getUserById(String userId) {
		return userDao.selectOne(userId);
	}

	@Override
	public boolean removeUser(String userId) {
		int result = userDao.deleteUser(userId);
		return result != 0;
	}

	@Override
	public boolean modifyUser(User user) {
		int result = userDao.updateUser(user);
		return result != 0;
	}

	@Override
	public boolean modifyDogWalker(User user) {
		int result = userDao.updateDogwalker(user);
		return result != 0;
	}

}
