# Secure Password Manager (Advanced Update)

A remarkably upgraded pure Java application managing user passwords cleanly using Core Java implementations (Object-Oriented Programming, Multithreading, File Handling, Swings GUI, Custom Exception strategies).

## Execution Instructions
1. Open a terminal to `c:\Users\krary\OneDrive\Desktop\java project`.
2. Compile the package natively utilizing valid paths:
   ```bash
   javac passwordmanager/model/*.java passwordmanager/core/*.java passwordmanager/util/*.java passwordmanager/ui/*.java passwordmanager/Main.java
   ```
3. Run the GUI Main class:
   ```bash
   java passwordmanager.Main
   ```

## Included Features (Viva Highlights)
1. **Upgraded UI Aesthetics (`Nimbus L&F / JTable / GridBagLayout`)**: Integrates modern layout handling rendering `JTable` layouts to cleanly tabulate encrypted datasets asynchronously. Interactive toggles reveal or hide structural encryption seamlessly on UI states natively using `Color` and `Font` combinations (Segoe UI).
2. **Double Encoded Cryptography Algorithm (`EncryptionUtil`)**: Enhanced password protection leveraging string reversal sequences mathematically joined with Character shifting algorithms (+2 padding). Drastically minimizes raw text extrapolation dynamically outputted into `passwords.txt`.
3. **Core Multithreading (`MainUI`)**: Saving, Modifying, or Wiping data safely triggers autonomous secondary `Runnable` blocks mimicking loading latency formats. Buttons cleanly restrict duplicate asynchronous interactions. Keeps formatting strictly without hanging!
4. **Enhanced Backups (`FileHandler`)**: Safely preserves `backup.txt` outputs automatically duplicating logic constraints securing credentials preventing unrecoverable stream crashes.
5. **Exceptions (`PasswordNotFoundException`) / Flow Validations**: Customized popups warning invalid interactions utilizing Swing `JOptionPane` rendering constraints (Confirmations natively).
6. **OOP Paradigms**: Complete class separations mapping distinct application responsibilities natively bridging Model configurations explicitly to Core processing handlers!

## Interactive Flowchart
```text
[Start Run]
    │
    ├─► Creates `PasswordManager` Application
    │      └─ Extends `Nimbus` UI scaling natively over `MainUI` interface.
    │      └─ Maps persistent sequences from prior logs (`FileHandler` -> `passwords.txt`) into array buffers!
    │
    └─► UI Render Configuration via JTable grids and TextBoxes / ActionListeners
           │
           │ (1) User input -> `Save`
           │      └─ Background `Runnable` encrypts using Double Pass constraints -> Updates File streams seamlessly.
           │
           │ (2) User Checkbox -> `Show Passwords`
           │      └─ Decrypts visually mapping row-based structures without triggering array overrides explicitly.
           │
           │ (3) User Action -> `Update / Delete`
           │      └─ Evaluates JTable selected row indices dynamically preventing blank inputs triggering robust user Popup warnings securely resolving actions!
```
