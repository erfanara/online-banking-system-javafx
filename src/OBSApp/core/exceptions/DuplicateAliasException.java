package OBSApp.core.exceptions;

public class DuplicateAliasException extends RuntimeException {
    public DuplicateAliasException() {
        super("this alias is already submited for another account");
    }
}
