package com.example.banking.application.adminPanel;

import com.example.banking.application.authentication.User;
import com.example.banking.application.authentication.UserRepo;
import com.example.banking.application.transactionManagement.Transaction;
import com.example.banking.application.transactionManagement.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TransactionRepository transactionRepository;

    public List<User> getAllUsersWithTransactions() {
        return userRepo.findAll();
    }

    public List<Transaction> getUserTransactions(Long userId) {
        Optional<User> user = userRepo.findById(userId);
        return user.map(User::getTransactions).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
