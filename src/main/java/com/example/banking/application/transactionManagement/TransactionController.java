package com.example.banking.application.transactionManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(@RequestParam String accountNumber, @RequestParam BigDecimal amount, @RequestParam String email) {
        Transaction transaction = transactionService.depositCash(accountNumber, amount, email);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(@RequestParam String accountNumber, @RequestParam BigDecimal amount, @RequestParam String email) {
        Transaction transaction = transactionService.withdrawCash(accountNumber, amount, email);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> transfer(@RequestParam String sourceAccount, @RequestParam String targetAccount, @RequestParam BigDecimal amount,
                                                @RequestParam String sourceEmail, @RequestParam String targetEmail) {
        Transaction transaction = transactionService.transferFunds(sourceAccount, targetAccount, amount, sourceEmail, targetEmail);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }
}
