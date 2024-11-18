package com.example.Banking.application.loanManagement;

import com.example.banking.application.authentication.User;
import com.example.banking.application.loanManagement.Loan;
import com.example.banking.application.loanManagement.LoanStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoanTest {

    private Loan loan;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");

        loan = new Loan();
        loan.setLoanType("Car Loan");
        loan.setLoanAmount(20000.0);
        loan.setLoanDurationInMonths(48);
        loan.setInterestRate(4.2);
        loan.setLoanStatus(LoanStatus.PENDING);
        loan.setUser(user);
    }

    @Test
    void testLoanFields() {
        assertEquals("Car Loan", loan.getLoanType());
        assertEquals(20000.0, loan.getLoanAmount());
        assertEquals(48, loan.getLoanDurationInMonths());
        assertEquals(4.2, loan.getInterestRate());
        assertEquals(LoanStatus.PENDING, loan.getLoanStatus());
        assertEquals(user, loan.getUser());
    }
}
