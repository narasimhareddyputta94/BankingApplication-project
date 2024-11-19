package com.example.banking.application.adminPanel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.banking.application.authentication.User;
import com.example.banking.application.authentication.UserRepo;
import com.example.banking.application.transactionManagement.Transaction;
import com.example.banking.application.transactionManagement.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AdminServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsersWithTransactions_WhenUsersExist() {
        User user = new User();
        when(userRepo.findAll()).thenReturn(Collections.singletonList(user));

        List<User> users = adminService.getAllUsersWithTransactions();
        assertNotNull(users);
        assertEquals(1, users.size());
    }

    @Test
    public void testGetAllUsersWithTransactions_WhenNoUsersExist() {
        when(userRepo.findAll()).thenReturn(Collections.emptyList());

        List<User> users = adminService.getAllUsersWithTransactions();
        assertNotNull(users);
        assertTrue(users.isEmpty());
    }

    @Test
    public void testGetUserTransactions_WhenUserExistsAndHasTransactions() {
        User user = new User();
        Transaction transaction = new Transaction();
        user.setTransactions(Collections.singletonList(transaction));
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        List<Transaction> transactions = adminService.getUserTransactions(1L);
        assertNotNull(transactions);
        assertEquals(1, transactions.size());
    }

    @Test
    public void testGetUserTransactions_WhenUserExistsAndHasNoTransactions() {
        User user = new User();
        user.setTransactions(Collections.emptyList());
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        List<Transaction> transactions = adminService.getUserTransactions(1L);
        assertNotNull(transactions);
        assertTrue(transactions.isEmpty());
    }

    @Test
    public void testGetUserTransactions_WhenUserDoesNotExist() {
        when(userRepo.findById(999L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            adminService.getUserTransactions(999L);
        });
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testGetUserTransactions_WhenUserIdIsNull() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            adminService.getUserTransactions(null);
        });
        assertEquals("User not found", exception.getMessage());
    }
}
