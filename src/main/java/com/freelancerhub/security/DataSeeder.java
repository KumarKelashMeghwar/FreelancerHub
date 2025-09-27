package com.freelancerhub.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.freelancerhub.entities.Role;
import com.freelancerhub.entities.User;
import com.freelancerhub.repositories.UserRepository;

@Component
public class DataSeeder implements CommandLineRunner{

	private UserRepository userRepository = null;
	private PasswordEncoder passwordEncoder = null;
	
	public DataSeeder(UserRepository repo, PasswordEncoder pe) {
		this.userRepository = repo;
		this.passwordEncoder = pe;
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		String email = "admin@freelancerhub.com";
		
		if(userRepository.findByEmail(email).isEmpty()) {
			User admin = new User();
			admin.setEmail(email);
			admin.setPassword(passwordEncoder.encode("admin123"));
			admin.setRole(Role.ROLE_ADMIN);
			admin.setFirstName("Super");
			admin.setLastName("Admin");
			
			userRepository.save(admin);
			System.out.println("Admin user created");
		}
		
	}

}
