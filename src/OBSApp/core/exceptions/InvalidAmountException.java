package OBSApp.core.exceptions;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException() {
        super("this amount is invalid");
    }
}
