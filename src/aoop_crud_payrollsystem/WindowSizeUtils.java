/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aoop_crud_payrollsystem;
import java.awt.Dimension;
import javax.swing.JFrame;
/**
 *
 * @author Sylani
 */
public class WindowSizeUtils {
     
    // Standard dimensions for the application
    public static final int STANDARD_WIDTH = 1200;
    public static final int STANDARD_HEIGHT = 650;
    
    /**
     * Standardizes the size of any JFrame to match the application standard dimensions
     * @param frame The JFrame to resize
     */
    public static void setStandardSize(JFrame frame) {
        if (frame == null) return;
        
        // Set the frame size
        frame.setSize(STANDARD_WIDTH, STANDARD_HEIGHT);
        frame.setPreferredSize(new Dimension(STANDARD_WIDTH, STANDARD_HEIGHT));
        
        // Center on screen
        frame.setLocationRelativeTo(null);
        
        // Prevent resizing (optional, remove if you want resizable windows)
        frame.setResizable(false);
        
        // Apply changes
        frame.revalidate();
        frame.repaint();
    }
}

