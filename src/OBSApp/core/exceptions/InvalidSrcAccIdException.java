package OBSApp.core.exceptions;

import OBSApp.client.formViews.Message;

public class InvalidSrcAccIdException extends RuntimeException {
    public InvalidSrcAccIdException() {
        Message.ShowMessage("Source Account id is invalid");
//        super("Source Account id is invalid");
    }
}
