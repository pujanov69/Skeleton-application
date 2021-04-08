/**
 * 
 */
package com.pujanov.resources;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pujanov.domain.User;
import com.pujanov.exception.domain.EntityNotFoundException;
import com.pujanov.service.UserService;

/**
 * @author Pujan K.C. <pujanov69@gmail.com>
 *
 * Created on Aug 27, 2019
 */
@RestController
@RequestMapping("/user")
public class UserController {

	UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public List<User> getAllUsers() throws EntityNotFoundException {
		return userService.getAllUsers();
	}
	
	@GetMapping("/{userId}")
	public User getUser(@PathVariable("userId") int userId) throws EntityNotFoundException {
		return userService.getUserByUserId(userId);
	}
	
	@PostMapping
	public User addUser(@RequestBody User user) {
		userService.addUser(user);
		return user;
	}
	
	@PutMapping("/{userId}")
	public void updateUser(@RequestBody User user, @PathVariable("userId") int userId) {
		userService.updateUser(user, userId);
	}
	
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable("userId") int userId) {
		userService.deleteUser(userId);
	}
}
