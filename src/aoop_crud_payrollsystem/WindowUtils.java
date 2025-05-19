/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aoop_crud_payrollsystem;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.JComponent;
/**
 *
 * @author Sylani
 */
public class WindowUtils {
      
    /**
     * Switch from one window to another
     * 
     * @param fromWindow The window to close
     * @param toWindow The window to open
     */
    public static void switchToWindow(JFrame fromWindow, JFrame toWindow) {
        // Get current size and location
        Dimension size = fromWindow.getSize();
        Point location = fromWindow.getLocation();
        
        // Set size and location for new window
        toWindow.setSize(size);
        toWindow.setLocation(location);
        
        // Display new window
        toWindow.setVisible(true);
        
        // Center any panel that needs to be centered
        centerPanelInWindow(toWindow);
        
        // Close old window
        fromWindow.dispose();
    }
    
    /**
     * Center a JPanel within its parent container
     * 
     * @param window The JFrame containing the panel to center
     */
    public static void centerPanelInWindow(JFrame window) {
        // This method looks for panels that need to be centered and centers them
        // It's especially useful for login panels
        
        // Check if window uses a layout that supports centering
        if (window.getContentPane().getLayout() == null) {
            // If using null layout (absolute positioning), try to find login panel
            // and center it
            
            // Find components that might be login panels
            for (java.awt.Component comp : window.getContentPane().getComponents()) {
                if (comp instanceof javax.swing.JPanel) {
                    javax.swing.JPanel panel = (javax.swing.JPanel) comp;
                    
                    // Check if this panel contains login-related components
                    boolean isLoginPanel = false;
                    for (java.awt.Component subComp : panel.getComponents()) {
                        String name = subComp.getName();
                        if (name != null && (
                                name.toLowerCase().contains("login") || 
                                name.toLowerCase().contains("username") || 
                                name.toLowerCase().contains("password"))) {
                            isLoginPanel = true;
                            break;
                        }
                    }
                    
                    if (isLoginPanel) {
                        // This is likely a login panel, center it
                        centerComponent(panel, window.getContentPane());
                    }
                }
            }
        }
    }
    
    /**
     * Center a component within its parent container
     * 
     * @param component The component to center
     * @param parent The parent container
     */
    public static void centerComponent(JComponent component, java.awt.Container parent) {
        // Get the parent size
        int parentWidth = parent.getWidth();
        int parentHeight = parent.getHeight();
        
        // Get the component size
        int compWidth = component.getWidth();
        int compHeight = component.getHeight();
        
        // Calculate center position
        int x = (parentWidth - compWidth) / 2;
        int y = (parentHeight - compHeight) / 2;
        
        // Make sure we don't position off-screen
        x = Math.max(x, 0);
        y = Math.max(y, 0);
        
        // Set the new position
        component.setLocation(x, y);
    }
}