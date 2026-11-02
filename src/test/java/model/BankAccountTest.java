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
    public void deposit0Test() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testAccount.deposit(0));
    }

    @Test
    public void deposit1Test() {
        testAccount.deposit(1);
        assertEquals(101, testAccount.getBalance());
    }

    @Test
    public void deposit30Test() {
        testAccount.deposit(30);
        assertEquals(130, testAccount.getBalance());
    }

    @Test
    public void depositNegativeTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testAccount.deposit(-10));
    }

    @Test
    public void depositNegativeDoesntChangeBalanceTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testAccount.deposit(-10));
        assertEquals(100, testAccount.getBalance());
    }

    @Test
    public void withdraw0Test() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testAccount.withdraw(0));
    }

    @Test
    public void withdrawFullBalanceTest() {
        testAccount.withdraw(100);
        assertEquals(0, testAccount.getBalance());
    }

    @Test
    public void withdraw99Test() {
        testAccount.withdraw(99);
        assertEquals(1, testAccount.getBalance());
    }

    @Test
    public void withdraw101Test() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testAccount.withdraw(101));
    }

    @Test
    public void withdraw30Test() {
        testAccount.withdraw(30);
        assertEquals(70, testAccount.getBalance());
    }

    @Test
    public void withdrawNegativeTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testAccount.withdraw(-10));
    }

    @Test
    public void withdrawMoreThanBalanceTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testAccount.withdraw(150));
    }

    @Test
    public void withdrawMoreThanBalanceDoesntChangeBalanceTest() {
        assertThrows(
                IllegalArgumentException.class,
                () -> testAccount.withdraw(150));
        assertEquals(100,  testAccount.getBalance());
    }

    @Test
    public void getBalanceTest() {
        assertEquals(100, testAccount.getBalance());
    }
}