package Banks;

import java.util.*;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;

public class BankLauncher {
    private static final ArrayList<Bank> BANKS = new ArrayList<>();
    private static Bank loggedBank = null;

    public static boolean isLogged() {
        return loggedBank != null;
    }

    public static void bankInit() {
        // Implement logic to initialize bank-related operations
                if (!isLogged()) {
            System.out.println("Please log in to a bank first.");
            return;
        }
        showBanksMenu();
    }

    private static void showAccounts() {
        if (!isLogged()) {
            System.out.println("Please log in to a bank first.");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.println("Select account type to show:");
        System.out.println("1. Credit Accounts");
        System.out.println("2. Savings Accounts");
        System.out.println("3. All Accounts");
        int choice = input.nextInt();
        switch (choice) {
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
        }
    }

    private static void newAccounts() {
        // Implement logic for creating new accounts
        if (!isLogged()) {
            System.out.println("Please log in to a bank first.");
            return;
        }
        Scanner input = new Scanner(System.in);
        System.out.println("Select account type to create:");
        System.out.println("1. Credit Account");
        System.out.println("2. Savings Account");
        int choice = input.nextInt();
        switch (choice) {
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
        Scanner input = new Scanner(System.in);
        System.out.print("Enter Bank Name: ");
        String name = input.nextLine();
        System.out.print("Enter Passcode: ");
        String passcode = input.nextLine();
        for (Bank bank : BANKS) {
            if (bank.toString().contains(name) && bank.toString().contains(passcode)) {
                setLogSession(bank);
                System.out.println("Logged in to bank: " + name);
                return;
            }
        }
        System.out.println("Invalid bank credentials.");
    }

    private static void setLogSession(Bank b) {
        loggedBank = b;
    }

    private static void logout() {
        loggedBank = null;
        System.out.println("Logged out successfully");
    }

    public static void createNewBank() {
        Scanner input = new Scanner(System.in);
        try {
            System.out.print("Enter Bank ID: ");
            int ID = input.nextInt();
            input.nextLine();
            System.out.print("Enter Bank Name: ");
            String name = input.nextLine();
            System.out.print("Enter Passcode: ");
            String passcode = input.nextLine();
            Bank newBank = new Bank(ID, name, passcode);
            addBank(newBank);
            System.out.println("New bank created successfully.");
        } 
        catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter correct values.");
        }
    }

    public static void showBanksMenu() {
        if (BANKS.isEmpty()) {
            System.out.println("No banks available.");
            return;
        }
        System.out.println("Registered Banks:");
        for (Bank bank : BANKS) {
            System.out.println(bank);
        }
    }

    private static void addBank(Bank b) {
        BANKS.add(b);
    }

    public static Bank getBank(Comparator<Bank> comparator, Bank bank) {
        return BANKS.stream().min(comparator).orElse(bank);
    }

    public static Account findAccount(String accountNum) {
        if (!isLogged()) {
            System.out.println("Please log in to a bank first.");
            return null;
        }
        return loggedBank.getBankAccount(loggedBank, accountNum);
    }

    public static int bankSize() {
        return BANKS.size();
    }
}
