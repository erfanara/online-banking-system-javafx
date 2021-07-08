package OBSApp.core.exceptions;

import OBSApp.client.formViews.Message;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {
        Message.ShowMessage("your account balance is not enough");
//        super("your account balance is not enough");
    }
}
