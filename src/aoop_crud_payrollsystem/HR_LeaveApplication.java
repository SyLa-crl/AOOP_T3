/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aoop_crud_payrollsystem;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.awt.Color;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * HR Leave Application class - Fixed version
 * @author Sylani
 */
public class HR_LeaveApplication extends javax.swing.JFrame {

    private static final String FILE_NAME = "leave_applications.csv";
    public List<LeaveDetails> employees = new ArrayList<>();
    private String[] employeeInfo;

    /**
     * Default constructor
     * @throws java.io.FileNotFoundException
     * @throws com.opencsv.exceptions.CsvException
     */
    public HR_LeaveApplication() throws FileNotFoundException, IOException, CsvException {
        this(new String[]{"0", "Unknown", "User"});
    }

    /**
     * Creates new form LeaveApplicationUser
     * @param employeeInfo Array containing [employeeNumber, lastName, firstName]
     * @throws java.io.FileNotFoundException
     * @throws com.opencsv.exceptions.CsvException
     */
    public HR_LeaveApplication(String[] employeeInfo) throws FileNotFoundException, IOException, CsvException {
        this.employeeInfo = employeeInfo;
        initComponents();
        csvRun();
        
        // Center window on screen
        setLocationRelativeTo(null);
        
        // Set Leave Application as selected
        resetButtonColors();
        jButtonLeaveApplication1.setBackground(new Color(204, 102, 255));
        jButtonLeaveApplication1.setForeground(Color.WHITE);
    }

    // Helper methods
    private void resetButtonColors() {
        Dashboard.setBackground(new Color(204, 153, 255));
        Dashboard.setForeground(Color.BLACK);
        jButtonViewEmployee.setBackground(new Color(204, 153, 255));
        jButtonViewEmployee.setForeground(Color.BLACK);
        jButtonLeaveApplication1.setBackground(new Color(204, 153, 255));
        jButtonLeaveApplication1.setForeground(Color.BLACK);
        jButtonLeaveApplication.setBackground(new Color(204, 153, 255));
        jButtonLeaveApplication.setForeground(Color.BLACK);
        SignOut.setBackground(new Color(204, 153, 255));
        SignOut.setForeground(Color.BLACK);
    }

    private void showDashboard() {
        try {
            HR_Dashboard dashboard = new HR_Dashboard(employeeInfo);
            WindowUtils.switchToWindow(this, dashboard);
        } catch (Exception ex) {
            Logger.getLogger(HR_LeaveApplication.class.getName()).log(Level.SEVERE, 
                            "Error opening dashboard", ex);
            JOptionPane.showMessageDialog(this, "Error opening dashboard: " + ex.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void signOut() {
        int response = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to sign out?",
            "Confirm Sign Out",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (response == JOptionPane.YES_OPTION) {
            try {
                LoginManager loginManager = new LoginManager();
                WindowUtils.switchToWindow(this, loginManager);
            } catch (Exception ex) {
                Logger.getLogger(HR_LeaveApplication.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Error during sign out: " + ex.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void csvRun() throws FileNotFoundException, IOException, CsvException {
        List<String[]> records = FileHandling.readCSV(FILE_NAME);
        List<LeaveDetails> employees = parseRecords(records);
        this.employees = employees; // Store in class variable for later use
        informationTable(employees);
    }

    public List<LeaveDetails> parseRecords(List<String[]> records) {
        List<LeaveDetails> employees = new ArrayList<>();
        
        for (String[] record : records) {
            try {
                // Skip header or invalid rows
                if (record.length < 6 || "entryID".equalsIgnoreCase(record[0])) {
                    continue;
                }
                
                // Safely extract values with bounds checking
                String entryNum = getSafeArrayValue(record, 0, "");
                String employeeNumber = getSafeArrayValue(record, 1, "");
                String lastName = getSafeArrayValue(record, 2, "");
                String firstName = getSafeArrayValue(record, 3, "");
                String leaveStatus = getSafeArrayValue(record, 4, "Pending");
                String dateFiled = getSafeArrayValue(record, 5, "");
                String leaveReason = getSafeArrayValue(record, 6, "");
                String startDate = getSafeArrayValue(record, 7, "");
                String endDate = getSafeArrayValue(record, 8, "");
                String leaveDay = getSafeArrayValue(record, 9, "0");
                
                LeaveDetails leaveDetails = new LeaveDetails(entryNum, employeeNumber,
                    lastName, firstName, leaveStatus, dateFiled,
                    leaveReason, startDate, endDate, leaveDay);
                
                employees.add(leaveDetails);
            } catch (Exception e) {
                System.err.println("Error processing record: " + e.getMessage());
            }
        }
        
        return employees;
    }

    /**
     * Safely gets a value from an array with bounds checking
     */
    private String getSafeArrayValue(String[] array, int index, String defaultValue) {
        if (array == null || index < 0 || index >= array.length) {
            return defaultValue;
        }
        return array[index] != null ? array[index] : defaultValue;
    }

    /**
     * Converts an object to a Date object.
     * @param dateObj
     * @return 
     * @throws java.text.ParseException
     */
 public Date convertToDate(Object dateObj) throws ParseException {
    if (dateObj == null) {
        return null;
    }
    
    // Handle Date objects
    if (dateObj instanceof Date date) {
        return date;
    }
    
    // Handle String objects
    if (dateObj instanceof String string) {
        String dateStr = string.trim();
        if (dateStr.isEmpty()) {
            return null;
        }
        
        // Try different date formats that might be used in your application
        SimpleDateFormat[] formats = {
            new SimpleDateFormat("MM/dd/yyyy"),
            new SimpleDateFormat("yyyy-MM-dd"),
            new SimpleDateFormat("dd/MM/yyyy")
        };
        
        for (SimpleDateFormat format : formats) {
            try {
                format.setLenient(false); // Strict parsing
                return format.parse(dateStr);
            } catch (ParseException e) {
                // Try the next format
            }
        }
        
        // If we get here, none of the formats worked
        throw new ParseException("Could not parse date: " + dateStr, 0);
    }
    
    // If we get here, the object type is not supported
    throw new ParseException("Unsupported date type: " + dateObj.getClass().getName(), 0);
}
    
    private void informationTable(List<LeaveDetails> employees) {
        DefaultTableModel tableModel = (DefaultTableModel) jTableLeaveApplications.getModel();
        tableModel.setRowCount(0); // Clear existing rows

        for (LeaveDetails employee : employees) {
            // Get the leave days value
            String leaveDay = employee.getLeaveDay();
            
            // Make sure to display the leave days value from the CSV, not a default "0"
            tableModel.addRow(new Object[]{
                employee.getentryNum(),
                employee.getEmployeeNumber(),
                employee.getLastName(),
                employee.getFirstName(),
                employee.getLeaveStatus(),
                employee.getSubmittedDate(),
                employee.getLeaveReason(),
                employee.getStartDate(),
                employee.getEndDate(),
                leaveDay // Make sure this is the actual value
            });
        }
    }

    public void showPendingLeaves() {
        DefaultTableModel tableModel = (DefaultTableModel) jTableLeaveApplications.getModel();
        tableModel.setRowCount(0); // Clear existing rows

        for (LeaveDetails employee : employees) {
            if ("Pending".equalsIgnoreCase(employee.getLeaveStatus())) {
                tableModel.addRow(new Object[]{
                    employee.getentryNum(),
                    employee.getEmployeeNumber(),
                    employee.getLastName(),
                    employee.getFirstName(),
                    employee.getLeaveStatus(),
                    employee.getSubmittedDate(),
                    employee.getLeaveReason(),
                    employee.getStartDate(),
                    employee.getEndDate(),
                    employee.getLeaveDay()});
            }
        }
    }

    public void previousLeaves() {
        DefaultTableModel tableModel = (DefaultTableModel) jTableLeaveApplications.getModel();
        tableModel.setRowCount(0); // Clear existing rows

        for (LeaveDetails employee : employees) {
            if (!"Pending".equalsIgnoreCase(employee.getLeaveStatus())) {
                tableModel.addRow(new Object[]{
                    employee.getentryNum(),
                    employee.getEmployeeNumber(),
                    employee.getLastName(),
                    employee.getFirstName(),
                    employee.getLeaveStatus(),
                    employee.getSubmittedDate(),
                    employee.getLeaveReason(),
                    employee.getStartDate(),
                    employee.getEndDate(),
                    employee.getLeaveDay()});
            }
        }
    }

    public int[] computeLeaveTotals() {
        String employeeNumber = jTextFieldEmployeeNum.getText();

        int sickLeaveTotal = 0;
        int vacationLeaveTotal = 0;
        int paternityLeaveTotal = 0;
        int maternityLeaveTotal = 0;
        int otherLeaveTotal = 0;

        for (LeaveDetails employee : employees) {
            if (employee.getEmployeeNumber().equals(employeeNumber) && employee.getLeaveStatus().equals("Approved")) {
                switch (employee.getLeaveReason()) {
                    case "Sick Leave" -> sickLeaveTotal += Integer.parseInt(employee.getLeaveDay());
                    case "Vacation Leave" -> vacationLeaveTotal += Integer.parseInt(employee.getLeaveDay());
                    case "Paternity Leave" -> paternityLeaveTotal += Integer.parseInt(employee.getLeaveDay());
                    case "Maternity Leave" -> maternityLeaveTotal += Integer.parseInt(employee.getLeaveDay());
                    default -> otherLeaveTotal += Integer.parseInt(employee.getLeaveDay());
                }
            }
        }

        return new int[]{sickLeaveTotal, vacationLeaveTotal, paternityLeaveTotal, maternityLeaveTotal, otherLeaveTotal};
    }

    public void showSummary() {
        int[] summary = computeLeaveTotals();
        String sickLeaveTotal = Integer.toString(summary[0]);
        String vacationLeaveTotal = Integer.toString(summary[1]);
        String paternityLeaveTotal = Integer.toString(summary[2]);
        String maternityLeaveTotal = Integer.toString(summary[3]);
        String otherLeaveTotal = Integer.toString(summary[4]);
        
        // Show summary in a message dialog
        String summaryMessage = "Leave Summary for Employee #" + jTextFieldEmployeeNum.getText() + ":\n\n" +
                               "Sick Leave: " + sickLeaveTotal + " days\n" +
                               "Vacation Leave: " + vacationLeaveTotal + " days\n" +
                               "Paternity Leave: " + paternityLeaveTotal + " days\n" +
                               "Maternity Leave: " + maternityLeaveTotal + " days\n" +
                               "Other Leave: " + otherLeaveTotal + " days";
        
        JOptionPane.showMessageDialog(this, summaryMessage, "Leave Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    public void updateLeaveStatus(String Status) {
        DefaultTableModel tableModel = (DefaultTableModel) jTableLeaveApplications.getModel();
        int selectedRow = jTableLeaveApplications.getSelectedRow();

        if (selectedRow != -1) {
            String selectedRowStatus = tableModel.getValueAt(selectedRow, 4).toString(); // Leave Status is in the 5th column (index 4)

            if (selectedRowStatus.equals("Pending")) {
                int response = JOptionPane.showConfirmDialog(null,
                        "Do you want to proceed with changing the leave status",
                        "Update Confirmation",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (response == JOptionPane.YES_OPTION) {
                    tableModel.setValueAt(Status, selectedRow, 4); // Update the status in the table model

                    // Update the employees list if you have it available
                    String employeeNumber = tableModel.getValueAt(selectedRow, 1).toString(); // Employee Number is in the 2nd column (index 1)
                    for (LeaveDetails employee : employees) {
                        if (employee.getEmployeeNumber().equals(employeeNumber) && "Pending".equalsIgnoreCase(employee.getLeaveStatus())) {
                            employee.setLeaveStatus(Status);
                            break; // Assuming each employee number is unique, we can break after the first match
                        }
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Only entries with 'Pending' leave status can be updated.",
                        "update Entry Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void updateCSV() {
        String csvFile = "leave_applications.csv";

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
            // Write header
            String[] header = {"Entry ID", "Employee Number", "Last Name", "First Name", "Leave Status",
                "Date Filed", "Reason for Leave", "Start Date", "End Date", "Leave Days"};
            writer.writeNext(header);

            // Write rows
            for (LeaveDetails employee : employees) {
                String[] row = {
                    employee.getentryNum(),
                    employee.getEmployeeNumber(),
                    employee.getLastName(),
                    employee.getFirstName(),
                    employee.getLeaveStatus(),
                    employee.getSubmittedDate(),
                    employee.getLeaveReason(),
                    employee.getStartDate(),
                    employee.getEndDate(),
                    employee.getLeaveDay()
                };
                writer.writeNext(row);
            }

            JOptionPane.showMessageDialog(null, "Record updated successfully");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Failed to update your record.");
        }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableLeaveApplications = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldEmployeeNum = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldEmployeeName = new javax.swing.JTextField();
        jTextFieldDateFiled = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldLeaveReason = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextFieldStartDate = new javax.swing.JTextField();
        jTextFieldEndDate = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldLeaveDays = new javax.swing.JTextField();
        jRadioButtonPending = new javax.swing.JRadioButton();
        jButtonReject = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jButtonApprove = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        MotorPH = new javax.swing.JLabel();
        MainMenu = new javax.swing.JLabel();
        Dashboard = new javax.swing.JButton();
        jButtonViewEmployee = new javax.swing.JButton();
        jButtonLeaveApplication1 = new javax.swing.JButton();
        jButtonLeaveApplication = new javax.swing.JButton();
        SignOut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTableLeaveApplications.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Entry ID", "Employee Number", "Last Name", "First Name", "Leave Status", "Date Filed", "Reason for Leave", "Start Date", "End Date", "Leave Days"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableLeaveApplications.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableLeaveApplications.getTableHeader().setReorderingAllowed(false);
        jTableLeaveApplications.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableLeaveApplicationsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableLeaveApplications);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255, 0));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Employee ID:");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 13, -1, -1));

        jTextFieldEmployeeNum.setEditable(false);
        jTextFieldEmployeeNum.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldEmployeeNum.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldEmployeeNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEmployeeNumActionPerformed(evt);
            }
        });
        jPanel3.add(jTextFieldEmployeeNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 10, 180, 22));

        jLabel8.setText("Employee Name");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 43, 90, -1));

        jTextFieldEmployeeName.setEditable(false);
        jTextFieldEmployeeName.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldEmployeeName.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldEmployeeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEmployeeNameActionPerformed(evt);
            }
        });
        jPanel3.add(jTextFieldEmployeeName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 180, 22));

        jTextFieldDateFiled.setEditable(false);
        jTextFieldDateFiled.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldDateFiled.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldDateFiled.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldDateFiledActionPerformed(evt);
            }
        });
        jPanel3.add(jTextFieldDateFiled, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 180, 22));

        jLabel3.setText("Date Filed");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 73, -1, -1));

        jTextFieldLeaveReason.setEditable(false);
        jTextFieldLeaveReason.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldLeaveReason.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldLeaveReason.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLeaveReasonActionPerformed(evt);
            }
        });
        jPanel3.add(jTextFieldLeaveReason, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 180, 22));

        jLabel10.setText("Reason for Leave");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 103, 100, -1));

        jTextFieldStartDate.setEditable(false);
        jTextFieldStartDate.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldStartDate.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldStartDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldStartDateActionPerformed(evt);
            }
        });
        jPanel3.add(jTextFieldStartDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 130, 180, 22));

        jTextFieldEndDate.setEditable(false);
        jTextFieldEndDate.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldEndDate.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldEndDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEndDateActionPerformed(evt);
            }
        });
        jPanel3.add(jTextFieldEndDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 180, 22));

        jLabel11.setText("End Date");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 163, -1, -1));

        jLabel12.setText("Start Date");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 133, -1, -1));

        jLabel14.setText("Leave Days");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 193, -1, -1));

        jTextFieldLeaveDays.setEditable(false);
        jTextFieldLeaveDays.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldLeaveDays.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldLeaveDays.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLeaveDaysActionPerformed(evt);
            }
        });
        jPanel3.add(jTextFieldLeaveDays, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 180, 22));

        jRadioButtonPending.setBackground(new java.awt.Color(148, 129, 93));
        jRadioButtonPending.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jRadioButtonPending.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButtonPending.setText("Show Pending Only");
        jRadioButtonPending.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonPendingActionPerformed(evt);
            }
        });

        jButtonReject.setBackground(new java.awt.Color(204, 153, 255));
        jButtonReject.setText("REJECT");
        jButtonReject.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonReject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRejectActionPerformed(evt);
            }
        });

        jButtonSave.setBackground(new java.awt.Color(204, 153, 255));
        jButtonSave.setText("SAVE");
        jButtonSave.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jButtonClose.setBackground(new java.awt.Color(204, 153, 255));
        jButtonClose.setText("CLOSE");
        jButtonClose.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jButtonApprove.setBackground(new java.awt.Color(204, 153, 255));
        jButtonApprove.setText("APPROVE");
        jButtonApprove.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonApprove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApproveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonReject, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonClose, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonApprove, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(160, 160, 160))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButtonPending)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 921, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jRadioButtonPending))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jButtonReject, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonClose)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonApprove, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                .addGap(80, 80, 80))
        );

        jPanel5.setBackground(new java.awt.Color(204, 153, 255));

        MotorPH.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        MotorPH.setText("MOTORPH ");

        MainMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        MainMenu.setForeground(new java.awt.Color(102, 102, 102));
        MainMenu.setText("HUMAN RESOURCE");

        Dashboard.setBackground(new java.awt.Color(204, 153, 255));
        Dashboard.setText("Dashboard");
        Dashboard.setBorder(null);
        Dashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DashboardActionPerformed(evt);
            }
        });

        jButtonViewEmployee.setBackground(new java.awt.Color(204, 153, 255));
        jButtonViewEmployee.setText("View Employee");
        jButtonViewEmployee.setBorder(null);
        jButtonViewEmployee.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonViewEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonViewEmployeeActionPerformed(evt);
            }
        });

        jButtonLeaveApplication1.setBackground(new java.awt.Color(204, 102, 255));
        jButtonLeaveApplication1.setText("Leave Application");
        jButtonLeaveApplication1.setBorder(null);
        jButtonLeaveApplication1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonLeaveApplication1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLeaveApplication1ActionPerformed(evt);
            }
        });

        jButtonLeaveApplication.setBackground(new java.awt.Color(204, 153, 255));
        jButtonLeaveApplication.setText("Payroll Summary");
        jButtonLeaveApplication.setBorder(null);
        jButtonLeaveApplication.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButtonLeaveApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLeaveApplicationActionPerformed(evt);
            }
        });

        SignOut.setBackground(new java.awt.Color(204, 153, 255));
        SignOut.setText("Sign Out");
        SignOut.setBorder(null);
        SignOut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SignOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignOutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SignOut, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                    .addComponent(jButtonLeaveApplication, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonLeaveApplication1, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                    .addComponent(jButtonViewEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MainMenu)
                    .addComponent(Dashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MotorPH))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MotorPH)
                .addGap(30, 30, 30)
                .addComponent(MainMenu)
                .addGap(18, 18, 18)
                .addComponent(Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonViewEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonLeaveApplication1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonLeaveApplication, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 374, Short.MAX_VALUE)
                .addComponent(SignOut, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldEmployeeNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEmployeeNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEmployeeNumActionPerformed

    private void jTextFieldEmployeeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEmployeeNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEmployeeNameActionPerformed

    private void jTextFieldDateFiledActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldDateFiledActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldDateFiledActionPerformed

    private void jTextFieldLeaveReasonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLeaveReasonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLeaveReasonActionPerformed

    private void jTextFieldStartDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldStartDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldStartDateActionPerformed

    private void jTextFieldEndDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEndDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEndDateActionPerformed

    private void jTextFieldLeaveDaysActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLeaveDaysActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLeaveDaysActionPerformed

    private void jButtonRejectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRejectActionPerformed
        // TODO add your handling code here:
        updateLeaveStatus("Rejected");
        showSummary();
    }//GEN-LAST:event_jButtonRejectActionPerformed

    private void jTableLeaveApplicationsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableLeaveApplicationsMouseClicked
        // Event Handlers
        DefaultTableModel model = (DefaultTableModel) jTableLeaveApplications.getModel();
        int selectedRowIndex = jTableLeaveApplications.getSelectedRow();
        String lastName = model.getValueAt(selectedRowIndex, 2).toString();
        String firstName = model.getValueAt(selectedRowIndex, 3).toString();
        String fullName = lastName + ", " + firstName;

        jTextFieldEmployeeNum.setText(model.getValueAt(selectedRowIndex, 1).toString());
        jTextFieldEmployeeName.setText(fullName);
        jTextFieldDateFiled.setText(model.getValueAt(selectedRowIndex, 5).toString());
        jTextFieldLeaveReason.setText(model.getValueAt(selectedRowIndex, 6).toString());
        jTextFieldStartDate.setText(model.getValueAt(selectedRowIndex, 7).toString());
        jTextFieldEndDate.setText(model.getValueAt(selectedRowIndex, 8).toString());
        jTextFieldLeaveDays.setText(model.getValueAt(selectedRowIndex, 9).toString());

        showSummary();
    }//GEN-LAST:event_jTableLeaveApplicationsMouseClicked

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        // TODO add your handling code here:
        int response = JOptionPane.showConfirmDialog(null, "Do you want to proceed with saving the changes to the records?",
            "Update Records Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            updateCSV();
        }
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        // TODO add your handling code here:
        setVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonApproveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonApproveActionPerformed
        // TODO add your handling code here:
        updateLeaveStatus("Approved");
        showSummary();
    }//GEN-LAST:event_jButtonApproveActionPerformed

    private void jRadioButtonPendingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonPendingActionPerformed
        // TODO add your handling code here:

        if (jRadioButtonPending.isSelected()) {
            showPendingLeaves();
        } else {
            informationTable(employees);
        }
    }//GEN-LAST:event_jRadioButtonPendingActionPerformed

    private void DashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DashboardActionPerformed
        // TODO add your handling code here:
        // Dashboard button
        showDashboard();
        resetButtonColors();
        Dashboard.setBackground(new Color(0, 204, 102));
        Dashboard.setForeground(Color.WHITE);
    }//GEN-LAST:event_DashboardActionPerformed

    private void jButtonViewEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonViewEmployeeActionPerformed
        try {
        HR_ViewEmployee viewEmployeeForm = new HR_ViewEmployee();
        if (viewEmployeeForm != null) {
            WindowUtils.switchToWindow(this, viewEmployeeForm);
        } else {
            JOptionPane.showMessageDialog(this, "Error creating view employee form", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (HeadlessException ex) {
        Logger.getLogger(HR_LeaveApplication.class.getName()).log(Level.SEVERE, "Error opening view employee: " + ex.getMessage(), ex);
        JOptionPane.showMessageDialog(this, "Error opening view employee: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_jButtonViewEmployeeActionPerformed

    private void jButtonLeaveApplication1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLeaveApplication1ActionPerformed
        try {
            HR_LeaveApplication leaveApp = new HR_LeaveApplication();
            leaveApp.setVisible(true);
            leaveApp.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        } catch (CsvException | IOException ex) {
            Logger.getLogger(HR_ViewEmployee.class.getName()).log(Level.SEVERE, "Error opening Leave Application", ex);
            JOptionPane.showMessageDialog(this,
                "Error opening Leave Application: " + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButtonLeaveApplication1ActionPerformed

    private void jButtonLeaveApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLeaveApplicationActionPerformed
        // TODO add your handling code here:
        try {
            HR_LeaveApplication leaveEmployee = null;
            try {
                leaveEmployee = new HR_LeaveApplication();
            } catch (FileNotFoundException | CsvException ex) {
                Logger.getLogger(UserManagement.class.getName()).log(Level.SEVERE, null, ex);
            }
            // Display the window
            leaveEmployee.setVisible(true);
            leaveEmployee.pack();
            leaveEmployee.setDefaultCloseOperation(Finance_HR_PayrollProcessing.DISPOSE_ON_CLOSE); //if viewEmployeeFrame is close, main frame will not close.

        } catch (IOException ex) {
            Logger.getLogger(UserManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonLeaveApplicationActionPerformed

    private void SignOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignOutActionPerformed
        // Confirm before exiting
        // Sign Out button - Return to Login Manager
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
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HR_LeaveApplication.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
    
            
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Dashboard;
    private javax.swing.JLabel MainMenu;
    private javax.swing.JLabel MotorPH;
    private javax.swing.JButton SignOut;
    private javax.swing.JButton jButtonApprove;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonLeaveApplication;
    private javax.swing.JButton jButtonLeaveApplication1;
    private javax.swing.JButton jButtonReject;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonViewEmployee;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButtonPending;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableLeaveApplications;
    private javax.swing.JTextField jTextFieldDateFiled;
    private javax.swing.JTextField jTextFieldEmployeeName;
    private javax.swing.JTextField jTextFieldEmployeeNum;
    private javax.swing.JTextField jTextFieldEndDate;
    private javax.swing.JTextField jTextFieldLeaveDays;
    private javax.swing.JTextField jTextFieldLeaveReason;
    private javax.swing.JTextField jTextFieldStartDate;
    // End of variables declaration//GEN-END:variables
}
