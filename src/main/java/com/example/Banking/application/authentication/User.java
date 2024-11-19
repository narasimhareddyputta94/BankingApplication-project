package com.example.Banking.application.authentication;

import com.example.Banking.application.accountManagement.AccountCreation.AccountType;
import com.example.Banking.application.accountManagement.AccountCreation;
import com.example.Banking.application.transactionManagement.Transaction;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "account_type", nullable = false)
	private AccountType accountType;

	@Column(name = "balance", nullable = false)
	private long balance;

	//@OneToOne
	//@JoinColumn(name = "account_id", referencedColumnName = "accountId")
	//private AccountCreation account;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Transaction> transactions;
}
