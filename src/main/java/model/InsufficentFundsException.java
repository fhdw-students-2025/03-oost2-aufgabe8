package model;

public class InsufficientFundsException extends RuntimeException {
    public InsufficentFundsException(String message) {
        super(message);
    }
}
