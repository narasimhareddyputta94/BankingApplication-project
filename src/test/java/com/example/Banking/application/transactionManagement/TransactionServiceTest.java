package com.example.Banking.application.transactionManagement;

import com.example.banking.application.emailNotifications.EmailNotificationService;
import com.example.banking.application.transactionManagement.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(emailNotificationService, times(1))
                .sendTransactionNotification("test@example.com", "1234567890", "CREDIT", "SUCCESS", BigDecimal.valueOf(100.00));
    }

    @Test
    public void testCreateTransaction_Failure() {
        doThrow(new RuntimeException("Database error"))
                .doAnswer(invocation -> invocation.getArgument(0)) // Return the same transaction on retry save
                .when(transactionRepository).save(any(Transaction.class));

        Transaction result = transactionService.createTransaction("1234567890", BigDecimal.valueOf(100.00), TransactionType.CREDIT, "test@example.com");

        assertNotNull(result);
        assertEquals(TransactionStatus.FAILED, result.getStatus());
        verify(transactionRepository, times(2)).save(any(Transaction.class)); // First fails, second for retry with FAILED
        verify(emailNotificationService, times(1))
                .sendTransactionNotification("test@example.com", "1234567890", "CREDIT", "FAILED", BigDecimal.valueOf(100.00));
    }


    @Test
    public void testDepositCash() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.depositCash("1234567890", BigDecimal.valueOf(100.00), "test@example.com");

        assertNotNull(result);
        assertEquals(TransactionType.CREDIT, result.getType());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(emailNotificationService, times(1))
                .sendTransactionNotification("test@example.com", "1234567890", "CREDIT", "SUCCESS", BigDecimal.valueOf(100.00));
    }

    @Test
    public void testWithdrawCash() {
        transaction.setType(TransactionType.DEBIT);  // Set transaction type as DEBIT
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.withdrawCash("1234567890", BigDecimal.valueOf(50.00), "test@example.com");

        assertNotNull(result);
        assertEquals(TransactionType.DEBIT, result.getType());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(emailNotificationService, times(1))
                .sendTransactionNotification("test@example.com", "1234567890", "DEBIT", "SUCCESS", BigDecimal.valueOf(50.00));
    }

    @Test
    public void testTransferFunds() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        transactionService.transferFunds("1234567890", "0987654321", BigDecimal.valueOf(100.00), "source@example.com", "target@example.com");

        verify(transactionRepository, times(2)).save(any(Transaction.class));  // One save for debit, one for credit
        verify(emailNotificationService, times(1))
                .sendTransactionNotification("source@example.com", "1234567890", "DEBIT", "SUCCESS", BigDecimal.valueOf(100.00));
        verify(emailNotificationService, times(1))
                .sendTransactionNotification("target@example.com", "0987654321", "CREDIT", "SUCCESS", BigDecimal.valueOf(100.00));
    }
}
