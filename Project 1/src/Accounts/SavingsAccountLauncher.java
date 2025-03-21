package Accounts;

import Bank.BankLauncher;
import Bank.Bank;
import Main.*;

/**
 * SavingsAccountLauncher handles user interactions for Savings Accounts,
 * allowing deposits, withdrawals, and fund transfers.
 */
public class SavingsAccountLauncher {

    private static SavingsAccount loggedAccount;

    /**
     * Initializes the Savings Account menu after login.
     */
    public static void savingsAccountInit() throws IllegalAccountType {
        if (loggedAccount == null) {
            System.out.println("No account logged in.");
            return;
        }

        boolean running = true;
        while (running) {
            Main.showMenuHeader("Savings Account Menu");
            Main.showMenu(51);
            Main.setOption();

            switch (Main.getOption()) {
                case 1 -> System.out.println(loggedAccount.getAccountBalanceStatement());
                case 2 -> depositProcess();
                case 3 -> withdrawProcess();
                case 4 -> fundTransfer();
                case 5 -> System.out.println(loggedAccount.getTransactionsInfo());
                case 6 -> running = false;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    /**
     * Handles the deposit process.
     */
    public static void depositProcess() {
        Field<Double, Double> amountField = new Field<Double, Double>("Deposit Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        amountField.setFieldValue("Enter deposit amount: ");
        double amount = amountField.getFieldValue();
        if (loggedAccount.depositFunds(amount)) {
            System.out.println("Deposit successful.");
        } else {
            System.out.println("Deposit failed. Amount exceeds limit or is invalid.");
        }
    }

    /**
     * Handles the withdrawal process.
     */
    public static void withdrawProcess() {
        Field<Double, Double> amountField = new Field<Double, Double>("Withdrawal Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        amountField.setFieldValue("Enter withdrawal amount: ");

        double amount = amountField.getFieldValue();
        if (loggedAccount.withdrawFunds(amount)) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Withdrawal failed. Insufficient balance or exceeds withdrawal limit.");
        }
    }

    /**
     * Handles the fund transfer process.
     */
    public static void fundTransfer() throws IllegalAccountType {
        if (loggedAccount == null) {
            System.out.println("No account logged in.");
            return;
        }

        // Get recipient account number
        Field<String, Integer> recipientField = new Field<>("Recipient Account Number", String.class, 5, new Field.StringFieldLengthValidator());
        recipientField.setFieldValue("Enter recipient account number: ");
        String recipientAccountNum = recipientField.getFieldValue();

        // Get transfer amount
        Field<Double, Double> amountField = new Field<>("Transfer Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        amountField.setFieldValue("Enter transfer amount: ");
        double amount = amountField.getFieldValue();

        // Search for recipient account in the same bank
        Account recipient = loggedAccount.getBank().getBankAccount(recipientAccountNum);
        
        if (recipient != null && recipient instanceof SavingsAccount) {
            if (loggedAccount.transfer(recipient, amount)) {
                System.out.println("✅ Transfer successful.");
            } else {
                System.out.println("❌ Transfer failed. Insufficient funds or limit exceeded.");
            }
            return;
        }
        
        // If not found internally, proceed with external transfer
        Field<Integer, Integer> recipientBankField = new Field<>("Recipient Bank ID", Integer.class, 1, new Field.IntegerFieldValidator());
        recipientBankField.setFieldValue("Enter recipient bank ID: ");
        int recipientBankId = recipientBankField.getFieldValue();

        Bank recipientBank = BankLauncher.getBanks().stream()
                .filter(bank -> bank.getBankId() == recipientBankId)
                .findFirst()
                .orElse(null);

        if (recipientBank == null) {
            System.out.println("❌ Recipient bank not found.");
            return;
        }

        recipient = recipientBank.getBankAccount(recipientAccountNum);

        if (!(recipient instanceof SavingsAccount)) {
            System.out.println("❌ Recipient account not found or is not a Savings Account.");
            return;
        }

        if (loggedAccount.transfer(recipientBank, recipient, amount)) {
            System.out.println("✅ External transfer successful. Processing fee of $" +
                    loggedAccount.getBank().getProcessingFee() + " applied.");
        } else {
            System.out.println("❌ Transfer failed. Insufficient funds or limit exceeded.");
        }
    }

    

    /**
     * Sets the currently logged-in Savings Account.
     *
     * @param account The logged-in SavingsAccount.
     */
    public static void setLoggedAccount(SavingsAccount account) {
        loggedAccount = account;
    }

    /**
     * Gets the currently logged-in Savings Account.
     *
     * @return The logged-in SavingsAccount.
     */
    public static SavingsAccount getLoggedAccount() {
        return loggedAccount;
    }
}
