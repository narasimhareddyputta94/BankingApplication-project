@SpringBootTest
public class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private TransactionRepository transactionRepository;

    @Test
    public void testGenerateAccountStatement() {
        // Mocking account and transaction data
        Account mockAccount = new Account();
        mockAccount.setAccountNumber("123456");
        mockAccount.setBalance(5000.00);

        Transaction mockTransaction = new Transaction();
        mockTransaction.setAmount(100.00);
        mockTransaction.setTransactionDate(LocalDateTime.now());
        mockTransaction.setType("DEPOSIT");

        Mockito.when(accountRepository.findByAccountNumber("123456"))
                .thenReturn(Optional.of(mockAccount));

        Mockito.when(transactionRepository.findByAccountIdAndTransactionDateBetween(
                Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class)))
                .thenReturn(Collections.singletonList(mockTransaction));

        // Call service method
        AccountStatement statement = reportService.generateAccountStatement(
                "123456", LocalDateTime.now().minusDays(30), LocalDateTime.now());

        // Assertions
        Assertions.assertNotNull(statement);
        Assertions.assertEquals(1, statement.getTransactions().size());
        Assertions.assertEquals("123456", statement.getAccount().getAccountNumber());
    }
}