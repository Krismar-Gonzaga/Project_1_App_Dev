package Banks;
import Main.Main;
import Main.Menu;

import Accounts.*;

import java.util.*;

public class BankLauncher {
    private static final ArrayList<Bank> BANKS = new ArrayList<>();
    private static Bank loggedBank = null;

    public static boolean isLogged() {
        return loggedBank != null;
    }

    public static void bankInit() {
        // Implement logic to initialize bank-related operations
        while (true){

            if (isLogged()) {

            Main.showMenuHeader("Bank Operation");
            Main.showMenu(31,1);
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

    private static void showAccounts() {

//        Scanner input = new Scanner(System.in);
//        System.out.println("Select account type to show:");
//        System.out.println("1. Credit Accounts");
//        System.out.println("2. Savings Accounts");
//        System.out.println("3. All Accounts");
//        int choice = input.nextInt();
//        switch (choice) {
//            case 1:
//                loggedBank.showAccounts(CreditAccount.class);
//                break;
//            case 2:
//                loggedBank.showAccounts(SavingsAccount.class);
//                break;
//            case 3:
//                loggedBank.showAccounts(Account.class);
//                break;
//            default:
//                System.out.println("Invalid choice.");
        while (true){
            if (!isLogged()) {
                System.out.println("Please log in to a bank first.");
                break;
            }
            Main.showMenuHeader("Account Types");
            Main.showMenu(32,1);
            Main.setOption();

            Class<?> accountType = null;

            switch(Main.getOption()){
                case 1:
                    accountType = CreditAccount.class;
                    loggedBank.showAccounts(accountType);
                    break;
                case 2:
                    accountType = SavingsAccount.class;
                    loggedBank.showAccounts(accountType);
                    break;
                case 3:
                    accountType = Account.class;
                    loggedBank.showAccounts(accountType);
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
                }
            }
    }

    private static void newAccounts() {
        // Implement logic for creating new accounts
        if (!isLogged()) {
            System.out.println("Please log in to a bank first.");
            return;
        }
//        Scanner input = new Scanner(System.in);
//        System.out.println("Select account type to create:");
//        System.out.println("1. Credit Account");
//        System.out.println("2. Savings Account");
//        int choice = input.nextInt();
            Main.showMenuHeader("Account Type Selection");
            Main.showMenu(33,1);
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
        Scanner input = new Scanner(System.in);
        System.out.print("Enter Bank Name: ");
        String name = input.nextLine();
        System.out.print("Enter Passcode: ");
        String passcode = input.nextLine();
        for (Bank bank : BANKS) {
            //if(bank.toString().contains(name) && bank.toString().contains(passcode)) altered
            if (bank.getName().equals(name) && bank.getPasscode().equals(passcode)) {
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
        } catch (NumberFormatException e) {
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
        for (Bank b : BANKS) {
            if (comparator.compare(b, bank) == 0) {
                return b;
            }
        }
        return null;
    }

    public static Account findAccount(String accountNum) {
        for (Bank b : BANKS) {
            Account account = b.getBankAccount(b, accountNum);
            if (account != null) {
                return account;
            }
        }
        return null;
    }

    public static int bankSize() {
        return BANKS.size();
    }
}