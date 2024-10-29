package com.example.Banking.application.report;

import java.math.BigDecimal;

public class FinancialSummary {

    private BigDecimal totalIncome;
    private BigDecimal totalExpenses;
    private BigDecimal remainingBalance;

    public FinancialSummary(BigDecimal totalIncome, BigDecimal totalExpenses, BigDecimal remainingBalance) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.remainingBalance = remainingBalance;
    }

    @Override
    public String toString() {
        return "FinancialSummary{" +
                "totalIncome=" + totalIncome +
                ", totalExpenses=" + totalExpenses +
                ", remainingBalance=" + remainingBalance +
                '}';
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(BigDecimal totalIncome) {
        this.totalIncome = totalIncome;
    }

    public BigDecimal getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(BigDecimal totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public BigDecimal getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(BigDecimal remainingBalance) {
        this.remainingBalance = remainingBalance;
    }
}
