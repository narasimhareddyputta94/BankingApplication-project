//package com.example.Banking.application.transactionManagement;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class TransactionServiceTest {
//
//    @Mock
//    private TransactionRepository transactionRepository;
//
//    @InjectMocks
//    private TransactionService transactionService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testCreateTransaction() {
//        // Arrange
//        String accountNumber = "1234567890";
//        BigDecimal amount = BigDecimal.valueOf(100);
//        TransactionType type = TransactionType.CREDIT; // Updated to CREDIT
//        Transaction transaction = new Transaction(1L, accountNumber, amount, TransactionStatus.SUCCESS, type, LocalDateTime.now());
//
//        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
//
//        // Act
//        Transaction result = transactionService.createTransaction(accountNumber, amount, type);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(accountNumber, result.getAccountNumber());
//        assertEquals(amount, result.getAmount());
//        assertEquals(TransactionStatus.SUCCESS, result.getStatus());
//        assertEquals(type, result.getType());
//    }
//
//    @Test
//    void testGetAllTransactions() {
//        // Arrange
//        Transaction transaction1 = new Transaction(1L, "123", BigDecimal.valueOf(100), TransactionStatus.SUCCESS, TransactionType.CREDIT, LocalDateTime.now());
//        Transaction transaction2 = new Transaction(2L, "456", BigDecimal.valueOf(200), TransactionStatus.SUCCESS, TransactionType.DEBIT, LocalDateTime.now());
//
//        when(transactionRepository.findAll()).thenReturn(Arrays.asList(transaction1, transaction2));
//
//        // Act
//        List<Transaction> transactions = transactionService.getAllTransactions();
//
//        // Assert
//        assertNotNull(transactions);
//        assertEquals(2, transactions.size());
//        assertEquals("123", transactions.get(0).getAccountNumber());
//        assertEquals("456", transactions.get(1).getAccountNumber());
//    }
//
//    @Test
//    void testGetTransactionById() {
//        // Arrange
//        Transaction transaction = new Transaction(1L, "123", BigDecimal.valueOf(100), TransactionStatus.SUCCESS, TransactionType.CREDIT, LocalDateTime.now());
//
//        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
//
//        // Act
//        Optional<Transaction> result = transactionService.getTransactionById(1L);
//
//        // Assert
//        assertTrue(result.isPresent());
//        assertEquals("123", result.get().getAccountNumber());
//    }
//
//    @Test
//    void testDeleteTransaction() {
//        // Act
//        transactionService.deleteTransaction(1L);
//
//        // Assert
//        verify(transactionRepository, times(1)).deleteById(1L);
//    }
//}
