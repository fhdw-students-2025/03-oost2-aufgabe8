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
    public void depositThrowsExceptionWhenAmountIsZero() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.deposit(0));
    }

    @Test
    public void depositIncreasesBalanceWhenAmountIsOne() {
        testAccount.deposit(1);
        assertEquals(101, testAccount.getBalance());
    }

    @Test
    public void depositIncreasesBalanceByAmount() {
        testAccount.deposit(30);
        assertEquals(130, testAccount.getBalance());
    }

    @Test
    public void depositThrowsExceptionWhenAmountIsNegative() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.deposit(-10));
    }

    @Test
    public void depositDoesNotChangeBalanceWhenAmountIsNegative() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.deposit(-10));
        assertEquals(100, testAccount.getBalance());
    }

    @Test
    public void withdrawThrowsExceptionWhenAmountIsZero() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.withdraw(0));
    }

    @Test
    public void withdrawAcceptsFullBalance() {
        testAccount.withdraw(100);
        assertEquals(0, testAccount.getBalance());
    }

    @Test
    public void withdrawDecreasesBalanceWhenAmountIsJustBelowBalance() {
        testAccount.withdraw(99);
        assertEquals(1, testAccount.getBalance());
    }

    @Test
    public void withdrawThrowsExceptionWhenAmountIsJustAboveBalance() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.withdraw(101));
    }

    @Test
    public void withdrawDecreasesBalanceByAmount() {
        testAccount.withdraw(30);
        assertEquals(70, testAccount.getBalance());
    }

    @Test
    public void withdrawThrowsExceptionWhenAmountIsNegative() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.withdraw(-10));
    }

    @Test
    public void withdrawThrowsExceptionWhenAmountExceedsBalance() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.withdraw(150));
    }

    @Test
    public void withdrawDoesNotChangeBalanceWhenAmountExceedsBalance() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.withdraw(150));
        assertEquals(100, testAccount.getBalance());
    }

    @Test
    public void getBalanceReturnsCurrentBalance() {
        assertEquals(100, testAccount.getBalance());
    }

    @Test
    public void constructorCreatesAccountWithZeroBalance() {
        assertEquals(0, new BankAccount().getBalance());
    }

    @Test
    public void depositDoesNotChangeBalanceWhenAmountIsZero() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.deposit(0));
        assertEquals(100, testAccount.getBalance());
    }

    @Test
    public void withdrawDoesNotChangeBalanceWhenAmountIsZero() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.withdraw(0));
        assertEquals(100, testAccount.getBalance());
    }

    @Test
    public void withdrawDoesNotChangeBalanceWhenAmountIsNegative() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.withdraw(-10));
        assertEquals(100, testAccount.getBalance());
    }

    @Test
    public void withdrawThrowsExceptionWhenAccountIsEmpty() {
        BankAccount emptyAccount = new BankAccount();
        assertThrows(
            IllegalArgumentException.class,
            () -> emptyAccount.withdraw(1));
        assertEquals(0, emptyAccount.getBalance());
    }

    @Test
    public void depositAcceptsMaxValueWhenAccountIsEmpty() {
        BankAccount emptyAccount = new BankAccount();
        emptyAccount.deposit(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, emptyAccount.getBalance());
    }

    @Test
    public void depositThrowsExceptionWhenBalanceWouldOverflow() {
        assertThrows(
            IllegalArgumentException.class,
            () -> testAccount.deposit(Integer.MAX_VALUE));
        assertEquals(100, testAccount.getBalance());
    }
}
