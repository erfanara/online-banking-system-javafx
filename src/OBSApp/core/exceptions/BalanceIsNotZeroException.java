package OBSApp.core.exceptions;

import OBSApp.client.formViews.Message;

public class BalanceIsNotZeroException extends RuntimeException {
    public BalanceIsNotZeroException() {
        Message.ShowMessage("your account balance is not zero, please deposit your balance to another account, and try agian");
//        super("your account balance is not zero, please deposit your balance to another account, and try agian");
    }
}
