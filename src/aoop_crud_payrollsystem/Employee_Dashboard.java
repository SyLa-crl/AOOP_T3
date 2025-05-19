/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aoop_crud_payrollsystem;
import com.opencsv.exceptions.CsvException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Sylani
 */
public class Employee_Dashboard extends javax.swing.JFrame {
 // Employee information fields
    private String userEmployeeNumber;
    private String userLastName;
    private String userFirstName;
    
    // Alternative constructor that accepts employee information array
    public Employee_Dashboard(String[] userInformation) {
        if (userInformation.length >= 3) {
            this.userEmployeeNumber = userInformation[0];
            this.userLastName = userInformation[1];
            this.userFirstName = userInformation[2];
        } else if (userInformation.length >= 1) {
            // If only employee number provided, set others to empty
            this.userEmployeeNumber = userInformation[0];
            this.userLastName = "";
            this.userFirstName = "";
        }
        initComponents();
        initializeDashboard();
    }
    
    /**
     * Creates new form Employee_Dashboard (default constructor)
     */
    public Employee_Dashboard() {
        initComponents();
        initializeDashboard();
    }
    
    /**
     * Initialize the dashboard with proper styling and user info
     */
    private void initializeDashboard() {
    // Set initial button colors
    resetButtonColors();
    Dashboard.setBackground(new Color(255, 153, 153));
    
    // Load employee information if available
    if (userEmployeeNumber != null && !userEmployeeNumber.isEmpty()) {
        loadEmployeeData();
        // Add this line to load dashboard data
        loadDashboardData();
    }
    
    // Set standard size for the dashboard
    WindowSizeUtils.setStandardSize(this);
}
    
    /**
     * Load employee data from CSV to populate dashboard
     */
    private void loadEmployeeData() {
        try {
            // Read employee data to get full name
            String csvFile = "MotorPHEmployeeData.csv";
            java.util.List<String[]> records = FileHandling.readCSV(csvFile);
            
            for (String[] record : records) {
                if (record[0].equals(userEmployeeNumber)) {
                    userLastName = record[1];
                    userFirstName = record[2];
                    break;
                }
            }
        } catch (CsvException | IOException ex) {
            Logger.getLogger(Employee_Dashboard.class.getName()).log(Level.WARNING, 
                "Could not load employee data", ex);
        }
    }
    
    /**
     * Reset all navigation button colors to default
     */
    private void resetButtonColors() {
        Dashboard.setBackground(new Color(255, 204, 204));
        MyProfile.setBackground(new Color(255, 204, 204));
        Payroll.setBackground(new Color(255, 204, 204));
        LeaveApplication.setBackground(new Color(255, 204, 204));
        
        Dashboard.setForeground(Color.BLACK);
        MyProfile.setForeground(Color.BLACK);
        Payroll.setForeground(Color.BLACK);
        LeaveApplication.setForeground(Color.BLACK);
    }
    
    /**
     * Get employee information array for passing to other forms
     */
    private String[] getEmployeeInformation() {
        return new String[]{userEmployeeNumber, userLastName, userFirstName};
    }
    
    /**
     * Show dashboard (placeholder for dashboard content)
     */
    private void showDashboard() {
        // Reset colors and highlight dashboard button
        resetButtonColors();
        Dashboard.setBackground(new Color(255, 153, 153));
        Dashboard.setForeground(Color.WHITE);
        
        // You can add code here to show dashboard content
        // For example, update panels to show dashboard widgets
    }
    
    /**
     * Open employee profile form
     */
    private void openMyProfile() {
        try {
            resetButtonColors();
            MyProfile.setBackground(new Color(255, 153, 153));
            MyProfile.setForeground(Color.WHITE);
            
            Employee_MyProfile profileForm = new Employee_MyProfile(userEmployeeNumber);
            profileForm.setLocationRelativeTo(this);
            profileForm.setVisible(true);
            this.dispose();
        } catch (IOException | CsvException ex) {
            Logger.getLogger(Employee_Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, 
                "Error opening profile: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Open leave application form
     */
    private void showEmployee_LeaveApplication() {
        try {
            resetButtonColors();
           LeaveApplication.setBackground(new Color(255, 153, 153));
            LeaveApplication.setForeground(Color.WHITE);
            
            String[] employeeInfo = getEmployeeInformation();
            Employee_LeaveApplication leaveForm = new Employee_LeaveApplication(employeeInfo);
            leaveForm.setLocationRelativeTo(this);
            leaveForm.setVisible(true);
            this.dispose();
        } catch (IOException | CsvException ex) {
            Logger.getLogger(Employee_Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, 
                "Error opening leave application: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Open payroll/payslip form
     */
    private void showEmployee_Payroll() {
        try {
            resetButtonColors();
            Payroll.setBackground(new Color(255, 153, 153));
            Payroll.setForeground(Color.WHITE);
            
            String[] employeeInfo = getEmployeeInformation();
            Employee_Payroll payrollForm = new Employee_Payroll(employeeInfo);
            payrollForm.setLocationRelativeTo(this);
            payrollForm.setVisible(true);
            this.dispose();
        } catch (IOException | CsvException ex) {
            Logger.getLogger(Employee_Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, 
                "Error opening payroll: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
 * Load data for dashboard panels
 */
private void loadDashboardData() {
    try {
        // Load employee data and payroll records
        String employeeCSV = "MotorPHEmployeeData.csv";
        String payrollCSV = "PayrollRecords.csv";
        String leaveCSV = "leave_applications.csv";
        
        // Get employee data
        java.util.List<String[]> employeeRecords = FileHandling.readCSV(employeeCSV);
        
        // Get payroll records
        java.util.List<String[]> payrollRecords = FileHandling.readCSV(payrollCSV);
        
        // Get leave records
        java.util.List<String[]> leaveRecords = FileHandling.readCSV(leaveCSV);
        
        // Populate Leave Balance Panel
        populateLeaveBalancePanel(leaveRecords);
        
        // Populate Last Payslip Panel
        populateLastPayslipPanel(payrollRecords);
        
        // Populate Leave Requests Panel
        populateLeaveRequestsPanel(leaveRecords);
        
        // Populate Recent Payslips Panel
        populateRecentPayslipsPanel(payrollRecords);
        
    } catch (Exception ex) {
        Logger.getLogger(Employee_Dashboard.class.getName()).log(Level.SEVERE, 
            "Error loading dashboard data", ex);
        JOptionPane.showMessageDialog(this, "Error loading dashboard data: " + ex.getMessage(),
            "Dashboard Error", JOptionPane.ERROR_MESSAGE);
    }
}

/**
 * Populate Leave Balance panel with remaining leave days
 */
private void populateLeaveBalancePanel(java.util.List<String[]> leaveRecords) {
    // Find approved leave days for current employee
    int usedLeaveDays = 0;
    int totalLeaveDays = 30; // Standard allocation
    
    for (String[] record : leaveRecords) {
        if (record.length > 9 && record[1].equals(userEmployeeNumber) && 
            record[4].equals("Approved") && record[12].equals("2025")) {
            // Add approved leave days
            usedLeaveDays += Integer.parseInt(record[9]);
        }
    }
    
    int remainingDays = totalLeaveDays - usedLeaveDays;
    
    // Create leave balance summary panel
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
    JLabel daysLabel = new JLabel(Integer.toString(remainingDays));
    daysLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
    daysLabel.setForeground(new Color(0, 102, 204));
    
    JLabel textLabel = new JLabel("Days Remaining");
    textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    
    panel.add(Box.createVerticalGlue());
    panel.add(daysLabel);
    panel.add(textLabel);
    panel.add(Box.createVerticalGlue());
    
    // Add to Leave Balance panel
    LeaveBalance.removeAll();
    LeaveBalance.setLayout(new BorderLayout());
    LeaveBalance.add(panel, BorderLayout.CENTER);
    
    // Add a header
    JLabel headerLabel = new JLabel("Leave Balance");
    headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    LeaveBalance.add(headerLabel, BorderLayout.NORTH);
    
    LeaveBalance.repaint();
    LeaveBalance.revalidate();
}

/**
 * Populate Last Payslip panel with most recent payslip data
 */
private void populateLastPayslipPanel(java.util.List<String[]> payrollRecords) {
    // Find latest payslip for current employee
    String[] latestPayslip = null;
    int latestYear = 0;
    int latestMonth = 0;
    
    for (String[] record : payrollRecords) {
        if (record.length > 15 && record[0].equals(userEmployeeNumber)) {
            int year = Integer.parseInt(record[12]);
            int month = getMonthNumber(record[11]);
            
            if (year > latestYear || (year == latestYear && month > latestMonth)) {
                latestYear = year;
                latestMonth = month;
                latestPayslip = record;
            }
        }
    }
    
    // Create payslip summary panel
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
    if (latestPayslip != null) {
        JLabel periodLabel = new JLabel(latestPayslip[11] + " " + latestPayslip[12]);
        periodLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JLabel amountLabel = new JLabel("₱" + latestPayslip[15]);
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        amountLabel.setForeground(new Color(0, 153, 0));
        
        JLabel textLabel = new JLabel("Net Pay");
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        panel.add(Box.createVerticalGlue());
        panel.add(periodLabel);
        panel.add(Box.createVerticalStrut(10));
        panel.add(amountLabel);
        panel.add(textLabel);
        panel.add(Box.createVerticalGlue());
    } else {
        JLabel noDataLabel = new JLabel("No payslip data available");
        panel.add(Box.createVerticalGlue());
        panel.add(noDataLabel);
        panel.add(Box.createVerticalGlue());
    }
    
    // Add to Last Payslip panel
    LastPayslip.removeAll();
    LastPayslip.setLayout(new BorderLayout());
    LastPayslip.add(panel, BorderLayout.CENTER);
    
    // Add a header
    JLabel headerLabel = new JLabel("Last Payslip");
    headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    LastPayslip.add(headerLabel, BorderLayout.NORTH);
    
    LastPayslip.repaint();
    LastPayslip.revalidate();
}

/**
 * Populate My Leave Requests panel with pending leave requests
 */
private void populateLeaveRequestsPanel(java.util.List<String[]> leaveRecords) {
    // Create a panel with a table for leave requests
    JPanel panel = new JPanel(new BorderLayout());
    
    // Create table model
    String[] columnNames = {"Date Filed", "Type", "Status", "Start Date", "End Date"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    
    // Add current employee's leave requests
    for (String[] record : leaveRecords) {
        if (record.length > 9 && record[1].equals(userEmployeeNumber)) {
            model.addRow(new Object[]{
                record[5],                  // Date Filed
                record[6].split("_")[0],    // Leave Type
                record[4],                  // Status
                record[7],                  // Start Date
                record[8]                   // End Date
            });
        }
    }
    
    // Create and configure table
    JTable table = new JTable(model);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setRowHeight(25);
    
    // Add table to a scroll pane
    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);
    
    // Add to Leave Request panel
    LeaveRequest.removeAll();
    LeaveRequest.setLayout(new BorderLayout());
    LeaveRequest.add(panel, BorderLayout.CENTER);
    
    // Add a header
    JLabel headerLabel = new JLabel("My Leave Requests");
    headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    LeaveRequest.add(headerLabel, BorderLayout.NORTH);
    
    LeaveRequest.repaint();
    LeaveRequest.revalidate();
}

/**
 * Populate Recent Payslips panel with payslip history
 */
private void populateRecentPayslipsPanel(java.util.List<String[]> payrollRecords) {
    // Create a panel with a table for recent payslips
    JPanel panel = new JPanel(new BorderLayout());
    
    // Create table model
    String[] columnNames = {"Period", "Gross Income", "Total Deductions", "Net Pay"};
    DefaultTableModel model = new DefaultTableModel(columnNames, 0);
    
    // Add current employee's payslips, sorted by date
    List<String[]> employeePayslips = new ArrayList<>();
    
    for (String[] record : payrollRecords) {
        if (record.length > 15 && record[0].equals(userEmployeeNumber)) {
            employeePayslips.add(record);
        }
    }
    
    // Sort by year and month (descending)
    Collections.sort(employeePayslips, (a, b) -> {
        int yearA = Integer.parseInt(a[12]);
        int yearB = Integer.parseInt(b[12]);
        
        if (yearA != yearB) {
            return Integer.compare(yearB, yearA);
        }
        
        int monthA = getMonthNumber(a[11]);
        int monthB = getMonthNumber(b[11]);
        return Integer.compare(monthB, monthA);
    });
    
    // Add to table
    for (String[] record : employeePayslips) {
        model.addRow(new Object[]{
            record[11] + " " + record[12],  // Period
            "₱" + record[6],                // Gross Income
            "₱" + record[14],               // Total Deductions
            "₱" + record[15]                // Net Pay
        });
    }
    
    // Create and configure table
    JTable table = new JTable(model);
    table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    table.setRowHeight(25);
    
    // Add table to a scroll pane
    JScrollPane scrollPane = new JScrollPane(table);
    panel.add(scrollPane, BorderLayout.CENTER);
    
    // Add to Recent Payslips panel
    RecentPayslips.removeAll();
    RecentPayslips.setLayout(new BorderLayout());
    RecentPayslips.add(panel, BorderLayout.CENTER);
    
    // Add a header
    JLabel headerLabel = new JLabel("Recent Payslips");
    headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
    RecentPayslips.add(headerLabel, BorderLayout.NORTH);
    
    RecentPayslips.repaint();
    RecentPayslips.revalidate();
}

/**
 * Helper method to convert month name to number
 */
private int getMonthNumber(String monthName) {
    String[] months = {"January", "February", "March", "April", "May", "June", 
                      "July", "August", "September", "October", "November", "December"};
    
    for (int i = 0; i < months.length; i++) {
        if (months[i].equalsIgnoreCase(monthName)) {
            return i + 1;
        }
    }
    return 0;
}

/**
 * Make panels clickable for navigation
 */
private void makeNavigablePanels() {
    // Make Leave Balance panel clickable to go to Leave Application
    LeaveBalance.setCursor(new Cursor(Cursor.HAND_CURSOR));
    LeaveBalance.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            showEmployee_LeaveApplication();
        }
    });
    
    // Make Last Payslip panel clickable to go to Payroll
    LastPayslip.setCursor(new Cursor(Cursor.HAND_CURSOR));
    LastPayslip.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            showEmployee_Payroll();
        }
    });
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        MotorPH = new javax.swing.JLabel();
        MainMenu = new javax.swing.JLabel();
        Dashboard = new javax.swing.JButton();
        LeaveApplication = new javax.swing.JButton();
        MyProfile = new javax.swing.JButton();
        Payroll = new javax.swing.JButton();
        SignOut = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        LeaveBalance = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        LastPayslip = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        RecentPayslips = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        LeaveRequest = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 204, 204));

        MotorPH.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        MotorPH.setText("MOTORPH ");

        MainMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        MainMenu.setForeground(new java.awt.Color(102, 102, 102));
        MainMenu.setText("EMPLOYEE ");

        Dashboard.setBackground(new java.awt.Color(255, 153, 153));
        Dashboard.setText("Dashboard");
        Dashboard.setBorder(null);
        Dashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DashboardActionPerformed(evt);
            }
        });

        LeaveApplication.setBackground(new java.awt.Color(255, 204, 204));
        LeaveApplication.setText("Leave Application");
        LeaveApplication.setBorder(null);
        LeaveApplication.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LeaveApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeaveApplicationActionPerformed(evt);
            }
        });

        MyProfile.setBackground(new java.awt.Color(255, 204, 204));
        MyProfile.setText("My Profile");
        MyProfile.setBorder(null);
        MyProfile.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MyProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MyProfileActionPerformed(evt);
            }
        });

        Payroll.setBackground(new java.awt.Color(255, 204, 204));
        Payroll.setText("Payroll");
        Payroll.setBorder(null);
        Payroll.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Payroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PayrollActionPerformed(evt);
            }
        });

        SignOut.setBackground(new java.awt.Color(255, 204, 204));
        SignOut.setText("Sign Out");
        SignOut.setBorder(null);
        SignOut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SignOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignOutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SignOut, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MainMenu)
                    .addComponent(Payroll, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MyProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LeaveApplication, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MotorPH))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MotorPH)
                .addGap(30, 30, 30)
                .addComponent(MainMenu)
                .addGap(18, 18, 18)
                .addComponent(Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(MyProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LeaveApplication, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Payroll, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SignOut, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Leave Balance");

        javax.swing.GroupLayout LeaveBalanceLayout = new javax.swing.GroupLayout(LeaveBalance);
        LeaveBalance.setLayout(LeaveBalanceLayout);
        LeaveBalanceLayout.setHorizontalGroup(
            LeaveBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeaveBalanceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(242, Short.MAX_VALUE))
        );
        LeaveBalanceLayout.setVerticalGroup(
            LeaveBalanceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeaveBalanceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 14, Short.MAX_VALUE)
                .addComponent(LeaveBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(LeaveBalance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel5.setBackground(new java.awt.Color(0, 153, 102));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Last Payslip");

        javax.swing.GroupLayout LastPayslipLayout = new javax.swing.GroupLayout(LastPayslip);
        LastPayslip.setLayout(LastPayslipLayout);
        LastPayslipLayout.setHorizontalGroup(
            LastPayslipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LastPayslipLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(234, Short.MAX_VALUE))
        );
        LastPayslipLayout.setVerticalGroup(
            LastPayslipLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LastPayslipLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 15, Short.MAX_VALUE)
                .addComponent(LastPayslip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(LastPayslip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Recent Payslip");

        javax.swing.GroupLayout RecentPayslipsLayout = new javax.swing.GroupLayout(RecentPayslips);
        RecentPayslips.setLayout(RecentPayslipsLayout);
        RecentPayslipsLayout.setHorizontalGroup(
            RecentPayslipsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecentPayslipsLayout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 797, Short.MAX_VALUE))
        );
        RecentPayslipsLayout.setVerticalGroup(
            RecentPayslipsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RecentPayslipsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(128, Short.MAX_VALUE))
        );

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("My Leave Request");

        javax.swing.GroupLayout LeaveRequestLayout = new javax.swing.GroupLayout(LeaveRequest);
        LeaveRequest.setLayout(LeaveRequestLayout);
        LeaveRequestLayout.setHorizontalGroup(
            LeaveRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeaveRequestLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(791, Short.MAX_VALUE))
        );
        LeaveRequestLayout.setVerticalGroup(
            LeaveRequestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LeaveRequestLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addContainerGap(128, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LeaveRequest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(RecentPayslips, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(72, 72, 72)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(LeaveRequest, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(RecentPayslips, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DashboardActionPerformed
        // TODO add your handling code here:
        // Dashboard button
        showDashboard();
    }//GEN-LAST:event_DashboardActionPerformed

    private void LeaveApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeaveApplicationActionPerformed
        // User Management button
        showEmployee_LeaveApplication();
        resetButtonColors();
        LeaveApplication.setBackground(new Color(0, 204, 102));
        LeaveApplication.setForeground(Color.WHITE);
    }//GEN-LAST:event_LeaveApplicationActionPerformed

    private void MyProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MyProfileActionPerformed
        // TODO add your handling code here:
        openMyProfile();
        resetButtonColors();
        MyProfile.setBackground(new Color(0, 204, 102));
        MyProfile.setForeground(Color.WHITE);
    }//GEN-LAST:event_MyProfileActionPerformed

    private void PayrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PayrollActionPerformed
        // TODO add your handling code here:
        showEmployee_Payroll();
        resetButtonColors();
        Payroll.setBackground(new Color(0, 204, 102));
        Payroll.setForeground(Color.WHITE);
    }//GEN-LAST:event_PayrollActionPerformed

    private void SignOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignOutActionPerformed
        // TODO add your handling code here:
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

    }//GEN-LAST:event_SignOutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Employee_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Employee_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Employee_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Employee_Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new Employee_Dashboard().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Dashboard;
    private javax.swing.JPanel LastPayslip;
    private javax.swing.JButton LeaveApplication;
    private javax.swing.JPanel LeaveBalance;
    private javax.swing.JPanel LeaveRequest;
    private javax.swing.JLabel MainMenu;
    private javax.swing.JLabel MotorPH;
    private javax.swing.JButton MyProfile;
    private javax.swing.JButton Payroll;
    private javax.swing.JPanel RecentPayslips;
    private javax.swing.JButton SignOut;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    // End of variables declaration//GEN-END:variables
}
