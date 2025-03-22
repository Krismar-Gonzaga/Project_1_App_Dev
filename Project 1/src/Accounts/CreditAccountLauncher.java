package Accounts;
import Main.*;

/**
 * The CreditAccountLauncher class manages the user interface and operations
 * for credit account functionality in the banking application.
 * It provides a dashboard for credit account holders to view loan information,
 * make payments, and perform recompense operations.
 */
public class CreditAccountLauncher {
    // Static reference to the currently logged-in credit account
    private static CreditAccount loggedAccount;
    
    /**
     * Initializes the credit account dashboard and manages user interactions.
     * Presents a menu of options for the user to choose from and routes to
     * appropriate methods based on selection.
     * 
     * @throws IllegalAccountType If an account type mismatch occurs during operations
     */
    public static void creditAccountInit() throws IllegalAccountType {
        // Check if a user is logged in
        if (loggedAccount == null) {
            System.out.println("No account logged in.");
            return;
        }
        
        // Main credit account dashboard loop
        while (true) {
            Main.showMenuHeader("Credit Account Menu");
            Main.showMenu(41);  // Display menu with ID 41 (Credit Account menu)
            Main.setOption();   // Get user input for menu selection
            
            switch (Main.getOption()) {
                case 1 -> 
                    // Display loan statement information
                    System.out.println(loggedAccount.getLoanStatement());
                case 2 -> 
                    // Handle credit payment process (paying another account)
                    creditPaymentProcess();
                case 3 -> 
                    // Handle credit recompense process (repaying the loan)
                    creditRecompenseProcess();
                case 4 -> 
                    // Display transaction history
                    System.out.println(loggedAccount.getTransactionsInfo());
                case 5 -> {
                    // Exit the credit account menu and return to previous menu
                    return;
                }
                default -> 
                    // Handle invalid input
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
    
    /**
     * Handles the credit payment process - using the credit account to pay
     * money to another account (typically a savings account).
     * Prompts for recipient account details and payment amount.
     * 
     * @throws IllegalAccountType If an account type mismatch occurs during the payment
     */
    public static void creditPaymentProcess() throws IllegalAccountType {
        // Get recipient account number with validation
        Field<String, Integer> receiver = new Field<String, Integer>("Recipient Account Number", String.class, 5, new Field.StringFieldLengthValidator());
        receiver.setFieldValue("Enter Recipient Account number: ");
        String receiverAccNum = receiver.getFieldValue();
        
        // Get payment amount with validation
        Field<Double, Double> paymentAmount = new Field<Double, Double>("Payment Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        paymentAmount.setFieldValue("Enter Payment Amount ");
        double amount = paymentAmount.getFieldValue();
        
        // Retrieve the recipient account from the same bank
        Account recipientAccount = loggedAccount.getBank().getBankAccount(receiverAccNum);
        
        // Verify recipient account is a savings account
        if (!(recipientAccount instanceof SavingsAccount)) {
            System.out.println("Account not Found!");
            return;
        }
        
        // Attempt the payment and display result
        System.out.println(loggedAccount.pay(recipientAccount, amount)
                ? "Credit payment successful."
                : "Credit payment failed. Insufficient funds or invalid amount.");
    }
    
    /**
     * Handles the credit recompense process - repaying a portion of the
     * credit loan balance. Prompts for recompense amount.
     */
    public static void creditRecompenseProcess() {
        // Get recompense amount with validation
        Field<Double, Double> recompenseAmount = new Field<Double, Double>("Recompense Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        recompenseAmount.setFieldValue("Enter recompense amount: ");
        double amount = recompenseAmount.getFieldValue();
        
        // Attempt the recompense and display result
        System.out.println(loggedAccount.recompense(amount)
                ? "Recompense successful."
                : "Recompense failed. Amount exceeds loan balance.");
    }
    
    /**
     * Sets the currently logged-in credit account.
     * Used when a user successfully logs into their credit account.
     * 
     * @param account The CreditAccount to be set as the active logged-in account
     */
    public static void setLoggedAccount(CreditAccount account) {
        loggedAccount = account;
    }
    
    /**
     * Returns the currently logged-in credit account.
     * 
     * @return The current active CreditAccount, or null if no user is logged in
     */
    public static CreditAccount getLoggedAccount() {
        return loggedAccount;
    }
    
    /**
     * Method for account login functionality.
     * Currently empty, likely intended for future implementation.
     */
    public void accountLogin() {
        // Method is empty - appears to be a placeholder for future implementation
    }
}
