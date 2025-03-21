package Accounts;
import Main.*;

public class CreditAccountLauncher {

    private static CreditAccount loggedAccount;

    public static void creditAccountInit() throws IllegalAccountType {
        if (loggedAccount == null) {
            System.out.println("No account logged in.");
            return;
        }

        while (true) {
            Main.showMenuHeader("Credit Account Menu");
            Main.showMenu(41);
            Main.setOption();

            switch (Main.getOption()) {
                case 1 -> System.out.println(loggedAccount.getLoanStatement());
                case 2 -> creditPaymentProcess();
                case 3 -> creditRecompenseProcess();
                case 4 -> System.out.println(loggedAccount.getTransactionsInfo());
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void creditPaymentProcess() throws IllegalAccountType {
        Field<String, Integer> receiver = new Field<String, Integer>("Recipient Account Number", String.class, 5, new Field.StringFieldLengthValidator());
        receiver.setFieldValue("Enter Recipient Account number: ");
        String receiverAccNum = receiver.getFieldValue();

        Field<Double, Double> paymentAmount = new Field<Double, Double>("Payment Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        paymentAmount.setFieldValue("Enter Payment Amount ");
        double amount = paymentAmount.getFieldValue();

        Account recipientAccount = loggedAccount.getBank().getBankAccount(receiverAccNum);

        if (!(recipientAccount instanceof SavingsAccount)) {
            System.out.println("Account not Found!");
            return;
        }
        System.out.println(loggedAccount.pay(recipientAccount, amount)
                ? "Credit payment successful."
                : "Credit payment failed. Insufficient funds or invalid amount.");
    }

    public static void creditRecompenseProcess() {
        Field<Double, Double> recompenseAmount = new Field<Double, Double>("Recompense Amount", Double.class, 1.0, new Field.DoubleFieldValidator());
        recompenseAmount.setFieldValue("Enter recompense amount: ");
        double amount = recompenseAmount.getFieldValue();

        System.out.println(loggedAccount.recompense(amount)
                ? "Recompense successful."
                : "Recompense failed. Amount exceeds loan balance.");
    }

    public static void setLoggedAccount(CreditAccount account) {
        loggedAccount = account;
    }

    public static CreditAccount getLoggedAccount() {
        return loggedAccount;
    }
}
