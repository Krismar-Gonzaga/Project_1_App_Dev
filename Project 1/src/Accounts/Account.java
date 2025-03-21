package Accounts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import Bank.Bank;

/**
 * Abstract Account class that serves as a base for different account types.
 * It includes personal details, basic account attributes, comparators, and transaction logging.
 */
public abstract class Account {

    // Core Attributes
    protected final Bank bank;
    protected final String accountNumber;
    protected final List<Transaction> transactions;

    // Owner Details
    protected final String ownerFname, ownerLname, ownerEmail; 
    protected final String pin; 

    /**
     * Constructor for an Account.
     *
     * @param bank          The bank associated with this account.
     * @param accountNumber The unique account number.
     * @param ownerFname    Owner's first name.
     * @param ownerLname    Owner's last name.
     * @param ownerEmail    Owner's email address.
     * @param pin           Security PIN (in real-world, store hashed PINs).
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

    // Getters

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

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    /**
     * Returns the full name of the account owner.
     *
     * @return Owner's full name.
     */
    public String getOwnerFullName() {
        return String.format("%s %s", ownerFname, ownerLname);
    }

    /**
     * Adds a new transaction log to this account.
     *
     * @param sourceAccount The source account number that triggered this transaction.
     * @param type          The type of transaction performed.
     * @param description   A brief description of the transaction.
     */
    public void addNewTransaction(String sourceAccount, Transaction.Transactions type, String description) {
        if (sourceAccount == null || type == null || description == null || description.isBlank()) {
            throw new IllegalArgumentException("Invalid transaction parameters.");
        }
        transactions.add(new Transaction(sourceAccount, type, description));
    }

    /**
     * Retrieves all transaction logs recorded for this account.
     *
     * @return A formatted string containing all transaction details.
     */
    public String getTransactionsInfo() {
        if (transactions.isEmpty()) {
            return "No transactions found for this account.";
        }
        StringBuilder transactionLog = new StringBuilder("Transaction History:\n");
        transactions.forEach(transaction -> transactionLog.append(transaction).append("\n"));
        return transactionLog.toString();
    }

    

    /**
     * Provides a string representation of the account details.
     *
     * @return Formatted account details.
     */
    @Override
    public String toString() {
        return String.format("Account {Owner='%s %s', Email='%s', Bank='%s', Account Number='%s', Transactions Count=%d}",
                ownerFname, ownerLname, ownerEmail, bank.getName(), accountNumber, transactions.size());
    }
}
