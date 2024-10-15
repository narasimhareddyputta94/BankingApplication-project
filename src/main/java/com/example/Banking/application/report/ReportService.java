package com.example.Banking.application.report;

import com.example.Banking.application.transactionManagement.Transaction;
import com.example.Banking.application.transactionManagement.TransactionRepository;
import com.example.Banking.application.transactionManagement.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    public FinancialSummary generateReport(LocalDateTime startDate, LocalDateTime endDate, String accountNumber) {
        List<Transaction> transactions = transactionRepository.findByAccountNumberAndTimestampBetween(accountNumber, startDate, endDate);

        BigDecimal totalIncome = BigDecimal.ZERO;
        BigDecimal totalExpenses = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            if (transaction.getType() == TransactionType.CREDIT) {
                totalIncome = totalIncome.add(transaction.getAmount());
            } else if (transaction.getType() == TransactionType.DEBIT) {
                totalExpenses = totalExpenses.add(transaction.getAmount());
            }
        }

        BigDecimal remainingBalance = totalIncome.subtract(totalExpenses);

        return new FinancialSummary(totalIncome, totalExpenses, remainingBalance);
    }
}
