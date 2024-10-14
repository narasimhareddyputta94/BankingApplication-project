package com.example.Banking.application.transactionManagement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTypeTest {

    @Test
    public void testEnumValues() {
        assertEquals(TransactionType.DEPOSIT, TransactionType.valueOf("DEPOSIT"));
        assertEquals(TransactionType.WITHDRAWAL, TransactionType.valueOf("WITHDRAWAL"));
        assertEquals(TransactionType.TRANSFER, TransactionType.valueOf("TRANSFER"));
    }

    @Test
    public void testEnumInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            TransactionType.valueOf("INVALID_TYPE");
        });
    }
}
