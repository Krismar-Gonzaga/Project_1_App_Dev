package Accounts;
import Banks.*;


public class SavingsAccount extends Account {
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
        if (hasEnoughBalance(amount)) {
            if (this.balance < amount) {
                this.balance = 0.0;
            }else{
                this.balance -= amount;
            }
           
        } else {
            insufficientBalance();
        }
    }

    // toString method
    @Override
    public String toString() {
        return super.toString() + "\nBalance: " + balance;
    }
}
