package com.example.Banking.application.Autentication;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	
	public User registerUser(String name , String password , String email) {
		User user = new User();
		user.setUsername(name);
		user.setPassword(password);
		user.setEmail(email);
		return user;
		
	}

}
