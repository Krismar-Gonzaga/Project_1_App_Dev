package Bank;

import Accounts.*;
import Main.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

/**
 * BankLauncher manages bank-related operations, including login, account handling, and bank creation.
 */
public class BankLauncher {

    private static final ArrayList<Bank> banks = new ArrayList<>();
    private static Bank loggedBank;

    /**
     * Initializes the banking module, allowing users to log in or create a new bank.
     */
    public static void bankInit() {
        while (isLogged()) {
            Main.showMenuHeader("Banking System");
            Main.showMenu(Menu.BankMenu.menuIdx);
            Main.setOption();

            switch (Main.getOption()) {
                case 1 -> showAccounts();
                case 2 -> createNewAccount();
                case 3 -> logout();
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static boolean isLogged() {
        return loggedBank != null;
    }

    public static void addBank(Bank bank) {
        if (!banks.contains(bank)) {
            banks.add(bank);
            System.out.println("✅ Bank successfully added: " + bank.getName());
        } else {
            System.out.println("⚠ Bank already exists.");
        }
    }

    public static void bankLogin() {
        if (banks.isEmpty()) {
            System.out.println("No banks registered yet. Create a new bank first.");
            return;
        }

        showBanksMenu();
        String bankName = Main.prompt("Enter Bank Name: ", false).trim();

        Bank selectedBank = banks.stream()
                .filter(bank -> bank.getName().equalsIgnoreCase(bankName))
                .findFirst()
                .orElse(null);

        if (selectedBank == null) {
            System.out.println("❌ No bank found with the name \"" + bankName + "\".");
            return;
        }

        String passcode = Main.prompt("Enter Bank Passcode: ", true).trim();
        if (!selectedBank.getPasscode().equals(passcode)) {
            System.out.println("❌ Incorrect passcode. Access denied.");
            return;
        }

        setLogSession(selectedBank);
        System.out.println("✅ Successfully logged into " + loggedBank.getName());
        bankInit();
    }

    public static void showBanksMenu() {
        if (banks.isEmpty()) {
            System.out.println("No banks have been registered yet.");
            return;
        }
        System.out.println("\n📌 Registered Banks:");
        System.out.printf("%-3s | %-30s | %s%n", "#", "Bank Name", "Bank ID");
        System.out.println("-----------------------------------------------------");
        for (int i = 0; i < banks.size(); i++) {
            System.out.printf("%-3d | %-30s | %s%n", i + 1, banks.get(i).getName(), banks.get(i).getBankId());
        }
    }

    public static Bank getBank(Comparator<Bank> bankComparator, Bank bank) {
        return banks.stream()
                .filter(b -> bankComparator.compare(b, bank) == 0)
                .findFirst()
                .orElse(null);
    }

    public static void showAccounts() {
        if (loggedBank == null) {
            System.out.println("No bank logged in.");
            return;
        }
        Main.showMenuHeader("Show Accounts");
        Main.showMenu(Menu.ShowAccounts.menuIdx);
        Main.setOption();

        switch (Main.getOption()) {
            case 1 -> loggedBank.showAccounts(CreditAccount.class);
            case 2 -> loggedBank.showAccounts(SavingsAccount.class);
            case 3 -> loggedBank.showAccounts(null);
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    public static void createNewAccount() {
        if (loggedBank == null) {
            System.out.println("No bank logged in.");
            return;
        }
        Main.showMenuHeader("Create a New Account");
        Main.showMenu(Menu.AccountTypeSelection.menuIdx);
        Main.setOption();

        switch (Main.getOption()) {
            case 1 -> System.out.println("Credit Account created: " + loggedBank.createNewCreditAccount());
            case 2 -> System.out.println("Savings Account created: " + loggedBank.createNewSavingsAccount());
            default -> System.out.println("Invalid choice.");
        }
    }

    public static void setLogSession(Bank bank) {
        loggedBank = bank;
    }

    private static void logout() {
        if (loggedBank != null) {
            System.out.println("Logging out from " + loggedBank.getName());
        }
        loggedBank = null;
    }

    public static void createNewBank() {
        Field<String, String> bankNameField = new Field<String, String>("Bank Name", String.class, null, new Field.StringFieldValidator());
        bankNameField.setFieldValue("Enter Bank Name: ", false);

        if (bankNameField.getFieldValue().isEmpty()) {
            System.out.println("❌ Error: Bank Name is required!");
            return; // Exit early
        }

        Field<String, Integer> bankPasscodeField = new Field<String, Integer>("Bank Passcode", String.class, 4, new Field.StringFieldLengthValidator());
        bankPasscodeField.setFieldValue("Enter Bank Passcode: ");

        if (bankPasscodeField.getFieldValue() == null || bankPasscodeField.getFieldValue().length() < 4) {
            System.out.println("❌ Error: Passcode must be at least 4 characters long.");
            return; // Exit early
        }

        System.out.println("Do you want to set custom limits? (Y/N): ");
        boolean customLimits = Main.prompt("", true).trim().equalsIgnoreCase("Y");
        String choice = Main.prompt("", true).trim().toUpperCase();
        Bank newBank;

        if (choice.equals("Y")) {
            // Custom Limits Fields
            Field<Double, Double> depositLimitField = new Field<Double, Double>("Deposit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
            depositLimitField.setFieldValue("Enter Deposit Limit: ");

            Field<Double, Double> withdrawLimitField = new Field<Double, Double>("Withdraw Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
            withdrawLimitField.setFieldValue("Enter Withdraw Limit: ");

            Field<Double, Double> creditLimitField = new Field<Double, Double>("Credit Limit", Double.class, 0.0, new Field.DoubleFieldValidator());
            creditLimitField.setFieldValue("Enter Credit Limit: ");

            Field<Double, Double> processingFeeField = new Field<Double, Double>("Processing Fee", Double.class, 0.0, new Field.DoubleFieldValidator());
            processingFeeField.setFieldValue("Enter Processing Fee: ");

            // Create Bank with custom values
            newBank = new Bank(
                    bankSize(),
                    bankNameField.getFieldValue(),
                    bankPasscodeField.getFieldValue(),
                    depositLimitField.getFieldValue(),
                    withdrawLimitField.getFieldValue(),
                    creditLimitField.getFieldValue(),
                    processingFeeField.getFieldValue()
            );
        } else {
            // Create Bank with default values
            newBank = new Bank(
                    bankSize(),
                    bankNameField.getFieldValue(),
                    bankPasscodeField.getFieldValue()
            );
        }
    }

    public static int bankSize() {
        return banks.size();
    }

    public static Optional<Bank> getBankByIndex(int index) {
        return (index > 0 && index <= banks.size()) ? Optional.of(banks.get(index - 1)) : Optional.empty();
    }

    public static ArrayList<Bank> getBanks() {
        return banks;
    }

    public static Account findAccount(String accountNum) {
        return banks.stream()
                .map(bank -> bank.getBankAccount(accountNum))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }
}