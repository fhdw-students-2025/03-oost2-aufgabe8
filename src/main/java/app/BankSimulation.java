package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;
import model.BankAccount;
import service.BankingOperation;

/**
 * The account starts with enough balance for all withdrawals, so without race conditions
 * none fails and the final balance equals the starting balance.
 * <p>
 * Optional program arguments: {@code [threadPairs] [repetitionsPerThread] [rounds]}
 */
final class BankSimulation {

    private static final int AMOUNT = 10;
    private static final int DEFAULT_THREAD_PAIRS = 2;
    private static final int DEFAULT_REPETITIONS = 100_000;
    private static final int DEFAULT_ROUNDS = 5;

    // German locale: "." as thousands separator (e.g. 1.000.000)
    private static final Locale LOCALE = Locale.GERMANY;

    private BankSimulation() {
    }

    static void run(String title, Supplier<BankAccount> accountFactory, String[] args)
        throws InterruptedException {
        int threadPairs = positiveArg(args, 0, "threadPairs", DEFAULT_THREAD_PAIRS);
        int repetitions = positiveArg(args, 1, "repetitionsPerThread", DEFAULT_REPETITIONS);
        int rounds = positiveArg(args, 2, "rounds", DEFAULT_ROUNDS);

        // If all deposits run first, the balance reaches twice the starting balance
        long maxBalance = 2L * threadPairs * repetitions * AMOUNT;
        if (maxBalance > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Too many operations: balance would overflow int");
        }
        int startBalance = (int) (maxBalance / 2);

        System.out.printf(LOCALE, "%n=== %s ===%n", title);
        System.out.printf(LOCALE, "Threads:              %,d (%,d × deposit, %,d × withdraw)%n",
            threadPairs * 2, threadPairs, threadPairs);
        System.out.printf(LOCALE, "Operations/thread:    %,d × %,d ct%n", repetitions, AMOUNT);
        System.out.printf(LOCALE, "Expected balance:     %,d ct%n%n", startBalance);

        // Column widths so that all rounds line up
        int roundWidth = String.valueOf(rounds).length();
        int balanceWidth = String.format(LOCALE, "%,d", maxBalance).length();
        String roundFormat = "Round %" + roundWidth + "d: balance = %," + balanceWidth + "d ct"
            + " | difference %+," + (balanceWidth + 1) + "d ct | %,6d ms%n";

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
            System.out.printf(LOCALE, roundFormat, round, balance, difference, millis);
        }
        System.out.printf(LOCALE, "%nCorrect: %d of %d rounds%n", correctRounds, rounds);
    }

    private static int positiveArg(String[] args, int index, String name, int defaultValue) {
        if (args.length <= index) {
            return defaultValue;
        }

        int value;
        try {
            value = Integer.parseInt(args[index]);
        } catch (NumberFormatException e) {
            value = 0;
        }

        if (value <= 0) {
            throw new IllegalArgumentException(name + " must be a positive integer, got: " + args[index]);
        }
        return value;
    }
}
