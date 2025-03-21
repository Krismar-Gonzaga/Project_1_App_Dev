package Accounts;

import Bank.Bank;

/**
 * CreditAccount class representing a bank account that operates on credit.
 * It allows credit transactions while ensuring credit limits are enforced.
 */
public class CreditAccount extends Account implements Payment, Recompense {

    private double loanBalance;  // The current outstanding loan

    /**
     * Constructor for CreditAccount.
     *
     * @param bank        The bank associated with this credit account.
     * @param accountNumber The unique account number.
     * @param ownerFname Owner's first name.
     * @param ownerLname Owner's last name.
     * @param ownerEmail Owner's email address.
     * @param pin         Security PIN for authentication.
     */
    public CreditAccount(Bank bank, String accountNumber, String pin, String ownerFname,
                         String ownerLname, String ownerEmail) {
        super(bank, accountNumber, pin, ownerFname, ownerLname, ownerEmail);
        this.loanBalance = 0.0;
    }

    /**
     * Gets the current loan statement.
     *
     * @return Formatted loan statement.
     */
    public String getLoanStatement() {
        return String.format("Credit Account [Account: %s, Owner: %s %s, Loan Balance: $%.2f]",
                accountNumber, ownerFname, ownerLname, loanBalance);
    }

    /**
     * Checks if the account can take additional credit without exceeding the bank's limit.
     *
     * @param amountAdjustment The amount to be credited.
     * @return True if the credit transaction can proceed, otherwise false.
     */
    public boolean canCredit(double amountAdjustment) {
        return (loanBalance + amountAdjustment) <= bank.getCreditLimit();
    }

    /**
     * Adjusts the loan balance of the credit account.
     *
     * @param amountAdjustment The amount to adjust.
     */
    public void adjustLoanAmount(double amountAdjustment) {
        this.loanBalance = Math.max(this.loanBalance + amountAdjustment, 0);
    }

    /**
     * Pays a certain amount to a SavingsAccount.
     *
     * @param recipient The target account to pay into.
     * @param amount    The amount to pay.
     * @return True if the payment was successful, false otherwise.
     */
    public boolean pay(Account recipient, double amount) {
        if (!(recipient instanceof SavingsAccount savingsRecipient)) {
            throw new IllegalArgumentException("Credit Accounts can only pay to Savings Accounts.");
        }
        if (!canCredit(amount)) {
            System.out.println("Payment failed: Not enough credit available.");
            return false;
        }

        adjustLoanAmount(amount);
        savingsRecipient.adjustAccountBalance(amount);

        addNewTransaction(recipient.getAccountNumber(), Transaction.Transactions.PAYMENT,
                String.format("Paid $%.2f to %s", amount, recipient.getAccountNumber()));

        savingsRecipient.addNewTransaction(this.accountNumber, Transaction.Transactions.RECEIVE_TRANSFER,
                String.format("Received $%.2f from Credit Account %s", amount, this.accountNumber));

        System.out.println("Payment successful. New loan balance: $" + loanBalance);
        return true;
    }

    /**
     * Recompense the bank by reducing the recorded loan balance.
     *
     * @param amount The amount to recompense.
     * @return True if successful, false otherwise.
     */
    public boolean recompense(double amount) {
        if (amount <= 0 || amount > loanBalance) {
            System.out.println("Recompense failed: Invalid amount.");
            return false;
        }

        adjustLoanAmount(-amount);
        addNewTransaction(this.accountNumber, Transaction.Transactions.COMPENSATION,
                String.format("Recompensed $%.2f to the bank", amount));

        System.out.println("Recompense successful. New loan balance: $" + loanBalance);
        return true;
    }

    /**
     * Gets the current loan balance.
     *
     * @return The outstanding loan amount.
     */
    public double getLoan() {
        return this.loanBalance;
    }
}
