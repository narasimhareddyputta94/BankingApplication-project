package com.example.Banking.application.authentication;

import com.example.Banking.application.accountManagement.AccountCreation;
import com.example.Banking.application.accountManagement.AccountCreationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private AccountCreationRepo accountRepo;

	@Autowired
	UserSecurityConfig userSecurityConfig;

	public User registerUser(String name , String password , String email, AccountCreation.AccountType accountType, long balance) {
		User user = new User();
		user.setUsername(name);
		user.setPassword(userSecurityConfig.passwordEncoder().encode(password));
		user.setEmail(email);
		user.setAccountType(accountType);
		user.setBalance(balance);
		User saveUser = userRepo.save(user);

		String accountNumber = generateAccountNumber();

		AccountCreation accountCreation = AccountCreation.builder()
				.user(saveUser)
				.accountType(accountType)
				.accountNumber(accountNumber)
				.balance(balance)
				.createOn(LocalDateTime.now())
				.build();
		AccountCreation saveAccount = accountRepo.save(accountCreation);
		saveUser.setAccount(saveAccount);
		userRepo.save(saveUser);

		return saveUser;
	}

	private String generateAccountNumber() {
		Random random = new Random();
		StringBuilder accountNumber = new StringBuilder();
		for(int i=0 ; i<= 10 ; i++) {
			accountNumber.append(random.nextInt(10));
		}
		return accountNumber.toString();
	}

	public User findbyEmail(String email) {
		return userRepo.findByemail(email);
	}
}
