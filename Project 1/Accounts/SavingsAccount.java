package Accounts;
import Banks.Bank;

public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer {
    private double balance;

    // Constructor
    public SavingsAccount(Bank bank, String accountNumber, String ownerFName, String ownerLName, String ownerEmail, String pin, double balance) {
        super(bank, accountNumber, ownerFName, ownerLName, ownerEmail, pin);
        this.balance = balance;
    }

    // Check if there is enough balance
    public boolean hasEnoughBalance(double amount) {
        return balance >= amount;
    }


    // Handle insufficient balance case
    private void insufficientBalance() {
        System.out.println("Insufficient balance!");
    }

    // To string method
    @Override
    public String toString() {
        return super.toString() + ", Balance: " + balance;
    }

    @Override
    public boolean cashDeposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Cash deposit successful. New balance: " + balance);
            return true;
        } else {
            System.out.println("Invalid deposit amount.");
            return false;
        }
    }


    @Override
    public boolean transfer(Bank bank, Account account, double amount) {
        return false;
    }

    @Override
    public boolean transfer(Account account, double amount) throws IllegalAccountType {
        if (!(account instanceof SavingsAccount) && !(account instanceof CreditAccount)) {
            throw new IllegalAccountType("Invalid account type for transfer.");
        }

        if (!hasEnoughBalance(amount)) {
            insufficientBalance();
        }
        return false;
    }
    }
