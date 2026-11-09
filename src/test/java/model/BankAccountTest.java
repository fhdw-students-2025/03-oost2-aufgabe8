package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount();
        account.deposit(100);
    }

    @Test
    void depositIncreasesBalanceWhenAmountIsOne() {
        account.deposit(1);
        assertEquals(101, account.getBalance());
    }

    @Test
    void depositIncreasesBalanceByAmount() {
        account.deposit(30);
        assertEquals(130, account.getBalance());
    }

    @Test
    void depositDoesNotChangeBalanceWhenAmountIsNegative() {
        assertThrows(
            IllegalArgumentException.class,
            () -> account.deposit(-10));
        assertEquals(100, account.getBalance());
    }

    @Test
    void withdrawAcceptsFullBalance() {
        account.withdraw(100);
        assertEquals(0, account.getBalance());
    }

    @Test
    void withdrawDecreasesBalanceWhenAmountIsJustBelowBalance() {
        account.withdraw(99);
        assertEquals(1, account.getBalance());
    }

    @Test
    void withdrawDecreasesBalanceByAmount() {
        account.withdraw(30);
        assertEquals(70, account.getBalance());
    }

    @Test
    void withdrawDoesNotChangeBalanceWhenAmountExceedsBalance() {
        assertThrows(
            InsufficientFundsException.class,
            () -> account.withdraw(150));
        assertEquals(100, account.getBalance());
    }

    @Test
    void getBalanceReturnsCurrentBalance() {
        assertEquals(100, account.getBalance());
    }

    @Test
    void constructorCreatesAccountWithZeroBalance() {
        assertEquals(0, new BankAccount().getBalance());
    }

    @Test
    void depositDoesNotChangeBalanceWhenAmountIsZero() {
        assertThrows(
            IllegalArgumentException.class,
            () -> account.deposit(0));
        assertEquals(100, account.getBalance());
    }

    @Test
    void withdrawDoesNotChangeBalanceWhenAmountIsZero() {
        assertThrows(
            IllegalArgumentException.class,
            () -> account.withdraw(0));
        assertEquals(100, account.getBalance());
    }

    @Test
    void withdrawDoesNotChangeBalanceWhenAmountIsNegative() {
        assertThrows(
            IllegalArgumentException.class,
            () -> account.withdraw(-10));
        assertEquals(100, account.getBalance());
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
            () -> account.deposit(Integer.MAX_VALUE));
        assertEquals(100, account.getBalance());
    }
}
