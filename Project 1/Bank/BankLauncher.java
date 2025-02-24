import java.util.*;

public class BankLauncher {
    private static final ArrayList<Bank> BANKS = new ArrayList<>();
    private static Bank loggedBank = null;

    public static boolean isLogged() {
        return loggedBank != null;
    }

    public static void bankInit() {
        // Implement logic to initialize bank-related operations
    }

    private static void showAccounts() {
        if (!isLogged()) {
            System.out.println("Please log in to a bank first.");
            return;
        }
        loggedBank.showAccounts();
    }

    private static void newAccounts() {
        // Implement logic for creating new accounts
    }

    public static void bankLogin() {
        // Implement logic for bank login
    }

    private static void setLogSession(Bank b) {
        loggedBank = b;
    }

    private static void logout() {
        loggedBank = null;
        System.out.println("Logged out successfully");
    }

    public static void createNewBank() {
        // Implement logic for creating a new bank
    }

    public static void showBanksMenu() {
        // Loop Bank menu
    }

    private static void addBank(Bank b) {
        BANKS.add(b);
    }

    public static Bank getBank(Comparator<Bank> comparator, Bank bank) {
        return BANKS.stream().min(comparator).orElse(bank);
    }

    public static Account findAccount(String accountNum) {
        if (!isLogged()) {
            System.out.println("Please log in to a bank first.");
            return null;
        }
        return loggedBank.getAccount(accountNum);
    }

    public static int bankSize() {
        return BANKS.size();
    }
}
