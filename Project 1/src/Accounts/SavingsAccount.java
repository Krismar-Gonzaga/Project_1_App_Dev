package Accounts;

import Bank.*;

/**
 * SavingsAccount class representing a standard savings account with balance tracking.
 * It allows deposits, withdrawals, and fund transfers while enforcing banking rules.
 */
public class SavingsAccount extends Account implements Withdrawal, Deposit, FundTransfer {

    @Override
    public boolean withdrawal(double amount) {
        return withdrawFunds(amount);
    }

    @Override
    public boolean cashDeposit(double amount) {
        return depositFunds(amount);
    }

    @Override
    public boolean transfer(Account recipient, double amount) throws IllegalAccountType {
        return transferFunds(recipient, amount);
    }

    @Override
    public boolean transfer(Bank recipientBank, Account recipient, double amount) throws IllegalAccountType {
        return transferFunds(recipientBank, recipient, amount);
    }

    private double balance;  // The current balance of the savings account

    /**
     * Constructor for SavingsAccount.
     *
     * @param bank        The bank associated with this savings account.
     * @param accountNumber The unique account number.
     * @param ownerFname Owner's first name.
     * @param ownerLname Owner's last name.
     * @param ownerEmail       Owner's email address.
     * @param pin         Security PIN for authentication.
     * @param balance The initial deposit amount.
     * @throws IllegalArgumentException If the initial deposit is below 0.
     */
    public SavingsAccount(Bank bank, String accountNumber, String pin, String ownerFname,
                          String ownerLname, String ownerEmail, double balance) {
        super(bank, accountNumber, pin, ownerFname, ownerLname, ownerEmail);
        if (balance < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative.");
        }
        this.balance = balance;
    }

    public String getAccountBalanceStatement() {
        return String.format("SavingsAccount{Account Number='%s', Owner='%s %s', Balance=$%.2f}",
                accountNumber, ownerFname, ownerLname, balance);
    }

    public boolean hasEnoughBalance(double amount) {
        return balance >= amount;
    }

    public void displayInsufficientBalanceWarning() {
        System.out.println("Warning: Insufficient balance to complete the transaction.");
    }

    public void adjustAccountBalance(double amount) {
        this.balance = Math.max(0, this.balance + amount);
    }

    public boolean depositFunds(double amount) {
        if (amount <= 0 || amount > bank.getDepositLimit()) {
            System.out.println("Invalid deposit amount or exceeds bank limit.");
            return false;
        }
        adjustAccountBalance(amount);
        addNewTransaction(accountNumber, Transaction.Transactions.DEPOSIT,
                "Deposited $" + String.format("%.2f", amount));
        return true;
    }

    public boolean withdrawFunds(double amount) {
        if (amount <= 0 || !hasEnoughBalance(amount) || amount > bank.getWithdrawLimit()) {
            displayInsufficientBalanceWarning();
            return false;
        }
        adjustAccountBalance(-amount);
        addNewTransaction(accountNumber, Transaction.Transactions.WITHDRAWAL,
                "Withdrew $" + String.format("%.2f", amount));
        return true;
    }

    public boolean transferFunds(Account recipient, double amount) throws IllegalAccountType {
        if (!(recipient instanceof SavingsAccount)) {
            throw new IllegalAccountType("Cannot transfer funds to a CreditAccount.");
        }
        if (amount <= 0 || !hasEnoughBalance(amount) || amount > bank.getWithdrawLimit()) {
            displayInsufficientBalanceWarning();
            return false;
        }
        adjustAccountBalance(-amount);
        ((SavingsAccount) recipient).adjustAccountBalance(amount);
        addNewTransaction(recipient.getAccountNumber(), Transaction.Transactions.FUNDTRANSFER,
                "Transferred $" + String.format("%.2f", amount) + " to " + recipient.getAccountNumber());
        recipient.addNewTransaction(accountNumber, Transaction.Transactions.RECEIVE_TRANSFER,
                "Received $" + String.format("%.2f", amount) + " from " + accountNumber);
        return true;
    }

    public boolean transferFunds(Bank recipientBank, Account recipient, double amount) throws IllegalAccountType {
        if (!(recipient instanceof SavingsAccount)) {
            throw new IllegalAccountType("Cannot transfer funds to a CreditAccount.");
        }
        double totalAmount = amount + bank.getProcessingFee();
        if (amount <= 0 || !hasEnoughBalance(totalAmount) || totalAmount > bank.getWithdrawLimit()) {
            displayInsufficientBalanceWarning();
            return false;
        }
        adjustAccountBalance(-totalAmount);
        ((SavingsAccount) recipient).adjustAccountBalance(amount);
        addNewTransaction(recipient.getAccountNumber(), Transaction.Transactions.EXTERNAL_TRANSFER,
                String.format("Transferred $%.2f to %s at %s (Fee: $%.2f)",
                        amount, recipient.getAccountNumber(), recipientBank.getName(), bank.getProcessingFee()));
        recipient.addNewTransaction(accountNumber, Transaction.Transactions.RECEIVE_TRANSFER,
                String.format("Received $%.2f from %s at %s", amount, accountNumber, bank.getName()));
        return true;
    }

    public double getAccountBalance() {
        return balance;
    }
}
