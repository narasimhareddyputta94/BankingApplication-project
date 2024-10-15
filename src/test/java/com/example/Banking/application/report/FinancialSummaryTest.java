package com.example.Banking.application.report;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class FinancialSummaryTest {

    @Test
    void testFinancialSummaryConstructorAndGetters() {
        // Arrange
        BigDecimal totalIncome = BigDecimal.valueOf(1000);
        BigDecimal totalExpenses = BigDecimal.valueOf(500);
        BigDecimal remainingBalance = BigDecimal.valueOf(500);

        // Act
        FinancialSummary summary = new FinancialSummary(totalIncome, totalExpenses, remainingBalance);

        // Assert
        assertEquals(totalIncome, summary.getTotalIncome());
        assertEquals(totalExpenses, summary.getTotalExpenses());
        assertEquals(remainingBalance, summary.getRemainingBalance());
    }

    @Test
    void testSetTotalIncome() {
        // Arrange
        FinancialSummary summary = new FinancialSummary(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        BigDecimal newTotalIncome = BigDecimal.valueOf(2000);

        // Act
        summary.setTotalIncome(newTotalIncome);

        // Assert
        assertEquals(newTotalIncome, summary.getTotalIncome());
    }

    @Test
    void testSetTotalExpenses() {
        // Arrange
        FinancialSummary summary = new FinancialSummary(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        BigDecimal newTotalExpenses = BigDecimal.valueOf(800);

        // Act
        summary.setTotalExpenses(newTotalExpenses);

        // Assert
        assertEquals(newTotalExpenses, summary.getTotalExpenses());
    }

    @Test
    void testSetRemainingBalance() {
        // Arrange
        FinancialSummary summary = new FinancialSummary(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        BigDecimal newRemainingBalance = BigDecimal.valueOf(1200);

        // Act
        summary.setRemainingBalance(newRemainingBalance);

        // Assert
        assertEquals(newRemainingBalance, summary.getRemainingBalance());
    }

    @Test
    void testFinancialSummaryToString() {
        // Arrange
        BigDecimal totalIncome = BigDecimal.valueOf(1500);
        BigDecimal totalExpenses = BigDecimal.valueOf(500);
        BigDecimal remainingBalance = BigDecimal.valueOf(1000);
        FinancialSummary summary = new FinancialSummary(totalIncome, totalExpenses, remainingBalance);

        // Act
        String summaryString = summary.toString();

        // Assert
        assertNotNull(summaryString);
        assertTrue(summaryString.contains("totalIncome=1500"));
        assertTrue(summaryString.contains("totalExpenses=500"));
        assertTrue(summaryString.contains("remainingBalance=1000"));
    }
}
