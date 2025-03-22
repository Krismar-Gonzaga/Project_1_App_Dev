package Accounts;
import Bank.*;

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
//            this.addNewTransaction(
//                    this.accountNumber,
//                    Transaction.Transactions.Withdraw,
//                    "Withdrew: -" + amount
//            );
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
//            this.addNewTransaction(
//                    this.accountNumber,
//                    Transaction.Transactions.Deposit,
//                    "Deposit: +"+ amount
//            );
            return true;
        } else {
//            System.out.println("Invalid deposit amount.");
            insufficientBalance();
            return false;

        }
    }

    // Implement transfer between accounts
    @Override
    public boolean transfer(Account account, double amount) throws IllegalAccountType {
        if (!(account instanceof Deposit)) {//changed the code here
//            this.addNewTransaction(
//                    this.accountNumber,
//                    Transaction.Transactions.FundTransfer,
//                    "Failed Transfer: Invalid Account Type"
//            );
            throw new IllegalAccountType("Invalid account type for transfer.");
        }

        if (!hasEnoughBalance(amount)) {
            insufficientBalance();
            this.addNewTransaction(
                    this.accountNumber,
                    Transaction.Transactions.FundTransfer,
                    "Failed Transfer: Insufficient Balance"
            );
            return false;
        }

        this.withdrawal(amount);
        ((Deposit) account).cashDeposit(amount);
        System.out.println("Transfer successful. New balance: " + balance);

//        this.addNewTransaction(
//                this.accountNumber,
//                Transaction.Transactions.FundTransfer,
//                "Transferred: -" + amount
//        );

        return true;
    }

    // Implement transfer to another bank account
    @Override
    public boolean transfer(Bank bank, Account account, double amount) throws IllegalAccountType {
        if (bank == null || account == null) {
            System.out.println("Invalid bank or account details.");
            return false;
        }
        boolean success = transfer(account, amount);

        if(success){
            double fee = this.getBank().getProcessingFee();
            this.balance -= fee;
            System.out.println("Processing fee of " + fee + " deducted. New balance: " + balance);
            addNewTransaction(
                    this.accountNumber,
                    Transaction.Transactions.FundTransfer,
                    "Transferred: " + this.balance
            );
        }
        return success;
    }

    // toString method
    @Override
    public String toString() {
        return super.toString() + "\nBalance: " + balance;
    }

    public double getAccountBalance() {
        return balance;
    }
}