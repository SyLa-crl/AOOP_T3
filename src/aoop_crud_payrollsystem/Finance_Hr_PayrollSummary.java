package aoop_crud_payrollsystem;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
/**
 *
 * @author Sylani
 */
public class Finance_Hr_PayrollSummary extends javax.swing.JFrame {

    private javax.swing.JTable payrollTable;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton exportButton;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JPanel footerPanel;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JPanel mainPanel;
    
    /**
     * Creates new form Finance_Hr_PayrollSummary
     */
    public Finance_Hr_PayrollSummary() {
        initComponents();
        customizeComponents();
        loadPayrollData();
    }
    
    /**
     * Initialize the UI components
     */
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Payroll Summary - MotorPH Payroll System");
        
        // Create main panel with border layout
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        // Create header panel
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(204, 255, 204));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create title label
        titleLabel = new JLabel("MotorPH Payroll Summary");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Create table for payroll data
        payrollTable = new JTable();
        payrollTable.setRowHeight(25);
        payrollTable.setIntercellSpacing(new Dimension(10, 5));
        payrollTable.setAutoCreateRowSorter(true);
        payrollTable.setFillsViewportHeight(true);
        
        // Create scroll pane for table
        JScrollPane scrollPane = new JScrollPane(payrollTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        
        // Create footer panel
        footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);
        
        // Create export button
        exportButton = new JButton("Export to PDF");
        exportButton.addActionListener((ActionEvent evt) -> {
            exportButtonActionPerformed(evt);
        });
        
        // Create close button
        closeButton = new JButton("Close");
        closeButton.addActionListener((ActionEvent evt) -> {
            closeButtonActionPerformed(evt);
        });
        
        // Add buttons to footer panel
        footerPanel.add(exportButton);
        footerPanel.add(closeButton);
        
        // Add components to main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        getContentPane().add(mainPanel);
        
        // Set frame properties
        setSize(1000, 600);
        setLocationRelativeTo(null);
    }
    
    /**
     * Apply custom styling to components
     */
    private void customizeComponents() {
        // Style the table header
        JTableHeader header = payrollTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(230, 230, 230));
        header.setForeground(Color.BLACK);
        
        // Style the buttons
        closeButton.setBackground(new Color(204, 204, 204));
        closeButton.setForeground(Color.BLACK);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        
        exportButton.setBackground(new Color(204, 255, 204));
        exportButton.setForeground(Color.BLACK);
        exportButton.setFocusPainted(false);
        exportButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
    }
    
    /**
     * Load payroll data from CSV file
     */
    private void loadPayrollData() {
        try {
            // Define table columns
            String[] columns = {
                "Employee No.", "Last Name", "First Name", "Worked Hours", 
                "Basic Salary", "Hourly Rate", "Gross Income", "SSS", 
                "PhilHealth", "Pag-IBIG", "Withholding Tax", "Total Deductions", 
                "Benefits", "Take-Home Pay", "Covered Period"
            };
            
            // Create table model with non-editable cells
            DefaultTableModel model = new DefaultTableModel(columns, 0) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // Make all cells non-editable
                }
            };
            
            // Set the model to the table
            payrollTable.setModel(model);
            
            // Load data from CSV
            try (CSVReader reader = new CSVReader(new FileReader("PayrollRecords.csv"))) {
                String[] nextLine;
                boolean headerSkipped = false;
                
                // Read data
                while ((nextLine = reader.readNext()) != null) {
                    // Skip header row
                    if (!headerSkipped) {
                        headerSkipped = true;
                        continue;
                    }
                    
                    // Check if we have enough data
                    if (nextLine.length < 16) {
                        continue; // Skip invalid rows
                    }
                    
                    // Format the data for display
                    String[] row = new String[15];
                    row[0] = nextLine[0];  // Employee No.
                    row[1] = nextLine[1];  // Last Name
                    row[2] = nextLine[2];  // First Name
                    row[3] = nextLine[3];  // Worked Hours
                    row[4] = nextLine[4];  // Basic Salary
                    row[5] = nextLine[5];  // Hourly Rate
                    row[6] = nextLine[6];  // Gross Income
                    row[7] = nextLine[7];  // SSS Deduction
                    row[8] = nextLine[8];  // PhilHealth Deduction
                    row[9] = nextLine[9];  // Pag-IBIG Deduction
                    row[10] = nextLine[10]; // Withholding Tax
                    row[11] = nextLine[14]; // Total Deductions
                    row[12] = nextLine[13]; // Benefits
                    row[13] = nextLine[15]; // Take-Home Pay
                    row[14] = nextLine[11] + " " + nextLine[12]; // Covered Period
                    
                    // Add to table
                    model.addRow(row);
                }
            }
            
            // Adjust column widths
            payrollTable.getColumnModel().getColumn(0).setPreferredWidth(80); // Employee No.
            payrollTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Last Name
            payrollTable.getColumnModel().getColumn(2).setPreferredWidth(100); // First Name
            payrollTable.getColumnModel().getColumn(14).setPreferredWidth(120); // Covered Period
            
            // If no data was loaded, show a message
            if (model.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, 
                        "No payroll records found. Please process payroll data first.", 
                        "No Data", JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (IOException | CsvValidationException ex) {
            Logger.getLogger(Finance_Hr_PayrollSummary.class.getName()).log(
                    Level.SEVERE, "Error loading payroll data", ex);
            JOptionPane.showMessageDialog(this, 
                    "Error loading payroll data: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(Finance_Hr_PayrollSummary.class.getName()).log(
                    Level.SEVERE, "Unexpected error", ex);
            JOptionPane.showMessageDialog(this, 
                    "Unexpected error: " + ex.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Close button action handler
     */
    private void closeButtonActionPerformed(ActionEvent evt) {
        dispose();
    }
    
    /**
     * Sign out action
     */
    private void SignOutActionPerformed(java.awt.event.ActionEvent evt) {
        int response = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to sign out?",
            "Confirm Sign Out",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (response == JOptionPane.YES_OPTION) {
            WindowUtils.switchToWindow(this, new LoginManager());
        }
    }
    
    /**
     * Export button action handler (placeholder)
     */
    private void exportButtonActionPerformed(ActionEvent evt) {
        // This is a placeholder - you would add PDF export functionality here
        JOptionPane.showMessageDialog(this, 
                "Export to PDF functionality to be implemented.", 
                "Export", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Main method to run the form
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Finance_Hr_PayrollSummary().setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(Finance_Hr_PayrollSummary.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        });
    }
}