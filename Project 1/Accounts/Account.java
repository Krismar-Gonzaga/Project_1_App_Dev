package Accounts;

import java.util.ArrayList;
import Bank.Bank;

// Abstract class Account
public abstract class Account {
    // Attributes
    private Bank BANK;
    private String ACCOUNTNUMBER;
    private String OWNERFNAME;
    private String OWNERLNAME;
    private String OWNEREMAIL;
    private String pin;

    private ArrayList<Transaction> TRANSACTION;

    public Bank getBANK() {
        return BANK;
    }

    public String getACCOUNTNUMBER() {
        return ACCOUNTNUMBER;
    }
    public String getOwnerFname() {
        return OWNERFNAME;
    }

    public String getOwnerLname() {
        return OWNERLNAME;
    }

    public String getOwnerEmail() {
        return OWNEREMAIL;
    }

    public String getPin() {
        return pin;
    }


    // Constructor
    public Account(Bank BANK, String ACCOUNTNUMBER, String OWNERFNAME, String OWNERLNAME, String OWNEREMAIL, String pin) {
        this.BANK = BANK;
        this.ACCOUNTNUMBER = ACCOUNTNUMBER;
        this.OWNERFNAME = OWNERFNAME;
        this.OWNERLNAME = OWNERLNAME;
        this.OWNEREMAIL = OWNEREMAIL;
        this.pin = pin;
        this.TRANSACTION = new ArrayList<>();
    }

    // Method to get owner's full name
    public String getOwnerFullName() {
        return this.OWNERFNAME + " " + this.OWNERLNAME;
    }

    // Method to add a new transaction
    public void addNewTransaction(String accountNum, Transaction.Transactions type, String description) {
        this.TRANSACTION.add(new Transaction(accountNum, type, description));
    }

    // Method to get transaction info
    public String getTransactionsInfo() {
        StringBuilder info = new StringBuilder("Transactions:\n");
        for (Transaction t : this.TRANSACTION) {
            info.append(t.toString()).append("\n");
        }
        return info.toString();
    }

    // toString method
    @Override
    public String toString() {
        return "Account Number: " + this.ACCOUNTNUMBER + "\n" +
               "Owner: " + getOwnerFullName() + "\n" +
               "Email: " + this.OWNEREMAIL + "\n" +
               getTransactionsInfo();
    }
}
