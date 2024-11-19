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
import java.time.format.DateTimeFormatter;
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
	
	private User testUser;
	private AccountPreference testPreference;

	
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
	
	
//	@AfterEach
//	void cleanup() {
//	    if (testPreference != null && repo.existsById(testPreference.getPreferenceId())) {
//	        repo.deleteById(testPreference.getPreferenceId());
//	    }
//	    if (testUser != null && userRepo.existsById(testUser.getId())) {
//	        userRepo.deleteById(testUser.getId());
//	    }
//	}

	
	@Test
	//@WithMockUser(username = "root", password = "Nfl123")
	public void getAll() throws Exception {
		// when - action
		ResultActions response = mvc.perform(MockMvcRequestBuilders.get(url));


		var recordCount = (int) repo.count();

		// then - verify the output
		response.andExpect(MockMvcResultMatchers.status().isOk());
		response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(recordCount)));
		

	}

	@Test
	public void testPostPreference() throws Exception {
	    
	    User user = userRepo.findByemail("root1@example.com");
	    
	    String updatedOn = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
	    String json = "{"+ "\"user\":{"
		        + "\"id\": \"" + user.getId() + "\","
		        + "\"username\": \"username\","
		        + "\"email\": \"root1@example.com\","
		        + "\"password\": \"123456\","
		        + "\"accountType\": \"CHECKINGS\","
		        + "\"balance\": \"100\"  },"
		            + "\"preferenceType\": \"COMMUNICATION\","
		            + "\"preferenceValue\": \"EMAIL\","
		            + "\"updatedOn\": \"" + updatedOn + "\""
		            + "}";
	  
	    ResultActions response = mvc.perform(MockMvcRequestBuilders.post("/api/preferences")
	            .with(csrf())
	            .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
	            .content(json));

	    
	    response.andExpect(MockMvcResultMatchers.status().isCreated());
	        
	}
	
	@Test
	public void testDeletePreference() throws Exception {
		
	    //User user = userRepo.findByemail("root1@example.com");
		testUser = userRepo.findByemail("root1@example.com");

	    AccountPreference preference = AccountPreference.builder()
	            .user(testUser)
	            .preferenceType(AccountPreference.PreferenceType.DISPLAY)
	            .preferenceValue(AccountPreference.PreferenceValue.PHONE)
	            .updatedOn(LocalDateTime.now())
	            .build();

	    //preference = repo.save(preference);
	    testPreference = repo.save(preference);
	    long preferenceId = preference.getPreferenceId();

	  
	    Assertions.assertTrue(repo.existsById(preferenceId));

	    
	    ResultActions response = mvc.perform(MockMvcRequestBuilders.delete(url + "/" + preferenceId)
	            .with(csrf())
	            .contentType(org.springframework.http.MediaType.APPLICATION_JSON));

	    
	    response.andExpect(MockMvcResultMatchers.status().isNoContent());

	    
	    Assertions.assertFalse(repo.existsById(preferenceId));
	}

	
	
	


	




}
