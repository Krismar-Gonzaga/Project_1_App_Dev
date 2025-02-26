package Accounts;

public class CreditAccountLauncher extends AccountLauncher {
    /**
     * Constructor for CreditAccountLauncher.
     *
     * @param bank The bank associated with this launcher.
     */
    public CreditAccountLauncher(Bank bank) {
        super(bank);
    }


    /**
     * Handles the deposit process for a credit account.
     *
     * @param amount The amount to deposit.
     * @param account The credit account to receive the deposit.
     */
    public void depositProcess(double amount, Account account) {
        // Implementation pending
    }

    /**
     * Handles the withdrawal process for a credit account.
     *
     * @param amount The amount to withdraw.
     * @param account The credit account from which to withdraw.
     */
    public void withdrawProcess(double amount, Account account) {
        // Implementation pending
    }

    /**
     * Processes loan repayments for a credit account.
     *
     * @param amount The repayment amount.
     * @param account The credit account making the repayment.
     */
    public void creditRepaymentProcess(double amount, Account account) {
        // Implementation pending
    }
        @Override
    public Account getLoggedAccount() {
        return null;
    }

    @Override
    public void setBank(Bank bank) {

    }

    @Override
    public Bank getBank() {
        return null;
    }

    @Override
    public boolean isLogged() {
        return false;
    }

    @Override
    public void setAccount(Account account) {

    }

    @Override
    public boolean checkCredentials(String accountNumber, String pin) {
        return false;
    }
}
