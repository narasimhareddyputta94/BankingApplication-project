package com.example.banking.application.report;

import com.example.banking.application.transactionManagement.Transaction;
import com.example.banking.application.transactionManagement.TransactionRepository;
import com.example.banking.application.transactionManagement.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
    void testGenerateReport_NoTransactions() {
        String accountNumber = "12345";
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();

        when(transactionRepository.findByAccountNumberAndTimestampBetween(accountNumber, startDate, endDate))
                .thenReturn(Collections.emptyList());

        FinancialSummary result = reportService.generateReport(startDate, endDate, accountNumber);

        assertEquals(BigDecimal.ZERO, result.getTotalIncome());
        assertEquals(BigDecimal.ZERO, result.getTotalExpenses());
        assertEquals(BigDecimal.ZERO, result.getRemainingBalance());
    }

    @Test
    void testGenerateReport_OnlyCreditTransactions() {
        String accountNumber = "12345";
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(new BigDecimal("100.00"));
        transaction1.setType(TransactionType.CREDIT);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(new BigDecimal("200.00"));
        transaction2.setType(TransactionType.CREDIT);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        when(transactionRepository.findByAccountNumberAndTimestampBetween(accountNumber, startDate, endDate))
                .thenReturn(transactions);

        FinancialSummary result = reportService.generateReport(startDate, endDate, accountNumber);

        assertEquals(new BigDecimal("300.00"), result.getTotalIncome());
        assertEquals(BigDecimal.ZERO, result.getTotalExpenses());
        assertEquals(new BigDecimal("300.00"), result.getRemainingBalance());
    }

    @Test
    void testGenerateReport_OnlyDebitTransactions() {
        String accountNumber = "12345";
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(new BigDecimal("50.00"));
        transaction1.setType(TransactionType.DEBIT);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(new BigDecimal("30.00"));
        transaction2.setType(TransactionType.DEBIT);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        when(transactionRepository.findByAccountNumberAndTimestampBetween(accountNumber, startDate, endDate))
                .thenReturn(transactions);

        FinancialSummary result = reportService.generateReport(startDate, endDate, accountNumber);

        assertEquals(BigDecimal.ZERO, result.getTotalIncome());
        assertEquals(new BigDecimal("80.00"), result.getTotalExpenses());
        assertEquals(new BigDecimal("-80.00"), result.getRemainingBalance());
    }

    @Test
    void testGenerateReport_MixedTransactions() {
        String accountNumber = "12345";
        LocalDateTime startDate = LocalDateTime.now().minusDays(7);
        LocalDateTime endDate = LocalDateTime.now();

        Transaction transaction1 = new Transaction();
        transaction1.setAmount(new BigDecimal("100.00"));
        transaction1.setType(TransactionType.CREDIT);

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(new BigDecimal("50.00"));
        transaction2.setType(TransactionType.DEBIT);

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        when(transactionRepository.findByAccountNumberAndTimestampBetween(accountNumber, startDate, endDate))
                .thenReturn(transactions);

        FinancialSummary result = reportService.generateReport(startDate, endDate, accountNumber);

        assertEquals(new BigDecimal("100.00"), result.getTotalIncome());
        assertEquals(new BigDecimal("50.00"), result.getTotalExpenses());
        assertEquals(new BigDecimal("50.00"), result.getRemainingBalance());
    }
}
