package com.example.Banking.application.loanManagement;

import com.example.Banking.application.authentication.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class LoanRepositoryTest {

    @Autowired
    private LoanRepository loanRepository;

    @MockBean
    private User user;

    private Loan loan;

    @BeforeEach
    void setUp() {
        loan = new Loan();
        loan.setLoanType("Home Loan");
        loan.setLoanAmount(50000.0);
        loan.setLoanDurationInMonths(60);
        loan.setInterestRate(3.5);
        loan.setLoanStatus(LoanStatus.PENDING);
        loan.setUser(user);
    }

    @Test
    void testSaveLoan() {
        Loan savedLoan = loanRepository.save(loan);
        assertEquals(savedLoan.getLoanType(), loan.getLoanType());
        assertEquals(savedLoan.getLoanAmount(), loan.getLoanAmount());
    }

    @Test
    void testFindById() {
        Loan savedLoan = loanRepository.save(loan);
        Optional<Loan> foundLoan = loanRepository.findById(savedLoan.getId());
        assertTrue(foundLoan.isPresent());
        assertEquals(foundLoan.get().getId(), savedLoan.getId());
    }

    @Test
    void testFindAll() {
        Loan loan2 = new Loan();
        loan2.setLoanType("Personal Loan");
        loan2.setLoanAmount(10000.0);
        loan2.setLoanDurationInMonths(12);
        loan2.setInterestRate(5.0);
        loan2.setLoanStatus(LoanStatus.PENDING);
        loan2.setUser(user);

        loanRepository.saveAll(Arrays.asList(loan, loan2));
        List<Loan> loans = loanRepository.findAll();
        assertEquals(2, loans.size());
    }

    @Test
    void testDeleteLoan() {
        Loan savedLoan = loanRepository.save(loan);
        loanRepository.deleteById(savedLoan.getId());
        Optional<Loan> deletedLoan = loanRepository.findById(savedLoan.getId());
        assertTrue(deletedLoan.isEmpty());
    }
}
