package com.example.Banking.application.authentication;

import com.example.Banking.application.accountManagement.AccountCreation;
import com.example.Banking.application.accountManagement.AccountCreationRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private AccountCreationRepo accountRepo;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    public void testRegisterUser() {
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");

        User user = new User();
        user.setUsername("testUser");
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");

        when(userRepo.save(any(User.class))).thenReturn(user);

        AccountCreation accountCreation = new AccountCreation();
        when(accountRepo.save(any(AccountCreation.class))).thenReturn(accountCreation);

        User result = userService.registerUser("testUser", "password", "test@example.com", AccountCreation.AccountType.SAVINGS, 1000);

        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("encodedPassword", result.getPassword());
    }

    @Test
    public void testFindByEmail() {
        User user = new User();
        user.setEmail("test@example.com");

        when(userRepo.findByemail(anyString())).thenReturn(user);
        User result = userService.findbyEmail("test@example.com");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }
}
