package com.example.Banking.application.accountManagement;


import java.time.LocalDateTime;

import com.example.Banking.application.Authentication.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "Accounts")
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;
	
	//@ManyToOne
	//@JoinColumn(name = "userId")
	//private User user;
	@NotNull(message = "Account type is required")
	private String accountType;
	@NotNull(message = "Balance is required")
	private Long balance;
	@NotNull(message = "Creation date is required")
	private LocalDateTime createOn;

	}



