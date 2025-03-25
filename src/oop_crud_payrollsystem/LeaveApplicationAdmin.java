/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package oop_crud_payrollsystem;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
//import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Sylani
 */
public class LeaveApplicationAdmin extends javax.swing.JFrame {

   private static final String FILE_NAME = "leave_applications.csv";
    public List<LeaveDetails> employees = new ArrayList<>();

    /**
     * Creates new form LeaveApplicationUser
     * @throws java.io.FileNotFoundException
     * @throws com.opencsv.exceptions.CsvException
     */
    public LeaveApplicationAdmin() throws FileNotFoundException, IOException, CsvException {
        initComponents();
        csvRun();
//        setIconImage();

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
            String leaveDay = getSafeArrayValue(record, 9, "0"); // This is the problematic line - index 9
            
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
 * @param array The array to access
 * @param index The index to access
 * @param defaultValue The default value if index is out of bounds
 * @return The value at the index or defaultValue if index is out of bounds
 */
private String getSafeArrayValue(String[] array, int index, String defaultValue) {
    if (array == null || index < 0 || index >= array.length) {
        return defaultValue;
    }
    return array[index] != null ? array[index] : defaultValue;
}
/**
 * Converts an object to a Date object.
 * Handles Date objects and common String date formats.
 * 
 * @param dateObj The object to convert (Date or String)
 * @return A Date object
 * @throws ParseException If the date cannot be parsed
 */
public Date convertToDate(Object dateObj) throws ParseException {
    if (dateObj == null) {
        return null;
    }
    
    if (dateObj instanceof Date) {
        return (Date) dateObj;
    } else if (dateObj instanceof String) {
        String dateStr = ((String) dateObj).trim();
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
                    case "Sick Leave":
                        sickLeaveTotal += Integer.parseInt(employee.getLeaveDay());
                        break;
                    case "Vacation Leave":
                        vacationLeaveTotal += Integer.parseInt(employee.getLeaveDay());
                        break;
                    case "Paternity Leave":
                        paternityLeaveTotal += Integer.parseInt(employee.getLeaveDay());
                        break;
                    case "Maternity Leave":
                        maternityLeaveTotal += Integer.parseInt(employee.getLeaveDay());
                        break;
                    default:
                        otherLeaveTotal += Integer.parseInt(employee.getLeaveDay());
                        break;
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
    
    // Instead of updating existing text fields, you could display the information differently
    // For example, using a JOptionPane to show the summary:
    String summaryMessage = "Leave Summary for Employee #" + jTextFieldEmployeeNum.getText() + ":\n\n" +
                           "Sick Leave: " + sickLeaveTotal + " days\n" +
                           "Vacation Leave: " + vacationLeaveTotal + " days\n" +
                           "Paternity Leave: " + paternityLeaveTotal + " days\n" +
                           "Maternity Leave: " + maternityLeaveTotal + " days\n" +
                           "Other Leave: " + otherLeaveTotal + " days";
    
    // Show this in a message dialog or update a text area instead
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

                    // Optionally update the employees list if you have it available
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
        jButtonReject = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableLeaveApplications = new javax.swing.JTable();
        jButtonSave = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jButtonApprove = new javax.swing.JButton();
        jRadioButtonPending = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        jButtonReject.setText("REJECT");
        jButtonReject.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonReject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRejectActionPerformed(evt);
            }
        });

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

        jButtonSave.setText("SAVE");
        jButtonSave.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jButtonClose.setText("CLOSE");
        jButtonClose.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jButtonApprove.setText("APPROVE");
        jButtonApprove.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonApprove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonApproveActionPerformed(evt);
            }
        });

        jRadioButtonPending.setBackground(new java.awt.Color(148, 129, 93));
        jRadioButtonPending.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jRadioButtonPending.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButtonPending.setText("Show Pending Only");
        jRadioButtonPending.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonPendingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonClose, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonApprove, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRadioButtonPending)))
                .addContainerGap(626, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jButtonReject, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(30, 30, 30)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(23, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(283, 283, 283)
                .addComponent(jButtonSave, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonClose)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jRadioButtonPending))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonApprove, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(247, 247, 247)
                            .addComponent(jButtonReject, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(10, 10, 10)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(23, Short.MAX_VALUE)))
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
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(LeaveApplicationAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LeaveApplicationAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LeaveApplicationAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LeaveApplicationAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
   java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    try {
                        new LeaveApplicationAdmin().setVisible(true);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(LeaveApplicationAdmin.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (CsvException ex) {
                        Logger.getLogger(LeaveApplicationAdmin.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(LeaveApplicationAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonApprove;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonReject;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel3;
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
