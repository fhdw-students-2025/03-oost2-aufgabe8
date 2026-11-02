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
     * @throws IllegalArgumentException if {@code amount} is zero or negative;
     *                                  the balance stays unchanged
     */
    public void deposit(int amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Amount must be positive");
        }

        accountBalance += amount;
    }

    /**
     * Subtracts the given amount from the balance.
     *
     * @param amount the amount to withdraw; must be greater than {@code 0}
     *               and must not exceed the current balance
     * @throws IllegalArgumentException if {@code amount} is zero or negative,
     *                                  or greater than the current balance;
     *                                  the balance stays unchanged
     */
    public void withdraw(int amount){
        if(amount <= 0){
            throw new IllegalArgumentException("Amount must be positive");
        }
        if(amount > accountBalance){
            throw new IllegalArgumentException("Insufficient funds");
        }
        accountBalance -= amount;
    }
    
    public int getBalance(){
        return accountBalance;
    }
}
