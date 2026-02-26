package Parent.Utils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Holds test user data so login and update-account scenarios can use
 * the same user we just registered (with random suffix).
 */
public class TestData {

    private static String lastRegisteredUsername;
    private static String lastRegisteredPassword;
    private static final String DEFAULT_PASSWORD = "Test123!";

    public static String randomSuffix() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(1000, 99999));
    }

    /** Build unique username/email so registration can run many times. */
    public static String uniqueUsername(String baseName) {
        return baseName + randomSuffix();
    }

    public static String uniqueEmail(String baseName) {
        return baseName + randomSuffix() + "@team.test";
    }

    public static void setLastRegisteredUser(String username, String password) {
        lastRegisteredUsername = username;
        lastRegisteredPassword = password;
    }

    public static String getLastRegisteredUsername() {
        return lastRegisteredUsername;
    }

    public static String getLastRegisteredPassword() {
        return lastRegisteredPassword != null ? lastRegisteredPassword : DEFAULT_PASSWORD;
    }

    public static void clear() {
        lastRegisteredUsername = null;
        lastRegisteredPassword = null;
    }
}
