package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BankAccountTest {

    private BankAccount testAccount;

    @BeforeEach
    void setUp() {
        testAccount = new BankAccount();
        testAccount.deposit(100);
    }

    @Test
    void depositIncreasesBalanceWhenAmountIsOne() {
        testAccount.deposit(1);
        assertEquals(101, testAccount.getBalance());
    }

    @Test
    void depositIncreasesBalanceByAmount() {
        testAccount.deposit(30);
        assertEquals(130, testAccount.getBalance());
    }

    @Test
    void depositDoesNotChangeBalanceWhenAmountIsNegative() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.deposit(-10));
        assertEquals(100, testAccount.getBalance());
    }

    @Test
    void withdrawAcceptsFullBalance() {
        testAccount.withdraw(100);
        assertEquals(0, testAccount.getBalance());
    }

    @Test
    void withdrawDecreasesBalanceWhenAmountIsJustBelowBalance() {
        testAccount.withdraw(99);
        assertEquals(1, testAccount.getBalance());
    }

    @Test
    void withdrawDecreasesBalanceByAmount() {
        testAccount.withdraw(30);
        assertEquals(70, testAccount.getBalance());
    }

    @Test
    void withdrawThrowsExceptionWhenAmountIsNegative() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.withdraw(-10));
    }

    @Test
    void withdrawThrowsExceptionWhenAmountExceedsBalance() {
        assertThrows(
            InsufficientFundsException.class,
            () -> testAccount.withdraw(150));
    }

    @Test
    void withdrawDoesNotChangeBalanceWhenAmountExceedsBalance() {
        assertThrows(
            InsufficientFundsException.class,
            () -> testAccount.withdraw(150));
        assertEquals(100, testAccount.getBalance());
    }

    @Test
    void getBalanceReturnsCurrentBalance() {
        assertEquals(100, testAccount.getBalance());
    }

    @Test
    void constructorCreatesAccountWithZeroBalance() {
        assertEquals(0, new BankAccount().getBalance());
    }

    @Test
    void depositDoesNotChangeBalanceWhenAmountIsZero() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.deposit(0));
        assertEquals(100, testAccount.getBalance());
    }

    @Test
    void withdrawDoesNotChangeBalanceWhenAmountIsZero() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.withdraw(0));
        assertEquals(100, testAccount.getBalance());
    }

    @Test
    void withdrawDoesNotChangeBalanceWhenAmountIsNegative() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.withdraw(-10));
        assertEquals(100, testAccount.getBalance());
    }

    @Test
    void withdrawThrowsExceptionWhenAccountIsEmpty() {
        BankAccount emptyAccount = new BankAccount();
        assertThrows(
            InsufficientFundsException.class,
            () -> emptyAccount.withdraw(1));
        assertEquals(0, emptyAccount.getBalance());
    }

    @Test
    void depositAcceptsMaxValueWhenAccountIsEmpty() {
        BankAccount emptyAccount = new BankAccount();
        emptyAccount.deposit(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, emptyAccount.getBalance());
    }

    @Test
    void depositThrowsExceptionWhenBalanceWouldOverflow() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.deposit(Integer.MAX_VALUE));
        assertEquals(100, testAccount.getBalance());
    }
}
