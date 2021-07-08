package OBSApp.core.exceptions;

import OBSApp.client.formViews.Message;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException() {
        Message.ShowMessage("this amount is invalid");
//        super("this amount is invalid");
    }
}
