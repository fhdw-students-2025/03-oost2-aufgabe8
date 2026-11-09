package src.main;

import src.classes.SynchronizedBankAccount;

/**
 * Variante MIT synchronized: alle Threads arbeiten auf einem SynchronizedBankAccount.
 *
 * deposit, withdraw und getBalance sperren denselben Monitor (das Konto-Objekt).
 * Es arbeitet also immer nur ein Thread gleichzeitig am Kontostand, der Endstand
 * stimmt in jedem Durchlauf mit dem erwarteten Wert ueberein.
 */
public class MainSynchronized {

    public static void main(String[] args) throws InterruptedException {
        BankSimulation.run("Mit synchronized", SynchronizedBankAccount::new, args);
    }
}
