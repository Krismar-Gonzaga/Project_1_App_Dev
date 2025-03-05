package Banks;

import java.util.*;

import Accounts.*;

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
    public <T> void showAccounts(Class<T> accountType){
        for (Account account: BANKACCOUNTS){
            if (accountType.isInstance(account)){
                System.out.println(account);
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

    public Account getBankAccount(Bank bank, String AccountNum){
        for (Account account: bank.BANKACCOUNTS) {
            if (account.getACCOUNTNUMBER().equals(AccountNum)) {
                return account;
            }
        }
        return null; // Return null if account is not found
    }

    /*
     * Returns the bank account with the specified account number.
     * @param AccountNum The account number of the account to be returned.
     */
    public ArrayList<String> createNewAccount(){
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> newAccount = new ArrayList<>();

        System.out.print("Enter First Name: ");
        String Fname = scanner.nextLine();

        System.out.print("Enter Last Name: ");
        String Lname = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter 4-digit PIN: ");
        String pin = scanner.nextLine();

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        newAccount.add(accountNumber);
        newAccount.add(Fname);
        newAccount.add(Lname);
        newAccount.add(email);
        newAccount.add(pin);

        scanner.close();
        return newAccount;
    }
    /*
     * Creates a new credit account and adds it to the bank.
     * @return The newly created credit account.
     */

    public CreditAccount createNewCreditAccount(){
        ArrayList<String> newAccount = createNewAccount();
        String accountNumber = newAccount.get(0);
        String Fname = newAccount.get(1);
        String Lname = newAccount.get(2);
        String email = newAccount.get(3);
        String pin = newAccount.get(4);

        CreditAccount newCreditAccount = new CreditAccount(this, accountNumber, Fname, Lname, email, pin, 0.0);
        BANKACCOUNTS.add(newCreditAccount);
        return newCreditAccount;
    }
    /*
     * Creates a new savings account and adds it to the bank.
     * @return The newly created savings account.
     */
    public SavingsAccount createNewSavingsAccount(){
        ArrayList<String> newAccount = createNewAccount();
        String accountNumber = newAccount.get(0);
        String Fname = newAccount.get(1);
        String Lname = newAccount.get(2);
        String email = newAccount.get(3);
        String pin = newAccount.get(4);
        SavingsAccount newSavingsAccount = new SavingsAccount(this, accountNumber, Fname, Lname, email, pin, 0.0);
        BANKACCOUNTS.add(newSavingsAccount);
        return newSavingsAccount;
    }

    public void addNewAccount(Account account){
        if (account != null){
            BANKACCOUNTS.add(account);
            System.out.println("Account created successfully!");
        } else{
            System.out.println("Error: Cannot add null account.");
        }
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
            if (account.getACCOUNTNUMBER().equals(accountNum)) {
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
