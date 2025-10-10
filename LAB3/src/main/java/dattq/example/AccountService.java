package dattq.example;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class AccountService {



    private static Set<String> list = new HashSet<>();

    public static void clearUserList() {
        list.clear();
    }

    public static boolean registerAccount(String username, String password, String email) {
        if (username == null
                || username.length() <=3
                || (existUserName(username))
                || password == null
                || username.isBlank()
                || password.isBlank()
                || password.length() < 6
                || !isValidEmail(email)) {
            return false;
        }
        list.add(username);
        return true;
    }

    private static boolean existUserName(String username) {
        return list.contains(username);
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        String regex = "^[a-zA-Z0-9._%+-]+@([a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        return Pattern.matches(regex, email);
    }
}
