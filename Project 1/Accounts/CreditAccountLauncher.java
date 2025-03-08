package Accounts;
import Banks.*;

public class CreditAccountLauncher extends AccountLauncher {
    public CreditAccountLauncher(Bank bank, Account account) {
        super(bank, account);
    }
    
    /**
     * Method that deals with all things about credit accounts. Mainly utilized for showing the main
     * menu after Credit Account users log in to the application.
     */
    public void creditAccountInit() {
        CreditAccount account = getLoggedAccount();
        if (account != null) {
            System.out.println("Credit Account Menu");
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Current Balance: " + account.getBalance());
            System.out.println("Available Credit: " + account.getAvailableCredit());
        } else {
            System.out.println("No credit account is currently logged in.");
        }
    }
    
    public void creditRepaymentProcess() {
        // Implementation pending
    }
  
    public void creditRecompenseProcess(Bank bank) {

    }

  
    protected CreditAccount getLoggedAccount() {
       Account account = super.getLoggedAccount();
        if (account instanceof CreditAccount) {
            return (CreditAccount) account;
        }
        return null;
    }
}
