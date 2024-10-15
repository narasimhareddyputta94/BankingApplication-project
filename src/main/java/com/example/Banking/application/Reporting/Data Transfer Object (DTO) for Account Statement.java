public class AccountStatement {

    private Account account;
    private List<Transaction> transactions;

    public AccountStatement(Account account, List<Transaction> transactions) {
        this.account = account;
        this.transactions = transactions;
    }

    public Account getAccount() {
        return account;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    // Additional methods for summary (e.g., total balance)
}