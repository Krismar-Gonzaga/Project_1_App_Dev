/**
 * The BankLauncher class serves as the central controller for the banking application.
 * It manages bank creation, authentication, and provides access to account operations.
 */
package Bank;

import Accounts.*;
import Main.*;

import java.util.ArrayList;
import java.util.Comparator;

public class BankLauncher {

    /** Collection of all banks registered in the system. */
    private final static ArrayList<Bank> banks = new ArrayList<>();
    
    /** Reference to the currently authenticated bank. */
    private static Bank loggedBank;

    /** Returns the list of all registered banks. */
    public static ArrayList<Bank> getBanks() {
        return banks;
    }

    /** Checks if a bank is currently logged in. */
    public static boolean isLogged() {
        return loggedBank != null;
    }

    /** Main bank operations menu that controls flow based on user input. */
    public static void bankInit() {
        // Loop continues until user decides to exit
        while (true){
            // Check if a bank is logged in before showing operations
            if (isLogged()) {
                // Display menu options for bank operations
                Main.showMenuHeader("Bank Operation");
                Main.showMenu(31);
                Main.setOption();

                // Process user selection
                switch (Main.getOption()){
                    case 1:
                        showAccounts(); // View accounts option
                        break;
                    case 2:
                        newAccounts();  // Create new accounts option
                        break;
                    case 3:
                        logout();       // Logout option
                        break;
                    default:
                        System.out.println("Invalid input");
                }
            }
            else {
                // Cannot proceed without login
                System.out.println("Please log in to a bank first.");
                break;
            }
        }
    }
    
    /** Displays accounts filtered by type for the logged-in bank. */
    public static void showAccounts() {
        while (true){
            // Verify login status
            if (!isLogged()) {
                System.out.println("Please log in to a bank first.");
                break;
            }
            
            // Display account type selection menu
            Main.showMenuHeader("Account Types");
            Main.showMenu(32);
            Main.setOption();

            // Handle account type selection
            switch(Main.getOption()){
                case 1:
                    loggedBank.showAccounts(CreditAccount.class); // Show only credit accounts
                    break;
                case 2:
                    loggedBank.showAccounts(SavingsAccount.class); // Show only savings accounts
                    break;
                case 3:
                    loggedBank.showAccounts(Account.class); // Show all accounts
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    /** Creates new Credit or Savings accounts for the currently logged-in bank. */
    public static void newAccounts() {
        // Verify login status before proceeding
        if (!isLogged()) {
            System.out.println("Please log in to a bank first.");
            return;
        }

        // Display account creation menu
        Main.showMenuHeader("Account Type Selection");
        Main.showMenu(33);
        Main.setOption();

        // Process account type selection
        switch (Main.getOption()) {
            case 1:
                loggedBank.createNewCreditAccount(); // Create credit account
                break;
            case 2:
                loggedBank.createNewSavingsAccount(); // Create savings account
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    /** Authenticates a bank based on name and passcode credentials. */
    public static void bankLogin() {
        try{
            // Create field for bank name input with validation
            Field <String, String> nameField = new Field<String, String> (
                    "Bank Name",
                    String.class,
                    " ",
                    new Field.StringFieldValidator()
            );

            // Create field for passcode input with length validation
            Field <String, Integer> passcodeField = new Field<String, Integer> (
                    "Passcode",
                    String.class,
                    4,
                    new Field.StringFieldLengthValidator()
            );

            // Prompt user for input
            nameField.setFieldValue("Enter Bank Name: ", false);
            passcodeField.setFieldValue("Enter Passcode: ", false);

            // Get entered values
            String enteredName = nameField.getFieldValue();
            String enteredPasscode = passcodeField.getFieldValue();

            // Check credentials against all banks
            for (Bank bank: banks){
                if (bank.getName().equals(enteredName) && bank.getPasscode().equals(enteredPasscode)){
                    setLogSession(bank);
                    System.out.println("Logged in successfully.");
                    bankInit();
                    return;
                }
            }
            // No matching bank found
            System.out.println("Invalid bank credentials.");
        } catch (Exception e) {
            // Handle input errors
            System.out.println("Invalid input. Please enter correct values.");
        }
    }


    /** Sets the provided bank as the currently logged-in bank. */
    public static void setLogSession(Bank bank) {
        loggedBank = bank;
    }

    /** Logs out the currently authenticated bank and clears the session. */
    private static void logout() {
        // Display logout message if a bank is logged in
        if (loggedBank != null) {
            System.out.println("Logging out from " + loggedBank.getName());
        }
        // Clear the logged bank reference
        loggedBank = null;
    }
    
    /** Creates a new bank with user-provided details and adds it to the system. */
    public static void createNewBank() {
        // Get bank name with validation
        Field<String, String> bankNameField = new Field<String, String>("Bank Name", String.class, null, new Field.StringFieldValidator());
        bankNameField.setFieldValue("Enter Bank Name: ", false);
        String bankName = bankNameField.getFieldValue();

        // Validate bank name
        if (bankName.isEmpty()) {
            System.out.println("Bank name Required!");
            return;
        }

        // Get bank passcode with length validation
        Field<String, Integer> bankPasscodeField = new Field<String, Integer>("Bank Passcode", String.class, 4, new Field.StringFieldLengthValidator());
        bankPasscodeField.setFieldValue("Enter Bank Passcode: ");
        String bankPasscode = bankPasscodeField.getFieldValue();

        // Validate passcode length
        if (bankPasscode == null || bankPasscode.length() < 4) {
            System.out.println("PIN too short!");
            return;
        }

        // Get operational limits with validation
        Field<Double, Double> depositLimit = new Field<Double, Double>("Deposit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        depositLimit.setFieldValue("Enter Deposit Limit: ");
        double depositLimitInput = depositLimit.getFieldValue();

        Field<Double, Double> withdrawLimit = new Field<Double, Double>("Withdraw Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        withdrawLimit.setFieldValue("Enter Withdraw Limit: ");
        double withdrawLimitInput = withdrawLimit.getFieldValue();

        Field<Double, Double> creditLimit = new Field<Double, Double>("Credit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
        creditLimit.setFieldValue("Enter Credit Limit: ");
        double creditLimitInput = creditLimit.getFieldValue();

        Field<Double, Double> processingFee = new Field<Double, Double>("Processing Fee", Double.class, 0.0, new Field.DoubleFieldValidator());
        processingFee.setFieldValue("Enter Processing Fee: ");
        double processingFeeInput = processingFee.getFieldValue();

        // Create new bank with collected parameters
        Bank newBank = new Bank(bankSize(), bankName, bankPasscode, depositLimitInput, withdrawLimitInput, creditLimitInput, processingFeeInput);

        // Add bank to the system
        addBank(newBank);
        System.out.println("Bank Created: " + newBank.getName());
    }

    /** Displays a formatted list of all registered banks in tabular format. */
    public static void showBanksMenu() {
        // Check if banks exist
        if (banks.isEmpty()) {
            System.out.println("No banks exist");
            return;
        }

        // Display header for bank list
        System.out.println("\nRegistered Banks:");
        System.out.println("#   | Name                      | ID");

        // Display each bank with formatted output
        for (int i = 0; i < banks.size(); i++) {
            Bank b = banks.get(i);
            System.out.printf("%-3d | %-25s | %d%n", i + 1, b.getName(), b.getId());
        }
    }

    /** Adds a new bank to the system after checking for duplicates. */
    public static void addBank(Bank b) {
        // Check if bank already exists
        if (banks.contains(b)) {
            System.out.println("Bank already exists!");
            return;
        }
        // Add bank and confirm
        banks.add(b);
        System.out.println("Bank added: " + b.getName());
    }

    /** Retrieves a bank from the system using the specified comparator. */
    public static Bank getBank(Comparator<Bank> bankComparator, Bank bank) {
        // Search for bank using stream API and comparator
        return banks.stream()
                .filter(b -> bankComparator.compare(b, bank) == 0)
                .findFirst()
                .orElse(null);
    }

    /** Searches for an account by number across all banks in the system. */
    public static Account findAccount(String accountNum) {
        // Search through all banks
        for (Bank bank : banks) {
            Account acc = bank.getBankAccount(accountNum);
            if (acc != null) {
                // Account found
                System.out.println("Found account " + accountNum);
                return acc;
            }
        }
        // Account not found
        System.out.println("Account " + accountNum + " not found.");
        return null;
    }

    /** Returns the total number of banks currently registered in the system. */
    public static int bankSize() {
        return banks.size();
    }
}
