/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aoop_crud_payrollsystem;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.HeadlessException;
import java.io.File;
import java.io.FileWriter;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Sylani
 */
public final class HR_ViewEmployee extends javax.swing.JFrame {

    /**
     * Creates new form HR_Management
     */
public HR_ViewEmployee() {
    
    try {
        initComponents();
        // Load data
        try {
            String csvFile = "MotorPHEmployeeData.csv";
            csvRun(csvFile);
            textFieldEditSetting(false);
        } catch (CsvException | IOException ex) {
            Logger.getLogger(HR_ViewEmployee.class.getName()).log(Level.SEVERE, "Error loading data", ex);
            JOptionPane.showMessageDialog(this, 
                "Error loading employee data: " + ex.getMessage(),
                "Data Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    } catch (HeadlessException ex) {
        Logger.getLogger(HR_ViewEmployee.class.getName()).log(Level.SEVERE, "Error initializing UI", ex);
    }
}
private void showDashboard() {
    try {
        HR_Dashboard dashboard = new HR_Dashboard();
        WindowUtils.switchToWindow(this, dashboard);
    } catch (Exception ex) {
        Logger.getLogger(HR_ViewEmployee.class.getName()).log(Level.SEVERE, 
                        "Error opening dashboard", ex);
        JOptionPane.showMessageDialog(this, "Error opening dashboard: " + ex.getMessage(), 
                                    "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void resetButtonColors() {
    Dashboard.setBackground(new Color(204, 153, 255));
    Dashboard.setForeground(Color.BLACK);
    ViewEmployee.setBackground(new Color(204, 153, 255));
    ViewEmployee.setForeground(Color.BLACK);
    LeaveApplication.setBackground(new Color(204, 153, 255));
    LeaveApplication.setForeground(Color.BLACK);
    PayrollSummary.setBackground(new Color(204, 153, 255));
    PayrollSummary.setForeground(Color.BLACK);
    SignOut.setBackground(new Color(204, 153, 255));
    SignOut.setForeground(Color.BLACK);
}

    
    /**
     * Process CSV file and populate employee data
     */
    private void csvRun(String csvFile) throws FileNotFoundException, IOException, CsvException {
        // Check if file exists first
        File file = new File(csvFile);
        if (!file.exists()) {
            // Create the file with headers if it doesn't exist
            try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
                String[] headers = {"Employee", "Last Name", "First Name", "Birthday", "Address", 
                                  "Phone Number", "SSS #", "Philhealth #", "TIN", "Pag-ibig #", 
                                  "Status", "Position", "Immediate Supervisor", "Basic Salary", 
                                  "Rice Subsidy", "Phone Allowance", "Clothing Allowance"};
                writer.writeNext(headers);
            }
            JOptionPane.showMessageDialog(this, "Created new employee data file.", 
                                        "File Created", JOptionPane.INFORMATION_MESSAGE);
        }
        
        List<String[]> records = FileHandling.readCSV(csvFile);
        informationTable(records);
    }

    /**
     * Display employee data in the table
     */
    private void informationTable(List<String[]> records) {
        DefaultTableModel tableModel = (DefaultTableModel) jTableEmployeeList.getModel();
        tableModel.setRowCount(0); // Clear existing rows
        
        for (String[] record : records) {
            tableModel.addRow(record);
        }
    }

    /**
     * Only allow digits in text fields
     * @param evt
     */
    public static void allowOnlyDigits(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }

    /**
     * Allow digits and hyphens in text fields
     * @param evt
     */
    public static void allowOnlyDigitsSpecial(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '-') {
            evt.consume();
        }
    }

    /**
     * Allow only date characters in text fields
     * @param evt
     */
    public static void allowOnlyDate(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) && c != '/' && c != '-' && c != '.') {
            evt.consume();
        }
    }

/**
 * Convert various date formats to Date object
 * @param dateObj
 * @return 
 */
public Date convertToDate(Object dateObj) {
    try {
        if (dateObj == null) {
            return null;
        }
        
        // JDK 19 compatible switch statement
        if (dateObj instanceof Date) {
            return (Date) dateObj;
        } else if (dateObj instanceof String) {
            String dateStr = (String) dateObj;
            if (dateStr.trim().isEmpty()) {
                return null;
            }
            
            // Try different date formats
            SimpleDateFormat[] formats = {
                new SimpleDateFormat("MM/dd/yyyy"),
                new SimpleDateFormat("yyyy-MM-dd"),
                new SimpleDateFormat("dd/MM/yyyy"),
                new SimpleDateFormat("MM-dd-yyyy"),
                new SimpleDateFormat("dd-MM-yyyy")
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
            Logger.getLogger(HR_ViewEmployee.class.getName()).log(
                Level.WARNING, "Could not parse date: {0}", dateStr);
        }
        
        return null;
    } catch (Exception e) {
        Logger.getLogger(HR_ViewEmployee.class.getName()).log(Level.SEVERE, "Error converting date", e);
        JOptionPane.showMessageDialog(this, 
            "Error converting date: " + e.getMessage(), 
            "Date Error", 
            JOptionPane.ERROR_MESSAGE);
        return null;
    }
}

    /**
     * Enable or disable text fields for editing
     * @param condition
     */
    public void textFieldEditSetting(boolean condition) {
        jTextFieldEmployeeNum.setEnabled(false);
        jTextFieldLastName.setEnabled(condition);
        jTextFieldFirstName.setEnabled(condition);
        jDateChooserBirthday.setEnabled(condition);
        jTextAreaAddress.setEnabled(condition);
        jTextFieldPhoneNum.setEnabled(condition);     
        jTextFieldSSSnum.setEnabled(condition);
        jTextFieldPhilhealthNum.setEnabled(condition);
        jTextFieldTINnum.setEnabled(condition);
        jTextFieldPagibigNum.setEnabled(condition);
        jTextFieldStatus.setEnabled(condition);
        jTextFieldPosition.setEnabled(condition);
        jTextFieldSupervisor.setEnabled(condition);
        jTextFieldBasicSalary.setEnabled(condition);
        jTextFieldRiceSubsidy.setEnabled(condition);
        jTextFieldPhoneAllow.setEnabled(condition);
        jTextFieldClothAllow.setEnabled(condition);

        // Set consistent text colors for better readability against the custom background
        Color textColor = new Color(0, 0, 0);
        jTextFieldEmployeeNum.setDisabledTextColor(textColor);
        jTextFieldLastName.setDisabledTextColor(textColor);
        jTextFieldFirstName.setDisabledTextColor(textColor);
        jTextAreaAddress.setDisabledTextColor(textColor);
        jTextFieldPhoneNum.setDisabledTextColor(textColor);
        jTextFieldSSSnum.setDisabledTextColor(textColor);
        jTextFieldPhilhealthNum.setDisabledTextColor(textColor);
        jTextFieldTINnum.setDisabledTextColor(textColor);
        jTextFieldPagibigNum.setDisabledTextColor(textColor);
        jTextFieldStatus.setDisabledTextColor(textColor);
        jTextFieldPosition.setDisabledTextColor(textColor);
        jTextFieldSupervisor.setDisabledTextColor(textColor);
        jTextFieldBasicSalary.setDisabledTextColor(textColor);
        jTextFieldRiceSubsidy.setDisabledTextColor(textColor);
        jTextFieldPhoneAllow.setDisabledTextColor(textColor);
        jTextFieldClothAllow.setDisabledTextColor(textColor);
    }
    
    /**
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldStatus = new javax.swing.JTextField();
        jTextFieldEmployeeNum = new javax.swing.JTextField();
        jTextFieldLastName = new javax.swing.JTextField();
        jTextFieldSSSnum = new javax.swing.JTextField();
        jTextFieldPagibigNum = new javax.swing.JTextField();
        jTextFieldTINnum = new javax.swing.JTextField();
        jTextFieldPhoneNum = new javax.swing.JTextField();
        jTextFieldPhilhealthNum = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldSupervisor = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldRiceSubsidy = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextFieldPhoneAllow = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldClothAllow = new javax.swing.JTextField();
        jTextFieldBasicSalary = new javax.swing.JTextField();
        jLabelBasicSalary = new javax.swing.JLabel();
        jTextFieldFirstName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaAddress = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldPosition = new javax.swing.JTextField();
        jDateChooserBirthday = new com.toedter.calendar.JDateChooser();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableEmployeeList = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        MotorPH = new javax.swing.JLabel();
        MainMenu = new javax.swing.JLabel();
        Dashboard = new javax.swing.JButton();
        ViewEmployee = new javax.swing.JButton();
        LeaveApplication = new javax.swing.JButton();
        PayrollSummary = new javax.swing.JButton();
        SignOut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255, 0));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setMinimumSize(new java.awt.Dimension(1037, 364));
        jPanel3.setPreferredSize(new java.awt.Dimension(1037, 364));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Status");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 13, -1, 22));

        jLabel3.setText("Employee No.");
        jPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 43, -1, -1));

        jLabel4.setText("Last Name");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 13, -1, -1));

        jLabel6.setText("Birthday");
        jPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 103, -1, -1));

        jLabel7.setText("SSS No.");
        jPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 43, -1, -1));

        jLabel8.setText("Phone Number");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 73, -1, -1));

        jLabel9.setText("PhilHealth No.");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 73, -1, -1));

        jLabel10.setText("TIN");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 13, -1, -1));

        jLabel11.setText("Pagibig No.");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 103, -1, -1));

        jTextFieldStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldStatus.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldStatusActionPerformed(evt);
            }
        });
        jPanel3.add(jTextFieldStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 10, 190, 22));

        jTextFieldEmployeeNum.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldEmployeeNum.setCaretColor(new java.awt.Color(51, 51, 51));
        jTextFieldEmployeeNum.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldEmployeeNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEmployeeNumActionPerformed(evt);
            }
        });
        jTextFieldEmployeeNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldEmployeeNumKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldEmployeeNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 40, 190, 22));

        jTextFieldLastName.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldLastName.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldLastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLastNameActionPerformed(evt);
            }
        });
        jPanel3.add(jTextFieldLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 190, 22));

        jTextFieldSSSnum.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldSSSnum.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldSSSnum.setName(""); // NOI18N
        jTextFieldSSSnum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSSSnumActionPerformed(evt);
            }
        });
        jTextFieldSSSnum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldSSSnumKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldSSSnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 40, 190, 22));

        jTextFieldPagibigNum.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldPagibigNum.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldPagibigNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPagibigNumKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldPagibigNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 100, 190, 22));

        jTextFieldTINnum.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldTINnum.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldTINnum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTINnumActionPerformed(evt);
            }
        });
        jTextFieldTINnum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldTINnumKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldTINnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, 190, 22));

        jTextFieldPhoneNum.setEditable(false);
        jTextFieldPhoneNum.setBackground(new java.awt.Color(255, 255, 255));
        jTextFieldPhoneNum.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldPhoneNum.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldPhoneNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPhoneNumActionPerformed(evt);
            }
        });
        jPanel3.add(jTextFieldPhoneNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 70, 190, 22));

        jTextFieldPhilhealthNum.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldPhilhealthNum.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldPhilhealthNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPhilhealthNumActionPerformed(evt);
            }
        });
        jTextFieldPhilhealthNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPhilhealthNumKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldPhilhealthNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 70, 190, 22));

        jLabel14.setText("Immediate Supervisor");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 103, -1, -1));

        jTextFieldSupervisor.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldSupervisor.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldSupervisor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSupervisorActionPerformed(evt);
            }
        });
        jPanel3.add(jTextFieldSupervisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 100, 190, 22));

        jLabel15.setText("Rice Subsidy");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 253, -1, -1));

        jTextFieldRiceSubsidy.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldRiceSubsidy.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldRiceSubsidy.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldRiceSubsidyKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldRiceSubsidy, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 250, 190, 22));
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(311, 372, -1, -1));

        jLabel17.setText("Phone Allowance");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 253, -1, -1));

        jTextFieldPhoneAllow.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldPhoneAllow.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldPhoneAllow.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPhoneAllowKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldPhoneAllow, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 250, 190, 22));

        jLabel19.setText("Clothing  Allowance");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 253, -1, -1));

        jTextFieldClothAllow.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldClothAllow.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldClothAllow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldClothAllowActionPerformed(evt);
            }
        });
        jTextFieldClothAllow.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldClothAllowKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldClothAllow, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 250, 190, 22));

        jTextFieldBasicSalary.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldBasicSalary.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldBasicSalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBasicSalaryActionPerformed(evt);
            }
        });
        jTextFieldBasicSalary.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldBasicSalaryKeyTyped(evt);
            }
        });
        jPanel3.add(jTextFieldBasicSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 220, 190, 22));

        jLabelBasicSalary.setText("Basic Salary");
        jPanel3.add(jLabelBasicSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 223, -1, -1));

        jTextFieldFirstName.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldFirstName.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldFirstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFirstNameActionPerformed(evt);
            }
        });
        jPanel3.add(jTextFieldFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 40, 190, 22));

        jLabel5.setText("First Name");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 43, -1, -1));

        jLabel20.setText("Address");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 158, -1, -1));

        jTextAreaAddress.setColumns(20);
        jTextAreaAddress.setLineWrap(true);
        jTextAreaAddress.setRows(5);
        jTextAreaAddress.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextAreaAddress.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jScrollPane1.setViewportView(jTextAreaAddress);

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 140, 190, 56));

        jLabel18.setText("Positon");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(315, 73, -1, -1));

        jTextFieldPosition.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldPosition.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPositionActionPerformed(evt);
            }
        });
        jPanel3.add(jTextFieldPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(445, 70, 190, 22));

        jDateChooserBirthday.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooserBirthday.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jDateChooserBirthday.setToolTipText("");
        jDateChooserBirthday.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jDateChooserBirthdayKeyTyped(evt);
            }
        });
        jPanel3.add(jDateChooserBirthday, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 190, 30));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255, 0));
        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane2.setAutoscrolls(true);
        jScrollPane2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane2MouseClicked(evt);
            }
        });

        jTableEmployeeList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employee", "Last Name", "First Name", "Birthday", "Address", "Phone Number", "SSS #", "Philhealth #", "TIN ", "Pag-ibig #", "Status", "Position", "Immediate Supervisor", "Basic Salary", "Rice Subsidy", "Phone Allowance", "Clothing Allowance"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableEmployeeList.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jTableEmployeeList.setAutoscrolls(false);
        jTableEmployeeList.getTableHeader().setReorderingAllowed(false);
        jTableEmployeeList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableEmployeeListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTableEmployeeList);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 168, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

        ViewEmployee.setBackground(new java.awt.Color(204, 102, 255));
        ViewEmployee.setText("View Employee");
        ViewEmployee.setBorder(null);
        ViewEmployee.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ViewEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ViewEmployeeActionPerformed(evt);
            }
        });

        LeaveApplication.setBackground(new java.awt.Color(204, 153, 255));
        LeaveApplication.setText("Leave Application");
        LeaveApplication.setBorder(null);
        LeaveApplication.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LeaveApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeaveApplicationActionPerformed(evt);
            }
        });

        PayrollSummary.setBackground(new java.awt.Color(204, 153, 255));
        PayrollSummary.setText("Payroll Summary");
        PayrollSummary.setBorder(null);
        PayrollSummary.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        PayrollSummary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PayrollSummaryActionPerformed(evt);
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
                    .addComponent(SignOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PayrollSummary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LeaveApplication, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                    .addComponent(ViewEmployee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(ViewEmployee, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(LeaveApplication, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PayrollSummary, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ViewEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ViewEmployeeActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTableEmployeeList.getModel();
        int selectedRowIndex = jTableEmployeeList.getSelectedRow();

        // Check if a row is selected
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select an employee from the list first.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Check if the necessary files exist before creating the PayrollProcessing frame
            File employeeHoursFile = new File("Employee_Hours_Worked.csv");
            if (!employeeHoursFile.exists()) {
                // Create the parent directory if it doesn't exist
                employeeHoursFile.getParentFile().mkdirs();

                // Create an empty CSV file with headers
                try (CSVWriter writer = new CSVWriter(new FileWriter(employeeHoursFile))) {
                    String[] headers = {"employeeNumber", "date", "timeIn", "timeOut", "hoursWorked"};
                    writer.writeNext(headers);
                    System.out.println("Created Employee_Hours_Worked.csv file");
                }
            }

            // Now try to create the PayrollProcessing frame
            Finance_HR_PayrollProcessing viewEmployeeFrame = new Finance_HR_PayrollProcessing();

            // Display the window
            viewEmployeeFrame.setVisible(true);
            viewEmployeeFrame.pack();
            viewEmployeeFrame.setDefaultCloseOperation(Finance_HR_PayrollProcessing.DISPOSE_ON_CLOSE);

            // Display the data in viewEmployeeFrame
            viewEmployeeFrame.jTextFieldEmployeeNum.setText(model.getValueAt(selectedRowIndex, 0).toString());
            viewEmployeeFrame.jTextFieldLastName.setText(model.getValueAt(selectedRowIndex, 1).toString());
            viewEmployeeFrame.jTextFieldFirstName.setText(model.getValueAt(selectedRowIndex, 2).toString());
            viewEmployeeFrame.jTextFieldBasicSalary.setText(model.getValueAt(selectedRowIndex, 13).toString());

            //Benefits
            try {
                double riceSubsidy = Double.parseDouble(model.getValueAt(selectedRowIndex, 14).toString());
                double phoneAllowance = Double.parseDouble(model.getValueAt(selectedRowIndex, 15).toString());
                double clothingAllowance = Double.parseDouble(model.getValueAt(selectedRowIndex, 16).toString());
                double totalBenefits = riceSubsidy + phoneAllowance + clothingAllowance;
                String formattedTotalBenefits = String.format("%.2f", totalBenefits);
                viewEmployeeFrame.jTextFieldBenefits.setText(formattedTotalBenefits);
            } catch (NumberFormatException e) {
                // Handle case where benefits data is not valid numbers
                viewEmployeeFrame.jTextFieldBenefits.setText("0.00");
                System.err.println("Error calculating benefits: " + e.getMessage());
            }

        } catch (FileNotFoundException | CsvException ex) {
            Logger.getLogger(UserManagement.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,
                "Error loading employee data: " + ex.getMessage(),
                "Data Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(UserManagement.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this,
                "I/O Error: " + ex.getMessage(),
                "File Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_ViewEmployeeActionPerformed

    private void PayrollSummaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PayrollSummaryActionPerformed
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
    }//GEN-LAST:event_PayrollSummaryActionPerformed

    private void SignOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignOutActionPerformed
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

    private void jTextFieldStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldStatusActionPerformed

    private void jTextFieldEmployeeNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEmployeeNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEmployeeNumActionPerformed

    private void jTextFieldEmployeeNumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEmployeeNumKeyTyped
        // TODO add your handling code here:
        allowOnlyDigits(evt);
    }//GEN-LAST:event_jTextFieldEmployeeNumKeyTyped

    private void jTextFieldLastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLastNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLastNameActionPerformed

    private void jTextFieldSSSnumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSSSnumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSSSnumActionPerformed

    private void jTextFieldSSSnumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSSSnumKeyTyped
        // TODO add your handling code here:
        allowOnlyDigitsSpecial(evt);
    }//GEN-LAST:event_jTextFieldSSSnumKeyTyped

    private void jTextFieldPagibigNumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPagibigNumKeyTyped
        // TODO add your handling code here:
        allowOnlyDigitsSpecial(evt);
    }//GEN-LAST:event_jTextFieldPagibigNumKeyTyped

    private void jTextFieldTINnumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTINnumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTINnumActionPerformed

    private void jTextFieldTINnumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTINnumKeyTyped
        // TODO add your handling code here:
        allowOnlyDigitsSpecial(evt);
    }//GEN-LAST:event_jTextFieldTINnumKeyTyped

    private void jTextFieldPhoneNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPhoneNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPhoneNumActionPerformed

    private void jTextFieldPhilhealthNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPhilhealthNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPhilhealthNumActionPerformed

    private void jTextFieldPhilhealthNumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPhilhealthNumKeyTyped
        // TODO add your handling code here:
        allowOnlyDigitsSpecial(evt);
    }//GEN-LAST:event_jTextFieldPhilhealthNumKeyTyped

    private void jTextFieldSupervisorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSupervisorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSupervisorActionPerformed

    private void jTextFieldRiceSubsidyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldRiceSubsidyKeyTyped
        // TODO add your handling code here:

        allowOnlyDigits(evt);
    }//GEN-LAST:event_jTextFieldRiceSubsidyKeyTyped

    private void jTextFieldPhoneAllowKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPhoneAllowKeyTyped
        // TODO add your handling code here:
        allowOnlyDigits(evt);
    }//GEN-LAST:event_jTextFieldPhoneAllowKeyTyped

    private void jTextFieldClothAllowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldClothAllowActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldClothAllowActionPerformed

    private void jTextFieldClothAllowKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldClothAllowKeyTyped
        // TODO add your handling code here:
        allowOnlyDigits(evt);
    }//GEN-LAST:event_jTextFieldClothAllowKeyTyped

    private void jTextFieldBasicSalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBasicSalaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBasicSalaryActionPerformed

    private void jTextFieldBasicSalaryKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBasicSalaryKeyTyped
        // TODO add your handling code here:
        allowOnlyDigits(evt);
    }//GEN-LAST:event_jTextFieldBasicSalaryKeyTyped

    private void jTextFieldFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFirstNameActionPerformed

    private void jTextFieldPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPositionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPositionActionPerformed

    private void jDateChooserBirthdayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooserBirthdayKeyTyped
        // TODO add your handling code here:
        allowOnlyDate(evt);
    }//GEN-LAST:event_jDateChooserBirthdayKeyTyped

    private void jTableEmployeeListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableEmployeeListMouseClicked
        // TODO add your handling code here:

        textFieldEditSetting(false);

        DefaultTableModel model = (DefaultTableModel) jTableEmployeeList.getModel();
        int selectedRowIndex = jTableEmployeeList.getSelectedRow();
        Object birthday = model.getValueAt(selectedRowIndex, 3);
        Date birthday_ = convertToDate(birthday);
        jTextFieldEmployeeNum.setText(model.getValueAt(selectedRowIndex, 0).toString());
        jTextFieldLastName.setText(model.getValueAt(selectedRowIndex, 1).toString());
        jTextFieldFirstName.setText(model.getValueAt(selectedRowIndex, 2).toString());
        jDateChooserBirthday.setDate(birthday_);
        jTextAreaAddress.setText(model.getValueAt(selectedRowIndex, 4).toString());
        jTextFieldPhoneNum.setText(model.getValueAt(selectedRowIndex, 5).toString());
        jTextFieldSSSnum.setText(model.getValueAt(selectedRowIndex, 6).toString());
        jTextFieldPhilhealthNum.setText(model.getValueAt(selectedRowIndex, 7).toString());
        jTextFieldTINnum.setText(model.getValueAt(selectedRowIndex, 8).toString());
        jTextFieldPagibigNum.setText(model.getValueAt(selectedRowIndex, 9).toString());
        jTextFieldStatus.setText(model.getValueAt(selectedRowIndex, 10).toString());
        jTextFieldPosition.setText(model.getValueAt(selectedRowIndex, 11).toString());
        jTextFieldSupervisor.setText(model.getValueAt(selectedRowIndex, 12).toString());
        jTextFieldBasicSalary.setText(model.getValueAt(selectedRowIndex, 13).toString());
        jTextFieldRiceSubsidy.setText(model.getValueAt(selectedRowIndex, 14).toString());
        jTextFieldPhoneAllow.setText(model.getValueAt(selectedRowIndex, 15).toString());
        jTextFieldClothAllow.setText(model.getValueAt(selectedRowIndex, 16).toString());
    }//GEN-LAST:event_jTableEmployeeListMouseClicked

    private void jScrollPane2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane2MouseClicked

    private void LeaveApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeaveApplicationActionPerformed
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

    }//GEN-LAST:event_LeaveApplicationActionPerformed

    private void DashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DashboardActionPerformed
        // TODO add your handling code here:
        // Dashboard button
        showDashboard();
        resetButtonColors();
        Dashboard.setBackground(new Color(0, 204, 102));
        Dashboard.setForeground(Color.WHITE);
    }//GEN-LAST:event_DashboardActionPerformed

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
        java.util.logging.Logger.getLogger(HR_ViewEmployee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(() -> {
        new HR_ViewEmployee().setVisible(true);
    });
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Dashboard;
    private javax.swing.JButton LeaveApplication;
    private javax.swing.JLabel MainMenu;
    private javax.swing.JLabel MotorPH;
    private javax.swing.JButton PayrollSummary;
    private javax.swing.JButton SignOut;
    private javax.swing.JButton ViewEmployee;
    private com.toedter.calendar.JDateChooser jDateChooserBirthday;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelBasicSalary;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTableEmployeeList;
    private javax.swing.JTextArea jTextAreaAddress;
    private javax.swing.JTextField jTextFieldBasicSalary;
    private javax.swing.JTextField jTextFieldClothAllow;
    private javax.swing.JTextField jTextFieldEmployeeNum;
    private javax.swing.JTextField jTextFieldFirstName;
    private javax.swing.JTextField jTextFieldLastName;
    private javax.swing.JTextField jTextFieldPagibigNum;
    private javax.swing.JTextField jTextFieldPhilhealthNum;
    private javax.swing.JTextField jTextFieldPhoneAllow;
    private javax.swing.JTextField jTextFieldPhoneNum;
    private javax.swing.JTextField jTextFieldPosition;
    private javax.swing.JTextField jTextFieldRiceSubsidy;
    private javax.swing.JTextField jTextFieldSSSnum;
    private javax.swing.JTextField jTextFieldStatus;
    private javax.swing.JTextField jTextFieldSupervisor;
    private javax.swing.JTextField jTextFieldTINnum;
    // End of variables declaration//GEN-END:variables
}
