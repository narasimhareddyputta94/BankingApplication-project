package com.example.Banking.application.authentication;

import com.example.Banking.application.authentication.User;
import com.example.Banking.application.authentication.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class UserTest {
	
	@Autowired
	public UserService userService;
	
	@Test
	public void UserTest() {
		
		String Username = "Shashank";
		String Password = "DePaul";
		String email = "depaul@test.com";
		
		User user = userService.registerUser(Username, Password, email);
		assertNotNull(user);
		assertEquals(Username, user.getUsername());
		assertEquals(Password , user.getPassword());
		assertEquals(email , user.getEmail());
	}
	
}
