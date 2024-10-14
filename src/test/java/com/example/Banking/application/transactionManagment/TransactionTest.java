package com.example.Banking.application.transactionManagment;

import com.example.Banking.application.transactionManagement.Transaction;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    public void testTransactionConstructorAndGetters() {
        // Create a LocalDateTime instance for the transaction date
        LocalDateTime transactionDate = LocalDateTime.now();

        // Create a Transaction object using the constructor
        Transaction transaction = new Transaction(1L, 100L, 200L, 500.00,
                Transaction.TransactionType.TRANSFER,
                transactionDate, "Transfer to savings");

        // Test if values are correctly set
        assertEquals(1L, transaction.getTransactionId());
        assertEquals(100L, transaction.getFromAccountId());
        assertEquals(200L, transaction.getToAccountId());
        assertEquals(500.00, transaction.getAmount());
        assertEquals(Transaction.TransactionType.TRANSFER, transaction.getType());
        assertEquals(transactionDate, transaction.getTransactionDate());
        assertEquals("Transfer to savings", transaction.getDescription());
    }

    @Test
    public void testTransactionSetters() {
        // Create a LocalDateTime instance for the transaction date
        LocalDateTime transactionDate = LocalDateTime.now();

        // Create a Transaction object and modify fields
        Transaction transaction = new Transaction();
        transaction.setTransactionId(2L);
        transaction.setFromAccountId(300L);
        transaction.setToAccountId(400L);
        transaction.setAmount(1000.00);
        transaction.setType(Transaction.TransactionType.DEPOSIT);
        transaction.setTransactionDate(transactionDate);
        transaction.setDescription("Deposit to account");

        // Verify updated values
        assertEquals(2L, transaction.getTransactionId());
        assertEquals(300L, transaction.getFromAccountId());
        assertEquals(400L, transaction.getToAccountId());
        assertEquals(1000.00, transaction.getAmount());
        assertEquals(Transaction.TransactionType.DEPOSIT, transaction.getType());
        assertEquals(transactionDate, transaction.getTransactionDate());
        assertEquals("Deposit to account", transaction.getDescription());
    }

    @Test
    public void testTransactionBuilder() {
        // Create a LocalDateTime instance for the transaction date
        LocalDateTime transactionDate = LocalDateTime.now();

        // Create a Transaction object using the builder
        Transaction transaction = Transaction.builder()
                .transactionId(3L)
                .fromAccountId(500L)
                .toAccountId(600L)
                .amount(1500.00)
                .type(Transaction.TransactionType.WITHDRAWAL)
                .transactionDate(transactionDate)
                .description("Withdrawal from account")
                .build();

        // Verify the values
        assertEquals(3L, transaction.getTransactionId());
        assertEquals(500L, transaction.getFromAccountId());
        assertEquals(600L, transaction.getToAccountId());
        assertEquals(1500.00, transaction.getAmount());
        assertEquals(Transaction.TransactionType.WITHDRAWAL, transaction.getType());
        assertEquals(transactionDate, transaction.getTransactionDate());
        assertEquals("Withdrawal from account", transaction.getDescription());
    }
}

