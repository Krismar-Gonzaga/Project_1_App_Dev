
package Banks;
import Accounts.*; // Replace 'com.example.bank' with the actual package name where Account class is located
import java.util.ArrayList;
import java.util.InputMismatchException;
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

    public ArrayList<Bank> createNewAccount(){
        
        ArrayList<Bank> accounts = new ArrayList<>();
        int id;
        while (true) 
        { 
            try {
                System.out.println("Enter ID: ");
                id = input.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please Enter Valid ID!");
            }
        }
        String name;
        while (true) { 
            System.out.println("Enter Name: ");
            name = input.nextLine();
            if (name == ""){
                System.out.println("Please Enter Name!");

            }else{
                break;
            }

        }
        String passcode;
        while (true) { 
            System.out.println("Enter Passcode: ");
            passcode = input.nextLine();
            if (passcode == ""){
                System.out.println("Please Enter Passcode!");

            }else{
                break;
            }

        }
        
        double depositlimit;
        while (true) 
        { 
            try {
                System.out.println("Enter Depositlimit: ");
                depositlimit = input.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please Enter Valid Depositlimit!");
            }
        }

        double withdrawlimit;
        while (true) 
        { 
            try {
                System.out.println("Enter withdrawlimit: ");
                withdrawlimit = input.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please Enter Valid withdrawlimit!");
            }
        }

        double creditlimit;
        while (true) 
        { 
            try {
                System.out.println("Enter creditlimit: ");
                creditlimit = input.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please Enter Valid creditlimit!");
            }
        }

        double processingFee;
        while (true) 
        { 
            try {
                System.out.println("Enter processingFee: ");
                processingFee = input.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please Enter Valid processing Fee!");
            }
        }
        

        Bank newaccount = new Bank(id,name,passcode,depositlimit,withdrawlimit,creditlimit,processingFee);
        accounts.add(newaccount);
        return accounts;
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
