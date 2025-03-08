package Comparator;

import Banks.Bank;

import java.util.Comparator;

public class BankComparator implements Comparator<Bank> {

    @Override
    public int compare(Bank b1, Bank b2) {
        // Assuming you want to compare by some integer field, e.g., bankId
        return b1.equals(b2) ? 0 : 1;
    }

}
