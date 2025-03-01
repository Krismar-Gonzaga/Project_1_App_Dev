
package Banks;
import Accounts.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Bank {
    private int ID;
    private String name, passcode;
    private double DEPOSITLIMIT;
    private double WITHDRAWLIMIT;
    private double CREDITLIMIT;
    private double processingFee;
    private final ArrayList<Account> BANKACCOUNTS;
    Scanner input = new Scanner(System.in);
    public Bank(int ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.BANKACCOUNTS = new ArrayList<>();
    }

    public Bank(int ID, String name, String passcode, double DEPOSITLIMIT, double WITHDRAWLIMIT, double CREDITLIMIT, double processingFee) {
        this(ID, name, passcode);
        this.DEPOSITLIMIT = DEPOSITLIMIT;
        this.WITHDRAWLIMIT = WITHDRAWLIMIT;
        this.CREDITLIMIT = CREDITLIMIT;
        this.processingFee = processingFee;
    }
    /**
     * Displays all accounts of a specific type stored in the bank.
     *
     * @param accountType The class type of the accounts to be displayed (e.g., SavingsAccount.class).
     * @param <T> The generic type representing the account type.
     */
    // public <T> void showAccounts(class <T> accountType){
    //     //logics here
    // }

    /*
     * Returns the bank account with the specified account number.
     * @param bank The bank to search for the account.
     * @param AccountNum The account number of the account to be returned.
     * 
     * @return The account with the specified account number.
     */

    public Account getBankAccount(Bank bank, String AccountNum){

        for (Account account : bank.BANKACCOUNTS){
            if (AccountNum.equals(account.getAccountNumber())){
                return  account;
            }

        }
        return null;
    }

    public ArrayList<String> createNewAccount(){
        return null;
    }

    public CreditAccount createNewCrediAccount(){
        return null; //temporary
    }

    public SavingsAccount createNewSavingAccount(){
        return null;
    }

    public void addNewAccount(Account Account){
        for (Account account : this.BANKACCOUNTS){
            if (Account.getAccountNumber() != account.getAccountNumber()){
                this.BANKACCOUNTS.add(account);
            }
        }
    }
    /*
     * Returns if the account with the specified account number exists in the bank.
     * @param AccountNum The account number of the account to be returned.
     * @param bank The bank to search for the account.
     */
    public static boolean accountExists(Bank bank, String AccountNum){
        return false;
    }
    public String toString(){
        return "";
    }
}
