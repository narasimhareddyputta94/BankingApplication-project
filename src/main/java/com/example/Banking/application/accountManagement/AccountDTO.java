package com.example.Banking.application.accountManagement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private AccountCreation.AccountType accountType; // Match the type with AccountCreation
    private String accountNumber;
    private Long balance;
    private LocalDate createOn;
}


