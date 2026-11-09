package src.main;

import src.classes.BankAccount;
import src.services.BankingOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Gemeinsame Logik fuer MainUnsynchronized und MainSynchronized.
 *
 * Startet abwechselnd Einzahlungs- und Abhebungs-Threads auf demselben Konto.
 * Das Konto bekommt vorher genug Startguthaben, damit keine Abhebung mangels Deckung
 * scheitert. Ohne Race-Conditions muss der Endstand also genau dem Startguthaben entsprechen.
 *
 * Optionale Programmargumente: [Threadpaare] [Wiederholungen pro Thread] [Durchlaeufe]
 */
final class BankSimulation {

    private static final int AMOUNT = 10;

    private BankSimulation() {
    }

    static void run(String title, Supplier<BankAccount> accountFactory, String[] args)
            throws InterruptedException {
        int threadPairs = args.length > 0 ? Integer.parseInt(args[0]) : 2;
        int repetitions = args.length > 1 ? Integer.parseInt(args[1]) : 100_000;
        int rounds = args.length > 2 ? Integer.parseInt(args[2]) : 5;

        int startBalance = threadPairs * repetitions * AMOUNT;

        System.out.println("=== " + title + " ===");
        System.out.println((threadPairs * 2) + " Threads (" + threadPairs + " x Einzahlung, "
                + threadPairs + " x Abhebung), je " + repetitions + " x " + AMOUNT);
        System.out.println("Erwarteter Kontostand: " + startBalance);

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
            System.out.printf("Durchlauf %d: Finaler Kontostand = %d (Abweichung %+d, %d ms)%n",
                    round, balance, difference, millis);
        }
        System.out.println("Korrekt: " + correctRounds + " von " + rounds + " Durchlaeufen");
    }
}
