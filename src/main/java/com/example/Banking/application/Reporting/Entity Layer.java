@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String accountNumber;
    private Double balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    // Getters and Setters
}

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Double amount;
    private LocalDateTime transactionDate;
    
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    
    private String type;  // DEPOSIT, WITHDRAWAL, TRANSFER

    // Getters and Setters
}
