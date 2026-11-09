package app;

import model.BankAccount;

/**
 * Demo WITHOUT {@code synchronized}: all threads share a plain {@link BankAccount}.
 * <p>
 * {@code balance += amount} is not atomic (read, add, write). When two threads run it at the
 * same time, one overwrites the result of the other (lost update). The final balance therefore
 * usually differs from the expected one, and differently in every round.
 */
public class MainUnsynchronized {

    /**
     * Runs the simulation on an unsynchronized account.
     *
     * @param args optional {@code [threadPairs] [repetitionsPerThread] [rounds]}
     * @throws InterruptedException if the main thread is interrupted while waiting for the workers
     */
    public static void main(String[] args) throws InterruptedException {
        BankSimulation.run("Without synchronized", BankAccount::new, args);
    }
}
