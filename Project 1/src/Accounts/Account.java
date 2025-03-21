package Accounts;

import java.util.ArrayList;
import Bank.Bank;

public abstract class Account {
    protected final Bank bank;
    protected final String accountNumber;
    protected final ArrayList<Transaction> transactions;
    protected final String ownerFname, ownerLname, ownerEmail;
    protected final String pin;  

    public String getOwnerFname() {
        return ownerFname;
    }

    public String getOwnerLname() {
        return ownerLname;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getPin() {
        return pin;
    }

        public Bank getBank() {
        return bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public ArrayList<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public Account(Bank bank, String accountNumber, String pin, String ownerFname,
                   String ownerLname, String ownerEmail) {
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.ownerFname = ownerFname;
        this.ownerLname = ownerLname;
        this.ownerEmail = ownerEmail;
        this.transactions = new ArrayList<>();
    }

    public String getOwnerFullName()throws IllegalStateException{
        return this.ownerFname + " " + this.ownerLname;
    }

    public void addNewTransaction(String sourceAccount, Transaction.Transactions type, String description) {
        transactions.add(new Transaction(sourceAccount, type, description));
    }

    public String getTransactionsInfo() {
        if (transactions.isEmpty()) {
            return "No transactions found for this account.";
        }

        StringBuilder transactionLog = new StringBuilder("Transaction History:\n");
        for (Transaction transaction : transactions) {
            transactionLog.append(transaction.toString()).append("\n");
        }
        return transactionLog.toString();
    }

    @Override
    public String toString() {
        return "Account {" +
                "Owner='" + ownerFname + " " + ownerLname + '\'' +
                ", Email='" + ownerEmail + '\'' +
                ", Bank='" + bank.getName() + '\'' +
                ", Account Number='" + accountNumber + '\'' +
                ", Transactions Count=" + transactions.size() +
                '}';
    }
}
