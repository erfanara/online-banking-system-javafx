package OBSApp.core.exceptions;

import OBSApp.client.formViews.Message;

public class InvalidDestAccIdException extends RuntimeException {
    public InvalidDestAccIdException() {
        Message.ShowMessage("Destination Account id is invalid");
//        super("Destination Account id is invalid");
    }
}
