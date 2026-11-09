package model;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import service.BankingOperation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SynchronizedBankAccountTest {

    @Test
    void depositAndWithdrawWorkLikeBankAccount() {
        SynchronizedBankAccount account = new SynchronizedBankAccount();
        account.deposit(100);
        account.withdraw(30);
        assertEquals(70, account.getBalance());
    }

    @Test
    void withdrawThrowsExceptionWhenAmountExceedsBalance() {
        SynchronizedBankAccount account = new SynchronizedBankAccount();
        account.deposit(100);
        assertThrows(
            InsufficientFundsException.class,
            () -> account.withdraw(150));
        assertEquals(100, account.getBalance());
    }

    @Test
    void concurrentDepositsLoseNoUpdates() throws InterruptedException {
        SynchronizedBankAccount account = new SynchronizedBankAccount();
        int threadCount = 8;
        int repetitions = 50_000;

        runConcurrently(account, threadCount, 1, repetitions);

        assertEquals(threadCount * repetitions, account.getBalance());
    }

    @Test
    void concurrentDepositsAndWithdrawalsKeepBalance() throws InterruptedException {
        SynchronizedBankAccount account = new SynchronizedBankAccount();
        int threadPairs = 4;
        int repetitions = 50_000;
        int startBalance = threadPairs * repetitions;
        account.deposit(startBalance);

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadPairs; i++) {
            threads.add(new Thread(new BankingOperation(account, 1, repetitions)));
            threads.add(new Thread(new BankingOperation(account, -1, repetitions)));
        }
        startAndJoin(threads);

        assertEquals(startBalance, account.getBalance());
    }

    private static void runConcurrently(BankAccount account, int threadCount, int amount, int repetitions)
            throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadCount; i++) {
            threads.add(new Thread(new BankingOperation(account, amount, repetitions)));
        }
        startAndJoin(threads);
    }

    private static void startAndJoin(List<Thread> threads) throws InterruptedException {
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
    }
}
