package Accounts;

import Bank.BankLauncher;
import Bank.Bank;
import Main.*;

public class SavingsAccountLauncher {

    private static SavingsAccount loggedAccount;

    public static void savingsAccountInit() throws IllegalAccountType {
        if (loggedAccount == null) {
            System.out.println("Error: No active session detected.");
            return;
        }

        while (true) {
            Main.showMenuHeader("Savings Account Dashboard");
            Main.showMenu(51);
            Main.setOption();

            int choice = Main.getOption();
            switch (choice) {
                case 1:
                    System.out.println("Account Balance: " + loggedAccount.getAccountBalanceStatement());
                    break;
                case 2:
                    depositProcess();
                    break;
                case 3:
                    withdrawProcess();
                    break;
                case 4:
                    fundTransfer();
                    break;
                case 5:
                    System.out.println("Transaction History:\n" + loggedAccount.getTransactionsInfo());
                    break;
                case 6:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid selection. Enter a valid option.");
            }
        }
    }

    private static void depositProcess() {
        System.out.print("Enter deposit amount: ");
        Field<Double, Double> deposit = new Field<Double, Double>("Deposit Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        deposit.setFieldValue("Enter Deposit Amount: ");

        double depositAmount = deposit.getFieldValue();
        boolean success = loggedAccount.cashDeposit(depositAmount);
        System.out.println(success ? "Deposit successful." : "Deposit failed. Verify amount and try again.");
    }

    private static void withdrawProcess() {
        System.out.print("Enter withdrawal amount: ");
        Field<Double, Double> withdrawal = new Field<Double, Double>("Withdrawal Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        withdrawal.setFieldValue("Enter Withdrawal Amount");

        double withdrawalAmount = withdrawal.getFieldValue();
        boolean success = loggedAccount.withdrawal(withdrawalAmount);
        System.out.println(success ? "Withdrawal successful." : "Withdrawal failed. Insufficient funds or limit exceeded.");
    }

    private static void fundTransfer() throws IllegalAccountType {
        if (loggedAccount == null) {
            System.out.println("No active account session.");
            return;
        }

        Main.showMenuHeader("Select Transfer Type");
        System.out.println("[1] Internal Transfer\n[2] External Transfer");
        Main.setOption();
        int transferType = Main.getOption();

        Field<String, Integer> recipientAccount = new Field<String, Integer>("Recipient Account", String.class, 5, new Field.StringFieldLengthValidator());
        recipientAccount.setFieldValue("Enter Recipient Account Number: ");
        String recipientAccountNum = recipientAccount.getFieldValue();

        Field<Double, Double> transferAmount = new Field<Double, Double>("Transfer Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        transferAmount.setFieldValue("Enter Transfer Amount:");
        double amount = transferAmount.getFieldValue();

        if (transferType == 1) {
            Account recipient = loggedAccount.getBank().getBankAccount(recipientAccountNum);

            if (!(recipient instanceof SavingsAccount)) {
                System.out.println("Error: Invalid recipient account.");
                return;
            }

            boolean success = loggedAccount.transfer(recipient, amount);
            System.out.println(success ? "Transfer successful." : "Transfer failed. Insufficient funds or exceeded limit.");
        } else if (transferType == 2) {
            Field<Integer, Integer> bankID = new Field<Integer, Integer>("Recipient Bank ID", Integer.class, 1, new Field.IntegerFieldValidator());
            bankID.setFieldValue("Enter recipient Bank ID");
            int bankId = bankID.getFieldValue();

            Bank recipientBank = BankLauncher.getBanks().stream()
                    .filter(bank -> bank.getId() == bankId)
                    .findFirst()
                    .orElse(null);

            if (recipientBank == null) {
                System.out.println("Error: Bank not found.");
                return;
            }

            Account recipient = recipientBank.getBankAccount(recipientAccountNum);
            if (!(recipient instanceof SavingsAccount)) {
                System.out.println("Error: Invalid recipient account.");
                return;
            }

            boolean success = loggedAccount.transfer(recipientBank, recipient, amount);
            System.out.println(success ? "Transfer completed with a processing fee of " + loggedAccount.getBank().getProcessingFee() : "Transfer unsuccessful. Verify details and retry.");
        } else {
            System.out.println("Invalid transfer option selected.");
        }
    }

    public static void setLoggedAccount(SavingsAccount account) {
        loggedAccount = account;
    }

    protected SavingsAccount getLoggedAccount() {
        return loggedAccount;
    }
}