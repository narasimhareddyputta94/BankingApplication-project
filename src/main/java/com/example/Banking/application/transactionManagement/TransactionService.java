package com.example.Banking.application.transactionManagement;

import com.example.Banking.application.emailNotifications.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.Banking.application.transactionManagement.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Transactional
    public Transaction createTransaction(String accountNumber, BigDecimal amount, TransactionType type, String recipientEmail) {
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(accountNumber);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setTimestamp(LocalDateTime.now());

        try {
            transaction.setStatus(TransactionStatus.SUCCESS);
            Transaction savedTransaction = transactionRepository.save(transaction);
            emailNotificationService.sendTransactionNotification(recipientEmail, accountNumber, type.name(), "SUCCESS", amount);
            return savedTransaction;
        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.FAILED);
            emailNotificationService.sendTransactionNotification(recipientEmail, accountNumber, type.name(), "FAILED", amount);
            transactionRepository.save(transaction);  // Save the failed transaction state
            return transaction;
        }
    }


    @Transactional
    public Transaction depositCash(String accountNumber, BigDecimal amount, String recipientEmail) {
        return createTransaction(accountNumber, amount, TransactionType.CREDIT, recipientEmail);
    }

    @Transactional
    public Transaction withdrawCash(String accountNumber, BigDecimal amount, String recipientEmail) {
        return createTransaction(accountNumber, amount, TransactionType.DEBIT, recipientEmail);
    }

    @Transactional
    public Transaction transferFunds(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount, String sourceEmail, String targetEmail) {
        createTransaction(sourceAccountNumber, amount, TransactionType.DEBIT, sourceEmail);
        return createTransaction(targetAccountNumber, amount, TransactionType.CREDIT, targetEmail);
    }

    @Transactional(readOnly = true)
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }
}
