package com.example.Banking.application.authentication;

import com.example.Banking.application.accountManagement.AccountCreation;
import com.example.Banking.application.accountManagement.AccountCreationRepo;
import com.example.Banking.application.transactionManagement.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private AccountCreationRepo accountRepo;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private JwtRepo jwtRepo;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private TransactionRepository transactionRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser() {
        String username = "testUser";
        String password = "password";
        String email = "test@example.com";
        long balance = 5000;
        AccountCreation.AccountType accountType = AccountCreation.AccountType.SAVINGS;

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);

        when(bCryptPasswordEncoder.encode(password)).thenReturn("encodedPassword");
        when(userRepo.save(any(User.class))).thenReturn(user);

        AccountCreation accountCreation = new AccountCreation();
        when(accountRepo.save(any(AccountCreation.class))).thenReturn(accountCreation);

        User result = userService.registerUser(username, password, email, accountType, balance);

        assertNotNull(result);
        assertEquals(username, result.getUsername());
        assertEquals(email, result.getEmail());
        verify(userRepo, times(2)).save(any(User.class));
        verify(accountRepo, times(1)).save(any(AccountCreation.class));
    }

    @Test
    void testFindByEmail() {
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepo.findByemail(email)).thenReturn(user);

        User result = userService.findbyEmail(email);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userRepo, times(1)).findByemail(email);
    }

    @Test
    void testLogin() {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedPassword");

        when(userRepo.findByemail(email)).thenReturn(user);
        when(bCryptPasswordEncoder.matches(password, "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken(user)).thenReturn("testToken");

        String token = userService.login(email, password);

        assertEquals("testToken", token);
        verify(userRepo, times(1)).findByemail(email);
        verify(jwtUtil, times(1)).generateToken(user);
    }

    @Test
    void testChangePassword() {
        String email = "test@example.com";
        String newPassword = "newPassword";
        String confirmPassword = "newPassword";
        User user = new User();
        user.setEmail(email);

        when(userRepo.findByemail(email)).thenReturn(user);
        when(bCryptPasswordEncoder.encode(newPassword)).thenReturn("encodedNewPassword");

        userService.changePassword(email, newPassword, confirmPassword);

        assertEquals("encodedNewPassword", user.getPassword());
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void testValidateToken() {
        String token = "validToken";
        String email = "test@example.com";
        User user = new User();
        user.setEmail(email);

        when(userRepo.findByemail(email)).thenReturn(user);
        when(jwtUtil.isTokenValid(token, user)).thenReturn(true);

        boolean isValid = userService.validateToken(token, email);

        assertTrue(isValid);
        verify(jwtUtil, times(1)).isTokenValid(token, user);
    }

    @Test
    void testLogout() {
        String token = "validToken";
        userService.logout(token);
        verify(jwtUtil, times(1)).revokeToken(token);
    }

    @Test
    void testIsTokenValidForLogout() {
        String token = "validToken";
        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(token);
        jwtToken.setExpired(false);
        jwtToken.setRevoked(false);

        when(jwtRepo.findByToken(token)).thenReturn(Optional.of(jwtToken));

        boolean isValid = userService.isTokenValidForLogout(token);

        assertTrue(isValid);
        verify(jwtRepo, times(1)).findByToken(token);
    }

    @Test
    void testGetAllUsers() {
        userService.getAllUsers();
        verify(userRepo, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        Long id = 1L;
        User user = new User();
        user.setId(id);

        when(userRepo.findById(id)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(userRepo, times(1)).findById(id);
    }

    @Test
    void testUpdateUser() {
        Long id = 1L;
        UpdateUser updateUser = new UpdateUser();
        updateUser.setUserName("updatedUser");
        updateUser.setEmail("updated@example.com");

        User user = new User();
        user.setId(id);
        user.setUsername("oldUser");
        user.setEmail("old@example.com");

        when(userRepo.findById(id)).thenReturn(Optional.of(user));

        userService.updateUser(id, updateUser);

        assertEquals("updatedUser", user.getUsername());
        assertEquals("updated@example.com", user.getEmail());
        verify(userRepo, times(1)).save(user);
    }
}
