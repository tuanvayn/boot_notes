package com.vmodev.tuanva.notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vmodev.tuanva.notes.service.user.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/signin")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
		return userService.signin(username, password);
	}
}
