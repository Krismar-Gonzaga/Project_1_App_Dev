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
        return null; // temporary
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
  public static boolean accountExists(Bank bank, String accountNum) {
        // Input validation
        if ((bank == null) || (accountNum == null) || accountNum.isEmpty()) {
            return false;
        }

        // Check each account in the bank's accounts list
        for (Account account : bank.BANKACCOUNTS) {
            // Compare the account number with the provided account number
            if (account.getAccountNumber().equals(accountNum)) {
                return true;
            }
        }

        // Account not found
        return false;
    }

    /**
     * Returns a string representation of the Bank object.
     * @return A string that represents this Bank object.
     */
    @Override
    public String toString() {
        String sb = "Bank [ID=" + ID +
                ", name=" + name +
                ", DEPOSITLIMIT=" + DEPOSITLIMIT +
                ", WITHDRAWLIMIT=" + WITHDRAWLIMIT +
                ", CREDITLIMIT=" + CREDITLIMIT +
                ", processingFee=" + processingFee +
                ", Number of accounts=" + BANKACCOUNTS.size() +
                "]";

        return sb;
    }
}
