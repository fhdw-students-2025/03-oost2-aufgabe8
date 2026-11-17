package service;

import model.BankAccount;
import model.InsufficientFundsException;

/**
 * Deposits ({@code amount > 0}) or withdraws ({@code amount < 0}) {@code repetitions} times.
 */
public class BankingOperation implements Runnable {

    private final BankAccount account;
    private final int amount;
    private final int repetitions;

    public BankingOperation(BankAccount account, int amount, int repetitions) {
        if (account == null) {
            throw new IllegalArgumentException("Account must not be null");
        }

        if (amount == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Amount must be greater than Integer.MIN_VALUE");
        }

        if (amount == 0) {
            throw new IllegalArgumentException("Amount must not be zero");
        }

        if (repetitions < 0) {
            throw new IllegalArgumentException("Repetitions must not be negative");
        }

        this.account = account;
        this.amount = amount;
        this.repetitions = repetitions;
    }

    @Override
    public void run() {
        for (int i = 0; i < repetitions; i++) {
            if (amount > 0) {
                account.deposit(amount);
            } else {
                try {
                    account.withdraw(-amount);
                } catch (InsufficientFundsException e) {
                    // Per the task, a withdrawal only happens if the balance covers it: skip it
                }
            }
        }
    }
}
