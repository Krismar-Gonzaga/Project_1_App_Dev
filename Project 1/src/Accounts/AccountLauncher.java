/**
 * The AccountLauncher class manages user authentication and operations for bank accounts.
 * It provides functionality for account login, validation, and session management.
 * This class serves as a controller for account-specific operations in the banking system.
 */
package Accounts;
import Bank.BankLauncher;
import Bank.Bank;
import Main.*;
import java.util.Optional;
public class AccountLauncher {
    /** Currently authenticated account. */
    private Account loggedAccount;
    
    /** Associated bank for account operations. */
    private Bank assocBank;
    
    /** Sets the bank associated with this account launcher instance. */
    public void setAssocBank(Bank assocBank) {
        this.assocBank = assocBank;
    }
    
    /**
     * Handles the account login process.
     * Prompts for account type, number, and PIN, then validates credentials.
     * Redirects to appropriate account launcher based on account type.
     */
    public void accountLogin() throws IllegalAccountType {
        // Verify bank association before proceeding
        if (assocBank == null) {
            System.out.println("Bank selection failed. Please try again.");
            return;
        }
        
        // Display account type selection menu
        Main.showMenuHeader("Select Account Type");
        Main.showMenu(Menu.AccountTypeSelection.menuIdx);
        Main.setOption();
        int accountTypeOption = Main.getOption();
        
        // Determine account type based on user selection
        Class<? extends Account> accountType;
        switch (accountTypeOption) {
            case 1:
                accountType = CreditAccount.class;
                break;
            case 2:
                accountType = SavingsAccount.class;
                break;
            default:
                System.out.println("Invalid option. Returning to main menu.");
                return;
            }
        
        // Get account number with validation
        Field<String, Integer> accountNum = new Field<String, Integer>("Account Number", String.class, 5, new Field.StringFieldLengthValidator());
        accountNum.setFieldValue("Enter Account Number: ");
        String accountNumber = accountNum.getFieldValue();
        
        // Get PIN with validation
        Field<String, Integer> accPin = new Field<String, Integer>("PIN", String.class, 4, new Field.StringFieldLengthValidator());
        accPin.setFieldValue("Enter 4-digit PIN: ");
        String pin = accPin.getFieldValue();
        
        // Retrieve and validate account
        Account account = assocBank.getBankAccount(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }
        
        // Verify account type matches selection
        if (!account.getClass().equals(accountType)) {
            System.out.println("Invalid account type.");
            return;
        }
        
        // Authenticate account credentials
        if (!checkCredentials(accountNumber, pin)) {
            System.out.println("Invalid credentials.");
            return;
        }
        
        // Establish account session
        setLogSession(account);
        System.out.println("Login successful. Welcome, " + loggedAccount.getOwnerFname() + "!");
        
        // Route to appropriate account type launcher
        if (loggedAccount instanceof SavingsAccount savingsAccount) {
            SavingsAccountLauncher.setLoggedAccount(savingsAccount);
            SavingsAccountLauncher.savingsAccountInit();
        } else if (loggedAccount instanceof CreditAccount creditAccount) {
            CreditAccountLauncher.setLoggedAccount(creditAccount);
            CreditAccountLauncher.creditAccountInit();
        }
        
        // End session after operations complete
        destroyLogSession();
    }
    
    /**
     * Allows user to select a bank from available banks.
     * Displays the list of banks and handles user selection.
     * @return Selected bank or null if selection fails
     */
    public static Bank selectBank() {
        // Check if banks exist
        if (BankLauncher.bankSize() == 0) {
            System.out.println("No banks are available. Please create a bank first.");
            return null;
        }
        
        // Display bank selection menu
        Main.showMenuHeader("Select a Bank");
        BankLauncher.showBanksMenu();
        Main.setOption();
        int bankIndex = Main.getOption();
        
        // Validate and return selected bank
        return (bankIndex > 0 && bankIndex <= BankLauncher.getBanks().size())
                ? BankLauncher.getBanks().get(bankIndex - 1)
                : null;
    }
    
    /**
     * Verifies account credentials against stored values.
     * @param accountNumber The account number to check
     * @param pin The PIN to validate
     * @return true if credentials are valid, false otherwise
     */
    private boolean checkCredentials(String accountNumber, String pin) {
        // Retrieve account and compare PIN
        Account account = assocBank.getBankAccount(accountNumber);
        if(account != null && account.getPin().equals(pin)){
            return true;
        }
        return false;
    }
    
    /**
     * Establishes a login session for the specified account.
     * @param account The account to set as the logged-in account
     */
    public void setLogSession(Account account) {
        this.loggedAccount = account;
    }
    
    /**
     * Ends the current account session.
     * Displays logout message and clears the logged account reference.
     */
    public void destroyLogSession() {
        System.out.println("Logging out of " + loggedAccount.getAccountNumber());
        loggedAccount = null;
    }
    
    /**
     * Checks if an account is currently logged in.
     * @return true if an account is authenticated, false otherwise
     */
    public boolean isLoggedIn() {
        return loggedAccount != null;
    }
}
