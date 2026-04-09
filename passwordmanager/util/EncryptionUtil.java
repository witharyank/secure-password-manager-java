package passwordmanager.util;

/**
 * Handles cryptography combining Reverse ordering and String Shifting.
 */
public class EncryptionUtil {
    private static final int SHIFT = 2;

    public static String encrypt(String rawPassword) {
        String reversed = new StringBuilder(rawPassword).reverse().toString();
        StringBuilder encrypted = new StringBuilder();
        for (char c : reversed.toCharArray()) {
            encrypted.append((char) (c + SHIFT));
        }
        return encrypted.toString();
    }

    public static String decrypt(String encryptedPassword) {
        StringBuilder decrypted = new StringBuilder();
        for (char c : encryptedPassword.toCharArray()) {
            decrypted.append((char) (c - SHIFT));
        }
        return decrypted.reverse().toString();
    }
}
