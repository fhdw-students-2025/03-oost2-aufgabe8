package model;

/**
 * Thread-safe variant of {@link BankAccount}.
 * <p>
 * All methods are {@code synchronized} and therefore lock the same monitor ({@code this}),
 * so only one thread at a time can access the balance:
 * <ul>
 *   <li>{@link #deposit(int)}: read, add and write happen as one step, so no update is lost.</li>
 *   <li>{@link #withdraw(int)}: the funds check and the booking happen as one step
 *       (check-then-act), so the balance can never become negative.</li>
 *   <li>{@link #getBalance()}: always sees the most recently written balance.</li>
 * </ul>
 */
public class SynchronizedBankAccount extends BankAccount {

    @Override
    public synchronized void deposit(int amount) {
        super.deposit(amount);
    }

    @Override
    public synchronized void withdraw(int amount) {
        super.withdraw(amount);
    }

    @Override
    public synchronized int getBalance() {
        return super.getBalance();
    }
}
