package keyboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class OnScreenKeyboard extends JFrame {
    
	 
	 
	private static final long serialVersionUID = 1L;
	private JTextArea textOutput;
    private JPanel keyboardPanel;
    private boolean shiftPressed = false;
    private boolean capsLockOn = false;
    private boolean systemTypingEnabled = true;
    private Robot robot;
    
    // Key colors
    private final Color KEY_NORMAL_BG = new Color(255, 255, 255);
    private final Color KEY_HOVER_BG = new Color(240, 240, 240);
    private final Color KEY_PRESSED_BG = new Color(208, 208, 208);
    private final Color KEY_BORDER = new Color(204, 204, 204);
    private final Color KEYBOARD_BG = new Color(230, 230, 230);
    
    public OnScreenKeyboard() {
        setTitle("On-Screen Keyboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 475);
        setResizable(false);
        setLayout(new BorderLayout());
        
        // Initialize Robot for system-wide typing
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Could not initialize keyboard automation.\nThe keyboard will only type in the preview area.",
                "Warning", JOptionPane.WARNING_MESSAGE);
            systemTypingEnabled = false;
        }
        
        // Create text output area
        textOutput = new JTextArea(5, 40);
        textOutput.setEditable(true);
        textOutput.setLineWrap(true);
        textOutput.setWrapStyleWord(true);
        textOutput.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textOutput);
        scrollPane.setPreferredSize(new Dimension(850, 100));
        
        // Add toggle button for system typing
        JCheckBox toggleTypingMode = new JCheckBox("Type to system", systemTypingEnabled);
        toggleTypingMode.addActionListener(e -> {
            systemTypingEnabled = toggleTypingMode.isSelected();
            if (systemTypingEnabled && robot == null) {
                JOptionPane.showMessageDialog(this, 
                    "Robot initialization failed. Cannot enable system typing.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                toggleTypingMode.setSelected(false);
                systemTypingEnabled = false;
            }
        });
        
        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.add(scrollPane, BorderLayout.CENTER);
        textPanel.add(toggleTypingMode, BorderLayout.SOUTH);
        
        add(textPanel, BorderLayout.NORTH);
        
        // Create keyboard panel
        keyboardPanel = new JPanel();
        keyboardPanel.setLayout(new BoxLayout(keyboardPanel, BoxLayout.Y_AXIS));
        keyboardPanel.setBackground(KEYBOARD_BG);
        keyboardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create keyboard rows
        createKeyboardRows();
        
        // Add keyboard panel to frame
        add(keyboardPanel, BorderLayout.CENTER);
        
        // Center frame on screen
        setLocationRelativeTo(null);
    }
    
    private void createKeyboardRows() {
        // Row 1: Function keys
        JPanel row1 = createRow();
        addKey(row1, "Esc", 50, 30);
        addKey(row1, "F1", 40, 30);
        addKey(row1, "F2", 40, 30);
        addKey(row1, "F3", 40, 30);
        addKey(row1, "F4", 40, 30);
        addKey(row1, "F5", 40, 30);
        addKey(row1, "F6", 40, 30);
        addKey(row1, "F7", 40, 30);
        addKey(row1, "F8", 40, 30);
        addKey(row1, "F9", 40, 30);
        addKey(row1, "F10", 40, 30);
        addKey(row1, "F11", 40, 30);
        addKey(row1, "F12", 40, 30);
        addKey(row1, "PrtSc", 50, 30);
        addKey(row1, "Delete", 50, 30);
        keyboardPanel.add(row1);
        
        // Row 2: Numbers row
        JPanel row2 = createRow();
        addKey(row2, "`", "~", 40, 40);
        addKey(row2, "1", "!", 40, 40);
        addKey(row2, "2", "@", 40, 40);
        addKey(row2, "3", "#", 40, 40);
        addKey(row2, "4", "$", 40, 40);
        addKey(row2, "5", "%", 40, 40);
        addKey(row2, "6", "^", 40, 40);
        addKey(row2, "7", "&", 40, 40);
        addKey(row2, "8", "*", 40, 40);
        addKey(row2, "9", "(", 40, 40);
        addKey(row2, "0", ")", 40, 40);
        addKey(row2, "-", "_", 40, 40);
        addKey(row2, "=", "+", 40, 40);
        addKey(row2, "Backspace", 80, 40);
        keyboardPanel.add(row2);
        
        // Row 3: QWERTY row
        JPanel row3 = createRow();
        addKey(row3, "Tab", 60, 40);
        addKey(row3, "q", "Q", 40, 40);
        addKey(row3, "w", "W", 40, 40);
        addKey(row3, "e", "E", 40, 40);
        addKey(row3, "r", "R", 40, 40);
        addKey(row3, "t", "T", 40, 40);
        addKey(row3, "y", "Y", 40, 40);
        addKey(row3, "u", "U", 40, 40);
        addKey(row3, "i", "I", 40, 40);
        addKey(row3, "o", "O", 40, 40);
        addKey(row3, "p", "P", 40, 40);
        addKey(row3, "[", "{", 40, 40);
        addKey(row3, "]", "}", 40, 40);
        addKey(row3, "\\", "|", 60, 40);
        keyboardPanel.add(row3);
        
        // Row 4: ASDF row
        JPanel row4 = createRow();
        JButton capsLockKey = addKey(row4, "Caps Lock", 80, 40);
        capsLockKey.addActionListener(e -> {
            capsLockOn = !capsLockOn;
            if (capsLockOn) {
                capsLockKey.setBackground(KEY_PRESSED_BG);
                if (systemTypingEnabled && robot != null) {
                    robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                    robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
                }
            } else {
                capsLockKey.setBackground(KEY_NORMAL_BG);
                if (systemTypingEnabled && robot != null) {
                    robot.keyPress(KeyEvent.VK_CAPS_LOCK);
                    robot.keyRelease(KeyEvent.VK_CAPS_LOCK);
                }
            }
        });
        
        addKey(row4, "a", "A", 40, 40);
        addKey(row4, "s", "S", 40, 40);
        addKey(row4, "d", "D", 40, 40);
        addKey(row4, "f", "F", 40, 40);
        addKey(row4, "g", "G", 40, 40);
        addKey(row4, "h", "H", 40, 40);
        addKey(row4, "j", "J", 40, 40);
        addKey(row4, "k", "K", 40, 40);
        addKey(row4, "l", "L", 40, 40);
        addKey(row4, ";", ":", 40, 40);
        addKey(row4, "'", "\"", 40, 40);
        addKey(row4, "Enter", 80, 40);
        keyboardPanel.add(row4);
        
        // Row 5: ZXCV row
        JPanel row5 = createRow();
        JButton shiftKeyLeft = addKey(row5, "Shift", 90, 40);
        shiftKeyLeft.addActionListener(e -> toggleShift(shiftKeyLeft));
        
        addKey(row5, "z", "Z", 40, 40);
        addKey(row5, "x", "X", 40, 40);
        addKey(row5, "c", "C", 40, 40);
        addKey(row5, "v", "V", 40, 40);
        addKey(row5, "b", "B", 40, 40);
        addKey(row5, "n", "N", 40, 40);
        addKey(row5, "m", "M", 40, 40);
        addKey(row5, ",", "<", 40, 40);
        addKey(row5, ".", ">", 40, 40);
        addKey(row5, "/", "?", 40, 40);
        
        JButton shiftKeyRight = addKey(row5, "Shift", 90, 40);
        shiftKeyRight.addActionListener(e -> toggleShift(shiftKeyRight));
        keyboardPanel.add(row5);
        
        // Row 6: Bottom row (Ctrl, Alt, Space)
        JPanel row6 = createRow();
        addKey(row6, "Ctrl", 50, 40);
        addKey(row6, "Win", 50, 40);
        addKey(row6, "Alt", 50, 40);
        addKey(row6, " ", 300, 40); // Space bar
        addKey(row6, "Alt", 50, 40);
        addKey(row6, "Menu", 50, 40);
        addKey(row6, "Ctrl", 50, 40);
        keyboardPanel.add(row6);
    }
    
    private JPanel createRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(850, 50));
        return row;
    }
    
    private JButton addKey(JPanel row, String text, int width, int height) {
        JButton key = new JButton(text);
        key.setPreferredSize(new Dimension(width, height));
        key.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        key.setFocusable(false);
        key.setBackground(KEY_NORMAL_BG);
        key.setBorder(BorderFactory.createLineBorder(KEY_BORDER));
        
        // Add action based on key type
        if (text.length() == 1) {
            // Single character key
            key.addActionListener(e -> typeCharacter(text));
        } else if (text.equals("Backspace")) {
            key.addActionListener(e -> deleteLastCharacter());
        } else if (text.equals("Enter")) {
            key.addActionListener(e -> typeCharacter("\n"));
        } else if (text.equals("Tab")) {
            key.addActionListener(e -> typeCharacter("\t"));
        } else if (text.equals(" ")) {
            key.addActionListener(e -> typeCharacter(" "));
        } else if (text.equals("Delete")) {
            key.addActionListener(e -> {
                if (systemTypingEnabled && robot != null) {
                    robot.keyPress(KeyEvent.VK_DELETE);
                    robot.keyRelease(KeyEvent.VK_DELETE);
                }
                // Delete doesn't have a standard text representation
            });
        } else if (text.equals("Esc")) {
            key.addActionListener(e -> {
                if (systemTypingEnabled && robot != null) {
                    robot.keyPress(KeyEvent.VK_ESCAPE);
                    robot.keyRelease(KeyEvent.VK_ESCAPE);
                }
            });
        } else if (text.startsWith("F") && text.length() > 1) {
            // Function keys F1-F12
            key.addActionListener(e -> {
                if (systemTypingEnabled && robot != null) {
                    try {
                        int fKeyNum = Integer.parseInt(text.substring(1));
                        if (fKeyNum >= 1 && fKeyNum <= 12) {
                            int keyCode = KeyEvent.VK_F1 + (fKeyNum - 1);
                            robot.keyPress(keyCode);
                            robot.keyRelease(keyCode);
                        }
                    } catch (NumberFormatException ex) {
                        // Ignore if not a valid F-key
                    }
                }
            });
        } else if (text.equals("Ctrl")) {
            key.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    key.setBackground(KEY_PRESSED_BG);
                    if (systemTypingEnabled && robot != null) {
                        robot.keyPress(KeyEvent.VK_CONTROL);
                    }
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    key.setBackground(KEY_NORMAL_BG);
                    if (systemTypingEnabled && robot != null) {
                        robot.keyRelease(KeyEvent.VK_CONTROL);
                    }
                }
            });
        } else if (text.equals("Alt")) {
            key.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    key.setBackground(KEY_PRESSED_BG);
                    if (systemTypingEnabled && robot != null) {
                        robot.keyPress(KeyEvent.VK_ALT);
                    }
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    key.setBackground(KEY_NORMAL_BG);
                    if (systemTypingEnabled && robot != null) {
                        robot.keyRelease(KeyEvent.VK_ALT);
                    }
                }
            });
        } else if (text.equals("Win")) {
            key.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    key.setBackground(KEY_PRESSED_BG);
                    if (systemTypingEnabled && robot != null) {
                        robot.keyPress(KeyEvent.VK_WINDOWS);
                    }
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {
                    key.setBackground(KEY_NORMAL_BG);
                    if (systemTypingEnabled && robot != null) {
                        robot.keyRelease(KeyEvent.VK_WINDOWS);
                    }
                }
            });
        } else if (text.equals("Menu")) {
            key.addActionListener(e -> {
                if (systemTypingEnabled && robot != null) {
                    robot.keyPress(KeyEvent.VK_CONTEXT_MENU);
                    robot.keyRelease(KeyEvent.VK_CONTEXT_MENU);
                }
            });
        }
        
        // Add hover effect
        key.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!text.equals("Caps Lock") || !capsLockOn) {
                    key.setBackground(KEY_HOVER_BG);
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (text.equals("Caps Lock") && capsLockOn) {
                    key.setBackground(KEY_PRESSED_BG);
                } else if ((text.equals("Shift") && shiftPressed)) {
                    key.setBackground(KEY_PRESSED_BG);
                } else {
                    key.setBackground(KEY_NORMAL_BG);
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                if (!text.equals("Caps Lock") || !capsLockOn) {
                    key.setBackground(KEY_PRESSED_BG);
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                if (text.equals("Caps Lock") && capsLockOn) {
                    key.setBackground(KEY_PRESSED_BG);
                } else if ((text.equals("Shift") && shiftPressed)) {
                    key.setBackground(KEY_PRESSED_BG);
                } else {
                    key.setBackground(key.getMousePosition() != null ? KEY_HOVER_BG : KEY_NORMAL_BG);
                }
            }
        });
        
        row.add(key);
        return key;
    }
    
    private JButton addKey(JPanel row, String text, String shiftText, int width, int height) {
        JButton key = new JButton("<html>" + shiftText + "<br>" + text + "</html>");
        key.setHorizontalTextPosition(SwingConstants.CENTER);
        key.setVerticalTextPosition(SwingConstants.CENTER);
        key.setPreferredSize(new Dimension(width, height));
        key.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        key.setFocusable(false);
        key.setBackground(KEY_NORMAL_BG);
        key.setBorder(BorderFactory.createLineBorder(KEY_BORDER));
        
        // Add action based on key type
        key.addActionListener(e -> {
            String charToAppend = shiftPressed || capsLockOn ? shiftText : text;
            // If both shift and caps lock are on for letters, they cancel each other out
            if (shiftPressed && capsLockOn && text.length() == 1 && Character.isLetter(text.charAt(0))) {
                charToAppend = text;
            }
            typeCharacter(charToAppend);
            
            // Auto-release shift after one character
            if (shiftPressed) {
                shiftPressed = false;
                updateShiftKeys();
            }
        });
        
        // Add hover effect
        key.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                key.setBackground(KEY_HOVER_BG);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                key.setBackground(KEY_NORMAL_BG);
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                key.setBackground(KEY_PRESSED_BG);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                key.setBackground(key.getMousePosition() != null ? KEY_HOVER_BG : KEY_NORMAL_BG);
            }
        });
        
        row.add(key);
        return key;
    }
    
    private void toggleShift(JButton shiftKey) {
        shiftPressed = !shiftPressed;
        updateShiftKeys();
        
        // Press or release shift key in system
        if (systemTypingEnabled && robot != null) {
            if (shiftPressed) {
                robot.keyPress(KeyEvent.VK_SHIFT);
            } else {
                robot.keyRelease(KeyEvent.VK_SHIFT);
            }
        }
    }
    
    private void updateShiftKeys() {
        for (Component rowPanel : keyboardPanel.getComponents()) {
            if (rowPanel instanceof JPanel) {
                for (Component key : ((JPanel) rowPanel).getComponents()) {
                    if (key instanceof JButton && ((JButton) key).getText().equals("Shift")) {
                        ((JButton) key).setBackground(shiftPressed ? KEY_PRESSED_BG : KEY_NORMAL_BG);
                    }
                }
            }
        }
    }
    
    private void typeCharacter(String text) {
        // Update the text area
    	if (!systemTypingEnabled) {
            textOutput.append(text);
        }

        // Send keystrokes to the system if enabled
        if (systemTypingEnabled && robot != null && text.length() > 0) {
            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                typeSystemCharacter(c);
            }
        }
    }
    
    private void typeSystemCharacter(char c) {
        try {
            // Handle common special characters
            switch (c) {
                case '\n':
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    return;
                case '\t':
                    robot.keyPress(KeyEvent.VK_TAB);
                    robot.keyRelease(KeyEvent.VK_TAB);
                    return;
                case ' ':
                    robot.keyPress(KeyEvent.VK_SPACE);
                    robot.keyRelease(KeyEvent.VK_SPACE);
                    return;
            }
            
            // Get key code for the character
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            
            if (keyCode != KeyEvent.VK_UNDEFINED) {
                // Check if we need shift for this character (and it's not already being handled)
                boolean needsShiftForChar = Character.isUpperCase(c) || "~!@#$%^&*()_+{}|:\"<>?".contains(String.valueOf(c));
                boolean temporaryShift = needsShiftForChar && !shiftPressed;
                
                if (temporaryShift) {
                    robot.keyPress(KeyEvent.VK_SHIFT);
                }
                
                robot.keyPress(keyCode);
                robot.keyRelease(keyCode);
                
                if (temporaryShift) {
                    robot.keyRelease(KeyEvent.VK_SHIFT);
                }
            } else {
                // Handle special characters not directly mapped to a key code
                switch (c) {
                    case '~':
                        pressWithShift(KeyEvent.VK_BACK_QUOTE);
                        break;
                    case '!':
                        pressWithShift(KeyEvent.VK_1);
                        break;
                    case '@':
                        pressWithShift(KeyEvent.VK_2);
                        break;
                    case '#':
                        pressWithShift(KeyEvent.VK_3);
                        break;
                    case '$':
                        pressWithShift(KeyEvent.VK_4);
                        break;
                    case '%':
                        pressWithShift(KeyEvent.VK_5);
                        break;
                    case '^':
                        pressWithShift(KeyEvent.VK_6);
                        break;
                    case '&':
                        pressWithShift(KeyEvent.VK_7);
                        break;
                    case '*':
                        pressWithShift(KeyEvent.VK_8);
                        break;
                    case '(':
                        pressWithShift(KeyEvent.VK_9);
                        break;
                    case ')':
                        pressWithShift(KeyEvent.VK_0);
                        break;
                    case '_':
                        pressWithShift(KeyEvent.VK_MINUS);
                        break;
                    case '+':
                        pressWithShift(KeyEvent.VK_EQUALS);
                        break;
                    case '{':
                        pressWithShift(KeyEvent.VK_OPEN_BRACKET);
                        break;
                    case '}':
                        pressWithShift(KeyEvent.VK_CLOSE_BRACKET);
                        break;
                    case '|':
                        pressWithShift(KeyEvent.VK_BACK_SLASH);
                        break;
                    case ':':
                        pressWithShift(KeyEvent.VK_SEMICOLON);
                        break;
                    case '"':
                        pressWithShift(KeyEvent.VK_QUOTE);
                        break;
                    case '<':
                        pressWithShift(KeyEvent.VK_COMMA);
                        break;
                    case '>':
                        pressWithShift(KeyEvent.VK_PERIOD);
                        break;
                    case '?':
                        pressWithShift(KeyEvent.VK_SLASH);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void pressWithShift(int keyCode) {
        boolean temporaryShift = !shiftPressed;
        
        if (temporaryShift) {
            robot.keyPress(KeyEvent.VK_SHIFT);
        }
        
        robot.keyPress(keyCode);
        robot.keyRelease(keyCode);
        
        if (temporaryShift) {
            robot.keyRelease(KeyEvent.VK_SHIFT);
        }
    }
    
    private void deleteLastCharacter() {
        String currentText = textOutput.getText();
        if (currentText.length() > 0) {
            textOutput.setText(currentText.substring(0, currentText.length() - 1));
            
            // Simulate backspace key
            if (systemTypingEnabled && robot != null) {
                robot.keyPress(KeyEvent.VK_BACK_SPACE);
                robot.keyRelease(KeyEvent.VK_BACK_SPACE);
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            // Set the look and feel to the system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            OnScreenKeyboard keyboard = new OnScreenKeyboard();
            keyboard.setVisible(true);
        });
    }
}
