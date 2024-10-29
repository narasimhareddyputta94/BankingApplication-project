package com.example.Banking.application.transactionManagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private Transaction transaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAccountNumber("1234567890");
        transaction.setAmount(BigDecimal.valueOf(100.00));
        transaction.setStatus(TransactionStatus.SUCCESS);
        transaction.setType(TransactionType.CREDIT);
    }

    @Test
    public void testDeposit() throws Exception {
        when(transactionService.depositCash("1234567890", BigDecimal.valueOf(100.00), "test@example.com"))
                .thenReturn(transaction);

        mockMvc.perform(post("/api/transactions/deposit")
                        .param("accountNumber", "1234567890")
                        .param("amount", "100.00")
                        .param("email", "test@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andExpect(jsonPath("$.amount").value(100.00));
    }

    @Test
    public void testWithdraw() throws Exception {
        when(transactionService.withdrawCash("1234567890", BigDecimal.valueOf(50.00), "test@example.com"))
                .thenReturn(transaction);

        mockMvc.perform(post("/api/transactions/withdraw")
                        .param("accountNumber", "1234567890")
                        .param("amount", "50.00")
                        .param("email", "test@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andExpect(jsonPath("$.amount").value(50.00));
    }

    @Test
    public void testTransfer() throws Exception {
        when(transactionService.transferFunds("1234567890", "0987654321", BigDecimal.valueOf(100.00), "source@example.com", "target@example.com"))
                .thenReturn(transaction);

        mockMvc.perform(post("/api/transactions/transfer")
                        .param("sourceAccount", "1234567890")
                        .param("targetAccount", "0987654321")
                        .param("amount", "100.00")
                        .param("sourceEmail", "source@example.com")
                        .param("targetEmail", "target@example.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.accountNumber").value("1234567890"))
                .andExpect(jsonPath("$.amount").value(100.00));
    }
}
