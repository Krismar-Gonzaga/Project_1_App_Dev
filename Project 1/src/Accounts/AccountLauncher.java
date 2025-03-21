package Accounts;

import Bank.BankLauncher;
import Bank.Bank;
import Main.*;
import java.util.Optional;

/**
 * AccountLauncher handles user login and navigation to account-specific menus.
 */
public class AccountLauncher {

    private Account loggedAccount;
    private Bank assocBank;

    public void setAssocBank(Bank assocBank) {
        this.assocBank = assocBank;
    }

    /**
     * Initializes the account login process.
     */
    public void accountLogin() throws IllegalAccountType {
        if (assocBank == null) {
            System.out.println("Bank selection failed. Please try again.");
            return;
        }

        Main.showMenuHeader("Select Account Type");
        Main.showMenu(Menu.AccountTypeSelection.menuIdx);
        Main.setOption();

        int accountTypeOption = Main.getOption();
        Class<? extends Account> accountType = switch (accountTypeOption) {
            case 1 -> CreditAccount.class;
            case 2 -> SavingsAccount.class;
            default -> {
                System.out.println("Invalid option. Returning to main menu.");
                yield null;
            }
        };

        if (accountType == null) return;

        // Prompt user for account number and PIN
        Field<String, Integer> accountField = new Field<String, Integer>("Account Number", String.class, 5, new Field.StringFieldLengthValidator());
        accountField.setFieldValue("Enter Account Number: ");

        Field<String, Integer> pinField = new Field<String, Integer>("4-digit PIN", String.class, 3, new Field.StringFieldLengthValidator());
        pinField.setFieldValue("Enter 4-digit PIN: ");

        String accountNumber = accountField.getFieldValue();
        String pin = pinField.getFieldValue();

        // Retrieve and validate account
        Optional<Account> accountOpt = Optional.ofNullable(assocBank.getBankAccount(accountNumber));

        if (accountOpt.isEmpty()) {
            System.out.println("Account not found. Please try again.");
            return;
        }

        Account account = accountOpt.get();
        if (!account.getClass().equals(accountType)) {
            System.out.println("Invalid account type. Please select the correct type.");
            return;
        }

        if (!account.getPin().equals(pin)) {
            System.out.println("Invalid credentials. Login failed.");
            return;
        }

        // Log in the user
        setLogSession(account);
        System.out.println("Login successful. Welcome, " + loggedAccount.getOwnerFname() + "!");

        if (loggedAccount instanceof SavingsAccount) {
            SavingsAccountLauncher.setLoggedAccount((SavingsAccount) loggedAccount);
            SavingsAccountLauncher.savingsAccountInit();
        } else if (loggedAccount instanceof CreditAccount) {
            CreditAccountLauncher.setLoggedAccount((CreditAccount) loggedAccount);
            CreditAccountLauncher.creditAccountInit();
        }

        destroyLogSession();
    }

    /**
     * Allows the user to select a bank before logging into an account.
     *
     * @return The selected Bank instance.
     */
    public static Bank selectBank() {
        if (BankLauncher.bankSize() == 0) {
            System.out.println("No banks are available. Please create a bank first.");
            return null;
        }

        Main.showMenuHeader("Select a Bank");
        BankLauncher.showBanksMenu();
        Main.setOption();

        return BankLauncher.getBankByIndex(Main.getOption()).orElse(null);
    }

    /**
     * Creates a session for the logged-in account.
     *
     * @param account The account that successfully logged in.
     */
    private void setLogSession(Account account) {
        this.loggedAccount = account;
    }

    /**
     * Destroys the current account session.
     */
    private void destroyLogSession() {
        System.out.println("Logging out of " + loggedAccount.getAccountNumber());
        loggedAccount = null;
    }

    /**
     * Checks if an account is currently logged in.
     *
     * @return True if an account is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return loggedAccount != null;
    }

    
}
