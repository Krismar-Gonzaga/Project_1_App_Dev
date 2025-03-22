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
        // Implement logic to initialize bank-related operations
        while (true){

            if (isLogged()) {

                Main.showMenuHeader("Bank Operation");
                Main.showMenu(31);
                Main.setOption();

                switch (Main.getOption()){
                    case 1:
                        showAccounts();
                        break;
                    case 2:
                        newAccounts();
                        break;
                    case 3:
                        logout();
                        break;
                    default:
                        System.out.println("Invalid input");
                }
            }
            else {
                System.out.println("Please log in to a bank first.");
                break;
            }
        }
    }
    public static void showAccounts() {
        while (true){
            if (!isLogged()) {
                System.out.println("Please log in to a bank first.");
                break;
            }
            Main.showMenuHeader("Account Types");
            Main.showMenu(32);
            Main.setOption();

            Class<?> accountType = null;

            switch(Main.getOption()){
                case 1:
                    loggedBank.showAccounts(CreditAccount.class);
                    break;
                case 2:
                    loggedBank.showAccounts(SavingsAccount.class);
                    break;
                case 3:
                    loggedBank.showAccounts(Account.class);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
            }
        }
    }

    public static void newAccounts() {
        if (!isLogged()) {
            System.out.println("Please log in to a bank first.");
            return;
        }

        Main.showMenuHeader("Account Type Selection");
        Main.showMenu(33);
        Main.setOption();

        switch (Main.getOption()) {
            case 1:
                loggedBank.createNewCreditAccount();
                break;
            case 2:
                loggedBank.createNewSavingsAccount();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    public static void bankLogin() {
        try{
            Field <String, String> nameField = new Field<String, String> (
                    "Bank Name",
                    String.class,
                    " ",
                    new Field.StringFieldValidator()
            );

            Field <String, Integer> passcodeField = new Field<String, Integer> (
                    "Passcode",
                    String.class,
                    4,
                    new Field.StringFieldLengthValidator()
            );

            nameField.setFieldValue("Enter Bank Name: ", false);
            passcodeField.setFieldValue("Enter Passcode: ", false);

            String enteredName = nameField.getFieldValue();
            String enteredPasscode = passcodeField.getFieldValue();

            for (Bank bank: banks){
                if (bank.getName().equals(enteredName) && bank.getPasscode().equals(enteredPasscode)){
                    setLogSession(bank);
                    System.out.println("Logged in successfully.");
                    bankInit();
                    return;
                }
            }
            System.out.println("Invalid bank credentials.");
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter correct values.");
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

//        System.out.println("Do you want to set custom deposit, withdrawal, and credit limits? (Y/N): ");
//        String choice = Main.prompt("", true).trim().toUpperCase();
//
        Bank newBank;
//
//        if (choice.equals("Y")) {
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
//        } else {
//            newBank = new Bank(bankSize(), bankName, bankPasscode);
//        }

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
                System.out.println("Found account " + accountNum);
                return acc;
            }
        }
        System.out.println("Account " + accountNum + " not found.");
        return null;
    }


    public static int bankSize() {
        return banks.size();
    }

}
