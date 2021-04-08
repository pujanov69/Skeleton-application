package com.pujanov.service;

import java.util.List;

import com.pujanov.domain.User;
/**
 * 
 * @author Pujan KC
 *
 */
public interface UserService {
	User getUserByUsername(String username);

	void addUser(User user);

	void updateUser(User user, int userId);

	void deleteUser(int userId);

	User getUserByUserId(int userId);

	/**
	 * @return list of Users
	 */
	List<User> getAllUsers();
}
