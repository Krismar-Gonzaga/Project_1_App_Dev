package Accounts;

import java.util.ArrayList;
import Bank.Bank;


/**
 * The {@code Account} class represents a generic bank account.
 * It serves as a base class for different types of accounts (e.g., Savings, Credit).
 * This class is abstract, meaning it cannot be instantiated directly.
 */
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

    /**
     * Retrieves the list of transactions for this account.
     *
     * @return A new list containing all transactions associated with this account.
     */
    public ArrayList<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    /**
     * Constructs a new Account instance.
     * @param ownerEmail   The owner's email address.
     */
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

    /**
     * Retrieves the full name of the account owner.
     */
    public String getOwnerFullName()throws IllegalStateException{
        return this.ownerFname + " " + this.ownerLname;
    }

    /**
    * Adds a new transaction to the account's transaction history.
    *
    */
    public void addNewTransaction(String sourceAccount, Transaction.Transactions type, String description) {
        transactions.add(new Transaction(sourceAccount, type, description));
    }

    /**
     * Retrieves the transaction history of this account.
     */
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

    /**
     * Returns a string representation of the account, including owner details,
    * bank information, and the total number of transactions.
    */
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
