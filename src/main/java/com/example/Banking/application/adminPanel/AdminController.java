package com.example.Banking.application.adminPanel;

import com.example.Banking.application.authentication.User;
import com.example.Banking.application.transactionManagement.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Endpoint to get all users with their details and transaction history
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsersWithTransactions() {
        List<User> users = adminService.getAllUsersWithTransactions();
        return ResponseEntity.ok(users);
    }

    // Endpoint to get transaction history for a specific user by user ID
    @GetMapping("/users/{userId}/transactions")
    public ResponseEntity<List<Transaction>> getUserTransactions(@PathVariable Long userId) {
        List<Transaction> transactions = adminService.getUserTransactions(userId);
        return ResponseEntity.ok(transactions);
    }
}