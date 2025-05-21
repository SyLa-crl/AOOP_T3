/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aoop_crud_payrollsystem;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Sylani
 */
//removes UI logic from the LoginManager class, to keep the code organized and easier to maintain.
public class LoginManagerHelper {
    
        private static final Logger LOGGER = Logger.getLogger(LoginManagerHelper.class.getName());
    
    /**
     * Apply centering to the login form
     * 
     * @param loginManager The LoginManager instance to modify
     */
    public static void centerLoginPanel(LoginManager loginManager) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Get the login panel (jPanel4)
                Component[] components = loginManager.getContentPane().getComponents();
                for (Component component : components) {
                    if (component instanceof JPanel) {
                        JPanel panel = (JPanel) component;
                        
                        // Check if this is one of the login panels (jPanel4)
                        if (panel.getName() != null && panel.getName().equals("jPanel4")) {
                            // This is our login panel - center it
                            centerPanel(panel, loginManager);
                        }
                    }
                }
                
                // Make sure the window is centered on screen as well
                centerFrameOnScreen(loginManager);
                
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error centering login panel", e);
            }
        });
    }
    
    /**
     * Center a panel within its parent frame
     * 
     * @param panel The panel to center
     * @param frame The parent frame
     */
    private static void centerPanel(JPanel panel, JFrame frame) {
        // Get frame dimensions
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();
        
        // Get panel dimensions
        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();
        
        // Calculate center position
        int x = (frameWidth - panelWidth) / 2;
        int y = (frameHeight - panelHeight) / 2;
        
        // Update panel bounds
        panel.setBounds(x, y, panelWidth, panelHeight);
    }
    
    /**
     * Center a frame on the screen
     * 
     * @param frame The frame to center
     */
    private static void centerFrameOnScreen(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        
        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;
        
        frame.setLocation(x, y);
    }
    
    /**
     * Use a centering layout for the login panel
     * 
     * @param loginManager The LoginManager instance
     */
    public static void applyLayoutForCentering(LoginManager loginManager) {
        SwingUtilities.invokeLater(() -> {
            try {
                // We'll use a special approach to center the panel while maintaining input functionality
                
                // First, find the login panel
                JPanel loginPanel = null;
                Component[] components = loginManager.getContentPane().getComponents();
                for (Component component : components) {
                    if (component instanceof JPanel) {
                        JPanel panel = (JPanel) component;
                        for (Component child : panel.getComponents()) {
                            if ((child instanceof JTextField) || 
                                (child instanceof javax.swing.JPasswordField)) {
                                loginPanel = panel;
                                break;
                            }
                        }
                        if (loginPanel != null) break;
                    }
                }
                
                if (loginPanel != null) {
                    // We found the login panel
                    
                    // 1. Make sure all text components are enabled and editable
                    ensureComponentsEditable(loginPanel);
                    
                    // 2. Make sure we're not using a layout that breaks input
                    if (loginManager.getContentPane().getLayout() instanceof BorderLayout) {
                        // If we already have a BorderLayout, just center the panel
                        centerPanel(loginPanel, loginManager);
                    } else {
                        // Use a simpler approach - just set the bounds to center the panel
                        loginManager.getContentPane().setLayout(null); // Use absolute positioning
                        
                        // Get window dimensions
                        Dimension windowSize = loginManager.getSize();
                        
                        // Calculate centered position
                        int x = (windowSize.width - loginPanel.getWidth()) / 2;
                        int y = (windowSize.height - loginPanel.getHeight()) / 2;
                        
                        // Set the panel's position
                        loginPanel.setLocation(x, y);
                    }
                }
                
                // Final check to make sure all input components are properly configured
                validateAllInputComponents(loginManager);
                
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "Error applying centering layout", e);
            }
        });
    }
    
    /**
     * Recursively ensure all text components in a container are enabled and editable
     * 
     * @param container The container to check
     */
    private static void ensureComponentsEditable(java.awt.Container container) {
        Component[] components = container.getComponents();
        
        for (Component component : components) {
            // Handle text fields
            if (component instanceof JTextField) {
                JTextField textField = (JTextField) component;
                textField.setEditable(true);
                textField.setEnabled(true);
                textField.setFocusable(true);
            }
            // Handle password fields
            else if (component instanceof javax.swing.JPasswordField) {
                javax.swing.JPasswordField passField = (javax.swing.JPasswordField) component;
                passField.setEditable(true);
                passField.setEnabled(true);
                passField.setFocusable(true);
            }
            // Recursively check containers
            else if (component instanceof java.awt.Container) {
                ensureComponentsEditable((java.awt.Container) component);
            }
        }
    }
    
    /**
     * Final validation of all input components
     * 
     * @param loginManager The LoginManager instance
     */
    private static void validateAllInputComponents(LoginManager loginManager) {
        try {
            // Force these specific components to be editable using reflection
            java.lang.reflect.Field usernameField = LoginManager.class.getDeclaredField("JTextFieldUsername");
            java.lang.reflect.Field passwordField = LoginManager.class.getDeclaredField("JPasswordField");
            
            usernameField.setAccessible(true);
            passwordField.setAccessible(true);
            
            JTextField username = (JTextField) usernameField.get(loginManager);
            javax.swing.JPasswordField password = (javax.swing.JPasswordField) passwordField.get(loginManager);
            
            if (username != null) {
                username.setEditable(true);
                username.setEnabled(true);
                username.setFocusable(true);
            }
            
            if (password != null) {
                password.setEditable(true);
                password.setEnabled(true);
                password.setFocusable(true);
            }
            
        } catch (Exception e) {
            // If this fails, it's not critical - just log it
            LOGGER.log(Level.WARNING, "Could not force text field settings", e);
        }
    }
}