package passwordmanager.model;

/**
 * Model class demonstrating Encapsulation and Constructors.
 */
public class PasswordEntry {
    private String website;
    private String username;
    private String encryptedPassword;

    public PasswordEntry(String website, String username, String encryptedPassword) {
        this.website = website;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    @Override
    public String toString() {
        return website + " | " + username + " | " + encryptedPassword;
    }
    
    // Method Overloading demonstration
    public String toString(boolean decrypted, String realPassword) {
        if(decrypted) {
            return website + " | " + username + " | " + realPassword;
        }
        return toString();
    }
}
