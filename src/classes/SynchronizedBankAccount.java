package src.classes;

/**
 * Thread-sichere Variante von {@link BankAccount}.
 *
 * Alle Methoden sind synchronized und sperren damit denselben Monitor (this).
 * Dadurch kann immer nur ein Thread gleichzeitig auf den Kontostand zugreifen:
 * - deposit: "lesen, addieren, schreiben" laeuft am Stueck, es gehen keine Updates verloren.
 * - withdraw: Pruefung (genug Guthaben?) und Abbuchung laufen am Stueck (check-then-act),
 *   das Konto kann also nicht ins Minus rutschen.
 * - getBalance: liest garantiert den zuletzt geschriebenen Wert (Sichtbarkeit zwischen Threads).
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
