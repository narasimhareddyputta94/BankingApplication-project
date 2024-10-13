package com.example.Banking.application.accountManagement;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.Banking.application.Authentication.User;

@DataJpaTest
@ActiveProfiles("test")
public class AccountCreationTest {
	
	@Autowired
	private AccountCreationRepo accountRepo;
	
	@DisplayName("Test Creating An Account")
	@Test
	public void testAccountCreation() {
		long  accountId = 1;
		long accountBalance = 1000;
		User user = new User();
		LocalDateTime time = LocalDateTime.now();
		AccountCreation account = new AccountCreation();
		user.setPassword("Test123");
		
		account.setUser(user);
		account.setAccountId(accountId);
		account.setAccountType("Checkings");
		account.setBalance(accountBalance);
		account.setCreateOn(time);
		String expectedToString = "AccountCreation(accountId=1, user=" + user + ", accountType=Checkings, balance=1000, createOn=" + time + ")";
		assertEquals(expectedToString, account.toString());
		
		assertEquals("Checkings", account.getAccountType());
		assertEquals(accountBalance, account.getBalance());
		assertEquals(time, account.getCreateOn());
		assertEquals(user, account.getUser());
	}

		@DisplayName("Test Account Creation With Missing Data")
		@Test
		public void testMissingUser() {
		        long initialCount = accountRepo.count();  
		        long accountBalance = 1000;
		        AccountCreation account = new AccountCreation();
		        account.setAccountId(initialCount + 1);
		        account.setAccountType("Checkings");
		        account.setBalance(accountBalance);
		        account.setCreateOn(LocalDateTime.now());

		        assertThrows(Exception.class, () -> {
		            accountRepo.save(account); 
		        });

		        long finalCount = accountRepo.count(); 
		        assertEquals(initialCount, finalCount);
		    }
		
		
		
	}
	
	

