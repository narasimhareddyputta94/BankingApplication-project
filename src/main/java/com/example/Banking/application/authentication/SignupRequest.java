package com.example.Banking.application.authentication;

import com.example.Banking.application.accountManagement.AccountCreation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupRequest {

    @NotNull(message = "UserName is Required")
    private String userName;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Account type is required")
    private AccountCreation.AccountType accountType;

    @NotNull(message = "balance is Required")
    private long balance;

}