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
import java.time.format.DateTimeFormatter;
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
	
	private User testUser;
	private AccountCreation testCreation;
	
	@BeforeEach
	void setup() {
		User checkUser = userRepo.findByemail("root1@example.com");
		if(checkUser != null) {
			testUser = checkUser;
		}
		else {
	    User user = new User();
	    user.setUsername("root");
	    user.setEmail("root1@example.com");
	    user.setPassword("Nfl123");
	    user.setAccountType(AccountType.CHECKINGS);
	    user.setBalance(5000);
	    
	    testUser = userRepo.save(user);    
	}
	}
	


	@Test
	public void getAll() throws Exception {
		// when - action
		ResultActions response = mvc.perform(MockMvcRequestBuilders.get(url));


		var recordCount = (int) repo.count();

		// then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(recordCount)));
		

	}



	@Test
	public void testPost() throws Exception {
		User user = userRepo.findByemail("root1@example.com");
		
		String createdOn = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		 String json = "{"+ "\"user\":{"
			        + "\"id\": \"" + user.getId() + "\","
			        + "\"username\": \"username\","
			        + "\"email\": \"root1@example.com\","
			        + "\"password\": \"password\","
			        + "\"accountType\": \"CHECKINGS\","
			        + "\"balance\": \"100\"  },"
			            + "\"accountType\": \"CHECKINGS\","
			            + "\"balance\": \"1000\","
			            + "\"createOn\": \"" + createdOn + "\","
			            + "\"accountNumber\": \"123456789\"" +"}";


		ResultActions response = mvc.perform(MockMvcRequestBuilders.post(url)
				.with(csrf())
				.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
				.content(json));


		response.andExpect(MockMvcResultMatchers.status().isCreated());
		//response.andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is("New Account")));
	    
	}
	
	@Test
	public void testDeleteAccount() throws Exception {
	    
	   // User user = userRepo.findByemail("root1@example.com");
		testUser = userRepo.findByemail("root1@example.com");

	    AccountCreation account = AccountCreation.builder()
	            .user(testUser)
	            .accountType(AccountCreation.AccountType.SAVINGS)
	            .balance(1000l)
	            .createOn(LocalDate.now())
	            .accountNumber("2222222")
	            .build();

	    account = repo.save(account);
		
	    long accountId = account.getAccountId();

	  
	    Assertions.assertTrue(repo.existsById(accountId));

	    
	    ResultActions response = mvc.perform(MockMvcRequestBuilders.delete(url + "/" + accountId)
	            .with(csrf())
	            .contentType(org.springframework.http.MediaType.APPLICATION_JSON));

	    
	    response.andExpect(MockMvcResultMatchers.status().isNoContent());

	   
	    Assertions.assertFalse(repo.existsById(accountId));
	}
	
	@Test
	public void testPostValidationFail() throws Exception {
		User user = userRepo.findByemail("root1@example.com");
		
		
		String createdOn = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		
		 String json = "{"+ "\"user\":{"
			        + "\"id\": \"" + user.getId() + "\","
			        + "\"username\": \"username\","
			        + "\"email\": \"root1@example.com\","
			        + "\"password\": \"password\","
			        + "\"accountType\": \"CHECKINGS\","
			        + "\"balance\": \"100\"  },"
			            + "\"accountType\": \"null\","
			            + "\"balance\": \"1000\","
			            + "\"createOn\": \"" + createdOn + "\","
			            + "\"accountNumber\": \"11111111\"" +"}";

	    ResultActions response = mvc.perform(MockMvcRequestBuilders.post(url)
	            .with(csrf())
	            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
	            .content(json));

	    response.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}





}