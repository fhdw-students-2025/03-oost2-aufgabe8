package model;

/**
 * A simple bank account that holds a balance as a whole number.
 * <p>
 * The balance starts at {@code 0} and can never become negative:
 * withdrawals that exceed the current balance are rejected.
 */
public class BankAccount {
    private int accountBalance;

    /**
     * Adds the given amount to the balance.
     *
     * @param amount the amount to deposit; must be greater than {@code 0}
     * @throws IllegalArgumentException if {@code amount} is zero or negative; the balance stays unchanged
     * @throws IllegalArgumentException if the balance would overflow.
     */
    public void deposit(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (amount > Integer.MAX_VALUE - accountBalance) {
            throw new IllegalArgumentException("Deposit would overflow balance");
        }

        accountBalance += amount;
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
        if (amount > accountBalance) {
            throw new InsufficientFundsException("Can't withdraw amount greater than accountBalance");
        }
        accountBalance -= amount;
    }

    public int getBalance() {
        return accountBalance;
    }
}
