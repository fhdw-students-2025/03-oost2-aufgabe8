package model;

/**
 * A simple bank account that holds a balance as a whole number.
 * <p>
 * The balance starts at {@code 0} and can never become negative:
 * withdrawals that exceed the current balance are rejected.
 */
public class BankAccount {
    private int balance;

    /**
     * Adds the given amount to the balance.
     *
     * @param amount the amount to deposit; must be greater than {@code 0}
     * @throws IllegalArgumentException if {@code amount} is zero or negative,
     *                                  or if the balance would overflow;
     *                                  the balance stays unchanged
     */
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
     * Subtracts the given amount from the balance.
     *
     * @param amount the amount to withdraw; must be greater than {@code 0}
     *               and must not exceed the current balance
     * @throws IllegalArgumentException   if {@code amount} is zero or negative;
     *                                    the balance stays unchanged
     * @throws InsufficientFundsException if {@code amount} is greater than the current balance;
     *                                    the balance stays unchanged
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

    /**
     * Returns the current balance.
     *
     * @return the current balance; never negative
     */
    public int getBalance() {
        return balance;
    }
}
