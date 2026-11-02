package src.classes;

import src.classes.BankAccount;
import src.services.BankingOperation;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount();
        int operations = 1000;
        int amount = 100;

        // Threads starten (Ein- und Auszahlungen)
        Thread t1 = new Thread(new BankingOperation(account, amount, operations));
        Thread t2 = new Thread(new BankingOperation(account, -amount, operations));
        Thread t3 = new Thread(new BankingOperation(account, amount, operations));
        Thread t4 = new Thread(new BankingOperation(account, -amount, operations));

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println("Finaler Kontostand: " + account.getBalance());
        System.out.println("Erwarteter Kontostand: 0");
    }
}