package Accounts;

import Bank.BankLauncher;
import Bank.Bank;
import Main.*;

import java.util.Optional;

public class AccountLauncher {

    private Account loggedAccount;
    private Bank assocBank;

    public void setAssocBank(Bank assocBank) {
        this.assocBank = assocBank;
    }

    public void accountLogin() throws IllegalAccountType {
        if (assocBank == null) {
            System.out.println("Bank selection failed. Please try again.");
            return;
        }

        Main.showMenuHeader("Select Account Type");
        Main.showMenu(Menu.AccountTypeSelection.menuIdx);
        Main.setOption();

        int accountTypeOption = Main.getOption();
        Class<? extends Account> accountType;
        switch (accountTypeOption) {
            case 1:
                accountType = CreditAccount.class;
                break;
            case 2:
                accountType = SavingsAccount.class;
                break;
            default:
                System.out.println("Invalid option. Returning to main menu.");
                return;
            }

        Field<String, Integer> accountNum = new Field<String, Integer>("Account Number", String.class, 5, new Field.StringFieldLengthValidator());
        accountNum.setFieldValue("Enter Account Number: ");
        String accountNumber = accountNum.getFieldValue();

        Field<String, Integer> accPin = new Field<String, Integer>("PIN", String.class, 4, new Field.StringFieldLengthValidator());
        accPin.setFieldValue("Enter 4-digit PIN: ");
        String pin = accPin.getFieldValue();

        Account account = assocBank.getBankAccount(accountNumber);
        if (account == null) {
            System.out.println("Account not found.");
            return;
        }
        if (!account.getClass().equals(accountType)) {
            System.out.println("Invalid account type.");
            return;
        }
        if (!checkCredentials(accountNumber, pin)) {
            System.out.println("Invalid credentials.");
            return;
        }

        setLogSession(account);
        System.out.println("Login successful. Welcome, " + loggedAccount.getOwnerFname() + "!");

        if (loggedAccount instanceof SavingsAccount savingsAccount) {
            SavingsAccountLauncher.setLoggedAccount(savingsAccount);
            SavingsAccountLauncher.savingsAccountInit();
        } else if (loggedAccount instanceof CreditAccount creditAccount) {
            CreditAccountLauncher.setLoggedAccount(creditAccount);
            CreditAccountLauncher.creditAccountInit();
        }

        destroyLogSession();
    }


    private static Bank selectBank() {
        if (BankLauncher.bankSize() == 0) {
            System.out.println("No banks are available. Please create a bank first.");
            return null;
        }

        Main.showMenuHeader("Select a Bank");
        BankLauncher.showBanksMenu();
        Main.setOption();

        int bankIndex = Main.getOption();

        return (bankIndex > 0 && bankIndex <= BankLauncher.getBanks().size())
                ? BankLauncher.getBanks().get(bankIndex-1)
                : null;
    }

    public boolean checkCredentials(String accountNumber, String pin) {
        Account account = assocBank.getBankAccount(accountNumber);
        if(account != null && account.getPin().equals(pin)){
            return true;
        }
        return false;
    }

    public void setLogSession(Account account) {
        this.loggedAccount = account;
    }

    public void destroyLogSession() {
        System.out.println("Logging out of " + loggedAccount.getAccountNumber());
        loggedAccount = null;
    }

    protected  Account getLoggedAccount() {return loggedAccount;}

    public static Bank getselectBank(){
        return selectBank();
    }

    public boolean isLoggedIn() {
        return loggedAccount != null;
    }
}
