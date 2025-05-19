/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aoop_crud_payrollsystem;

import java.awt.Component;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Sylani
 */
public class ErrorHandler {
    private static final Logger LOGGER = Logger.getLogger(ErrorHandler.class.getName());
    
    /**
     * Handles an exception with appropriate user feedback and logging
     * @param parent the parent component for the error dialog
     * @param ex the exception to handle
     * @param title the dialog title
     * @param friendlyMessage a user-friendly message to display
     */
    public static void handleException(Component parent, Exception ex, String title, String friendlyMessage) {
        // Log the full exception
        LOGGER.log(Level.SEVERE, friendlyMessage, ex);
        
        // Show a user-friendly message
        JOptionPane.showMessageDialog(
                parent,
                friendlyMessage + "\n\nDetails: " + ex.getMessage(),
                title,
                JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Handles a specific error condition with user feedback
     * @param parent the parent component for the dialog
     * @param message the message to display and log
     * @param title the dialog title
     * @param level the logging level
     */
    public static void handleError(Component parent, String message, String title, Level level) {
        // Log the error
        LOGGER.log(level, message);
        
        // Show a message to the user
        JOptionPane.showMessageDialog(
                parent,
                message,
                title,
                JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Shows a confirmation dialog with standard options
     * @param parent the parent component
     * @param message the message to display
     * @param title the dialog title
     * @return true if confirmed, false otherwise
     */
    public static boolean confirmAction(Component parent, String message, String title) {
        int response = JOptionPane.showConfirmDialog(
                parent,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        
        return response == JOptionPane.YES_OPTION;
    }
}
