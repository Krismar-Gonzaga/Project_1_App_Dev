package Accounts;
import Bank.*;
import Main.Main;
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
//            System.out.println("Credit Account Initialized: " + acc.getACCOUNTNUMBER());

            System.out.println("\n--- Credit Account Dashboard ---");
            System.out.println("Account Number: " + acc.getACCOUNTNUMBER());
            System.out.println("Outstanding Loan: " + acc.getLoanStatement());

            boolean stayInMenu = true;

            while (stayInMenu){

                Main.showMenuHeader("Credit Account Menu");
                Main.showMenu(41,1);
                Main.setOption();

                switch(Main.getOption()){
                    case 1:
                        System.out.println(acc.getLoanStatement());
                        break;
                    case 2:
                        creditRepaymentProcess();
                        break;
                    case 3:
                        creditRecompenseProcess();
                        break;
                    case 4:
                        System.out.println(acc.getTransactionsInfo());
                        break;
                    case 5:
                        stayInMenu = false;
                        System.out.println("Logging out from Account...");
                        break;
                    default:
                        System.out.print("Invalid choice");

                }
            }
        } else{
            System.out.print("No credit account login");
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
