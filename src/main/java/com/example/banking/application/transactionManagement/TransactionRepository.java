package com.example.banking.application.transactionManagement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountNumberAndTimestampBetween(String accountNumber, LocalDateTime startDate, LocalDateTime endDate);
}
