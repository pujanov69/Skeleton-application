package com.pujanov.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pujanov.service.UserService;
/**
 * 
 * @author Pujan KC
 *
 */
@RestController
@RequestMapping("/")
public class HomeController {
	UserService userService;
	
	public HomeController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public String sayHello() {
		return "Hello, world!";
	}
	
	@GetMapping("/admin")
	public String sayHelloAdmin() {
		return "Hello, admin!";
	}
}
