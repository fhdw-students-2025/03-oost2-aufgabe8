package app;

import model.BankAccount;

/**
 * Lost updates make the final balance wrong. A balance that drops too low this way also makes
 * covered withdrawals fail and get skipped, so the difference can be positive or negative.
 */
public class MainUnsynchronized {

    public static void main(String[] args) throws InterruptedException {
        BankSimulation.run("Without synchronized", BankAccount::new, args);
    }
}
