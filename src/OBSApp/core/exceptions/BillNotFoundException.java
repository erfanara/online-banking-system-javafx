package OBSApp.core.exceptions;

import OBSApp.client.formViews.Message;

public class BillNotFoundException extends RuntimeException{
    public BillNotFoundException() {
        Message.ShowMessage("specified Bill not Found");
//        super("specified Bill not Found");
    }
}
