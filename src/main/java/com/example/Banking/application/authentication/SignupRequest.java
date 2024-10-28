package com.example.Banking.application.authentication;

import com.example.Banking.application.accountManagement.AccountCreation;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignupRequest {

    @NotNull(message = "UserName is Required")
    private String userName;

    @NotNull(message = "Password is Required")
    private String password;

    @NotNull(message = "emailId is Required")
    private String email;

    @NotNull(message = "Account type is required")
    private AccountCreation.AccountType accountType;

    @NotNull(message = "balance is Required")
    private long balance;

}
