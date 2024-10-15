package com.example.Banking.application.loanManagement;

import com.example.Banking.application.authentication.User;
import com.example.Banking.application.authentication.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class LoanRepositoryTest {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private UserRepo userRepository; // Inject the UserRepository to save the User entity

    private User user;
    private Loan loan;

    @BeforeEach
    void setUp() {
        // Create and save a User entity
        user = new User();
        user.setUsername("testUser");
        user.setPassword("testPass");
        user.setEmail("test@example.com");
        userRepository.save(user); // Save the user to the database

        // Create a Loan entity
        loan = new Loan();
        loan.setLoanType("Home Loan");
        loan.setLoanAmount(50000.0);
        loan.setLoanDurationInMonths(60);
        loan.setInterestRate(3.5);
        loan.setLoanStatus(LoanStatus.PENDING);
        loan.setUser(user); // Set the persisted user
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
        loan2.setUser(user); // Set the same user

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
