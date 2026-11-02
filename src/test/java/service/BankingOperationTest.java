package service;

import model.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankingOperationTest {

    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        bankAccount = new BankAccount();
    }

    @Test
    void constructorThrowsExceptionWhenAccountIsNull() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new BankingOperation(null, 100, 10)
        );
    }

    @Test
    void constructorThrowsExceptionWhenAmountIsZero() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new BankingOperation(bankAccount, 0, 10)
        );
    }

    @Test
    void constructorThrowsExceptionWhenRepetitionsAreNegative() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new BankingOperation(bankAccount, 100, -1)
        );
    }

    @Test
    void constructorAcceptsPositiveAmount() {
        assertDoesNotThrow(
            () -> new BankingOperation(bankAccount, 100, 10)
        );
    }

    @Test
    void constructorAcceptsNegativeAmount() {
        assertDoesNotThrow(
            () -> new BankingOperation(bankAccount, -100, 10)
        );
    }

    @Test
    void constructorAcceptsZeroRepetitions() {
        assertDoesNotThrow(
            () -> new BankingOperation(bankAccount, 100, 0)
        );
    }

    @Test
    void runDepositsMoneyOnce() {

        BankingOperation operation =
            new BankingOperation(bankAccount, 100, 1);

        operation.run();

        assertEquals(100, bankAccount.getBalance());
    }

    @Test
    void runDepositsMoneyMultipleTimes() {

        BankingOperation operation =
            new BankingOperation(bankAccount, 100, 5);

        operation.run();

        assertEquals(500, bankAccount.getBalance());
    }

    @Test
    void runWithdrawsMoneyOnce() {

        bankAccount.deposit(500);

        BankingOperation operation =
            new BankingOperation(bankAccount, -100, 1);

        operation.run();

        assertEquals(400, bankAccount.getBalance());
    }

    @Test
    void runWithdrawsMoneyMultipleTimes() {

        bankAccount.deposit(1000);

        BankingOperation operation =
            new BankingOperation(bankAccount, -100, 3);

        operation.run();

        assertEquals(700, bankAccount.getBalance());
    }

    @Test
    void runDoesNothingWhenRepetitionsIsZero() {

        bankAccount.deposit(500);

        BankingOperation operation =
            new BankingOperation(bankAccount, 100, 0);

        operation.run();

        assertEquals(500, bankAccount.getBalance());
    }

    @Test
    void runIgnoresFailedWithdrawal() {

        BankingOperation operation =
            new BankingOperation(bankAccount, -100, 1);

        assertDoesNotThrow(operation::run);

        assertEquals(0, bankAccount.getBalance());
    }

    @Test
    void runSkipsOnlyFailedWithdrawals() {

        bankAccount.deposit(250);

        BankingOperation operation =
            new BankingOperation(bankAccount, -100, 5);

        assertDoesNotThrow(operation::run);

        assertEquals(50, bankAccount.getBalance());
    }

    @Test
    void runTwiceBooksTwice() {

        BankingOperation operation =
            new BankingOperation(bankAccount, 100, 2);

        operation.run();
        operation.run();

        assertEquals(400, bankAccount.getBalance());
    }

    @Test
    void constructorThrowsExceptionWhenAmountIsMinValue() {
        assertThrows(
            IllegalArgumentException.class,
            () -> new BankingOperation(bankAccount, Integer.MIN_VALUE, 1)
        );
    }
}
