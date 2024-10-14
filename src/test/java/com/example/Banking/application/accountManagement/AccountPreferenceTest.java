package com.example.Banking.application.accountManagement;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.Banking.application.Authentication.User;
import com.example.Banking.application.Authentication.UserRepo;
import com.example.Banking.application.Authentication.UserService;
import com.example.Banking.application.accountManagement.AccountPreference.PreferenceType;
import com.example.Banking.application.accountManagement.AccountPreference.PreferenceValue;

@DataJpaTest
@ActiveProfiles("test")

public class AccountPreferenceTest {
	
	@Autowired
	private AccountPreferenceRepo prefRepo;
	@Autowired
	private UserRepo userRepo;
	
	@DisplayName("Test Creating An Account")
	@Test
	public void testAccountPreference() {
		User user = new User();
		user.setUsername("username123");
		user.setEmail("123@gmail.com");
		user.setPassword("Password");
		assertNotNull(userRepo);
		userRepo.save(user);
		
		long initialCount = prefRepo.count();
		
		AccountPreference account = new AccountPreference();
		account.setUser(user);
		account.setPreferenceTypeEnum(PreferenceType.COMMUNICATION);
		account.setPreferenceValueEnum(PreferenceValue.EMAIL);
		account.setUpdatedOn(LocalDateTime.now());
		//account.setCreatedOn(time);
		prefRepo.save(account);
		long finalCount = prefRepo.count();
		
		assertEquals(PreferenceType.COMMUNICATION, account.getPreferenceType());
		assertEquals(PreferenceValue.EMAIL, account.getPreferenceValue());
		assertEquals(user, account.getUser());
		assertEquals(initialCount + 1, finalCount); 
	}
	
	@DisplayName("Test Missing Field")
	@Test
	public void testMissingData() {
		User user = new User();
		user.setUsername("username123");
		user.setEmail("123@gmail.com");
		user.setPassword("Password");
		//assertNotNull(userRepo);
		userRepo.save(user);
		long initialCount = prefRepo.count();
		
		AccountPreference account = new AccountPreference();
		account.setUser(user);
		//account.setPreferenceTypeEnum(PreferenceType.COMMUNICATION);
		account.setPreferenceValueEnum(PreferenceValue.EMAIL);
		account.setUpdatedOn(LocalDateTime.now());
		//account.setCreatedOn(time);
		 assertThrows(Exception.class, () -> {
	            prefRepo.save(account); 
	        });
		
		
		
	}
	@DisplayName("Test Deleting Tuple")
	@Test
	public void testDeleteTuple() {
		User user = new User();
		user.setUsername("username123");
		user.setEmail("123@gmail.com");
		user.setPassword("Password");
		userRepo.save(user);
		
		long initialCount = prefRepo.count();
		
		AccountPreference account = new AccountPreference();
		account.setUser(user);
		account.setPreferenceTypeEnum(PreferenceType.COMMUNICATION);
		account.setPreferenceValueEnum(PreferenceValue.EMAIL);
		account.setUpdatedOn(LocalDateTime.now());
		//account.setCreatedOn(time);
		prefRepo.save(account);
		long beforeDelete = prefRepo.count();
		
		assertEquals(initialCount + 1, beforeDelete);
		prefRepo.delete(account);
		long afterDelete = prefRepo.count();
		assertEquals(initialCount, afterDelete);
	}
	
	@DisplayName("Test Invalid Preference Value")
	@Test
	public void testInvalidPreferenceValue() {
	    User user = new User();
	    user.setUsername("username123");
	    user.setEmail("123@gmail.com");
	    user.setPassword("Password");
	    userRepo.save(user);
	    
	    AccountPreference account = new AccountPreference();
	    account.setUser(user);
	    account.setPreferenceTypeEnum(PreferenceType.COMMUNICATION);
	    account.setPreferenceValueEnum(null); 
	    account.setUpdatedOn(LocalDateTime.now());
	    
	    assertThrows(Exception.class, () -> {
	        prefRepo.save(account);
	    });
	}
	
	@DisplayName("Test Updating Tuple")
	@Test
	public void testUpdate() {
		User user = new User();
		user.setUsername("username123");
		user.setEmail("123@gmail.com");
		user.setPassword("Password");
		userRepo.save(user);
		
		AccountPreference account = new AccountPreference();
		account.setUser(user);
		account.setPreferenceTypeEnum(PreferenceType.COMMUNICATION);
		account.setPreferenceValueEnum(PreferenceValue.EMAIL);
		account.setUpdatedOn(LocalDateTime.now());
		//account.setCreatedOn(time);
		prefRepo.save(account);
		account.setPreferenceValue(PreferenceValue.PHONE);
		prefRepo.save(account);
		
		AccountPreference account1 = prefRepo.findById(account.getPreferenceId()).orElse(null);
	    assertNotNull(account1);
	    assertEquals(PreferenceValue.PHONE, account1.getPreferenceValue());
		
		
	}
	
		
		
	}
	
	
	