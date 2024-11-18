package com.example.Banking.application.authentication;

import com.example.banking.application.authentication.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.example.banking.application.accountManagement.AccountCreation;


public class CreateAccountTest {

    @InjectMocks
    private CreateAccount createAccount;

    @Mock
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignupSuccess() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@example.com");
        request.setUserName("testUser");
        request.setPassword("password");
        request.setAccountType(AccountCreation.AccountType.SAVINGS);
        request.setBalance(1000);

        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testUser");
        when(userService.findbyEmail(request.getEmail())).thenReturn(null);
        when(userService.registerUser(
                request.getUserName(),
                request.getPassword(),
                request.getEmail(),
                request.getAccountType(),
                request.getBalance())).thenReturn(user);

        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
    }

    @Test
    public void testSignupEmailExists() {
        SignupRequest request = new SignupRequest();
        request.setEmail("test@example.com");

        when(userService.findbyEmail(anyString())).thenReturn(new User());

        ResponseEntity<String> response = createAccount.Signup(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email Id already exists.", response.getBody());
    }

    @Test
    public void testLoginSuccess() {
        Login loginRequest = new Login();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userService.findbyEmail(anyString())).thenReturn(user);
        when(bCryptPasswordEncoder.matches(anyString(), anyString())).thenReturn(true);

        ResponseEntity<String> response = createAccount.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login Successful", response.getBody());
    }

    @Test
    public void testLoginFailure() {
        Login loginRequest = new Login();
        loginRequest.setEmail("wrong@example.com");
        loginRequest.setPassword("wrongPassword");

        when(userService.findbyEmail(anyString())).thenReturn(null);

        ResponseEntity<String> response = createAccount.login(loginRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email or password is incorrect.", response.getBody());
    }
}

