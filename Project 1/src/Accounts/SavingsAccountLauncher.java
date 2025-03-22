package Accounts;

import Bank.BankLauncher;
import Bank.Bank;
import Main.*;

/**
 * The SavingsAccountLauncher class manages the user interface and operations
 * for savings account functionality in the banking application.
 * It provides a dashboard for account holders to perform various operations
 * on their savings accounts.
 */
public class SavingsAccountLauncher {

    // Static reference to the currently logged-in savings account
    private static SavingsAccount loggedAccount;

    /**
     * Initializes the savings account dashboard and manages user interactions.
     * Presents a menu of options for the user to choose from and routes to
     * appropriate methods based on selection.
     * 
     * @throws IllegalAccountType If an account type mismatch occurs during operations
     */
    public static void savingsAccountInit() throws IllegalAccountType {
        // Check if a user is logged in
        if (loggedAccount == null) {
            System.out.println("Error: No active session detected.");
            return;
        }

        // Main account dashboard loop
        while (true) {
            Main.showMenuHeader("Savings Account Dashboard");
            Main.showMenu(51);  // Display menu with ID 51 (Savings Account menu)
            Main.setOption();   // Get user input for menu selection

            int choice = Main.getOption();
            switch (choice) {
                case 1:
                    // Display current account balance
                    System.out.println("Account Balance: " + loggedAccount.getAccountBalanceStatement());
                    break;
                case 2:
                    // Handle deposit functionality
                    depositProcess();
                    break;
                case 3:
                    // Handle withdrawal functionality
                    withdrawProcess();
                    break;
                case 4:
                    // Handle fund transfer functionality
                    fundTransfer();
                    break;
                case 5:
                    // Display transaction history
                    System.out.println("Transaction History:\n" + loggedAccount.getTransactionsInfo());
                    break;
                case 6:
                    // Log out and return to previous menu
                    System.out.println("Logging out...");
                    return;
                default:
                    // Handle invalid input
                    System.out.println("Invalid selection. Enter a valid option.");
            }
        }
    }
    
    /**
     * Handles the deposit process for the logged-in savings account.
     * Prompts the user for a deposit amount and attempts to make the deposit.
     */
    public static void depositProcess() {
        System.out.print("Enter deposit amount: ");
        // Create a field for deposit amount with validation
        Field<Double, Double> deposit = new Field<Double, Double>("Deposit Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        deposit.setFieldValue("Enter Deposit Amount: ");

        // Get the validated deposit amount
        double depositAmount = deposit.getFieldValue();
        // Attempt to make the deposit and display result
        boolean success = loggedAccount.cashDeposit(depositAmount);
        System.out.println(success ? "Deposit successful." : "Deposit failed. Verify amount and try again.");
    }
    
    /**
     * Handles the withdrawal process for the logged-in savings account.
     * Prompts the user for a withdrawal amount and attempts to make the withdrawal.
     */
    public static void withdrawProcess() {
        System.out.print("Enter withdrawal amount: ");
        // Create a field for withdrawal amount with validation
        Field<Double, Double> withdrawal = new Field<Double, Double>("Withdrawal Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        withdrawal.setFieldValue("Enter Withdrawal Amount");

        // Get the validated withdrawal amount
        double withdrawalAmount = withdrawal.getFieldValue();
        // Attempt to make the withdrawal and display result
        boolean success = loggedAccount.withdrawal(withdrawalAmount);
        System.out.println(success ? "Withdrawal successful." : "Withdrawal failed. Insufficient funds or limit exceeded.");
    }
    
    /**
     * Handles the fund transfer process for the logged-in savings account.
     * Supports both internal transfers (within the same bank) and
     * external transfers (to other banks).
     * 
     * @throws IllegalAccountType If an account type mismatch occurs during the transfer
     */
    public static void fundTransfer() throws IllegalAccountType {
        // Check if a user is logged in
        if (loggedAccount == null) {
            System.out.println("No active account session.");
            return;
        }

        // Display transfer type selection menu
        Main.showMenuHeader("Select Transfer Type");
        System.out.println("[1] Internal Transfer\n[2] External Transfer");
        Main.setOption();
        int transferType = Main.getOption();

        // Get recipient account number with validation
        Field<String, Integer> recipientAccount = new Field<String, Integer>("Recipient Account", String.class, 5, new Field.StringFieldLengthValidator());
        recipientAccount.setFieldValue("Enter Recipient Account Number: ");
        String recipientAccountNum = recipientAccount.getFieldValue();

        // Get transfer amount with validation
        Field<Double, Double> transferAmount = new Field<Double, Double>("Transfer Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        transferAmount.setFieldValue("Enter Transfer Amount:");
        double amount = transferAmount.getFieldValue();

        if (transferType == 1) {
            // Internal transfer (within same bank)
            Account recipient = loggedAccount.getBank().getBankAccount(recipientAccountNum);

            // Verify recipient account is a savings account
            if (!(recipient instanceof SavingsAccount)) {
                System.out.println("Error: Invalid recipient account.");
                return;
            }

            // Attempt the transfer and display result
            boolean success = loggedAccount.transfer(recipient, amount);
            System.out.println(success ? "Transfer successful." : "Transfer failed. Insufficient funds or exceeded limit.");
        } else if (transferType == 2) {
            // External transfer (to another bank)
            
            // Get recipient bank ID with validation
            Field<Integer, Integer> bankID = new Field<Integer, Integer>("Recipient Bank ID", Integer.class, 1, new Field.IntegerFieldValidator());
            bankID.setFieldValue("Enter recipient Bank ID");
            int bankId = bankID.getFieldValue();

            // Find the recipient bank by ID
            Bank recipientBank = BankLauncher.getBanks().stream()
                    .filter(bank -> bank.getId() == bankId)
                    .findFirst()
                    .orElse(null);

            // Verify recipient bank exists
            if (recipientBank == null) {
                System.out.println("Error: Bank not found.");
                return;
            }

            // Get recipient account from the recipient bank
            Account recipient = recipientBank.getBankAccount(recipientAccountNum);
            
            // Verify recipient account is a savings account
            if (!(recipient instanceof SavingsAccount)) {
                System.out.println("Error: Invalid recipient account.");
                return;
            }

            // Attempt the external transfer and display result with fee information
            boolean success = loggedAccount.transfer(recipientBank, recipient, amount);
            System.out.println(success ? "Transfer completed with a processing fee of " + loggedAccount.getBank().getProcessingFee() : "Transfer unsuccessful. Verify details and retry.");
        } else {
            // Handle invalid transfer type selection
            System.out.println("Invalid transfer option selected.");
        }
    }
    
    /**
     * Sets the currently logged-in savings account.
     * Used when a user successfully logs into their savings account.
     * 
     * @param account The SavingsAccount to be set as the active logged-in account
     */
    public static void setLoggedAccount(SavingsAccount account) {
        loggedAccount = account;
    }
    
    /**
     * Returns the currently logged-in savings account.
     * 
     * @return The current active SavingsAccount, or null if no user is logged in
     */
    public SavingsAccount getLoggedAccount() {
        return loggedAccount;
    }
}
