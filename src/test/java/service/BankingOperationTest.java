package service;

import model.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankingOperationTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount();
    }

    @Test
    void constructorThrowsExceptionWhenAccountIsNull() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new BankingOperation(null, 100, 10));
    }

    @Test
    void constructorThrowsExceptionWhenAmountIsZero() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new BankingOperation(account, 0, 10));
    }

    @Test
    void constructorThrowsExceptionWhenRepetitionsAreNegative() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new BankingOperation(account, 100, -1));
    }

    @Test
    void constructorAcceptsPositiveAmount() {
        assertDoesNotThrow(
            () -> new BankingOperation(account, 100, 10));
    }

    @Test
    void constructorAcceptsNegativeAmount() {
        assertDoesNotThrow(
            () -> new BankingOperation(account, -100, 10));
    }

    @Test
    void constructorAcceptsZeroRepetitions() {
        assertDoesNotThrow(
            () -> new BankingOperation(account, 100, 0));
    }

    @Test
    void runDepositsMoneyOnce() {
        BankingOperation operation = new BankingOperation(account, 100, 1);

        operation.run();

        assertEquals(100, account.getBalance());
    }

    @Test
    void runDepositsMoneyMultipleTimes() {
        BankingOperation operation = new BankingOperation(account, 100, 5);

        operation.run();

        assertEquals(500, account.getBalance());
    }

    @Test
    void runWithdrawsMoneyOnce() {
        account.deposit(500);

        BankingOperation operation = new BankingOperation(account, -100, 1);

        operation.run();

        assertEquals(400, account.getBalance());
    }

    @Test
    void runWithdrawsMoneyMultipleTimes() {
        account.deposit(1000);

        BankingOperation operation = new BankingOperation(account, -100, 3);

        operation.run();

        assertEquals(700, account.getBalance());
    }

    @Test
    void runDoesNothingWhenRepetitionsIsZero() {
        account.deposit(500);

        BankingOperation operation = new BankingOperation(account, 100, 0);

        operation.run();

        assertEquals(500, account.getBalance());
    }

    @Test
    void runIgnoresFailedWithdrawal() {
        BankingOperation operation = new BankingOperation(account, -100, 1);

        assertDoesNotThrow(operation::run);

        assertEquals(0, account.getBalance());
    }

    @Test
    void runSkipsOnlyFailedWithdrawals() {
        account.deposit(250);

        BankingOperation operation = new BankingOperation(account, -100, 5);

        assertDoesNotThrow(operation::run);

        assertEquals(50, account.getBalance());
    }

    @Test
    void runTwiceBooksTwice() {
        BankingOperation operation = new BankingOperation(account, 100, 2);

        operation.run();
        operation.run();

        assertEquals(400, account.getBalance());
    }

    @Test
    void constructorThrowsExceptionWhenAmountIsMinValue() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new BankingOperation(account, Integer.MIN_VALUE, 1));
    }
}
