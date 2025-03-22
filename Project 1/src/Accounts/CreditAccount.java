package Accounts;

import Bank.*;

public class CreditAccount extends Account implements Payment, Recompense {
    private double loan;

    // Constructor
    public CreditAccount(Bank bank, String accountNumber, String ownerFName, String ownerLName, String ownerEmail,
                         String pin) {
        super(bank, accountNumber, ownerFName, ownerLName, ownerEmail, pin);
        this.loan = 0.0;
    }

    // Get loan statement
    public String getLoanStatement() {
        return "Outstanding Loan: " + loan;
    }

    // Check if credit adjustment is within limit
    private boolean canCredit(double amountAdjustment) {
        double creditLimit = getBank().getCreditLimit();
        return (loan + amountAdjustment >= 0) && (loan + amountAdjustment <= creditLimit);
    }

    // Adjust loan amount
    private void adjustLoanAmount(double amountAdjustment) {
        if (canCredit(amountAdjustment)) {
            loan += amountAdjustment;
            System.out.println("Loan adjusted successfully. New loan amount: " + loan);
        } else {
            System.out.println("Loan adjustment not possible!");
        }
    }

    // Implement recompense (direct loan repayment)
    @Override
    public boolean recompense(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid recompense amount.");
            return false;
        }

        if (loan - amount < 0) {
            System.out.println("Recompense amount exceeds loan. Adjusting to zero.");
            loan = 0;
        } else {
            loan -= amount;
        }

        System.out.println("Recompense successful. Remaining loan: " + loan);
        return true;
    }

    // Implement pay (loan repayment from another account)
    @Override
    public boolean pay(Account account, double amount )throws IllegalAccountType {
        if (account instanceof SavingsAccount) {
            SavingsAccount savings = (SavingsAccount) account;

            if (!savings.hasEnoughBalance(amount)) {
                System.out.println("Insufficient funds in savings account for payment.");
                return false;
            }

            savings.withdrawal(amount);
            recompense(amount);
            System.out.println("Payment successful. New loan balance: " + loan);
            return true;
        }

        throw new IllegalAccountType("Invalid accont type for payment");

    }

    // toString method
    @Override
    public String toString() {
        return super.toString() + "\nLoan: " + loan;
    }

    public double getLoan() {
        return loan;
    }
}