package Accounts;

import Banks.*;

public class CreditAccount extends Account implements Payment {
    private double loan;

    // Constructor
    public CreditAccount(Bank bank, String accountNumber, String ownerFName, String ownerLName, String ownerEmail,
            String pin, double loan) {
        super(bank, accountNumber, ownerFName, ownerLName, ownerEmail, pin);
        this.loan = loan;
    }

    public String getLoanStatement() {
        return "Current Loan: " + loan;
    }

    // Check if loan can be adjusted
    public boolean canCredit(double amountAdjustment) {
        return loan + amountAdjustment <= this.getBANK().getCreditLimit();
    }

    // Adjust loan amount
    public void adjustLoanAmount(double amountAdjustment) {
        if (canCredit(amountAdjustment)) {
            loan += amountAdjustment;
            System.out.println("Loan adjusted successfully. New loan amount: " + loan);
        } else {
            System.out.println("Loan adjustment not possible!");
        }
    }

    // toString method
    @Override
    public String toString() {
        return super.toString() + "\nLoan: " + loan;
    }

    @Override
    public boolean pay(Account account, double amount) {
        return false;
    }
}