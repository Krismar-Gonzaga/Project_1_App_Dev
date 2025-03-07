package Accounts;
import Banks.*;



public class CreditAccountLauncher extends AccountLauncher {

    public CreditAccountLauncher(Bank bank, Account account) {
        super(bank, account);
    }
    
    public void creditAccountInit() {
       
    }


    public void creditRepaymentProcess() {
        // Implementation pending
    }
  
    public void creditRecompenseProcess(Bank bank) {

    }

  
    protected CreditAccount getLoggedAccount() {
       return null;
    }
}
