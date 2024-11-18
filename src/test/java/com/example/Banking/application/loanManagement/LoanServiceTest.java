
package com.example.Banking.application.loanManagement;
import java.util.List;

import com.example.Banking.application.authentication.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    private User testUser;
    private Loan testLoan;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User(); // Initialize as needed
        testLoan = new Loan();
        testLoan.setId(1L);
        testLoan.setUser(testUser);
        testLoan.setLoanStatus(LoanStatus.PENDING);
    }

    @Test
    void testCreateLoan() {
        when(loanRepository.save(any(Loan.class))).thenReturn(testLoan);

        Loan createdLoan = loanService.createLoan("Personal", 5000.0, 12, 5.0, testUser);

        assertNotNull(createdLoan);
        assertEquals(testLoan.getId(), createdLoan.getId());
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    void testGetAllLoans() {
        when(loanRepository.findAll()).thenReturn(Collections.singletonList(testLoan));

        List<Loan> loans = loanService.getAllLoans();

        assertNotNull(loans);
        assertEquals(1, loans.size());
        assertEquals(testLoan, loans.get(0));
    }

    @Test
    void testGetLoanById() {
        when(loanRepository.findById(testLoan.getId())).thenReturn(Optional.of(testLoan));

        Optional<Loan> loan = loanService.getLoanById(testLoan.getId());

        assertTrue(loan.isPresent());
        assertEquals(testLoan, loan.get());
    }

    @Test
    void testUpdateLoanStatus() {
        when(loanRepository.findById(testLoan.getId())).thenReturn(Optional.of(testLoan));
        when(loanRepository.save(any(Loan.class))).thenReturn(testLoan);

        Loan updatedLoan = loanService.updateLoanStatus(testLoan.getId(), LoanStatus.APPROVED);

        assertNotNull(updatedLoan);
        assertEquals(LoanStatus.APPROVED, updatedLoan.getLoanStatus());
        verify(loanRepository, times(1)).save(testLoan);
    }

    @Test
    void testDeleteLoan() {
        doNothing().when(loanRepository).deleteById(testLoan.getId());

        loanService.deleteLoan(testLoan.getId());

        verify(loanRepository, times(1)).deleteById(testLoan.getId());
    }

    @Test
    void testGetLoansByStatus() {
        when(loanRepository.findByLoanStatus(LoanStatus.PENDING)).thenReturn(Collections.singletonList(testLoan));

        List<Loan> loans = loanService.getLoansByStatus(LoanStatus.PENDING);

        assertNotNull(loans);
        assertEquals(1, loans.size());
        assertEquals(testLoan, loans.get(0));
    }
}
