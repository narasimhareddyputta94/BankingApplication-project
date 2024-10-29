package com.example.Banking.application.accountManagement;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.Banking.application.authentication.User;
import com.example.Banking.application.authentication.UserRepo;
import com.example.Banking.application.accountManagement.AccountCreation.AccountType;

@DataJpaTest
@ActiveProfiles("test")
public class AccountCreationTest {

	@Autowired
	private AccountCreationRepo accountRepo;

	@Autowired
	private UserRepo userRepo;

	@DisplayName("Test Creating An Account")
	@Test
	public void testAccountCreation() {
		User user = new User();
		user.setUsername("username123");
		user.setEmail("123@gmail.com");
		user.setPassword("Password");
		user.setAccountType(AccountType.CHECKINGS);
		user.setBalance(1000);
		userRepo.save(user);

		long initialCount = accountRepo.count();
		long accountBalance = 1000;
		LocalDate time = LocalDate.now();

		AccountCreation account = new AccountCreation();
		account.setUser(user);
		account.setAccountType(AccountType.CHECKINGS);
		account.setBalance(accountBalance);
		account.setCreateOn(time);
		account.setAccountNumber("1234567890");
		accountRepo.save(account);
		long finalCount = accountRepo.count();

		assertEquals(AccountType.CHECKINGS, account.getAccountType());
		assertEquals(accountBalance, account.getBalance());
		assertEquals(time, account.getCreateOn());
		assertEquals(user, account.getUser());
		assertEquals(initialCount + 1, finalCount);
	}

	@DisplayName("Test Account Creation With Missing Data")
	@Test
	public void testMissingData() {
		long initialCount = accountRepo.count();
		long accountBalance = 1000;

		AccountCreation account = new AccountCreation();
		account.setAccountType(AccountType.CHECKINGS);
		account.setBalance(accountBalance);
		account.setCreateOn(LocalDate.now());


		assertThrows(Exception.class, () -> {
			accountRepo.save(account);
		});
	}

	@DisplayName("Test Generated Id Value")
	@Test
	public void testGeneratedId() {
		long accountId = 0;
		User user = new User();
		user.setUsername("username123");
		user.setEmail("123@gmail.com");
		user.setPassword("Password");
		user.setAccountType(AccountType.SAVINGS);
		user.setBalance(1500);
		userRepo.save(user);

		AccountCreation account = new AccountCreation();
		account.setUser(user);
		account.setAccountType(AccountType.SAVINGS);
		account.setBalance(1000L);
		account.setCreateOn(LocalDate.now());
		account.setAccountNumber("1234567891");
		accountRepo.save(account);

		Long generatedId = account.getAccountId();
		assertNotEquals(accountId, generatedId);
	}

	@DisplayName("Test Deleting Account")
	@Test
	public void testDeleteAccount() {
		User user = new User();
		user.setUsername("username123");
		user.setEmail("123@gmail.com");
		user.setPassword("Password");
		user.setAccountType(AccountType.CHECKINGS);
		user.setBalance(2000);
		userRepo.save(user);

		AccountCreation account = new AccountCreation();
		account.setUser(user);
		account.setAccountType(AccountType.CHECKINGS);
		account.setBalance(200L);
		account.setCreateOn(LocalDate.now());
		account.setAccountNumber("1234567892");
		accountRepo.save(account);

		long firstAccountNumb = accountRepo.count();
		accountRepo.delete(account);
		long secondAccountNumb = accountRepo.count();

		assertEquals(firstAccountNumb - 1, secondAccountNumb);
	}

	@DisplayName("Test Updating Account")
	@Test
	public void testUpdateAccount() {
		User user = new User();
		user.setUsername("username123");
		user.setEmail("123@gmail.com");
		user.setPassword("Password");
		user.setAccountType(AccountType.CHECKINGS);
		user.setBalance(1000);
		userRepo.save(user);

		AccountCreation account = new AccountCreation();
		account.setUser(user);
		account.setAccountType(AccountType.CHECKINGS);
		account.setBalance(1000L);
		account.setCreateOn(LocalDate.now());
		account.setAccountNumber("1234567893");
		accountRepo.save(account);

		assertEquals(1000L, account.getBalance());
		account.setBalance(2000L);
		assertEquals(2000L, account.getBalance());
	}

	@DisplayName("Test Duplicate Account Types")
	@Test
	public void testDuplicateAccountType() {
		User user = new User();
		user.setUsername("username123");
		user.setEmail("123@gmail.com");
		user.setPassword("Password");
		user.setAccountType(AccountType.CHECKINGS);
		user.setBalance(1000);
		userRepo.save(user);

		long accountBalance = 1000;
		LocalDate time = LocalDate.now();

		AccountCreation account = new AccountCreation();
		account.setUser(user);
		account.setAccountType(AccountType.CHECKINGS);
		account.setBalance(accountBalance);
		account.setCreateOn(time);
		account.setAccountNumber("1234567894");
		accountRepo.save(account);

		AccountCreation account1 = new AccountCreation();
		account1.setUser(user);
		account1.setAccountType(AccountType.CHECKINGS);
		account1.setBalance(100L);
		account1.setCreateOn(LocalDate.now());
		account1.setAccountNumber("1234567895");

		assertThrows(Exception.class, () -> {
			accountRepo.save(account1);
		});
	}
}
