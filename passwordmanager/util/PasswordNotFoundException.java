package passwordmanager.util;

/**
 * Custom Exception demonstrating Exception Handling topics.
 */
public class PasswordNotFoundException extends Exception {
    public PasswordNotFoundException(String message) {
        super(message);
    }
}
