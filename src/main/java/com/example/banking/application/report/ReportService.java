package com.example.banking.application.report;

import com.example.banking.application.transactionManagement.Transaction;
import com.example.banking.application.transactionManagement.TransactionRepository;
import com.example.banking.application.transactionManagement.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService implements ReportServiceInterface {

    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional(readOnly = true)
    @Override
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
