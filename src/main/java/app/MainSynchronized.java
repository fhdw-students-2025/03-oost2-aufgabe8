package app;

import model.SynchronizedBankAccount;

public class MainSynchronized {

    public static void main(String[] args) throws InterruptedException {
        BankSimulation.run("With synchronized", SynchronizedBankAccount::new, args);
    }
}
