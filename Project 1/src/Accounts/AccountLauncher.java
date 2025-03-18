package Accounts;

import Bank.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AccountLauncher {
    // Private attributes
    private Account loggedAccount;
    private Bank assocBank;

    public AccountLauncher(Bank bank, Account account) {
        this.loggedAccount = account;
        this.assocBank = bank;
    }

    // Private methods
    private boolean isLoggedIn() {
        return loggedAccount != null;
    }

    public void accountLogin() {
        // Check if already logged in
        Bank selectedBank = selectBank();
        if (selectedBank == null) {
            System.out.println("No bank selected. Please select a bank first.");
            return;
        }

        // Prompt for account credentials
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter AccountNumber: ");
        String accountNumber = scanner.next();

        // Check if the account exists in the selected bank
        Account account = selectedBank.getBankAccount(assocBank, accountNumber);
        if (account != null) {
            setLogSession(account);
            System.out.println("Login successful.");
        } else {
            System.out.println("Invalid account number or password.");
        }

    }

    private Bank selectBank() {

        int id;
        while (true) { 
            try {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter ID to select a Bank: ");
                id = scanner.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid ID.");
            }
           
        }

        for (Account account : this.assocBank.getBankAccounts()) {
            Bank bank = account.getBANK();
            if (bank.getID() == id) {
                return bank;
            }
        }

        return null;
    }

    public void setLogSession(Account account) {
        this.loggedAccount = account;
        System.out.println("Login successfuly for " + account.getACCOUNTNUMBER());
    }

    private void destroyLogSession() {
        System.out.println("Logout successfully for " + this.loggedAccount.getACCOUNTNUMBER());
        this.loggedAccount = null;
        
    }
    /**
     * Checks inputted credentials during account login.
     * 
     * @param accountNum - Account number.
     * @param pin - 4-digit pin.
     * @return Account object if it passes verification. Null if not.
     */
    public Account checkCredentials(String accountNum, String pin) {
        // Get the associated bank
        Bank bank = this.assocBank;
        
        // Check if the account exists in the bank and the PIN matches
        if (bank != null && bank.accountExists(bank, pin)) {
            // If verification passes, retrieve the account from the bank
            return bank.getBankAccount(bank, accountNum);
        } 
        
        // If verification fails, return null
        return null;
    }

    // Protected method
    protected Account getLoggedAccount() {
        return loggedAccount;
    }

}
