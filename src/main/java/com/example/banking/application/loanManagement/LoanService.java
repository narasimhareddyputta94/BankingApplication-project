package com.example.banking.application.loanManagement;

import com.example.banking.application.authentication.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Transactional
    public Loan createLoan(String loanType, Double loanAmount, int durationInMonths, Double interestRate, User user) {
        Loan loan = new Loan();
        loan.setLoanType(loanType);
        loan.setLoanAmount(loanAmount);
        loan.setLoanDurationInMonths(durationInMonths);
        loan.setInterestRate(interestRate);
        loan.setLoanStatus(LoanStatus.PENDING);
        loan.setUser(user);

        return loanRepository.save(loan);
    }

    @Transactional(readOnly = true)
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Loan> getLoanById(Long id) {
        return loanRepository.findById(id);
    }

    @Transactional
    public Loan updateLoanStatus(Long loanId, LoanStatus status) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));
        loan.setLoanStatus(status);
        return loanRepository.save(loan);
    }

    @Transactional
    public void deleteLoan(Long id) {
        loanRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Loan> getLoansByStatus(LoanStatus status) {
        return loanRepository.findByLoanStatus(status);
    }
}
