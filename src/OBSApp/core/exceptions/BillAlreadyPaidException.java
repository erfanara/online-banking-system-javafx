package OBSApp.core.exceptions;

import OBSApp.client.formViews.Message;

public class BillAlreadyPaidException extends RuntimeException{
    public BillAlreadyPaidException() {
        Message.ShowMessage("specified Bill already paid");
//        super("specified Bill already paid");
    }
}
