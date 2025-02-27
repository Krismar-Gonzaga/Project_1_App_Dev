import java.util.*;

public class Bank {
    private int ID;
    private String name, passcode;
    private double DEPOSITLIMIT;
    private double WITHDRAWLIMIT;
    private double CREDITLIMIT;
    private double processingFee;
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
    public <T> void showAccounts(Class<T> accountType) {
        for (Account account : BANKACCOUNTS) {
            if (accountType.isInstance(account)) {
                System.out.println(account.toString());
            }
        }
    }

    /*
     * Returns the bank account with the specified account number.
     * @param bank The bank to search for the account.
     * @param AccountNum The account number of the account to be returned.
     * 
     * @return The account with the specified account number.
     */

    public Account getBankAccount(Bank bank, String accountNum) {
        for (Account account : bank.BANKACCOUNTS) {
            if (account.getAccountNumber().equals(accountNum)) {
                return account;
            }
        }
        return null;
    }

    public ArrayList<String> createNewAccount(){
        return null; //temporary
    }

    public CreditAccount createNewCrediAccount(){
        return null; //temporary
    }

    public SavingsAccount createNewSavingAccount(){
        return null;
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
