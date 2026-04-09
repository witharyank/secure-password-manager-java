package passwordmanager.ui;

import passwordmanager.core.PasswordManager;
import passwordmanager.model.PasswordEntry;
import passwordmanager.util.EncryptionUtil;
import passwordmanager.util.PasswordNotFoundException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class MainUI extends JFrame {
    private PasswordManager passwordManager;
    private JTextField websiteField, usernameField;
    private JPasswordField passwordField;
    private JCheckBox showPassCheckbox;
    private JLabel statusLabel;
    private JButton saveBtn, updateBtn, deleteBtn, clearBtn;
    
    private JTable passwordTable;
    private DefaultTableModel tableModel;

    public MainUI() {
        passwordManager = new PasswordManager();
        setTitle("Secure Password Manager");
        setSize(850, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        initComponents();
        refreshTable();
    }

    private void initComponents() {
        // --- INPUT PANEL (GridBagLayout) ---
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Manage Credentials"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        Font labelFont = new Font("Segoe UI", Font.BOLD, 12);
        Font inputFont = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel webLabel = new JLabel("Website:");
        webLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 0;
        inputPanel.add(webLabel, gbc);

        websiteField = new JTextField(20);
        websiteField.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 0;
        inputPanel.add(websiteField, gbc);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 1;
        inputPanel.add(userLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 1;
        inputPanel.add(usernameField, gbc);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 2;
        inputPanel.add(passLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(inputFont);
        gbc.gridx = 1; gbc.gridy = 2;
        inputPanel.add(passwordField, gbc);

        JCheckBox showInputPass = new JCheckBox("Show Input");
        showInputPass.addActionListener(e -> {
            if (showInputPass.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('•');
            }
        });
        gbc.gridx = 2; gbc.gridy = 2;
        inputPanel.add(showInputPass, gbc);

        // --- BUTTON PANEL ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        saveBtn = new JButton("Save New");
        saveBtn.setBackground(new Color(40, 167, 69)); // Green
        saveBtn.setForeground(Color.WHITE);
        
        updateBtn = new JButton("Update Selected");
        updateBtn.setBackground(new Color(23, 162, 184)); // Blue
        updateBtn.setForeground(Color.WHITE);

        deleteBtn = new JButton("Delete Selected");
        deleteBtn.setBackground(new Color(220, 53, 69)); // Red
        deleteBtn.setForeground(Color.WHITE);

        clearBtn = new JButton("Clear Fields");

        buttonPanel.add(saveBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(clearBtn);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 3;
        inputPanel.add(buttonPanel, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // --- TABLE PANEL ---
        String[] columns = {"Website", "Username", "Password"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        passwordTable = new JTable(tableModel);
        passwordTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordTable.setRowHeight(25);
        passwordTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBorder(BorderFactory.createTitledBorder("Vault Records"));
        tableWrapper.add(new JScrollPane(passwordTable), BorderLayout.CENTER);

        showPassCheckbox = new JCheckBox("Show Decrypted Passwords in Vault");
        showPassCheckbox.setFont(new Font("Segoe UI", Font.BOLD, 12));
        showPassCheckbox.addActionListener(e -> refreshTable());
        tableWrapper.add(showPassCheckbox, BorderLayout.SOUTH);

        add(tableWrapper, BorderLayout.CENTER);

        // --- STATUS PANEL ---
        statusLabel = new JLabel("Status: Ready to securely manage credentials.");
        statusLabel.setFont(labelFont);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(statusLabel, BorderLayout.SOUTH);

        // --- ACTION LISTENERS ---
        saveBtn.addActionListener(e -> executeSaveAsync());
        updateBtn.addActionListener(e -> executeUpdateAsync());
        deleteBtn.addActionListener(e -> executeDeleteAsync());
        clearBtn.addActionListener(e -> clearInputs());

        passwordTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && passwordTable.getSelectedRow() != -1) {
                int row = passwordTable.getSelectedRow();
                websiteField.setText((String) tableModel.getValueAt(row, 0));
                usernameField.setText((String) tableModel.getValueAt(row, 1));
                passwordField.setText("");
            }
        });
    }

    private void updateStatus(String msg, Color color) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("Status: " + msg);
            statusLabel.setForeground(color);
        });
    }

    private void clearInputs() {
        websiteField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        passwordTable.clearSelection();
        updateStatus("Fields cleared.", Color.BLACK);
    }

    private void refreshTable() {
        SwingUtilities.invokeLater(() -> {
            tableModel.setRowCount(0);
            boolean show = showPassCheckbox.isSelected();
            for (PasswordEntry e : passwordManager.getAllPasswords()) {
                String renderedPass = show ? EncryptionUtil.decrypt(e.getEncryptedPassword()) : "••••••••";
                tableModel.addRow(new Object[]{e.getWebsite(), e.getUsername(), renderedPass});
            }
        });
    }

    private void executeSaveAsync() {
        String site = websiteField.getText().trim();
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();

        if (site.isEmpty() || user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Empty fields are not allowed!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        new Thread(() -> {
            toggleButtons(false);
            try {
                updateStatus("Encrypting strictly...", new Color(255, 140, 0));
                Thread.sleep(800);
                
                updateStatus("Saving to vault formats...", new Color(255, 140, 0));
                Thread.sleep(600);
                
                passwordManager.addPassword(site, user, pass);
                
                SwingUtilities.invokeLater(() -> {
                    updateStatus("Save successful!", new Color(40, 167, 69));
                    JOptionPane.showMessageDialog(this, "Secured!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                    clearInputs();
                });
            } catch (Exception ex) {
                updateStatus("Save Exception Triggered.", Color.RED);
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
            } finally {
                toggleButtons(true);
            }
        }).start();
    }

    private void executeUpdateAsync() {
        int selectedRow = passwordTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a record from the table to update its password.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String site = (String) tableModel.getValueAt(selectedRow, 0);
        String pass = new String(passwordField.getPassword()).trim();
        
        if (pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please insert a new password into the password field before updating.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        new Thread(() -> {
            toggleButtons(false);
            try {
                updateStatus("Re-Encrypting & Updating Backup...", new Color(255, 140, 0));
                Thread.sleep(500);
                
                passwordManager.updatePassword(site, pass);
                
                SwingUtilities.invokeLater(() -> {
                    updateStatus("Update successful!", new Color(40, 167, 69));
                    JOptionPane.showMessageDialog(this, "Target Password Updated Successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshTable();
                    clearInputs();
                });
            } catch (Exception ex) {
                updateStatus("Update Exception.", Color.RED);
                SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
            } finally {
                toggleButtons(true);
            }
        }).start();
    }

    private void executeDeleteAsync() {
        int selectedRow = passwordTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select a record from the table to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String site = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to completely erase the record for: " + site + "?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            new Thread(() -> {
                toggleButtons(false);
                try {
                    updateStatus("Deleting from File Streams...", Color.RED);
                    Thread.sleep(500);
                    
                    passwordManager.deletePassword(site);
                    
                    SwingUtilities.invokeLater(() -> {
                        updateStatus("Deletion complete.", Color.BLACK);
                        refreshTable();
                        clearInputs();
                    });
                } catch (Exception ex) {
                    updateStatus("Deletion Exception.", Color.RED);
                    SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE));
                } finally {
                    toggleButtons(true);
                }
            }).start();
        }
    }

    private void toggleButtons(boolean state) {
        SwingUtilities.invokeLater(() -> {
            saveBtn.setEnabled(state);
            updateBtn.setEnabled(state);
            deleteBtn.setEnabled(state);
            clearBtn.setEnabled(state);
        });
    }
}
