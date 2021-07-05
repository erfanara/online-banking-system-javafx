package OBSApp.core.exceptions;

public class BalanceIsNotZeroException extends RuntimeException {
    public BalanceIsNotZeroException() {
        super("your account balance is not zero, please deposit your balance to another account, and try agian");
    }
}
