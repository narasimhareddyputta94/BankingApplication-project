package com.example.Banking.application.transactionManagement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTypeTest {

    @Test
    public void testEnumValues() {
        assertEquals(TransactionType.CREDIT, TransactionType.valueOf("CREDIT"));
        assertEquals(TransactionType.DEBIT, TransactionType.valueOf("DEBIT"));

    }

    @Test
    public void testEnumInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            TransactionType.valueOf("INVALID_TYPE");
        });
    }
}
