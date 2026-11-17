package model;

/**
 * Not thread-safe; see {@link SynchronizedBankAccount}.
 */
public class BankAccount {
    private int balance;

    public void deposit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (amount > Integer.MAX_VALUE - balance) {
            throw new IllegalArgumentException("Deposit would overflow balance");
        }

        balance += amount;
    }

    /**
     * Withdraws {@code amount} only if the balance covers it.
     *
     * @throws InsufficientFundsException if it does not; the balance stays unchanged
     */
    public void withdraw(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient funds");
        }

        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }
}
