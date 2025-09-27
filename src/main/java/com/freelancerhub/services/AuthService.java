package com.freelancerhub.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.freelancerhub.dto.AuthResponse;
import com.freelancerhub.entities.User;
import com.freelancerhub.repositories.UserRepository;
import com.freelancerhub.security.JwtUtil;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtUtil jwtUtil;
	
	
	public String register(User user) {
		
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return jwtUtil.generateToken(user.getEmail());
	}
	
	public AuthResponse login(String email, String password) {
		
		User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));
		
		if(!passwordEncoder.matches(password, user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credientials");
		}
		
		
		String token = jwtUtil.generateToken(user.getEmail());
		
		return new AuthResponse(token, user);
		
	}
	
}
