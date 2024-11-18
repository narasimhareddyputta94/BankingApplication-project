package com.example.Banking.application.loanManagement;

import com.example.banking.application.loanManagement.LoanStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoanStatusTest {

    @Test
    void testLoanStatusValues() {
        assertEquals(LoanStatus.PENDING, LoanStatus.valueOf("PENDING"));
        assertEquals(LoanStatus.APPROVED, LoanStatus.valueOf("APPROVED"));
        assertEquals(LoanStatus.REJECTED, LoanStatus.valueOf("REJECTED"));
    }

    @Test
    void testLoanStatusOrdinal() {
        assertEquals(0, LoanStatus.PENDING.ordinal());
        assertEquals(1, LoanStatus.APPROVED.ordinal());
        assertEquals(2, LoanStatus.REJECTED.ordinal());
    }
}
