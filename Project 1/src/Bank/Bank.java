package Bank;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import Accounts.Transaction;
import Main.Field;
import java.util.ArrayList;
import java.util.Comparator;

public class Bank {
    private String bankName, passcode;
    private final int iD;
    private final ArrayList<Account> bankAccounts = new ArrayList<>();
    private final double depositLimit, withdrawLimit, creditLimit;
    private double processingFee;

    public ArrayList<Account> getBankAccounts() {
        return new ArrayList<>(bankAccounts);
    }
    public String getName() {
        return bankName;
    }

    public int getId() {
        return iD;
    }

    public String getPasscode() {
        return passcode;
    }

    public double getDepositLimit() {
        return depositLimit;
    }

    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public double getProcessingFee() {
        return processingFee;
    }

    public Bank(int iD, String bankName, String passcode) {
        this(iD, bankName, passcode, 50000.0, 50000.0, 100000.0, 10.0);
    }

    public Bank(int iD, String bankName, String passcode, double depositLimit, double withdrawLimit, double creditLimit, double processingFee) {
        this.iD = iD;
        this.bankName = bankName;
        this.passcode = passcode;
        this.depositLimit = depositLimit;
        this.withdrawLimit = withdrawLimit;
        this.creditLimit = creditLimit;
        this.processingFee = processingFee;
    }

    public void showAccounts(Class<? extends Account> accountType) {
        for (Account account : bankAccounts) {
            if (accountType == null || accountType.isInstance(account)) {
                System.out.println(account);
            }
        }
    }

    public Account getBankAccount(String accountNum) {
        return bankAccounts.stream().filter(acc -> acc.getAccountNumber().equals(accountNum)).findFirst().orElse(null);
    }

    private Account createNewAccount(Class<? extends Account> accountType) {
        Field<String, Integer> accountNumber = new Field<String, Integer>("Account Number", String.class, 5, new Field.StringFieldLengthValidator());
        accountNumber.setFieldValue("Enter Account Number: ");

        Field<String, Integer> pin = new Field<String, Integer>("PIN", String.class, 3, new Field.StringFieldLengthValidator());
        pin.setFieldValue("Enter PIN: ");

        Field<String, String> fname = new Field<String, String>("First Name", String.class, null, new Field.StringFieldValidator());
        fname.setFieldValue("Enter First Name: ");

        Field<String, String> lname = new Field<String, String>("Last Name", String.class, null, new Field.StringFieldValidator());
        lname.setFieldValue("Enter Last Name: ");

        Field<String, String> email = new Field<String, String>("Email", String.class, null, new Field.StringFieldValidator());
        email.setFieldValue("Enter Email: ");

        Account account;
        if (accountType.equals(SavingsAccount.class)) {
            Field<Double, Double> balance = new Field<Double, Double>("Initial Deposit", Double.class, 0.0, new Field.DoubleFieldValidator());
            balance.setFieldValue("Enter Initial Deposit: ");
            account = new SavingsAccount(this, accountNumber.getFieldValue(), pin.getFieldValue(), fname.getFieldValue(), lname.getFieldValue(), email.getFieldValue(), balance.getFieldValue());
            // Log the initial deposit transaction
            account.addNewTransaction(
                    account.getAccountNumber(),
                    Transaction.Transactions.Deposit,
                    "Initial Deposit: +" + balance.getFieldValue()
            );
        } else {
            account = new CreditAccount(this, accountNumber.getFieldValue(), pin.getFieldValue(), fname.getFieldValue(), lname.getFieldValue(), email.getFieldValue());
        }
        addNewAccount(account);
        return account;
    }

    public CreditAccount createNewCreditAccount() {
        return (CreditAccount) createNewAccount(CreditAccount.class);
    }

    public SavingsAccount createNewSavingsAccount() {
        return (SavingsAccount) createNewAccount(SavingsAccount.class);
    }

    public void addNewAccount(Account account) {
        if (bankAccounts.stream().noneMatch(a -> a.getAccountNumber().equals(account.getAccountNumber()))) {
            bankAccounts.add(account);
            System.out.println("Account registered successfully");
        } else {
            System.out.println("Account number already exists!");
        }
    }

    public static class BankCredentialsComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            BankComparator name = new BankComparator();
            int compareName = name.compare(b1, b2);

            if (compareName != 0) {
                return compareName;
            }

            return Integer.compare(b1.getId(), b2.getId());
        }
    }

    public static class BankIdComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            return Integer.compare(b1.getId(), b2.getId());
        }
    }

    public static class BankComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            return b1.getName().compareTo(b2.getName());
        }
    }

    public String toString() {
        String sb = "Bank [ID=" + getId() +
                ", name=" + getName() +
                ", passcode= " + getPasscode() +
                ", DEPOSITLIMIT=" + getDepositLimit() +
                ", WITHDRAWLIMIT=" + getDepositLimit() +
                ", CREDITLIMIT=" + getCreditLimit() +
                ", processingFee=" + getProcessingFee() +
                ", Number of accounts=" + bankAccounts.size() +
                "]";

        return sb;
    }
}
