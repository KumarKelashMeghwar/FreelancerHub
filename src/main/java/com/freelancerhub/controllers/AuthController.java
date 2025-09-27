package com.freelancerhub.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freelancerhub.dto.AuthResponse;
import com.freelancerhub.entities.User;
import com.freelancerhub.services.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<Map<String, String>> register(@RequestBody User user) {

		String token = authService.register(user);
		return ResponseEntity.ok(Map.of("token", token));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody Map<String, String> request) {
		AuthResponse authResponse = authService.login(request.get("email"), request.get("password"));
		
		return ResponseEntity.ok(authResponse);
	}

}
