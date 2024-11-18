package com.example.banking.application.loanManagement;

import com.example.banking.application.authentication.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByLoanStatus(LoanStatus status);
    List<Loan> findByUser(User user);
}

