package com.example.Banking.application.transactionManagement;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    public void testTransactionCreation() {
        LocalDateTime now = LocalDateTime.now();
        Transaction transaction = new Transaction(1L, "123456789", new BigDecimal("1000"), TransactionStatus.SUCCESS, TransactionType.DEPOSIT, now);

        assertEquals(1L, transaction.getId());
        assertEquals("123456789", transaction.getAccountNumber());
        assertEquals(new BigDecimal("1000"), transaction.getAmount());
        assertEquals(TransactionStatus.SUCCESS, transaction.getStatus());
        assertEquals(TransactionType.DEPOSIT, transaction.getType());
        assertEquals(now, transaction.getTimestamp());
    }

    @Test
    public void testSetters() {
        Transaction transaction = new Transaction();
        LocalDateTime now = LocalDateTime.now();

        transaction.setId(2L);
        transaction.setAccountNumber("987654321");
        transaction.setAmount(new BigDecimal("500"));
        transaction.setStatus(TransactionStatus.FAILED);
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setTimestamp(now);

        assertEquals(2L, transaction.getId());
        assertEquals("987654321", transaction.getAccountNumber());
        assertEquals(new BigDecimal("500"), transaction.getAmount());
        assertEquals(TransactionStatus.FAILED, transaction.getStatus());
        assertEquals(TransactionType.WITHDRAWAL, transaction.getType());
        assertEquals(now, transaction.getTimestamp());
    }
}
