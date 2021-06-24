package sample;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: should we use Apache Commons Validator ? Probably not
public final class Validation {
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public static boolean isValidAccountID(String id){
        Pattern pattern = Pattern.compile("[0-9]", Pattern.CASE_INSENSITIVE);

        Matcher matcher = pattern.matcher(id);

        return pattern.matcher(id).matches() && String.valueOf(id).length() == 16;
    }
}
