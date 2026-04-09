# Secure Password Manager (Java)

A professional, modern, and robust Java desktop application for managing user credentials securely. Built exclusively with Core Java concepts, this project aligns perfectly with university syllabus standards while demonstrating enterprise-level logic.

## Key Features
- **Modern Swing GUI**: Uses Nimbus LookAndFeel and GridBagLayout for a clean user interface.
- **Double Encryption Algorithm**: Leverages Reverse String processing integrated with Character Shifting (+2 padding).
- **Asynchronous Processing**: Background threads seamlessly handle encrypting and loading animations without hanging the UI.
- **Robust File Management**: Uses `BufferedReader/BufferedWriter` to handle I/O gracefully alongside automated `backup.txt` configurations.
- **Data Validation & Exceptions**: Pre-configured with custom exceptions like `PasswordNotFoundException` mapped to responsive `JOptionPane` dialog alerts.

## Technologies Used
- **Language**: Java 8+
- **GUI Framework**: javax.swing.*, java.awt.*
- **Storage**: Flat-file database (.txt files)
- **Concepts**: Object-Oriented Programming (OOP), Multithreading, File I/O, Event Handling, Exceptions.

## Execution Steps
1. Navigate to the root folder `secure-password-manager-java` in your terminal.
2. Compile the Java files:
   ```bash
   javac passwordmanager/model/*.java passwordmanager/core/*.java passwordmanager/util/*.java passwordmanager/ui/*.java passwordmanager/Main.java
   ```
3. Run the application:
   ```bash
   java passwordmanager.Main
   ```

## Viva Explanation Points
1. **How does standard encryption work?** We manipulate the string by reversing its array components entirely and utilizing constant padding shifts, demonstrating native `String` manipulation loops securely before saving to plain text files.
2. **Why use Threads in this application?** It ensures that `Java Swing` UI graphics don't freeze and lock up when file reading and encryption operations trigger artificial `Thread.sleep` delays, preventing user disruption.
3. **What happens during invalid inputs?** Duplicate checks prevent overriding active arrays, while `try-catch` structures filter errors passing clear graphical popups out towards the user instead of printing blind console stack traces.
