package OBSApp.core.exceptions;

public class InvalidSrcAccIdException extends RuntimeException {
    public InvalidSrcAccIdException() {
        super("Source Account id is invalid");
    }
}
