package com.example.Banking.application.transactionManagement;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * During my research on Lombok, I discovered several powerful features that greatly simplify code,
 * including annotations such as @NoArgsConstructor, @AllArgsConstructor, and @Builder.
 * These annotations streamline the creation of constructors and builder patterns, making it easier
 * to build and maintain classes with less boilerplate code.
 */

public class Transaction {
    private Long transactionId;
    private Long fromAccountId;
    private Long toAccountId;
    private Double amount;
    private TransactionType type;
    private LocalDateTime transactionDate;
    private String description;

    // Enum for transaction type
    public enum TransactionType {
        TRANSFER, DEPOSIT, WITHDRAWAL
    }
}

