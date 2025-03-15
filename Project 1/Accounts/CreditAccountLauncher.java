package Accounts;
import Banks.*;
import java.util.Scanner;

public class CreditAccountLauncher extends AccountLauncher {
    public CreditAccountLauncher(Bank bank, Account account) {
        super(bank, account);
    }
    
    /**
     * Method that deals with all things about credit accounts. Mainly utilized for showing the main
     * menu after Credit Account users log in to the application.
     */
    public void creditAccountInit() {
        CreditAccount acc =getLoggedAccount();
        if (acc != null){
            System.out.println("Credit Account Initialized: " + acc.getACCOUNTNUMBER());
        }
    }
    
    public void creditRepaymentProcess() {
        // Implementation pending
        CreditAccount acc = getLoggedAccount();
        if (acc != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter target account number: ");
            String targetAccountNum = scanner.nextLine();
            System.out.print("Enter amount: ");
            double amount = scanner.nextDouble();

            Account targetAccount = BankLauncher.findAccount(targetAccountNum);
            if (targetAccount != null) {
                acc.pay(targetAccount, amount);
            } else {
                System.out.println("Target account not found.");
            }
        }
    }
  
    public void creditRecompenseProcess() {
        CreditAccount acc = getLoggedAccount();
        if(acc != null) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter repayment amount: ");
            double amount = scanner.nextDouble();
            acc.recompense(amount);
        }
    }

    @Override
    protected CreditAccount getLoggedAccount() {
       return (CreditAccount)   super.getLoggedAccount();
    }
}
