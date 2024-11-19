package com.example.banking.application.transactionManagement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionStatusTest {

    @Test
    public void testEnumValues() {
        assertEquals(TransactionStatus.SUCCESS, TransactionStatus.valueOf("SUCCESS"));
        assertEquals(TransactionStatus.FAILED, TransactionStatus.valueOf("FAILED"));
    }

    @Test
    public void testEnumInvalidValue() {
        assertThrows(IllegalArgumentException.class, () -> {
            TransactionStatus.valueOf("INVALID_STATUS");
        });
    }
}
