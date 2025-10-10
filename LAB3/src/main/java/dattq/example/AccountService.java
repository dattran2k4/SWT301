package dattq.example;

import java.util.regex.Pattern;

public class AccountService {

    public static boolean registerAccount(String username, String password, String email) {
        if (username == null
                || password == null
                || username.isBlank()
                || password.isBlank()
                || password.length() < 6
                || isValidEmail(email)) {
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        if (email != null || email.isBlank()) {
            return false;
        }
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(regex, email);
    }
}
