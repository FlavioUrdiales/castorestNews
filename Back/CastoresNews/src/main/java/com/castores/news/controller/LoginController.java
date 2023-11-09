package com.castores.news.controller;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castores.news.model.RequestUser;
import com.castores.news.model.User;
import com.castores.news.service.LoginInterface;

@RestController
@RequestMapping("/Login")
public class LoginController {

	@Autowired
	LoginInterface loginService;
	
	@PostMapping
	public User login(@RequestBody RequestUser user) {
		return loginService.login(user);
	}
}
