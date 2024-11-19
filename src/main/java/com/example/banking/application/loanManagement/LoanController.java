package com.example.banking.application.loanManagement;

import com.example.banking.application.authentication.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // Endpoint to create a new loan
    @PostMapping("/create")
    public ResponseEntity<Loan> createLoan(
        @RequestParam String loanType,
        @RequestParam Double loanAmount,
        @RequestParam int durationInMonths,
        @RequestParam Double interestRate,
        @RequestBody User user
    ) {
        Loan createdLoan = loanService.createLoan(loanType, loanAmount, durationInMonths, interestRate, user);
        return ResponseEntity.status(201).body(createdLoan);
    }

    // Endpoint to get all loans
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        List<Loan> loans = loanService.getAllLoans();
        return ResponseEntity.ok(loans);
    }

    // Endpoint to get a loan by its ID
    @GetMapping("/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        Optional<Loan> loan = loanService.getLoanById(id);
        return loan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to update loan status
    @PutMapping("/{id}/status")
    public ResponseEntity<Loan> updateLoanStatus(@PathVariable Long id, @RequestParam LoanStatus status) {
        Loan updatedLoan = loanService.updateLoanStatus(id, status);
        return ResponseEntity.ok(updatedLoan);
    }

    // Endpoint to delete a loan by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long id) {
        loanService.deleteLoan(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint to get loans by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Loan>> getLoansByStatus(@PathVariable LoanStatus status) {
        List<Loan> loans = loanService.getLoansByStatus(status);
        return ResponseEntity.ok(loans);
    }
}
