package Accounts;
import Banks.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class for launching savings account-related operations.
 */
public class SavingsAccountLauncher extends AccountLauncher {
    
    private Bank bank;
    Scanner scanner = new Scanner(System.in);
    /**
     * Constructor for SavingsAccountLauncher.
     *
     * @param bank The bank associated with this launcher.
     */

    public SavingsAccountLauncher(Bank bank, Account account) {
        super(bank, account);
        this.bank = bank;
       
    }
    

    /**
     *depositProcess() 
    A method that deals with the deposit process transaction.
     */

    
    private void depositProcess() {
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter amount to deposit: ");
        double amount;
        while (true) {
            try {
                amount = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.print("Invalid amount. Please enter a valid number: ");
                scanner.nextLine();
                continue;
            }
        }

        Account account = this.bank.getBankAccount(this.bank, accountNumber);
        if (account != null) {
            if (account instanceof SavingsAccount savingsAccount) {
                getLoggedAccount().cashDeposit(amount);
                System.out.println("Deposit successful.");
            } else {
                System.out.println("Invalid account type.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    /*
     * withdrawProcess() 
     * A method that deals 
     * with the withdrawal 
     * process transaction. 
     */
    private void withdrawProcess() {

        String accountNumber;
        while (true) {
            try {
                System.out.print("Enter account number: ");
                accountNumber = scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.print("Invalid account number. Please enter a valid number: ");
                scanner.nextLine();
                continue;
            }
        }

        System.out.print("Enter amount to withdraw: ");
        double amount;
        while (true) {
            try {
                amount = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.print("Invalid amount. Please enter a valid number: ");
                scanner.nextLine();
                continue;
            }
        }

        Account account = this.bank.getBankAccount(this.bank, accountNumber);
        if (account != null) {
            if (account instanceof SavingsAccount savingsAccount) {
                if (savingsAccount.hasEnoughBalance(amount)) {
                    getLoggedAccount().withdrawal(amount);
                    System.out.println("Withdrawal successful.");
                } else {
                    System.out.println("Insufficient funds.");
                }
            } else {
                System.out.println("Invalid account type.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    /*
     *fundTransferProcess() 
        A method that deals 
        with the fund 
        transfer process transaction. 
     */
    private void fundTransferProcess() {
        // Implementation pending
        
        System.out.println("Enter the account number to transfer to: ");
        String accountNumber = scanner.nextLine();
        System.out.println("Enter the amount to transfer: ");
        double amount = scanner.nextDouble();

        Account account = bank.getBankAccount(this.bank,accountNumber);
        if (account != null) {
            if (account instanceof SavingsAccount savingsAccount) {
                if (getLoggedAccount().hasEnoughBalance(amount)) {
                    try {
                        getLoggedAccount().transfer(savingsAccount, amount);
                    } catch (IllegalAccountType e) {
                        System.out.println("Transfer failed: " + e.getMessage());
                    }
                    System.out.println("Transfer successful.");
                } else {
                    System.out.println("Insufficient funds.");
                }
            } else {
                System.out.println("Invalid account type.");
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    /*
     * Get the Savings Account instance of the currently logged account.
     */

    
    public SavingsAccount getLoggedAccount() {
        
        for (Account acc : bank.getBankAccounts()) {
            if (acc instanceof SavingsAccount creditAccount) {
                if (creditAccount.getACCOUNTNUMBER().equals(acc.getACCOUNTNUMBER())) {
                    return creditAccount;
                }
            }
        }
        return null;
    }
    }
