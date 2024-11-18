package com.example.Banking.application.loanManagement;

import com.example.Banking.application.authentication.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loanType;
    private Double loanAmount;
    private int loanDurationInMonths;
    private Double interestRate;

    @Enumerated(EnumType.STRING)
    private LoanStatus loanStatus;

    @ManyToOne
    private User user;
}
