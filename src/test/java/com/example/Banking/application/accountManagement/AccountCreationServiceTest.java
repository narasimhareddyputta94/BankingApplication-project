package com.example.Banking.application.accountManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.Banking.application.accountManagement.AccountCreation.AccountType;
import com.example.Banking.application.authentication.User;
import com.example.Banking.application.authentication.UserRepo;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.test.context.support.WithMockUser;



@SpringBootTest
@AutoConfigureMockMvc
public class AccountCreationServiceTest {

	@Autowired
	private AccountCreationRepo repo;
	@Autowired
	private MockMvc mvc;
	@Autowired 
	private UserRepo userRepo;
	
	private static String url = "/api/accounts";
	
	@BeforeEach
	void setup() {
	    //userRepo.deleteAll(); 

	    User user = new User();
	    user.setUsername("root");
	    user.setEmail("root2@example.com");
	    user.setPassword("Nfl123");
	    user.setAccountType(AccountType.CHECKINGS);
	    user.setBalance(5000);
	    
	    userRepo.save(user);
	    
	}
	
	@AfterEach
	void set() {
		repo.deleteAll();
		userRepo.deleteAll();
	}




	@Test
	@WithMockUser(username = "root", password = "Nfl123")
	public void getAll() throws Exception {
		// when - action
		ResultActions response = mvc.perform(MockMvcRequestBuilders.get(url));


		var recordCount = (int) repo.count();

		// then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(recordCount)));

	}

//	@Test
//	public void testNoAuthority() throws Exception {
//
//		ResultActions response = mvc.perform(MockMvcRequestBuilders.get(url));
//
//
//		var recordCount = (int) repo.count();
//
//		// then - verify the output
//		response.andExpect(MockMvcResultMatchers.status().isUnauthorized());
//		//response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(0)));
//	}

	@Test
	@WithMockUser(username = "root", password = "Nfl123")
	public void testPost() throws Exception {
		User user = userRepo.findByemail("root2@example.com");
		
		
		 String json = "{"+ "\"user\":{"
			        + "\"id\": \"" + user.getId() + "\","
			        + "\"username\": \"username\","
			        + "\"email\": \"root2@example.com\","
			        + "\"password\": \"123456\","
			        + "\"accountType\": \"CHECKINGS\","
			        + "\"balance\": \"100\"  },"
			            + "\"accountType\": \"CHECKINGS\","
			            + "\"balance\": \"1000\","
			            + "\"createOn\": \"2024-11-18\","
			            + "\"accountNumber\": \"12345678\"" +"}";


		ResultActions response = mvc.perform(MockMvcRequestBuilders.post(url)
				.with(csrf())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.content(json));


		response.andExpect(MockMvcResultMatchers.status().isCreated());
		//response.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("New Account")));
	}
	
	@Test
	@WithMockUser(username = "root", password = "Nfl123")
	public void testDeleteAccount() throws Exception {
	    // Arrange - Create and save an AccountPreference
	    User user = userRepo.findByemail("root2@example.com");

	    AccountCreation account = AccountCreation.builder()
	            .user(user)
	            .accountType(AccountCreation.AccountType.CHECKINGS)
	            .balance(1000l)
	            .createOn(LocalDate.now())
	            .accountNumber("12345678")
	            .build();

	    account = repo.save(account);
	    long accountId = account.getAccountId();

	  
	    Assertions.assertTrue(repo.existsById(accountId));

	    
	    ResultActions response = mvc.perform(MockMvcRequestBuilders.delete(url + "/" + accountId)
	            .with(csrf())
	            .contentType(org.springframework.http.MediaType.APPLICATION_JSON));

	    // Assert - Validate the response
	    response.andExpect(MockMvcResultMatchers.status().isNoContent());

	    // Ensure the preference no longer exists
	    Assertions.assertFalse(repo.existsById(accountId));
	}





}