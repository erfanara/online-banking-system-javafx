package OBSApp.core.exceptions;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        super("your account balance is not enough");
    }
}
