package app;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import model.BankAccount;
import service.BankingOperation;

/**
 * Shared logic of {@link MainUnsynchronized} and {@link MainSynchronized}.
 * <p>
 * Starts depositing and withdrawing threads in turn on one shared account. The account
 * gets enough starting balance first so that no withdrawal fails for lack of funds.
 * Without race conditions the final balance must therefore equal the starting balance.
 * <p>
 * Optional program arguments: {@code [threadPairs] [repetitionsPerThread] [rounds]}
 */
final class BankSimulation {

    private static final int AMOUNT = 10;
    private static final int DEFAULT_THREAD_PAIRS = 2;
    private static final int DEFAULT_REPETITIONS = 100_000;
    private static final int DEFAULT_ROUNDS = 5;

    private BankSimulation() {
    }

    /**
     * Runs the simulation and prints the result of every round.
     *
     * @param title          headline of the output
     * @param accountFactory creates a fresh account for every round
     * @param args           optional {@code [threadPairs] [repetitionsPerThread] [rounds]}
     * @throws InterruptedException if the main thread is interrupted while waiting for the workers
     */
    static void run(String title, Supplier<BankAccount> accountFactory, String[] args)
            throws InterruptedException {
        int threadPairs = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_THREAD_PAIRS;
        int repetitions = args.length > 1 ? Integer.parseInt(args[1]) : DEFAULT_REPETITIONS;
        int rounds = args.length > 2 ? Integer.parseInt(args[2]) : DEFAULT_ROUNDS;

        int startBalance = Math.multiplyExact(Math.multiplyExact(threadPairs, repetitions), AMOUNT);

        System.out.printf("=== %s ===%n", title);
        System.out.printf("%d threads (%d x deposit, %d x withdraw), %d x %d each%n",
            threadPairs * 2, threadPairs, threadPairs, repetitions, AMOUNT);
        System.out.printf("Expected account balance: %d%n", startBalance);

        int correctRounds = 0;
        for (int round = 1; round <= rounds; round++) {
            BankAccount account = accountFactory.get();
            account.deposit(startBalance);

            List<Thread> threads = new ArrayList<>();
            for (int i = 0; i < threadPairs; i++) {
                threads.add(new Thread(new BankingOperation(account, AMOUNT, repetitions)));
                threads.add(new Thread(new BankingOperation(account, -AMOUNT, repetitions)));
            }

            long start = System.nanoTime();
            for (Thread thread : threads) {
                thread.start();
            }
            for (Thread thread : threads) {
                thread.join();
            }
            long millis = (System.nanoTime() - start) / 1_000_000;

            int balance = account.getBalance();
            int difference = balance - startBalance;
            if (difference == 0) {
                correctRounds++;
            }
            System.out.printf("Round %d: final account balance = %d (difference %+d, %d ms)%n",
                round, balance, difference, millis);
        }
        System.out.printf("Correct: %d of %d rounds%n", correctRounds, rounds);
    }
}
