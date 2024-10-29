package com.example.Banking.application.transactionManagement;

import com.example.Banking.application.authentication.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    @Test
    public void testAllArgsConstructor() {
        User user = new User();
        LocalDateTime timestamp = LocalDateTime.now();

        Transaction transaction = new Transaction(
                1L,
                "1234567890",
                BigDecimal.valueOf(500.75),
                TransactionStatus.SUCCESS,
                TransactionType.CREDIT,
                timestamp,
                user
        );

        assertNotNull(transaction);
        assertEquals(1L, transaction.getId());
        assertEquals("1234567890", transaction.getAccountNumber());
        assertEquals(BigDecimal.valueOf(500.75), transaction.getAmount());
        assertEquals(TransactionStatus.SUCCESS, transaction.getStatus());
        assertEquals(TransactionType.CREDIT, transaction.getType());
        assertEquals(timestamp, transaction.getTimestamp());
        assertEquals(user, transaction.getUser());
    }

    @Test
    public void testNoArgsConstructor() {
        Transaction transaction = new Transaction();

        assertNotNull(transaction);
        assertNull(transaction.getId());
        assertNull(transaction.getAccountNumber());
        assertNull(transaction.getAmount());
        assertNull(transaction.getStatus());
        assertNull(transaction.getType());
        assertNull(transaction.getTimestamp());
        assertNull(transaction.getUser());
    }

    @Test
    public void testSettersAndGetters() {
        Transaction transaction = new Transaction();
        LocalDateTime timestamp = LocalDateTime.now();
        User user = new User();

        transaction.setId(2L);
        transaction.setAccountNumber("0987654321");
        transaction.setAmount(BigDecimal.valueOf(200.50));
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setType(TransactionType.DEBIT);
        transaction.setTimestamp(timestamp);
        transaction.setUser(user);

        assertEquals(2L, transaction.getId());
        assertEquals("0987654321", transaction.getAccountNumber());
        assertEquals(BigDecimal.valueOf(200.50), transaction.getAmount());
        assertEquals(TransactionStatus.PENDING, transaction.getStatus());
        assertEquals(TransactionType.DEBIT, transaction.getType());
        assertEquals(timestamp, transaction.getTimestamp());
        assertEquals(user, transaction.getUser());
    }

    @Test
    public void testEqualsAndHashCode() {
        User user = new User();
        LocalDateTime timestamp = LocalDateTime.now();

        Transaction transaction1 = new Transaction(
                1L,
                "1234567890",
                BigDecimal.valueOf(500.75),
                TransactionStatus.SUCCESS,
                TransactionType.CREDIT,
                timestamp,
                user
        );

        Transaction transaction2 = new Transaction(
                1L,
                "1234567890",
                BigDecimal.valueOf(500.75),
                TransactionStatus.SUCCESS,
                TransactionType.CREDIT,
                timestamp,
                user
        );

        assertEquals(transaction1, transaction2);
        assertEquals(transaction1.hashCode(), transaction2.hashCode());
    }

    @Test
    public void testToString() {
        User user = new User();
        user.setId(1L);
        user.setUsername("testUser");
        LocalDateTime timestamp = LocalDateTime.now();

        Transaction transaction = Transaction.builder()
                .id(1L)
                .accountNumber("1234567890")
                .amount(BigDecimal.valueOf(500.75))
                .status(TransactionStatus.SUCCESS)
                .type(TransactionType.CREDIT)
                .timestamp(timestamp)
                .user(user)
                .build();

        String expected = "Transaction(id=1, accountNumber=1234567890, amount=500.75, status=SUCCESS, type=CREDIT, timestamp="
                + timestamp.toString() + ", user=" + user.toString() + ")";
        assertEquals(expected, transaction.toString());
    }

    @Test
    public void testTransactionStatusEnum() {
        assertEquals(TransactionStatus.SUCCESS, TransactionStatus.valueOf("SUCCESS"));
        assertEquals(TransactionStatus.FAILED, TransactionStatus.valueOf("FAILED"));
        assertEquals("SUCCESS", TransactionStatus.SUCCESS.toString());
        assertEquals("FAILED", TransactionStatus.FAILED.toString());
    }

    @Test
    public void testTransactionTypeEnum() {
        assertEquals(TransactionType.CREDIT, TransactionType.valueOf("CREDIT"));
        assertEquals(TransactionType.DEBIT, TransactionType.valueOf("DEBIT"));
        assertEquals("CREDIT", TransactionType.CREDIT.toString());
        assertEquals("DEBIT", TransactionType.DEBIT.toString());
    }

    @Test
    public void testBigDecimalAmountPrecision() {
        Transaction transaction = new Transaction();
        BigDecimal preciseAmount = new BigDecimal("123.456789");

        transaction.setAmount(preciseAmount);
        assertEquals(new BigDecimal("123.456789"), transaction.getAmount());
    }

    @Test
    public void testNullFields() {
        Transaction transaction = new Transaction();

        assertNull(transaction.getId());
        assertNull(transaction.getAccountNumber());
        assertNull(transaction.getAmount());
        assertNull(transaction.getStatus());
        assertNull(transaction.getType());
        assertNull(transaction.getTimestamp());
        assertNull(transaction.getUser());
    }

    @Test
    public void testInvalidAccountNumber() {
        Transaction transaction = new Transaction();
        assertThrows(IllegalArgumentException.class, () -> {
            transaction.setAccountNumber(null);
        });
    }
}
