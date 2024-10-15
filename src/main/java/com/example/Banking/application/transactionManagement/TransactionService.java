package com.example.Banking.application.transactionManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public Transaction createTransaction(String accountNumber, BigDecimal amount, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(accountNumber);
        transaction.setAmount(amount);
        transaction.setType(type);
        transaction.setStatus(TransactionStatus.SUCCESS); // Assuming transaction is successful
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    // Example method for deposits (which would be a CREDIT)
    @Transactional
    public Transaction depositCash(String accountNumber, BigDecimal amount) {
        return createTransaction(accountNumber, amount, TransactionType.CREDIT);
    }

    // Example method for withdrawals (which would be a DEBIT)
    @Transactional
    public Transaction withdrawCash(String accountNumber, BigDecimal amount) {
        return createTransaction(accountNumber, amount, TransactionType.DEBIT);
    }

    // Example method for transfers
    @Transactional
    public Transaction transferFunds(String sourceAccountNumber, String targetAccountNumber, BigDecimal amount) {
        // Debit from source account
        createTransaction(sourceAccountNumber, amount, TransactionType.DEBIT);
        // Credit to target account
        return createTransaction(targetAccountNumber, amount, TransactionType.CREDIT);
    }

    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    @Transactional
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}
