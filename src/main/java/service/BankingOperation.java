package service;

import model.BankAccount;

/**
 * A task that repeatedly deposits money to or withdraws money from a
 * {@link BankAccount}. Designed to be run in its own {@link Thread}.
 * <p>
 * The sign of {@code amount} decides the operation:
 * <ul>
 *   <li>positive: deposit {@code amount}</li>
 *   <li>negative: withdraw {@code -amount}</li>
 * </ul>
 * <p>
 * A withdrawal that fails because the balance is too low is
 * <b>silently skipped</b>; the remaining repetitions still run.
 */
public class BankingOperation implements Runnable{

    private final BankAccount account;
    private final int amount;
    private final int repetitions;

    /**
     * Creates a new operation. Nothing is booked until {@link #run()} is called.
     *
     * @param account     the account to operate on; must not be {@code null}
     * @param amount      the amount per operation; positive to deposit,
     *                    negative to withdraw, must not be {@code 0}
     * @param repetitions how often the operation is executed; must not be negative
     * @throws IllegalArgumentException if {@code account} is {@code null},
     *                                  {@code amount} is {@code 0}
     *                                  or {@code repetitions} is negative
     */
    public BankingOperation(BankAccount account, int amount, int repetitions) {

        if (account == null) {
            throw new IllegalArgumentException("Account must not be null");
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

    /**
     * Executes the deposit or withdrawal {@code repetitions} times.
     * Failed withdrawals (insufficient funds) are skipped without an exception.
     */
    @Override
    public void run() {
        for (int i = 0; i < repetitions; i++) {
            if (amount > 0) {
                account.deposit(amount);
            } else {
                try {
                    account.withdraw(-amount);
                } catch (IllegalArgumentException e) {
                    //Not enough accountBalance; this withdrawal stops, thread keeps running
                }
            }
        }
    }
}
