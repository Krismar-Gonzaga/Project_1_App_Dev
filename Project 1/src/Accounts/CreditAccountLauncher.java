package Accounts;

import Bank.*;
import Main.*;

public class CreditAccountLauncher {

    private static CreditAccount loggedAccount;

    /**
     * Initializes the Credit Account menu after login.
     */
    public static void creditAccountInit() {
        if (loggedAccount == null) {
            System.out.println("No account logged in.");
            return;
        }

        while (true) {
            Main.showMenuHeader("Credit Account Menu");
            Main.showMenu(41);
            Main.setOption();

            int option = Main.getOption();
            switch (option) {
                case 1:
                    System.out.println(loggedAccount.getLoanStatement());
                    break;
                case 2:
                    processCreditPayment();
                    break;
                case 3:
                    processCreditRecompense();
                    break;
                case 4:
                    System.out.println(loggedAccount.getTransactionsInfo());
                    break;
                case 5:
                    System.out.println("Exiting Credit Account Menu.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Handles the credit payment process.
     */
    private static void processCreditPayment() {
        String recipientAccountNum = promptForString("Enter recipient Savings Account number: ", 5);
        double amount = promptForDouble("Enter payment amount: ");

        Bank recipientBank = loggedAccount.getBank();
        Account recipientAccount = recipientBank.getBankAccount(recipientAccountNum);

        if (!(recipientAccount instanceof SavingsAccount)) {
            System.out.println("Recipient account not found or is not a Savings Account.");
            return;
        }

        if (loggedAccount.pay(recipientAccount, amount)) {
            System.out.println("Credit payment successful.");
        } else {
            System.out.println("Credit payment failed. Insufficient funds or invalid amount.");
        }
    }

    /**
     * Handles the credit recompense process.
     */
    private static void processCreditRecompense() {
        double amount = promptForDouble("Enter recompense amount: ");

        if (loggedAccount.recompense(amount)) {
            System.out.println("Recompense successful.");
        } else {
            System.out.println("Recompense failed. Amount exceeds loan balance or is invalid.");
        }
    }

    /**
     * Prompts user for a string input with validation.
     *
     * @param message The message to display.
     * @param maxLength The maximum allowed length.
     * @return The validated string input.
     */
    private static String promptForString(String message, int maxLength) {
        Field<String, Integer> field = new Field<>("Input", String.class, maxLength, new Field.StringFieldLengthValidator());
        field.setFieldValue(message);
        return field.getFieldValue();
    }

    /**
     * Prompts user for a double input with validation.
     *
     * @param message The message to display.
     * @return The validated double input.
     */
    private static double promptForDouble(String message) {
        Field<Double, Double> field = new Field<>("Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        field.setFieldValue(message);
        return field.getFieldValue();
    }

    /**
     * Sets the currently logged-in Credit Account.
     *
     * @param account The logged-in CreditAccount.
     */
    public static void setLoggedAccount(CreditAccount account) {
        loggedAccount = account;
    }

    /**
     * Gets the Credit Account instance of the currently logged account.
     * @return The currently logged account.
     */
    public static CreditAccount getLoggedAccount() {
        return loggedAccount;
    }
}
