package Bank;

import Accounts.Account;
import Accounts.CreditAccount;
import Accounts.SavingsAccount;
import Main.*;
import java.util.ArrayList;
import java.util.Comparator;

public class Bank {
    private String bankName, passcode;
    private final int bankId;
    private final ArrayList<Account> bankAccounts;
    private final double depositLimit, withdrawLimit, creditLimit, processingFee;

    public Bank(int bankId, String bankName, String passcode) {
        this(bankId, bankName, passcode, 50000.0, 50000.0, 100000.0, 10.0);
    }

    public Bank(int bankId, String bankName, String passcode, double depositLimit, double withdrawLimit, double creditLimit, double processingFee) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.passcode = passcode;
        this.bankAccounts = new ArrayList<>();
        this.depositLimit = depositLimit;
        this.withdrawLimit = withdrawLimit;
        this.creditLimit = creditLimit;
        this.processingFee = processingFee;
    }


    public String getName() {
        return bankName;
    }

    public int getBankId() {
        return bankId;
    }

    public String getPasscode() {
        return passcode;
    }

    public ArrayList<Account> getBankAccounts() {
        return new ArrayList<>(bankAccounts);
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

    public void showAccounts(Class<? extends Account> accountType) {
        bankAccounts.stream()
            .filter(account -> accountType == null || accountType.isInstance(account))
            .forEach(System.out::println);
    }

    public Account getBankAccount(String accountNum) {
        return bankAccounts.stream()
            .filter(account -> account.getAccountNumber().equals(accountNum))
            .findFirst()
            .orElse(null);
    }

    public ArrayList<Field<?, ?>> createNewAccount() {
        ArrayList<Field<?, ?>> accountFields = new ArrayList<>();
        accountFields.add(new Field<>("Account Number", String.class, 5, new Field.StringFieldLengthValidator()));
        accountFields.add(new Field<>("PIN", String.class, 3, new Field.StringFieldLengthValidator()));
        accountFields.add(new Field<>("First Name", String.class, null, new Field.StringFieldValidator()));
        accountFields.add(new Field<>("Last Name", String.class, null, new Field.StringFieldValidator()));
        accountFields.add(new Field<>("Email", String.class, null, new Field.StringFieldValidator()));
        
        accountFields.forEach(field -> field.setFieldValue("Enter " + field.getFieldName() + ": "));
        return accountFields;
    }

    public SavingsAccount createNewSavingsAccount() {
        ArrayList<Field<?, ?>> accountData = createNewAccount();
        double initialDeposit = Double.parseDouble(Main.prompt("Enter Initial Deposit: ", true));
        SavingsAccount newAccount = new SavingsAccount(this,
                (String) accountData.get(0).getFieldValue(),
                (String) accountData.get(1).getFieldValue(),
                (String) accountData.get(2).getFieldValue(),
                (String) accountData.get(3).getFieldValue(),
                (String) accountData.get(4).getFieldValue(),
                initialDeposit);
        addNewAccount(newAccount);
        return newAccount;
    }

    public CreditAccount createNewCreditAccount() {
        ArrayList<Field<?, ?>> accountData = createNewAccount();
        CreditAccount newAccount = new CreditAccount(this,
                (String) accountData.get(0).getFieldValue(),
                (String) accountData.get(1).getFieldValue(),
                (String) accountData.get(2).getFieldValue(),
                (String) accountData.get(3).getFieldValue(),
                (String) accountData.get(4).getFieldValue());
        addNewAccount(newAccount);
        return newAccount;
    }

    public void addNewAccount(Account account) {
        if (accountExists(this, account.getAccountNumber())) {
            System.out.println("Account number already exists in this bank! Registration failed.");
        } else {
            bankAccounts.add(account);
            System.out.println("✅ Account successfully registered.");
        }
    }

    public static boolean accountExists(Bank bank, String accountNum) {
        return bank != null && bank.getBankAccounts().stream()
            .anyMatch(account -> account.getAccountNumber().equals(accountNum));
    }

    

    @Override
    public String toString() {
        return "Bank{" + "Bank ID='" + bankId + '\'' +
                "Bank Name='" + bankName + '\'' +
                ", Accounts Registered=" + bankAccounts.size() + '}';
    }

    public static class BankCredentialsComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            int compareName = new BankComparator().compare(b1, b2);
            return compareName != 0 ? compareName : Integer.compare(b1.getBankId(), b2.getBankId());
        }
    }

    public static class BankIdComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            return Integer.compare(b1.getBankId(), b2.getBankId());
        }
    }

    public static class BankComparator implements Comparator<Bank> {
        @Override
        public int compare(Bank b1, Bank b2) {
            return b1.getName().compareTo(b2.getName());
        }
    }
}
