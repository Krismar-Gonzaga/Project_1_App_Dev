package Accounts;

import Bank.*;

/**
 * The {@code CreditAccount} class represents a credit-based bank account.
 * It extends the {@code Account} class and allows users to borrow money up to a set credit limit.
 * This class implements {@code Payment} and {@code Recompense} interfaces for handling repayments.
 */
public class CreditAccount extends Account implements Payment, Recompense {
    /** The current outstanding loan balance for this account. */
    private double loan;

    // Constructor
    /**
     * Constructs a new {@code CreditAccount} instance.
     */
    public CreditAccount(Bank bank, String accountNumber, String ownerFName, String ownerLName, String ownerEmail,
                         String pin) {
        super(bank, accountNumber, ownerFName, ownerLName, ownerEmail, pin);
        this.loan = 0.0;
    }

    /**
     * Retrieves the loan statement of the account.
     */
    public String getLoanStatement() {
        return "Outstanding Loan: " + loan;
    }

    /**
     * Checks if a credit adjustment is within the bank's allowed credit limit.
     */
    public boolean canCredit(double amountAdjustment) {
        double creditLimit = bank.getCreditLimit();
        return (loan + amountAdjustment >= 0) && (loan + amountAdjustment <= creditLimit);
    }

    /**
     * Adjusts the outstanding loan amount based on the given adjustment value.
     */
    public void adjustLoanAmount(double amountAdjustment) {
        if (canCredit(amountAdjustment)) {
            loan += amountAdjustment;
            System.out.println("Loan adjusted successfully. New loan amount: " + loan);
        } else {
            System.out.println("Loan adjustment not possible!");
        }
    }

    /**
     * Implements recompense by directly repaying a specified loan amount.
     */
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

    /**
     * Implements payment by repaying the loan using funds from another account.
     * Only a {@code SavingsAccount} can be used for repayment.
     *
     * @param account The account from which the payment will be made.
     * @param amount  The amount to be transferred from the savings account.
     * @return {@code true} if the payment was successful, otherwise {@code false}.
     * @throws IllegalAccountType If the provided account is not of type {@code SavingsAccount}.
     */
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

    /**
     * Returns a string representation of this credit account.
     * It includes owner details, bank information, account number, and loan balance.
     *
     * @return A string representation of this account.
     */
    @Override
    public String toString() {
        return super.toString() + "\nLoan: " + loan;
    }

    public double getLoan() {
        return loan;
    }
}