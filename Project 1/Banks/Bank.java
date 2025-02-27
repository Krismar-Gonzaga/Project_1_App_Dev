package Banks;
import Accounts.*;

import java.util.*;


public class Bank {
    private int ID;
    private String name, passcode;
    private double DEPOSITLIMIT = 50000.0;
    private double WITHDRAWLIMIT = 50000.0;
    private double CREDITLIMIT = 100000.0;
    private double processingFee = 10.0;
    private final ArrayList<Account> BANKACCOUNTS;

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
    public <T> void showAccounts(class <T> accountType){
        //logics here
    }

    /*
     * Returns the bank account with the specified account number.
     * @param bank The bank to search for the account.
     * @param AccountNum The account number of the account to be returned.
     * 
     * @return The account with the specified account number.
     */

    public Account getBankAccount(Bank bank, String AccountNum){
        
    }

    public ArrayList<String> createNewAccount(){
        
        return null; //temporary
    }

    public CreditAccount createNewCreditAccount(){
        ArrayList<String> userInfo = createNewAccount();
        CreditAccount newCreditAccount = new CreditAccount(this, userInfo.get(0), userInfo.get(1), userInfo.get(2), userInfo.get(3), userInfo.get(4), 0.0);
        BANKACCOUNTS.add(newCreditAccount);
        return newCreditAccount;
    }

    public SavingsAccount createNewSavingsAccount(){
        ArrayList<String> userInfo = createNewAccount();
        SavingsAccount newSavingsAccount = new SavingsAccount(this, userInfo.get(0), userInfo.get(1), userInfo.get(2), userInfo.get(3), userInfo.get(4), 0.0);
        BANKACCOUNTS.add(newSavingsAccount);
        return newSavingsAccount;
    }

    public void addNewAccount(Account account){

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
