package com.example.banking.application.authentication;

import com.example.banking.application.authentication.User;
import com.example.banking.application.authentication.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class CreateAccountTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Test
    public void testSignupSuccess() throws Exception {
        String requestBody = """
            {
                "email": "newuser@example.com",
                "userName": "newUser",
                "password": "password",
                "accountType": "SAVINGS",
                "balance": 5000
            }
        """;

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully."));
    }

    @Test
    public void testSignupEmailExists() throws Exception {
        String requestBody = """
            {
                "email": "existinguser@example.com",
                "userName": "existingUser",
                "password": "password",
                "accountType": "SAVINGS",
                "balance": 5000
            }
        """;

        when(userService.findbyEmail("existinguser@example.com")).thenReturn(new User());

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email Id already exists."));
    }

    @Test
    public void testLoginSuccess() throws Exception {
        String requestBody = """
        {
            "email": "user@example.com",
            "password": "password"
        }
    """;

        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("encodedPassword");

        when(userService.findbyEmail("user@example.com")).thenReturn(user);
        when(bCryptPasswordEncoder.matches("password", "encodedPassword")).thenReturn(true);
        when(userService.login("user@example.com", "password")).thenReturn("validToken");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Login Successful. Token: validToken")));
    }


    @Test
    public void testLoginFailure() throws Exception {
        String requestBody = """
        {
            "email": "invalid@example.com",
            "password": "wrongPassword"
        }
    """;

        when(userService.findbyEmail(anyString())).thenReturn(null);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid email or password."));
    }

    @Test
    public void testChangePasswordSuccess() throws Exception {
        String email = "user@example.com";
        String currentPassword = "currentPassword";
        String newPassword = "newPassword";
        String confirmPassword = "newPassword";

        User user = new User();
        user.setEmail(email);
        user.setPassword("encodedCurrentPassword");

        when(userService.findbyEmail(email)).thenReturn(user);
        when(bCryptPasswordEncoder.matches(currentPassword, "encodedCurrentPassword")).thenReturn(true);

        mockMvc.perform(post("/auth/changepassword")
                        .param("email", email)
                        .param("currentPassword", currentPassword)
                        .param("newPassword", newPassword)
                        .param("confirmPassword", confirmPassword))
                .andExpect(status().isOk())
                .andExpect(content().string("Password changed successfully."));

        verify(userService).changePassword(email, newPassword, confirmPassword);
    }



    @Test
    public void testValidateTokenSuccess() throws Exception {
        when(userService.validateToken("validToken", "user@example.com")).thenReturn(true);

        mockMvc.perform(get("/auth/validate")
                        .param("token", "validToken")
                        .param("email", "user@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("Token is valid."));
    }

    @Test
    public void testValidateTokenInvalid() throws Exception {
        when(userService.validateToken("invalidToken", "user@example.com")).thenReturn(false);

        mockMvc.perform(get("/auth/validate")
                        .param("token", "invalidToken")
                        .param("email", "user@example.com"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid or expired token."));
    }

    @Test
    public void testLogoutSuccess() throws Exception {
        when(userService.isTokenValidForLogout("validToken")).thenReturn(true);

        mockMvc.perform(post("/auth/logout")
                        .param("token", "validToken"))
                .andExpect(status().isOk())
                .andExpect(content().string("Logout successful."));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/auth/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetUserByIdSuccess() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        when(userService.getUserById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/auth/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email", is("test@example.com")));
    }

    @Test
    public void testGetUserByIdNotFound() throws Exception {
        when(userService.getUserById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/auth/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with ID: 1"));
    }
}
