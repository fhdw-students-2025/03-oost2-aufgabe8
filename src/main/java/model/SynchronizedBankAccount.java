package model;

/**
 * Thread-safe {@link BankAccount}: all methods lock the same monitor ({@code this}).
 * <ul>
 *   <li>{@code deposit}: read, add and write become one step, so no update is lost.</li>
 *   <li>{@code withdraw}: funds check and booking become one step (check-then-act),
 *       so two threads cannot both pass the check for the same money.</li>
 *   <li>{@code getBalance}: sees the latest balance (visibility).</li>
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
