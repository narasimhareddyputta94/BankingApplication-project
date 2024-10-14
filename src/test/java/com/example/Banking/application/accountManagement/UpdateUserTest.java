package com.example.Banking.application.accountManagement;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.Banking.application.Authentication.User;
import com.example.Banking.application.Authentication.UserRepo;



@DataJpaTest
@ActiveProfiles("test")
public class UpdateUserTest {
	
	@Autowired
	private UpdateUserRepo updateUser;
	@Autowired
	private UserRepo userRepo;
	
	@DisplayName("Test Updating User Info")
	@Test
	public void testUserUpdate() {
		User user = new User();
		user.setUsername("username123");
		user.setEmail("123@gmail.com");
		user.setPassword("Password");
		userRepo.save(user);
		UpdateUser update = new UpdateUser();
		update.setUser(user);
		
		
	}

}
