package com.example.Banking.application.report;


import java.time.LocalDateTime;

public interface ReportServiceInterface {
    FinancialSummary generateReport(LocalDateTime startDate, LocalDateTime endDate, String accountNumber);
}
