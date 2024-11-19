package com.example.Banking.application.accountManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.test.context.support.WithMockUser;



@SpringBootTest
@AutoConfigureMockMvc
public class AccountPreferenceServiceTest {

	@Autowired
	private AccountPreferenceRepo repo;
	@Autowired
	private MockMvc mvc;
	@Autowired
	private UserRepo userRepo;
	
	
	private static String url = "/api/preferences";
	
	@BeforeEach
	void setup() {
	   // userRepo.deleteAll(); 
		//assertEquals(0, userRepo.count());
	    User user = new User();
	    user.setUsername("root");
	    user.setEmail("root1@example.com");
	    user.setPassword("Nfl123");
	    user.setAccountType(AccountType.CHECKINGS);
	    user.setBalance(5000);
	    
	    userRepo.save(user);
	    //assertEquals(1, userRepo.count());
	    
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
	public void testPostPreference() throws Exception {
	    
	    User user = userRepo.findByemail("root1@example.com");
	    //System.out.println(user);
	    
	    
	    
	    String json = "{"+ "\"user\":{"
		        + "\"id\": \"" + user.getId() + "\","
		        + "\"username\": \"username\","
		        + "\"email\": \"root1@example.com\","
		        + "\"password\": \"123456\","
		        + "\"accountType\": \"CHECKINGS\","
		        + "\"balance\": \"100\"  },"
		            + "\"preferenceType\": \"COMMUNICATION\","
		            + "\"preferenceValue\": \"EMAIL\","
		            + "\"updatedOn\": \"2024-11-18T10:00:00\""
		            + "}";
	  
	    ResultActions response = mvc.perform(MockMvcRequestBuilders.post("/api/preferences")
	            .with(csrf())
	            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
	            .content(json));

	    
	    response.andExpect(MockMvcResultMatchers.status().isCreated());
	}
	
	@Test
	@WithMockUser(username = "root", password = "Nfl123")
	public void testDeletePreference() throws Exception {
	    // Arrange - Create and save an AccountPreference
	    User user = userRepo.findByemail("root1@example.com");

	    AccountPreference preference = AccountPreference.builder()
	            .user(user)
	            .preferenceType(AccountPreference.PreferenceType.COMMUNICATION)
	            .preferenceValue(AccountPreference.PreferenceValue.EMAIL)
	            .updatedOn(LocalDateTime.now())
	            .build();

	    preference = repo.save(preference);
	    long preferenceId = preference.getPreferenceId();

	  
	    Assertions.assertTrue(repo.existsById(preferenceId));

	    
	    ResultActions response = mvc.perform(MockMvcRequestBuilders.delete(url + "/" + preferenceId)
	            .with(csrf())
	            .contentType(org.springframework.http.MediaType.APPLICATION_JSON));

	    // Assert - Validate the response
	    response.andExpect(MockMvcResultMatchers.status().isNoContent());

	    // Ensure the preference no longer exists
	    Assertions.assertFalse(repo.existsById(preferenceId));
	}

	
	
	


	




}
