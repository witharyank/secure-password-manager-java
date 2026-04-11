package passwordmanager;

import passwordmanager.ui.MainUI;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {

        // Ensures that all UI updates run on the Event Dispatch Thread (EDT)
        // This is important for thread safety in Swing applications
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                try {
                    // Set a modern look and feel (Nimbus)
                    // Loop through installed themes and apply Nimbus if available
                    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            UIManager.setLookAndFeel(info.getClassName());
                            break; // Stop once Nimbus is applied
                        }
                    }
                } catch (Exception e) {
                    // If Nimbus is not available, fallback to default look and feel
                    // No crash — keeps app running smoothly
                }

                // Launch the main UI of the password manager application
                new MainUI().setVisible(true);
            }
        });
    }
}