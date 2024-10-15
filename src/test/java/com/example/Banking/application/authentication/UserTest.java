package com.example.Banking.application.authentication;

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
		
		String username = "Shashank";
		String password = "DePaul";
		String email = "depaul@test.com";
		
		User user = userService.registerUser(username, password, email);
		assertNotNull(user);
		assertEquals(username, user.getUsername());
		assertEquals(password, user.getPassword());
		assertEquals(email, user.getEmail());
	}
}
