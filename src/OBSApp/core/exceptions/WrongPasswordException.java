package OBSApp.core.exceptions;

import OBSApp.client.formViews.Message;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException() {
        Message.ShowMessage("Password is wrong");
//        super("Password is wrong");
    }
}
