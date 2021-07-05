package OBSApp.core.exceptions;

public class BillAlreadyPaidException extends RuntimeException{
    public BillAlreadyPaidException() {
        super("specified Bill already paid");
    }
}
