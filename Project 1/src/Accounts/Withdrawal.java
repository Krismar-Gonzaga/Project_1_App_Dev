package Accounts;
/**
 * The {@code Withdrawal} interface defines the contract for objects that can perform withdrawal operations.
 * Any class that implements this interface must provide an implementation for the withdrawal method.
 * This interface is part of the Accounts package and supports the banking system's transaction capabilities.
 */
public interface Withdrawal {
    boolean withdrawal(double amount);
}
