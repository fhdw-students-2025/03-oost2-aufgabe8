package model;

/**
 * Thrown when a withdrawal exceeds the current balance of a {@link BankAccount}.
 */
public class InsufficientFundsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    /**
     * Creates the exception with the given detail message.
     *
     * @param message the detail message
     */
    public InsufficientFundsException(String message) {
        super(message);
    }
}
