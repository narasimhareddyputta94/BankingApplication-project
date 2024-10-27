package com.example.Banking.application.accountManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.security.test.context.support.WithMockUser;



@SpringBootTest
@AutoConfigureMockMvc
public class AccountCreationServiceTest {
	
	@Autowired 
	private AccountCreationRepo repo;
	@Autowired
	private MockMvc mvc;
	private static String url = "/api/accounts";
	
	@Test
	@WithMockUser(username = "root", password = "WelcomeToBanWorld!")
	public void getAll() throws Exception {
			// when - action
			ResultActions response = mvc.perform(MockMvcRequestBuilders.get(url));


			var recordCount = (int) repo.count();
			
			// then - verify the output
			response.andExpect(MockMvcResultMatchers.status().isOk());
			response.andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(recordCount)));
			
	    }
	 
	 


}
