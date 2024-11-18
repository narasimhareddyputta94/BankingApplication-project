package com.example.banking.application.authentication;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Login {

    @NotNull(message = "Email is Required")
    private String email;

    @NotNull(message = "Password is Required")
    private String password;


}
