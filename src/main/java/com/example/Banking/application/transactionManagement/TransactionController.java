package com.example.Banking.application.transactionManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@RequestParam String accountNumber, @RequestParam BigDecimal amount) {
        Transaction transaction = transactionService.depositCash(accountNumber, amount);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestParam String accountNumber, @RequestParam BigDecimal amount) {
        Transaction transaction = transactionService.withdrawCash(accountNumber, amount);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestParam String sourceAccount, @RequestParam String targetAccount, @RequestParam BigDecimal amount) {
        Transaction transaction = transactionService.transferFunds(sourceAccount, targetAccount, amount);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        return transaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/account")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountAndDateRange(
            @RequestParam String accountNumber,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountAndDateRange(accountNumber, startDate, endDate);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
