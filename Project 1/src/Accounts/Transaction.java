package Accounts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Transaction class records details of a specific account transaction.
 * Transactions are immutable once created.
 */
public class Transaction {

    public enum Transactions {
        DEPOSIT,
        WITHDRAWAL,
        FUNDTRANSFER,
        RECEIVE_TRANSFER,
        EXTERNAL_TRANSFER,
        PAYMENT,
        COMPENSATION
    }

    private final String sourceAccount;
    private final Transactions transactionType;
    private final String description;
    private final LocalDateTime timestamp;

    public Transaction(String sourceAccount, Transactions transactionType, String description) {
        this.sourceAccount = sourceAccount;
        this.transactionType = transactionType;
        this.description = description;
        this.timestamp = LocalDateTime.now();
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public Transactions getTransactionType() {
        return transactionType;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("Transaction{Time=%s, Source='%s', Type=%s, Description='%s'}",
                timestamp.format(formatter), sourceAccount, transactionType, description);
    }
}
