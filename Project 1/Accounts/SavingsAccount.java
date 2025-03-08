package Accounts;
import Banks.*;

public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer {
    private double balance;

    // Constructor
    public SavingsAccount(Bank bank, String accountNumber, String ownerFName, String ownerLName, String ownerEmail, String pin, double balance) {
        super(bank, accountNumber, ownerFName, ownerLName, ownerEmail, pin);
        this.balance = balance;
    }

    // Get account balance statement
    public String getAccountBalanceStatement() {
        return "Current Balance: " + balance;
    }

    // Check if there is enough balance
    public boolean hasEnoughBalance(double amount) {
        return balance >= amount;
    }

    // Handle insufficient balance case
    private void insufficientBalance() {
        System.out.println("Insufficient balance!");
    }

    // Adjust account balance
    public void adjustAccountBalance(double amount) {
        if (amount > 0 && hasEnoughBalance(amount)) {
            balance -= amount;
        } else {
            insufficientBalance();
        }
    }

    // Implement withdrawal method
    
    public boolean withdrawal(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return false;
        }

        if (hasEnoughBalance(amount)) {
            balance -= amount;
            System.out.println("Withdrawal successful. New balance: " + balance);
            return true;
        } else {
            insufficientBalance();
            return false;
        }
    }

    // Implement cash deposit method
    @Override
    public boolean cashDeposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Deposit successful. New balance: " + balance);
            return true;
        } else {
            System.out.println("Invalid deposit amount.");
            return false;
        }
    }

    // Implement transfer between accounts
    @Override
    public boolean transfer(Account account, double amount) throws IllegalAccountType {
        if (!(account instanceof CreditAccount) && !(account instanceof CreditAccount)) {
            throw new IllegalAccountType("Invalid account type for transfer.");
        }

        if (!hasEnoughBalance(amount)) {
            insufficientBalance();
            return false;
        }

        this.withdrawal(amount);
        if (account instanceof SavingsAccount) {
            ((SavingsAccount) account).cashDeposit(amount);
        } 
        System.out.println("Transfer successful. New balance: " + balance);
        return true;
    }

    // Implement transfer to another bank account
    @Override
    public boolean transfer(Bank bank, Account account, double amount) throws IllegalAccountType {
        if (bank == null || account == null) {
            System.out.println("Invalid bank or account details.");
            return false;
        }

        return transfer(account, amount);
    }

    // toString method
    @Override
    public String toString() {
        return super.toString() + "\nBalance: " + balance;
    }
}