package OBSApp.core.exceptions;

public class BillNotFoundException extends RuntimeException{
    public BillNotFoundException() {
        super("specified Bill not Found");
    }
}
