package Accounts;
import Banks.*;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class for launching savings account-related operations.
 */
public class SavingsAccountLauncher extends AccountLauncher {
    private Bank bank;

    /**
     * Constructor for SavingsAccountLauncher.
     *
     * @param bank The bank associated with this launcher.
     */

    public SavingsAccountLauncher(Bank bank, Account account) {
        super(bank, account);
        this.bank = bank;
    }
    
    private void depositProcess() {
        Scanner scanner = new Scanner(System.in);

        int accountNumber;
        while (true) {
            try {
                System.out.print("Enter account number: ");
                accountNumber = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.print("Invalid account number.please enter valid number!");
                scanner.nextLine();
                continue;
            }
        }

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

        SavingsAccount account = bank.getSavingsAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Account not found.");
        }
    }

    private void withdrawProcess() {
        // Implementation pending
    }

    private void fundTransferProcess() {
        // Implementation pending
    }

    /*
     * Get the Savings Account instance of the currently logged account.
     */
    public SavingsAccount getLoggedAccount() {
        return null;
    }
