package com.example.Banking.application.report;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FinancialSummaryTest {

    @Test
    void testFinancialSummaryConstructorAndGetters() {

        BigDecimal totalIncome = BigDecimal.valueOf(1000);
        BigDecimal totalExpenses = BigDecimal.valueOf(500);
        BigDecimal remainingBalance = BigDecimal.valueOf(500);


        FinancialSummary summary = new FinancialSummary(totalIncome, totalExpenses, remainingBalance);


        assertEquals(totalIncome, summary.getTotalIncome());
        assertEquals(totalExpenses, summary.getTotalExpenses());
        assertEquals(remainingBalance, summary.getRemainingBalance());
    }

    @Test
    void testSetTotalIncome() {

        FinancialSummary summary = new FinancialSummary(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        BigDecimal newTotalIncome = BigDecimal.valueOf(2000);


        summary.setTotalIncome(newTotalIncome);


        assertEquals(newTotalIncome, summary.getTotalIncome());
    }

    @Test
    void testSetTotalExpenses() {

        FinancialSummary summary = new FinancialSummary(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        BigDecimal newTotalExpenses = BigDecimal.valueOf(800);


        summary.setTotalExpenses(newTotalExpenses);


        assertEquals(newTotalExpenses, summary.getTotalExpenses());
    }

    @Test
    void testSetRemainingBalance() {

        FinancialSummary summary = new FinancialSummary(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        BigDecimal newRemainingBalance = BigDecimal.valueOf(1200);


        summary.setRemainingBalance(newRemainingBalance);


        assertEquals(newRemainingBalance, summary.getRemainingBalance());
    }

    @Test
    void testFinancialSummaryToString() {

        BigDecimal totalIncome = BigDecimal.valueOf(1500);
        BigDecimal totalExpenses = BigDecimal.valueOf(500);
        BigDecimal remainingBalance = BigDecimal.valueOf(1000);
        FinancialSummary summary = new FinancialSummary(totalIncome, totalExpenses, remainingBalance);


        String summaryString = summary.toString();


        assertNotNull(summaryString);
        assertTrue(summaryString.contains("totalIncome=1500"));
        assertTrue(summaryString.contains("totalExpenses=500"));
        assertTrue(summaryString.contains("remainingBalance=1000"));
    }
}
