package src.services;

import src.classes.BankAccount;

/**
 * BankingOperation.java
 * -> Runnable Class
 * -> Deposit or Withdraw (positive amount: Deposit; negative amount: Withdraw), n-times, on a BankAccount-Object
 * -> Constructor: BankingOperation(BankAccount account, int amount, int repetitions)
 */


public class BankingOperation implements Runnable{

    private final BankAccount account;
    private final int amount;
    private final int repetitions;

    //Constructor
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

    
    //override runnable-method run()
    @Override 
    public void run() {
        for (int i = 0; i < repetitions; i++) {
            if (amount > 0) {
                account.deposit(amount);
            } else {
                try {
                    account.withdraw(-amount);
                } catch (IllegalArgumentException e) {
                    //Not enough accountBalance; this withdraw stops, thread keeps running
                }
            }
        }
    }


}
