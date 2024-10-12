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
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	private String accountType;
	private Long balance;
	private LocalDateTime createOn;

	}



