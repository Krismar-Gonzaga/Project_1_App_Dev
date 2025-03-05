package Accounts;
import Banks.Bank;

/**
 * Abstract class representing an Account Launcher.
 * This class provides the basic structure for handling bank account logins.
 */
public abstract class AccountLauncher {
    /**
     * Bank reference associated with the launcher.
     */
    protected Bank bank;

    /**
     * Logged-in account.
     */
    protected Account loggedAccount;

    /**
     * Login status.
     */
    protected boolean isLogged;

    /**
     * Constructor for AccountLauncher.
     *
     * @param bank The bank associated with this launcher.
     */
    public AccountLauncher(Bank bank) {
        this.bank = bank;
        this.loggedAccount = null;
        this.isLogged = false;
    }

    /**
     * Retrieves the currently logged-in account.
     *
     * @return The logged-in account.
     */
    public abstract Account getLoggedAccount();

    /**
     * Sets the bank reference.
     *
     * @param bank The bank to be associated with this launcher.
     */
    public abstract void setBank(Bank bank);

    /**
     * Retrieves the bank reference.
     *
     * @return The associated bank.
     */
    public abstract Bank getBank();

    /**
     * Checks if the user is logged in.
     *
     * @return True if the user is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return loggedAccount != null;
    }

    /**
     * Sets the currently logged-in account.
     *
     * @param account The account to set as logged-in.
     */
    public void setLogSession(Account account) {
        this.loggedAccount = account;
        System.out.println("Login successful for " + account.getAccountNumber());
    }
    
    public abstract boolean checkCredentials(String accountNumber, String pin);
}



