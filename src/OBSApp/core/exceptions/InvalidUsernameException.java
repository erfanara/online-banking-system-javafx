package OBSApp.core.exceptions;

public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException() {
        super("Username is invalid");
    }
}
