package com.example.Banking.application.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class CreateAccount {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<String> Signup(@RequestBody SignupRequest signupRequest) {
        if(userService.findbyEmail(signupRequest.getEmail()) != null){
            return ResponseEntity.badRequest().body("Email Id already exists.");
        }

        userService.registerUser(
                signupRequest.getUserName(),
                signupRequest.getPassword(),
                signupRequest.getEmail(),
                signupRequest.getAccountType(),
                signupRequest.getBalance()
        );
        return ResponseEntity.ok("User registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Login loginRequest) {
        User user = userService.findbyEmail(loginRequest.getEmail());

        if(user == null || !bCryptPasswordEncoder.matches((loginRequest.getPassword()), user.getPassword())) {
            return ResponseEntity.badRequest().body("Email or password is incorrect.");
        }

        return ResponseEntity.ok("Login Successful");
    }

    @ControllerAdvice
    public class ControllerExceptionHandler {

        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            return errors;
        }
    }
}

