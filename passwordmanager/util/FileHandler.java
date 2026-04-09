package passwordmanager.util;

import passwordmanager.model.PasswordEntry;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {
    private static final String FILE_NAME = "passwords.txt";
    private static final String BACKUP_NAME = "backup.txt";

    public static List<PasswordEntry> loadPasswords() throws IOException {
        return loadPasswords(FILE_NAME);
    }

    public static List<PasswordEntry> loadPasswords(String filename) throws IOException {
        List<PasswordEntry> entries = new ArrayList<>();
        File file = new File(filename);

        if (!file.exists()) {
            return entries;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    entries.add(new PasswordEntry(parts[0], parts[1], parts[2]));
                }
            }
        }
        return entries;
    }

    public static void savePassword(PasswordEntry entry) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            bw.write(entry.getWebsite() + "," + entry.getUsername() + "," + entry.getEncryptedPassword());
            bw.newLine();
        }
        createBackup();
    }

    public static void saveAllPasswords(List<PasswordEntry> entries) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, false))) {
            for (PasswordEntry entry : entries) {
                bw.write(entry.getWebsite() + "," + entry.getUsername() + "," + entry.getEncryptedPassword());
                bw.newLine();
            }
        }
        createBackup();
    }
    
    private static void createBackup() {
        try {
            List<PasswordEntry> current = loadPasswords(FILE_NAME);
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(BACKUP_NAME, false))) {
                for (PasswordEntry entry : current) {
                    bw.write(entry.getWebsite() + "," + entry.getUsername() + "," + entry.getEncryptedPassword());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Backup failed: " + e.getMessage());
        }
    }
}
