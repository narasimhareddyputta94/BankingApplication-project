package com.example.Banking.application.accountManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import com.example.Banking.application.authentication.User;
import com.example.Banking.application.authentication.UserRepo;
import com.example.Banking.application.accountManagement.AccountCreation.AccountType;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountCreationServiceTest {

	@Autowired
	private AccountCreationRepo accountRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private MockMvc mvc;

	private static final String url = "/api/accounts";

	@BeforeEach
	public void setup() {
		// Ensure a user exists in the repo before each test
		User user = new User();
		user.setUsername("username123");
		user.setEmail("123@gmail.com");
		user.setPassword("Password");
		user.setAccountType(AccountType.CHECKINGS);
		user.setBalance(1000);
		userRepo.save(user);
	}

	@Test
	@WithMockUser(username = "root", password = "WelcomeToBanWorld!")
	public void getAll() throws Exception {
		// When - action
		ResultActions response = mvc.perform(MockMvcRequestBuilders.get(url));

		// Count records in the repo to assert JSON size
		var recordCount = (int) accountRepo.count();

		// Then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(recordCount)));
	}

	@Test
	public void testNoAuthority() throws Exception {
		ResultActions response = mvc.perform(MockMvcRequestBuilders.get(url));

		// Then - verify unauthorized status
		response.andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "root", password = "WelcomeToBanWorld!")
	public void testAccountCreation() throws Exception {
		// Retrieve the user ID from the repository
		Long userId = userRepo.findAll().get(0).getId();

		String json = """
	    		{
	    	    "user": {"id": %d}, 
	    	    "accountType": "CHECKINGS",
	    	    "balance": 5000,
	    	    "createOn": "2024-10-27",
	    	    "accountNumber": "1234567890"
	    	}
	    	""".formatted(userId);

		ResultActions response = mvc.perform(MockMvcRequestBuilders.post(url)
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(json));

		// Then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isCreated());
		response.andExpect(MockMvcResultMatchers.jsonPath("$.accountType", CoreMatchers.is("CHECKINGS")));
		response.andExpect(MockMvcResultMatchers.jsonPath("$.balance", CoreMatchers.is(5000)));
	}
}
