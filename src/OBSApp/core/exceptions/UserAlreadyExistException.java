package OBSApp.core.exceptions;

import OBSApp.client.formViews.Message;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        Message.ShowMessage("This user with this national code already Exist");
//        super("This user with this national code already Exist");
    }
}
