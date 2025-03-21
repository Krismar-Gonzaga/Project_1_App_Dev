package Bank;

import Accounts.*;
import Main.*;

import java.util.ArrayList;
import java.util.Comparator;

public class BankLauncher {

    private final static ArrayList<Bank> banks = new ArrayList<>();
    private static Bank loggedBank;

    public static ArrayList<Bank> getBanks() {
        return banks;
    }

    public static boolean isLogged() {
        return loggedBank != null;
    }

    public static void bankInit() {
        while (isLogged()) {
            Main.showMenuHeader("Bank System");
            Main.showMenu(Menu.BankMenu.menuIdx);
            int choice = Main.getOption();  // Store the option in a variable to debug

            System.out.println("DEBUG: User selected option -> " + choice); // Debugging line

            switch (choice) {
                case 1 -> showAccounts();
                case 2 -> newAccounts();
                case 3 -> {
                    logout();
                    System.out.println("Exiting...");
                    return;
                }
                case -1 -> {
                    System.out.println("Invalid choice. Exiting to prevent infinite loop.");
                    logout();
                    return;
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
    }
    public static void showAccounts() {
        if (loggedBank == null) return;

        Main.showMenuHeader("Account Display");
        Main.showMenu(Menu.ShowAccounts.menuIdx);
        switch (Main.getOption()) {
            case 1 -> loggedBank.showAccounts(CreditAccount.class);
            case 2 -> loggedBank.showAccounts(SavingsAccount.class);
            case 3 -> loggedBank.showAccounts(null);
            default -> System.out.println("Invalid selection");
        }
    }

    public static void newAccounts() {
        if (loggedBank == null) return;

        Main.showMenuHeader("New Account Creation");
        Main.showMenu(Menu.AccountTypeSelection.menuIdx);
        switch (Main.getOption()) {
            case 1 -> System.out.println("Created: " + loggedBank.createNewCreditAccount());
            case 2 -> System.out.println("Created: " + loggedBank.createNewSavingsAccount());
            default -> System.out.println("Invalid choice");
        }
    }

    public static void bankLogin() {
        if (banks.isEmpty()) {
            System.out.println("No banks available");
            return;
        }

        showBanksMenu();
        String nameInput = Main.prompt("Bank Name: ", false).trim();
        Bank target = banks.stream()
                .filter(b -> b.getName().equalsIgnoreCase(nameInput))
                .findFirst()
                .orElse(null);

        if (target == null) {
            System.out.println("Bank not found: " + nameInput);
            return;
        }

        String codeInput = Main.prompt("Passcode: ", true).trim();
        if (!target.getPasscode().equals(codeInput)) {
            System.out.println("Wrong passcode");
            return;
        }

        loggedBank = target;
        System.out.println("Logged in: " + loggedBank.getName());
        bankInit();
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
        String bankName = bankNameField.getFieldValue();

        if (bankName.isEmpty()) {
            System.out.println("Bank name Required!");
            return;
        }

        Field<String, Integer> bankPasscodeField = new Field<String, Integer>("Bank Passcode", String.class, 4, new Field.StringFieldLengthValidator());
        bankPasscodeField.setFieldValue("Enter Bank Passcode: ");
        String bankPasscode = bankPasscodeField.getFieldValue();

        if (bankPasscode == null || bankPasscode.length() < 4) {
            System.out.println("PIN too short!");
            return;
        }

        System.out.println("Do you want to set custom deposit, withdrawal, and credit limits? (Y/N): ");
        String choice = Main.prompt("", true).trim().toUpperCase();

        Bank newBank;

        if (choice.equals("Y")) {
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

            newBank = new Bank(bankSize(), bankName, bankPasscode, depositLimitInput, withdrawLimitInput, creditLimitInput, processingFeeInput);
        } else {
            newBank = new Bank(bankSize(), bankName, bankPasscode);
        }

        addBank(newBank);
        System.out.println("Bank Created: " + newBank.getName());
    }

    public static void showBanksMenu() {
        if (banks.isEmpty()) {
            System.out.println("No banks exist");
            return;
        }

        System.out.println("\nRegistered Banks:");
        System.out.println("#   | Name                      | ID");

        for (int i = 0; i < banks.size(); i++) {
            Bank b = banks.get(i);
            System.out.printf("%-3d | %-25s | %d%n", i + 1, b.getName(), b.getId());
        }
    }

    public static void addBank(Bank b) {
        if (banks.contains(b)) {
            System.out.println("Bank already exists!");
            return;
        }
        banks.add(b);
        System.out.println("Bank added: " + b.getName());
    }

    public static Bank getBank(Comparator<Bank> bankComparator, Bank bank) {
        return banks.stream()
                .filter(b -> bankComparator.compare(b, bank) == 0)
                .findFirst()
                .orElse(null);
    }

    public static Account findAccount(String accountNum) {
        for (Bank bank : banks) {
            Account acc = bank.getBankAccount(accountNum);
            if (acc != null) {
                System.out.println("DEBUG: Found account " + accountNum);
                return acc;
            }
        }
        System.out.println("DEBUG: Account " + accountNum + " not found.");
        return null;
    }


    public static int bankSize() {
        return banks.size();
    }

}
