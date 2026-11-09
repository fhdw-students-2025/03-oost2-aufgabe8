package app;

import model.SynchronizedBankAccount;

/**
 * Demo WITH {@code synchronized}: all threads share a {@link SynchronizedBankAccount}.
 * <p>
 * {@code deposit}, {@code withdraw} and {@code getBalance} lock the same monitor (the account),
 * so only one thread at a time works on the balance and the final balance is correct in every round.
 */
public class MainSynchronized {

    /**
     * Runs the simulation on a synchronized account.
     *
     * @param args optional {@code [threadPairs] [repetitionsPerThread] [rounds]}
     * @throws InterruptedException if the main thread is interrupted while waiting for the workers
     */
    public static void main(String[] args) throws InterruptedException {
        BankSimulation.run("With synchronized", SynchronizedBankAccount::new, args);
    }
}
