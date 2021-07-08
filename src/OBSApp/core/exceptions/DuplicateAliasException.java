package OBSApp.core.exceptions;

import OBSApp.client.formViews.Message;

public class DuplicateAliasException extends RuntimeException {
    public DuplicateAliasException() {
        Message.ShowMessage("this alias is already submitted for another account");
//        super("this alias is already submitted for another account");
    }
}
