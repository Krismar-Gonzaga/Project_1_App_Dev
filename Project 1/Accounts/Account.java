package Accounts;

import java.util.ArrayList;
import Banks.Bank;

// Abstract class Account
public abstract class Account {
    // Attributes
    private Bank BANK;
    private String ACCOUNTNUMBER;
    private ArrayList<Transaction> TRANSACTION;

    public Bank getBANK() {
        return BANK;
    }

    public String getACCOUNTNUMBER() {
        return ACCOUNTNUMBER;
    }


    // Constructor
    public Account(Bank BANK, String ACCOUNTNUMBER) {
        this.BANK = BANK;
        this.ACCOUNTNUMBER = ACCOUNTNUMBER;
        this.TRANSACTION = new ArrayList<>();
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
}
