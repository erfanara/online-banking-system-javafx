package OBSApp.core.exceptions;

public class PictureNotFoundException extends RuntimeException {
    public PictureNotFoundException() {
        super("requested picture not found");
    }
}
