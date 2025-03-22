package Accounts;
import Bank.*;

/**
 * The {@code SavingsAccount} class represents a savings account in a bank.
 * It provides functionalities for deposits, withdrawals, and fund transfers.
 */
public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer {
    /** The current balance of the savings account. */
    private double balance;

    /**
     * Constructs a new {@code SavingsAccount} instance.
     */
    public SavingsAccount(Bank bank, String accountNumber, String ownerFName, String ownerLName, String ownerEmail, String pin, double balance) {
        super(bank, accountNumber, ownerFName, ownerLName, ownerEmail, pin);
        this.balance = balance;
    }

    /**
     * Retrieves the current account balance statement.
     * @return A string representing the current balance.
     */
    public String getAccountBalanceStatement() {
        return "Current Balance: " + balance;
    }

    /**
     * Checks if the account has sufficient balance for a transaction.
     *
     * @param amount The amount to be checked.
     * @return {@code true} if there is enough balance, otherwise {@code false}.
     */
    public boolean hasEnoughBalance(double amount) {
        return balance >= amount;
    }

    /**
     * Handles the case where a transaction cannot be completed due to insufficient balance.
     */
    private void insufficientBalance() {
        System.out.println("Insufficient balance!");
    }

    /**
     * Adjusts the account balance by the specified amount.
     * @param amount The amount to adjust the balance by.
     */
    public void adjustAccountBalance(double amount) {
        if (amount > 0 && hasEnoughBalance(amount)) {
            balance -= amount;
        } else {
            insufficientBalance();
        }
    }

    /**
     * Withdraws the specified amount from the account.
     *
     * @param amount The amount to withdraw.
     * @return {@code true} if the withdrawal was successful, otherwise {@code false}.
     */
    public boolean withdrawal(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return false;
        }

        if (hasEnoughBalance(amount)) {
            adjustAccountBalance(amount);
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

    /**
     * Deposits the specified cash amount into the account.
     *
     * @param amount The amount to deposit.
     * @return {@code true} if the deposit was successful, otherwise {@code false}.
     */
    @Override
    public boolean cashDeposit(double amount) {
        if (amount > 0) {
            if (amount > this.getBank().getDepositLimit()){
                System.out.println("Deposit Failed: Amount exceeds bank limit.");
                return false;
            }
            this.balance += amount;
            System.out.println("Deposit successful. New balance: " + balance);
//            this.addNewTransaction(
//                    this.accountNumber,
//                    Transaction.Transactions.Deposit,
//                    "Deposit: +"+ amount
//            );
            return true;
        } else {
            insufficientBalance();
            return false;

        }
    }

    /**
     * Transfers funds to another account within the same bank.
     *
     * @param account The recipient account.
     * @param amount  The amount to transfer.
     * @return {@code true} if the transfer was successful, otherwise {@code false}.
     * @throws IllegalAccountType if the recipient account does not support deposits.
     */
    @Override
    public boolean transfer(Account account, double amount) throws IllegalAccountType {
        if (!(account instanceof Deposit)) {//changed the code here
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

    /**
     * Transfers funds to another bank account, including processing fee deduction.
     *
     * @throws IllegalAccountType if the recipient account does not support deposits.
     */
    @Override
    public boolean transfer(Bank bank, Account account, double amount) throws IllegalAccountType {
        if (bank == null || account == null) {
            System.out.println("Invalid bank or account details.");
            return false;
        }
        boolean success = transfer(account, amount);

        if(success){
            double fee = this.getBank().getProcessingFee();
            adjustAccountBalance(fee);
            System.out.println("Processing fee of " + fee + " deducted. New balance: " + balance);
            addNewTransaction(
                    this.accountNumber,
                    Transaction.Transactions.FundTransfer,
                    "Transferred: " + this.balance
            );
        }
        return success;
    }

    /**
     * Returns a string representation of the savings account.
     *
     * @return A string with account details and balance.
     */
    @Override
    public String toString() {
        return super.toString() + "\nBalance: " + balance;
    }

    public double getAccountBalance() {
        return balance;
    }
}