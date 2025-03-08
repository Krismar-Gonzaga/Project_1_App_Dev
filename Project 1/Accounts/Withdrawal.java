package Accounts;

public interface Withdrawal {
    default boolean withdrawal(double amount){

        return false;
    }
}
