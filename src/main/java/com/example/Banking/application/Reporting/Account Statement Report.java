@Service
public class ReportService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Generate Account Statement
    public AccountStatement generateAccountStatement(String accountNumber, LocalDateTime startDate, LocalDateTime endDate) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        List<Transaction> transactions = transactionRepository.findByAccountIdAndTransactionDateBetween(
                account.getId(), startDate, endDate);

        return new AccountStatement(account, transactions);
    }
}