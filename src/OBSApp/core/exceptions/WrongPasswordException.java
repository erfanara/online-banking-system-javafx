package OBSApp.core.exceptions;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        super("Password is wrong");
    }
}
