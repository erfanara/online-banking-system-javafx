package obsjApp.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: should we use Apache Commons Validator ? Probably not
public final class Validation {
    public static boolean isValidEmail(String email) {
        if (email == null)
            return false;

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        return pat.matcher(email).matches();
    }

    public static boolean isValidAccountID(String id) {
        if (id == null)
            return false;

        Pattern pat = Pattern.compile("^[1-9][0-9]{15}$");
        return pat.matcher(id).matches();
    }
}
