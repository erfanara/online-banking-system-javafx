package OBSApp.core.exceptions;

public class LogInFirstException extends RuntimeException {
    public LogInFirstException() {
        super("you have to LogIn first");
    }
}
