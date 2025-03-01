package Accounts;
import Banks.Bank;

public class CreditAccount extends Account {
    private double loan;

    // Constructor
    public CreditAccount(Bank bank, String accountNumber, String ownerFName, String ownerLName, String ownerEmail, String pin, double loan) {
        super(bank, accountNumber, ownerFName, ownerLName, ownerEmail, pin);
        this.loan = loan;
    }

    // Get loan statement
    public String getLoanStatement() {
        return "Current Loan: " + loan;
    }

    // Check if loan can be adjusted
    public boolean canCredit(double amountAdjustment) {
        return loan + amountAdjustment >= 0;
    }

    // Adjust loan amount
    public void adjustLoanAmount(double amountAdjustment) {
        if (canCredit(amountAdjustment)) {
            loan += amountAdjustment;
        } else {
            System.out.println("Loan adjustment not possible!");
        }
    }

    // toString method
    @Override
    public String toString() {
        return super.toString() + "\nLoan: " + loan;
    }
}
