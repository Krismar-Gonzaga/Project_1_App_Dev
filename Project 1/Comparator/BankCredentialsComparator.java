package Comparator;

import java.util.Comparator;
import Banks.*;

public class BankCredentialsComparator implements Comparator<Bank> {

    @Override
    public int compare(Bank b1, Bank b2) {
        return b1.getPasscode().compareTo(b2.getPasscode());
    }

}