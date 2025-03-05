package Accounts;
import Banks.Bank;


/**
 * Class for launching savings account-related operations.
 */
public class SavingsAccountLauncher extends AccountLauncher {
    /**
     * Constructor for SavingsAccountLauncher.
     *
     * @param bank The bank associated with this launcher.
     */
    public SavingsAccountLauncher(Bank bank) {
        super(bank);
    }

    /**
     * Handles the deposit process for a savings account.
     *
     * @param amount The amount to deposit.
     * @param account The account to receive the deposit.
     */
    public void depositProcess(double amount, Account account) {
        // Implementation pending
    }

    /**
     * Handles the withdrawal process for a savings account.
     *
     * @param amount The amount to withdraw.
     * @param account The account from which to withdraw.
     */
    public void withdrawProcess(double amount, Account account) {
        // Implementation pending
    }

    /**
     * Handles the fund transfer process between accounts.
     *
     * @param amount The amount to transfer.
     * @param fromAccount The account sending the funds.
     * @param toAccount The account receiving the funds.
     */
    public void fundTransferProcess(double amount, Account fromAccount, Account toAccount) {
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

