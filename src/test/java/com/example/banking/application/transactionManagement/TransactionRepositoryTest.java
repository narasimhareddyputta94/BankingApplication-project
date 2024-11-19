package com.example.banking.application.transactionManagement;

import com.example.banking.application.accountManagement.AccountCreation;
import com.example.banking.application.accountManagement.AccountCreation.AccountType;
import com.example.banking.application.accountManagement.AccountCreationRepo;
import com.example.banking.application.authentication.User;
import com.example.banking.application.authentication.UserRepo;
import com.example.banking.application.transactionManagement.Transaction;
import com.example.banking.application.transactionManagement.TransactionRepository;
import com.example.banking.application.transactionManagement.TransactionStatus;
import com.example.banking.application.transactionManagement.TransactionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TransactionRepositoryTest {


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private AccountCreationRepo accountCreationRepository;

    private User testUser;
    private AccountCreation testAccount;

    @BeforeEach
    public void setup() {
        testUser = new User();
        testUser.setUsername("testUser");
        testUser.setEmail("testUser@example.com");
        testUser.setPassword("securePassword");
        testUser.setBalance(1000L);
        testUser.setAccountType(AccountType.CHECKINGS); // Ensure account type is set
        userRepository.save(testUser);

        testAccount = AccountCreation.builder()
                .user(testUser)
                .accountType(AccountType.CHECKINGS)
                .balance(500L)
                .createOn(LocalDate.now())
                .accountNumber("123456789")
                .build();

        testAccount = accountCreationRepository.save(testAccount);

        Transaction transaction = new Transaction();
        transaction.setAccountNumber(testAccount.getAccountNumber());
        transaction.setAmount(new BigDecimal("100.00"));
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setType(TransactionType.DEBIT);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setUser(testUser);
        transactionRepository.save(transaction);
    }

    @Test
    public void testFindByAccountNumberAndTimestampBetween() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        LocalDateTime endDate = LocalDateTime.now().plusDays(1);

        List<Transaction> transactions = transactionRepository.findByAccountNumberAndTimestampBetween(
                testAccount.getAccountNumber(), startDate, endDate
        );

        assertThat(transactions).isNotEmpty();
        assertThat(transactions.get(0).getAccountNumber()).isEqualTo(testAccount.getAccountNumber());
        assertThat(transactions.get(0).getUser().getUsername()).isEqualTo(testUser.getUsername());
    }
}
