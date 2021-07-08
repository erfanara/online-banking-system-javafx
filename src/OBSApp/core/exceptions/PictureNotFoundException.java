package OBSApp.core.exceptions;

import OBSApp.client.formViews.Message;

public class PictureNotFoundException extends RuntimeException {
    public PictureNotFoundException() {
        Message.ShowMessage("requested picture not found");
//        super("requested picture not found");
    }
}
