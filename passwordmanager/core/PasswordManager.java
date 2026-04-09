package passwordmanager.core;

import passwordmanager.model.PasswordEntry;
import passwordmanager.util.EncryptionUtil;
import passwordmanager.util.FileHandler;
import passwordmanager.util.PasswordNotFoundException;

import java.io.IOException;
import java.util.List;

public class PasswordManager {
    private List<PasswordEntry> memoryVault;

    public PasswordManager() {
        try {
            memoryVault = FileHandler.loadPasswords();
        } catch (IOException e) {
            memoryVault = new java.util.ArrayList<>();
            System.err.println("File not found or IO Exception. Starting fresh vault.");
        }
    }

    public List<PasswordEntry> getAllPasswords() {
        return memoryVault;
    }

    public void addPassword(String website, String username, String rawPassword) throws IOException {
        if (website.isEmpty() || username.isEmpty() || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("Fields cannot be empty!");
        }

        // Prevent exact duplicate records
        for (PasswordEntry entry : memoryVault) {
            if (entry.getWebsite().equalsIgnoreCase(website) && entry.getUsername().equalsIgnoreCase(username)) {
                throw new IllegalArgumentException("Duplicate Entry: Record for this website & username already exists!");
            }
        }

        String encrypted = EncryptionUtil.encrypt(rawPassword);
        PasswordEntry newEntry = new PasswordEntry(website, username, encrypted);
        memoryVault.add(newEntry);
        
        FileHandler.savePassword(newEntry);
    }

    public PasswordEntry searchPassword(String website) throws PasswordNotFoundException {
        if (website.trim().isEmpty()) {
            throw new IllegalArgumentException("Search term is empty.");
        }
        for (PasswordEntry entry : memoryVault) {
            if (entry.getWebsite().equalsIgnoreCase(website.trim())) {
                return entry;
            }
        }
        throw new PasswordNotFoundException("No password entry found for website: " + website);
    }

    public void updatePassword(String website, String newRawPassword) throws PasswordNotFoundException, IOException {
        PasswordEntry target = searchPassword(website); // Will throw if missing
        if (newRawPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("New password cannot be empty.");
        }
        target.setEncryptedPassword(EncryptionUtil.encrypt(newRawPassword));
        FileHandler.saveAllPasswords(memoryVault);
    }

    public void deletePassword(String website) throws PasswordNotFoundException, IOException {
        PasswordEntry target = searchPassword(website);
        memoryVault.remove(target);
        FileHandler.saveAllPasswords(memoryVault);
    }
}
