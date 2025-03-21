package Main;

import Accounts.AccountLauncher;
import Accounts.IllegalAccountType;
import Bank.*;

import java.util.Scanner;


public class Main {

    private static final Scanner input = new Scanner(System.in);
    public static Field<Integer, Integer> option = new Field<Integer, Integer>("Option",
            Integer.class, -1, new Field.IntegerFieldValidator());
    public static void main(String[] args) throws IllegalAccountType {

        while (true)
        {
            showMenuHeader("Main Menu");
            showMenu(1);
            setOption();
            // Account Option
            if (getOption() == 1) {
                showMenuHeader("Account Login");
                showMenu(Menu.AccountLogin.menuIdx);
                setOption();

                if (getOption() == 1) {
                    Bank selectedBank = AccountLauncher.selectBank();

                    if (selectedBank == null) {
                        System.out.println("Invalid bank selection.");
                        return;
                    }

                    AccountLauncher accountLauncher = new AccountLauncher();
                    accountLauncher.setAssocBank(selectedBank);
                    accountLauncher.accountLogin();
                }
            }
            // Bank Option
            else if (getOption() == 2)
            {
                showMenuHeader("Bank Operations");
                showMenu(Menu.BankLogin.menuIdx);
                setOption();

                switch (getOption()) {
                    case 1 -> BankLauncher.bankLogin();
                    case 2 -> System.out.println("Exiting Bank Operations"); // BankLauncher.showBanksMenu();
                    default -> System.out.println("Invalid bank menu option.");
                }
            }
            // Create New Bank
            else if (getOption() == 3)
            {
                showMenuHeader("Create New Bank");
                BankLauncher.createNewBank();
            }
            else if (getOption() == 4)
            {
                System.out.println("Exiting. Thank you for banking!");
                System.exit(0);
            }
            else
            {
                System.out.println("Invalid option!");
            }
        }
    }

    private static void handleAccountLogin() {
        showMenuHeader("Account Login");
        showMenu(Menu.AccountLogin.menuIdx);
        setOption();

        if (getOption() == 1) {
            Bank selectedBank = AccountLauncher.selectBank();
            if (selectedBank == null) {
                System.out.println("Invalid bank selection.");
                return;
            }
            AccountLauncher accountLauncher = new AccountLauncher();
            accountLauncher.setAssocBank(selectedBank);
            try {
                accountLauncher.accountLogin();
            } catch (IllegalAccountType e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void handleBankOperations() {
        showMenuHeader("Bank Operations");
        showMenu(Menu.BankLogin.menuIdx);
        setOption();

        switch (getOption()) {
            case 1 -> BankLauncher.bankLogin();
            case 2 -> System.out.println("Exiting Bank Operations");
            default -> System.out.println("Invalid bank menu option.");
        }
    }

    private static void createNewBank() {
        showMenuHeader("Create New Bank");
        BankLauncher.createNewBank();
    }

    private static void exitApplication() {
        System.out.println("Exiting. Thank you for banking!");
        System.exit(0);
    }

    public static void showMenu(int menuIdx) {
        showMenu(menuIdx, 1);
    }

    public static void showMenu(int menuIdx, int inlineTexts) {
        String[] menu = Menu.getMenuOptions(menuIdx);
        if (menu == null) {
            System.out.println("Invalid menu index given!");
            return;
        }
        
        String format = "[%d] %-20s";
        for (int i = 0; i < menu.length; i++) {
            System.out.printf(format, i + 1, menu[i]);
            if ((i + 1) % inlineTexts == 0) {
                System.out.println();
            }
        }
    }

    public static String prompt(String phrase, boolean inlineInput) {
        System.out.print(phrase);
        return inlineInput ? input.next() : input.nextLine();
    }

    public static void setOption() {
        option.setFieldValue("Select an option: ");
    }

    public static int getOption() {
        return option.getFieldValue();
    }

    public static void showMenuHeader(String menuTitle) {
        System.out.printf("<---- %s ----->%n", menuTitle);
    }
}
