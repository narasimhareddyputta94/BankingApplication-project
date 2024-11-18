package com.example.Banking.application.accountManagement;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.banking.application.accountManagement.AccountPreference;
import com.example.banking.application.accountManagement.AccountPreferenceRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.banking.application.authentication.User;
import com.example.banking.application.authentication.UserRepo;
import com.example.banking.application.accountManagement.AccountPreference.PreferenceType;
import com.example.banking.application.accountManagement.AccountPreference.PreferenceValue;
import com.example.banking.application.accountManagement.AccountCreation.AccountType;

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
		user.setAccountType(AccountType.CHECKINGS);
		user.setBalance(1000);
		assertNotNull(userRepo);
		userRepo.save(user);

		long initialCount = prefRepo.count();

		AccountPreference account = new AccountPreference();
		account.setUser(user);
		account.setPreferenceTypeEnum(PreferenceType.COMMUNICATION);
		account.setPreferenceValueEnum(PreferenceValue.EMAIL);
		account.setUpdatedOn(LocalDateTime.now());
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
		user.setAccountType(AccountType.CHECKINGS);
		user.setBalance(1000);
		userRepo.save(user);

		AccountPreference account = new AccountPreference();
		account.setUser(user);
		account.setPreferenceValueEnum(PreferenceValue.EMAIL);
		account.setUpdatedOn(LocalDateTime.now());
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
		user.setAccountType(AccountType.SAVINGS);
		user.setBalance(1500);
		userRepo.save(user);

		long initialCount = prefRepo.count();

		AccountPreference account = new AccountPreference();
		account.setUser(user);
		account.setPreferenceTypeEnum(PreferenceType.COMMUNICATION);
		account.setPreferenceValueEnum(PreferenceValue.EMAIL);
		account.setUpdatedOn(LocalDateTime.now());
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
		user.setAccountType(AccountType.CHECKINGS);
		user.setBalance(500);
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
		user.setAccountType(AccountType.CHECKINGS);
		user.setBalance(1000);
		userRepo.save(user);

		AccountPreference account = new AccountPreference();
		account.setUser(user);
		account.setPreferenceTypeEnum(PreferenceType.COMMUNICATION);
		account.setPreferenceValueEnum(PreferenceValue.EMAIL);
		account.setUpdatedOn(LocalDateTime.now());
		prefRepo.save(account);

		account.setPreferenceValueEnum(PreferenceValue.PHONE);
		prefRepo.save(account);

		AccountPreference updatedAccount = prefRepo.findById(account.getPreferenceId()).orElse(null);
		assertNotNull(updatedAccount);
		assertEquals(PreferenceValue.PHONE, updatedAccount.getPreferenceValue());
	}
}
