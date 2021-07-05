package OBSApp.core.exceptions;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("This user with this national code already Exist");
    }
}
