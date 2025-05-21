/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aoop_crud_payrollsystem;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
//import java.io.IOException;
//import com.opencsv.exceptions.CsvException;
import java.awt.HeadlessException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Sylani
 */
//Centralized control that handles all logic for determining which dashboard to load based on the user's role.
public class DashboardFixer {
     private static final Logger LOGGER = Logger.getLogger(DashboardFixer.class.getName());
    
    /**
     * Open the appropriate dashboard based on role with error handling
     * 
     * @param currentFrame The current frame to close
     * @param employeeInfo Employee information array
     * @param userRole User role
     */
    public static void openDashboard(JFrame currentFrame, String[] employeeInfo, String userRole) {
        try {
            LOGGER.log(Level.INFO, "Opening {0} dashboard for employee #{1}", new Object[]{userRole, employeeInfo[0]});
            
            // Create appropriate dashboard based on role
            JFrame dashboard = null;
            
            switch (userRole.toLowerCase()) {
                case "admin" -> {
                    try {
                        dashboard = new EmployeeDashboard(employeeInfo[0]);
                    } catch (Exception ex) {
                        // If regular dashboard fails, try a simpler approach
                        JOptionPane.showMessageDialog(currentFrame,
                            "Opening simplified admin dashboard due to error: " + ex.getMessage(),
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                        dashboard = createSimpleDashboard("Admin Dashboard", employeeInfo);
                    }
                }
                case "finance" -> {
                    try {
                        dashboard = new Finance_Dashboard(employeeInfo);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(currentFrame,
                            "Opening simplified finance dashboard due to error: " + ex.getMessage(),
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                        dashboard = createSimpleDashboard("Finance Dashboard", employeeInfo);
                    }
                }
                case "hr" -> {
                    try {
                        dashboard = new HR_Dashboard(employeeInfo);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(currentFrame,
                            "Opening simplified HR dashboard due to error: " + ex.getMessage(),
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                        dashboard = createSimpleDashboard("HR Dashboard", employeeInfo);
                    }
                }
                default -> {
                    try {
                        dashboard = new EmployeeDashboard(employeeInfo[0]);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(currentFrame,
                            "Opening simplified employee dashboard due to error: " + ex.getMessage(),
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                        dashboard = createSimpleDashboard("Employee Dashboard", employeeInfo);
                    }
                }
            }
            
            // Show the dashboard and close the login form
            if (dashboard != null) {
                JFrame finalDashboard = dashboard;
                SwingUtilities.invokeLater(() -> {
                    finalDashboard.setVisible(true);
                    currentFrame.dispose();
                });
            } else {
                throw new IllegalStateException("Failed to create dashboard");
            }
            
        } catch (HeadlessException | IllegalStateException e) {
            LOGGER.log(Level.SEVERE, "Error opening dashboard", e);
            JOptionPane.showMessageDialog(currentFrame,
                "Error opening dashboard: " + e.getMessage(),
                "Navigation Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Create a simple dashboard as a fallback when the main dashboard fails to load
     * 
     * @param title Dashboard title
     * @param employeeInfo Employee information
     * @return A simple dashboard
     */
    private static JFrame createSimpleDashboard(String title, String[] employeeInfo) {
        JFrame frame = new JFrame(title);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        // Create a simple content panel
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.setLayout(new java.awt.BorderLayout(10, 10));
        
        // Add a welcome message
        javax.swing.JLabel welcomeLabel = new javax.swing.JLabel(
            "Welcome to MotorPH Payroll System - " + title);
        welcomeLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        welcomeLabel.setHorizontalAlignment(javax.swing.JLabel.CENTER);
        panel.add(welcomeLabel, java.awt.BorderLayout.NORTH);
        
        // Add user info
        javax.swing.JPanel infoPanel = new javax.swing.JPanel(new java.awt.GridLayout(3, 2, 5, 5));
        infoPanel.add(new javax.swing.JLabel("Employee Number:"));
        infoPanel.add(new javax.swing.JLabel(employeeInfo[0]));
        infoPanel.add(new javax.swing.JLabel("Role:"));
        infoPanel.add(new javax.swing.JLabel(employeeInfo[1]));
        infoPanel.add(new javax.swing.JLabel("Username:"));
        infoPanel.add(new javax.swing.JLabel(employeeInfo[2]));
        
        panel.add(infoPanel, java.awt.BorderLayout.CENTER);
        
        // Add a logout button
        javax.swing.JButton logoutButton = new javax.swing.JButton("Logout");
        logoutButton.addActionListener(e -> {
            frame.dispose();
            SwingUtilities.invokeLater(() -> {
                LoginManager loginManager = new LoginManager();
                loginManager.setVisible(true);
            });
        });
        
        javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
        buttonPanel.add(logoutButton);
        panel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
        
        frame.getContentPane().add(panel);
        
        return frame;
    }
    
    /**
     * Class to serve as a simplified employee dashboard
     */
    public static class EmployeeDashboard extends JFrame {
        public EmployeeDashboard(String employeeNumber) {
            setTitle("Employee Dashboard - #" + employeeNumber);
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            
            // Create a simple content panel
            javax.swing.JPanel panel = new javax.swing.JPanel();
            panel.setLayout(new java.awt.BorderLayout(10, 10));
            
            // Add a welcome message
            javax.swing.JLabel welcomeLabel = new javax.swing.JLabel(
                "Welcome to MotorPH Payroll System - Employee #" + employeeNumber);
            welcomeLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
            welcomeLabel.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            panel.add(welcomeLabel, java.awt.BorderLayout.NORTH);
            
            // Add a logout button
            javax.swing.JButton logoutButton = new javax.swing.JButton("Logout");
            logoutButton.addActionListener(e -> {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    LoginManager loginManager = new LoginManager();
                    loginManager.setVisible(true);
                });
            });
            
            javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
            buttonPanel.add(logoutButton);
            panel.add(buttonPanel, java.awt.BorderLayout.SOUTH);
            
            getContentPane().add(panel);
        }
    }
}

