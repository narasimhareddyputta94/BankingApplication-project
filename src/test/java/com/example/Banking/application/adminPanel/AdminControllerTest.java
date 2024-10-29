package com.example.Banking.application.adminPanel;

import com.example.Banking.application.authentication.User;
import com.example.Banking.application.transactionManagement.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdminService adminService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsersWithTransactions_WhenUsersExist() throws Exception {
        User user = new User();
        when(adminService.getAllUsersWithTransactions()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    public void testGetAllUsersWithTransactions_WhenNoUsersExist() throws Exception {
        when(adminService.getAllUsersWithTransactions()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/admin/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetUserTransactions_WhenUserHasTransactions() throws Exception {
        Transaction transaction = new Transaction();
        when(adminService.getUserTransactions(1L)).thenReturn(Collections.singletonList(transaction));

        mockMvc.perform(get("/api/admin/users/1/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    public void testGetUserTransactions_WhenUserHasNoTransactions() throws Exception {
        when(adminService.getUserTransactions(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/admin/users/1/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testGetUserTransactions_WhenUserDoesNotExist() throws Exception {
        when(adminService.getUserTransactions(999L)).thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(get("/api/admin/users/999/transactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("User not found"));
    }
}
