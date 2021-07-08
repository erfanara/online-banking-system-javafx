package OBSApp.core.exceptions;

import OBSApp.client.formViews.Message;

public class LogInFirstException extends RuntimeException {
    public LogInFirstException() {
        Message.ShowMessage("you have to LogIn first");
//        super("you have to LogIn first");
    }
}
