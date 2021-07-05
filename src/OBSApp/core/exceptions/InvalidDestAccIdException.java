package OBSApp.core.exceptions;

public class InvalidDestAccIdException extends RuntimeException {
    public InvalidDestAccIdException() {
        super("Destination Account id is invalid");
    }
}
