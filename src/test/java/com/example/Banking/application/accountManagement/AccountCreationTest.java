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

@DataJpaTest
@ActiveProfiles("test")
public class AccountCreationTest {
	
	@Autowired
	private AccountCreationRepo accountRepo;
	
	@DisplayName("Test Creating An Account")
	@Test
	public void testAccountCreation() {
		long initialCount = accountRepo.count();
		long accountBalance = 1000;
		//User user = new User();
		LocalDateTime time = LocalDateTime.now();
		AccountCreation account = new AccountCreation();
		//user.setPassword("Test123");
		//account.setUser(user);
		account.setAccountType("Checkings");
		account.setBalance(accountBalance);
		account.setCreateOn(time);
		accountRepo.save(account);
		long finalCount = accountRepo.count();
		
		assertEquals("Checkings", account.getAccountType());
		assertEquals(accountBalance, account.getBalance());
		assertEquals(time, account.getCreateOn());
		//assertEquals(user, account.getUser());
		assertEquals(initialCount + 1, finalCount);
	}

		@DisplayName("Test Account Creation With Missing Data")
		@Test
		public void testMissingData() {
		        long initialCount = accountRepo.count(); 
		        long accountBalance = 1000;
		        AccountCreation account = new AccountCreation();
		        //account.setAccountId(initialCount + 1);
		       // account.setAccountType("Checkings");
		        account.setBalance(accountBalance);
		        account.setCreateOn(LocalDateTime.now());

		        assertThrows(Exception.class, () -> {
		            accountRepo.save(account); 
		        });
		        
		        
		        
		    }
		
		@DisplayName("Test Generated Id Value")
		@Test
		public void testGeneratedId() {
			 long accountId = 0;
			 AccountCreation account = new AccountCreation();
			 account.setAccountType("Savings");
			 account.setBalance(1000L);
			 account.setCreateOn(LocalDateTime.now());
			 accountRepo.save(account);
			 Long generatedId = account.getAccountId();
			 
			 assertNotEquals(accountId, generatedId );
		}
		
		@DisplayName("Test Deleting Account")
		@Test
		public void testDeleteAccount() {
			AccountCreation account = new AccountCreation();
			account.setAccountType("Checkings");
			account.setBalance(200l);
			account.setCreateOn(LocalDateTime.now());
			accountRepo.save(account);
			long firstAccountNumb = accountRepo.count();
			accountRepo.delete(account);
			long secondAccountNumb = accountRepo.count();
			
			assertEquals(firstAccountNumb-1, secondAccountNumb);
			
		}
		
		@DisplayName("Test Updating Account")
		@Test
		public void testUpdateAccount() {
			AccountCreation account = new AccountCreation();
			account.setAccountType("Savings");
			account.setBalance(1000l);
			account.setCreateOn(LocalDateTime.now());
			accountRepo.save(account);
			assertEquals(1000l, account.getBalance());
			account.setBalance(2000l);
			assertEquals(2000l, account.getBalance());
			
		}
		
		
		
		
		
	}
	
	

