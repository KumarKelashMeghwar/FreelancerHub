package com.freelancerhub.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelancerhub.entities.User;

@RestController
public class HomeController {
	
	@GetMapping("/me")
	public ResponseEntity<?> getProfile(Authentication authentication){
		User user = (User) authentication.getPrincipal();
		return ResponseEntity.ok(user);
	}
	

}
