package com.example.Banking.application.transactionManagement;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    public void testTransactionCreation() {
        LocalDateTime now = LocalDateTime.now();
        Transaction transaction = new Transaction(1L, "123456789", new BigDecimal("1000"), TransactionStatus.SUCCESS, TransactionType.CREDIT, now);

        // Verify that the values are correctly set in the constructor
        assertEquals(1L, transaction.getId());
        assertEquals("123456789", transaction.getAccountNumber());
        assertEquals(new BigDecimal("1000"), transaction.getAmount());
        assertEquals(TransactionStatus.SUCCESS, transaction.getStatus());
        assertEquals(TransactionType.CREDIT, transaction.getType()); // Updated to CREDIT
        assertEquals(now, transaction.getTimestamp());
    }

    @Test
    public void testSetters() {
        Transaction transaction = new Transaction();
        LocalDateTime now = LocalDateTime.now();

        // Use setters to set the values
        transaction.setId(2L);
        transaction.setAccountNumber("987654321");
        transaction.setAmount(new BigDecimal("500"));
        transaction.setStatus(TransactionStatus.FAILED);
        transaction.setType(TransactionType.DEBIT); // Updated to DEBIT
        transaction.setTimestamp(now);

        // Verify that the values are correctly set via setters
        assertEquals(2L, transaction.getId());
        assertEquals("987654321", transaction.getAccountNumber());
        assertEquals(new BigDecimal("500"), transaction.getAmount());
        assertEquals(TransactionStatus.FAILED, transaction.getStatus());
        assertEquals(TransactionType.DEBIT, transaction.getType()); // Updated to DEBIT
        assertEquals(now, transaction.getTimestamp());
    }
}
