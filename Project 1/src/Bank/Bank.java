package Bank;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import Accounts.Transaction;
import Main.Field;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Represents a bank with multiple accounts, deposit limits, and processing fees.
 */
public class Bank {
    private String bankName, passcode;
    private final int iD;
    private final ArrayList<Account> bankAccounts = new ArrayList<>();
    private final double depositLimit, withdrawLimit, creditLimit;
    private double processingFee;

    // Getter methods for various bank attributes
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

    /**
     * Constructs a Bank object with default limits.
     */
    public Bank(int iD, String bankName, String passcode) {
        this(iD, bankName, passcode, 50000.0, 50000.0, 100000.0, 10.0);
    }

    /**
     * Constructs a Bank object with specified limits and processing fee.
     */
    public Bank(int iD, String bankName, String passcode, double depositLimit, double withdrawLimit, double creditLimit, double processingFee) {
        this.iD = iD;
        this.bankName = bankName;
        this.passcode = passcode;
        this.depositLimit = depositLimit;
        this.withdrawLimit = withdrawLimit;
        this.creditLimit = creditLimit;
        this.processingFee = processingFee;
    }

    /**
     * Displays all accounts of a specified type.
     */
    public void showAccounts(Class<? extends Account> accountType) {
        for (Account account : bankAccounts) {
            if (accountType == null || accountType.isInstance(account)) {
                System.out.println(account);
            }
        }
    }

    /**
     * Retrieves a bank account based on the account number.
     */
    public Account getBankAccount(String accountNum) {
        return bankAccounts.stream().filter(acc -> acc.getAccountNumber().equals(accountNum)).findFirst().orElse(null);
    }

    /**
     * Creates a new account of the specified type.
     */
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

    /**
     * Creates a new credit account.
     */
    public CreditAccount createNewCreditAccount() {
        return (CreditAccount) createNewAccount(CreditAccount.class);
    }

    /**
     * Creates a new savings account.
     */
    public SavingsAccount createNewSavingsAccount() {
        return (SavingsAccount) createNewAccount(SavingsAccount.class);
    }

    /**
     * Adds a new account to the bank if the account number is unique.
     */
    public void addNewAccount(Account account) {
        if (bankAccounts.stream().noneMatch(a -> a.getAccountNumber().equals(account.getAccountNumber()))) {
            bankAccounts.add(account);
            System.out.println("Account registered successfully");
        } else {
            System.out.println("Account number already exists!");
        }
    }

    // Comparator classes for sorting banks
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

    /**
     * Returns a string representation of the bank.
     */
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
