package OBSApp.core.exceptions;

import OBSApp.client.formViews.Message;

public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException() {
        Message.ShowMessage("Username is invalid");
//        super("Username is invalid");
    }
}
