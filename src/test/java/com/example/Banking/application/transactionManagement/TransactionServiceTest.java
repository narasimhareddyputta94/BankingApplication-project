package com.example.Banking.application.transactionManagement;

import com.example.Banking.application.emailNotifications.EmailNotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private EmailNotificationService emailNotificationService;

    @InjectMocks
    private TransactionService transactionService;

    private Transaction transaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAccountNumber("1234567890");
        transaction.setAmount(BigDecimal.valueOf(100.00));
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setType(TransactionType.CREDIT);
        transaction.setTimestamp(LocalDateTime.now());
    }

    @Test
    public void testCreateTransaction_Success() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.createTransaction("1234567890", BigDecimal.valueOf(100.00), TransactionType.CREDIT, "test@example.com");

        assertNotNull(result);
        assertEquals(TransactionStatus.SUCCESS, result.getStatus());
        verify(emailNotificationService).sendTransactionNotification("test@example.com", "1234567890", "CREDIT", "SUCCESS", BigDecimal.valueOf(100.00));
    }

    @Test
    public void testCreateTransaction_Failure() {
        when(transactionRepository.save(any(Transaction.class))).thenThrow(new RuntimeException("Database error"));

        Transaction result = transactionService.createTransaction("1234567890", BigDecimal.valueOf(100.00), TransactionType.CREDIT, "test@example.com");

        assertNotNull(result);
        assertEquals(TransactionStatus.FAILED, result.getStatus());
        verify(emailNotificationService).sendTransactionNotification("test@example.com", "1234567890", "CREDIT", "FAILED", BigDecimal.valueOf(100.00));
    }

    @Test
    public void testDepositCash() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.depositCash("1234567890", BigDecimal.valueOf(100.00), "test@example.com");

        assertNotNull(result);
        assertEquals(TransactionType.CREDIT, result.getType());
    }

    @Test
    public void testWithdrawCash() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.withdrawCash("1234567890", BigDecimal.valueOf(50.00), "test@example.com");

        assertNotNull(result);
        assertEquals(TransactionType.DEBIT, result.getType());
    }

    @Test
    public void testTransferFunds() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        transactionService.transferFunds("1234567890", "0987654321", BigDecimal.valueOf(100.00), "source@example.com", "target@example.com");

        verify(transactionRepository, times(2)).save(any(Transaction.class));
        verify(emailNotificationService).sendTransactionNotification("source@example.com", "1234567890", "DEBIT", "SUCCESS", BigDecimal.valueOf(100.00));
        verify(emailNotificationService).sendTransactionNotification("target@example.com", "0987654321", "CREDIT", "SUCCESS", BigDecimal.valueOf(100.00));
    }
}
