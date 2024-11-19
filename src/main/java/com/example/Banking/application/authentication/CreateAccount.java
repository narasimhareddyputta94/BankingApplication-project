package com.example.Banking.application.authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@Log4j2
public class CreateAccount {

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    //SignUp a New User

    @Operation(summary = "Sign Up a new User" , description = "Register a new User here with required details ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully."),
            @ApiResponse(responseCode = "400", description = "Email Id already exists.", content = @Content)
    })
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> Signup(@RequestBody SignupRequest signupRequest) {
        log.traceEntry("Entering Signup with request: {}", signupRequest);

        Map<String, String> response = new HashMap<>();

        if (userService.findbyEmail(signupRequest.getEmail()) != null) {
            log.warn("Email already exists: {}", signupRequest.getEmail());
            response.put("message", "Email Id already exists.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        userService.registerUser(
                signupRequest.getUserName(),
                signupRequest.getPassword(),
                signupRequest.getEmail(),
                signupRequest.getAccountType(),
                signupRequest.getBalance()
        );

        log.traceExit("Exiting Signup with success");
        response.put("message", "User registered successfully.");
        return ResponseEntity.ok(response);
    }

    //Login after signup

    @Operation(summary = "Log in a user", description = "Authenticate a user using email and password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login Successful. Token returned."),
            @ApiResponse(responseCode = "400", description = "Invalid email or password.", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Login loginRequest) {
        log.traceEntry("Entering login with email: {}", loginRequest.getEmail());
        User user = userService.findbyEmail(loginRequest.getEmail());
        if (user == null || !bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            log.warn("Login failed for email: {}", loginRequest.getEmail());
            return ResponseEntity.badRequest().body("Invalid email or password.");
        }
        String token = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        log.traceExit("Exiting login with token: {}", token);
        return ResponseEntity.ok("Login Successful. Token: " + token);
    }

    //change password for user

    @Operation(summary = "Change user password", description = "Allows a user to change their password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully."),
            @ApiResponse(responseCode = "400", description = "Current password is incorrect or passwords do not match.", content = @Content)
    })
    @PostMapping("/changepassword")
    public ResponseEntity<String> changePassword(
            @RequestParam String email,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword
    ) {

        User user = userService.findbyEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (!bCryptPasswordEncoder.matches(currentPassword, user.getPassword())) {
            return ResponseEntity.badRequest().body("Current Password is Incorrect.");
        }

        if (!newPassword.equals(confirmPassword)) {
            return ResponseEntity.badRequest().body("New password and confirm password do not match.");
        }

        userService.changePassword(email, newPassword, confirmPassword);
        return ResponseEntity.ok("Password changed successfully.");

    }


    //Validating Token

    @Operation(summary = "Validate JWT token", description = "Validates if the given JWT token is valid and belongs to the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token is valid."),
            @ApiResponse(responseCode = "400", description = "Invalid or expired token.", content = @Content)
    })
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam String token, @RequestParam String email) {
        boolean isValid = userService.validateToken(token, email);
        if (isValid) {
            return ResponseEntity.ok("Token is valid.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
    }

    //Logout a user

    @Operation(summary = "Log out a user", description = "Invalidates the given JWT token for logout.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful."),
            @ApiResponse(responseCode = "400", description = "Invalid or tampered token.", content = @Content)
    })
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String token) {
        log.traceEntry("Entering logout with token: {}", token);
        if (!userService.isTokenValidForLogout(token)) {
            log.warn("Invalid token for logout: {}", token);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or tampered token.");
        }
        userService.logout(token);
        log.traceExit("Exiting logout with success");
        return ResponseEntity.ok("Logout successful.");
    }


    //Getting all Users

    @Operation(summary = "Get all users", description = "Fetch the list of all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved users.")
    })
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    //Getting a user by userId

    @Operation(summary = "Get user by ID", description = "Fetch a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found."),
            @ApiResponse(responseCode = "404", description = "User not found.", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(404).body("User not found with ID: " + id);
        }
    }

    //Updating User Details

    @Operation(summary = "Update user", description = "Update the details of an existing user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.", content = @Content)
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UpdateUser updateUser) {
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isEmpty()) {
            return ResponseEntity.status(404).body("User not found with ID: " + id);
        }
        userService.updateUser(id, updateUser);
        return ResponseEntity.ok("User updated successfully.");
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