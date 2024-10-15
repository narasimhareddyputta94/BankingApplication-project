package com.example.Banking.application.report;

import com.example.Banking.application.transactionManagement.Transaction;
import com.example.Banking.application.transactionManagement.TransactionRepository;
import com.example.Banking.application.transactionManagement.TransactionStatus;
import com.example.Banking.application.transactionManagement.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGenerateReport() {
        // Arrange
        String accountNumber = "1234567890";
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();

        Transaction transaction1 = new Transaction(1L, accountNumber, BigDecimal.valueOf(100),
                TransactionStatus.SUCCESS, TransactionType.CREDIT, LocalDateTime.now());
        Transaction transaction2 = new Transaction(2L, accountNumber, BigDecimal.valueOf(50),
                TransactionStatus.SUCCESS, TransactionType.DEBIT, LocalDateTime.now());

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        when(transactionRepository.findByAccountNumberAndTimestampBetween(accountNumber, startDate, endDate))
                .thenReturn(transactions);

        // Act
        FinancialSummary summary = reportService.generateReport(startDate, endDate, accountNumber);

        // Assert
        assertNotNull(summary);
        assertEquals(BigDecimal.valueOf(100), summary.getTotalIncome());
        assertEquals(BigDecimal.valueOf(50), summary.getTotalExpenses());
        assertEquals(BigDecimal.valueOf(50), summary.getRemainingBalance());
    }

    @Test
    void testGenerateReport_NoTransactions() {
        // Arrange
        String accountNumber = "1234567890";
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();

        when(transactionRepository.findByAccountNumberAndTimestampBetween(accountNumber, startDate, endDate))
                .thenReturn(Arrays.asList());

        // Act
        FinancialSummary summary = reportService.generateReport(startDate, endDate, accountNumber);

        // Assert
        assertNotNull(summary);
        assertEquals(BigDecimal.ZERO, summary.getTotalIncome());
        assertEquals(BigDecimal.ZERO, summary.getTotalExpenses());
        assertEquals(BigDecimal.ZERO, summary.getRemainingBalance());
    }
}
