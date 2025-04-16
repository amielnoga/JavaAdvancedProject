package BackEnd.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility class for handling password-related operations, including hashing (cryptographic process that converts an
 * input into a fixed-length string) passwords for secure storage and verifying passwords during login.
 */
public class PasswordUtil {

    private static final int COST_FACTOR = 10; // A higher cost factor makes it more secure but slower

    /**
     * The method hash the password before storing it on DB
     *
     * @param rawUserPassword the password that the user inputs before any processing in the sign-up page / change
     *                        password page
     * @return the user encrypted password (after processing)
     */
    public static String hashPassword(String rawUserPassword) {
        return BCrypt.hashpw(rawUserPassword, BCrypt.gensalt(COST_FACTOR));
    }

    /**
     * The method verify if the provided password the user entered the login page matches the stored hash password
     *
     * @param rawUserPassword   the password that the user inputs before any processing in the sign-in page
     * @param encryptedPassword the user encrypted password (after processing) from the DB
     * @return true if the provided password matches, false otherwise
     */
    public static boolean checkPassword(String rawUserPassword, String encryptedPassword) {
        return BCrypt.checkpw(rawUserPassword, encryptedPassword);
    }
}
