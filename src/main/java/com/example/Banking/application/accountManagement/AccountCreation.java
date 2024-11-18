package com.example.Banking.application.accountManagement;


import java.time.LocalDate;

import com.example.Banking.application.authentication.User;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "Accounts", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"userId", "accountType"}),
		@UniqueConstraint(columnNames = {"accountNumber"})
	})

//@Table(name = "Accounts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountCreation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long accountId;

	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userId")
	@NotNull
	private User user;
	@NotNull(message = "Account type is required")
	//private String accountType;
	//@Enumerated(EnumType.STRING)
	private AccountType accountType;
	@NotNull(message = "Balance is required")
	private Long balance;
	@NotNull(message = "Creation date is required")
	private LocalDate createOn;
	// CHeck changing LocalDateTime to LocalDate, check testing cases for errors

	@NotNull
	private String accountNumber;

	
	public enum AccountType {
        CHECKINGS,
        SAVINGS
        }
	

	public AccountType getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
}



