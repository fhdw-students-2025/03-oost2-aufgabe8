package app;

import model.BankAccount;
import service.BankingOperation;

/**
 * Demo that lets several threads deposit into and withdraw from one shared {@link BankAccount}.
 */
public class Main {
    /**
     * Starts two depositing and two withdrawing threads, waits for them and prints the
     * final balance next to the expected one.
     *
     * @param args not used
     * @throws InterruptedException if the main thread is interrupted while waiting for the workers
     */
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount();
        int operations = 1000;
        int amount = 100;
        int expectedAmount = 0;

        // Start threads for deposit/withdrawal
        Thread t1 = new Thread(new BankingOperation(account, amount, operations));
        Thread t2 = new Thread(new BankingOperation(account, -amount, operations));
        Thread t3 = new Thread(new BankingOperation(account, amount, operations));
        Thread t4 = new Thread(new BankingOperation(account, -amount, operations));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.printf("Final account balance: %d%n", account.getBalance());
        System.out.printf("Expected account balance: %d%n", expectedAmount);
    }
}
