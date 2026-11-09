package src.main;

import src.classes.BankAccount;

/**
 * Variante OHNE synchronized: alle Threads arbeiten auf einem normalen BankAccount.
 *
 * accountBalance += amount ist keine atomare Operation (lesen, addieren, schreiben).
 * Laufen zwei Threads gleichzeitig hinein, ueberschreibt einer das Ergebnis des anderen
 * (Lost Update). Der Endstand weicht deshalb meist vom erwarteten Wert ab, und zwar
 * bei jedem Durchlauf unterschiedlich.
 */
public class MainUnsynchronized {

    public static void main(String[] args) throws InterruptedException {
        BankSimulation.run("Ohne synchronized", BankAccount::new, args);
    }
}
