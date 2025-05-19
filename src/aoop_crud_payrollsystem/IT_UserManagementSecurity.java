/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aoop_crud_payrollsystem;

import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.HeadlessException;
import java.io.File;
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
import javax.swing.text.BadLocationException;
import java.lang.String;
/**
 *
 * @author Sylani
 */
public final class IT_UserManagementSecurity extends javax.swing.JFrame {
    
    private String employeeNumber;
    public List<Employee> employeeDetails = new ArrayList<>();

    /**q1   `
     * Creates new form IT Dashboard
     * @param employeeInfo
     */
    public IT_UserManagementSecurity(String employeeInfo) {
       
        initComponents();

        // Try to load data
        try {
            String csvFile = "MotorPHEmployeeData.csv";
            csvRun(csvFile);
            textFieldEditSetting(false);
        } catch (CsvException | IOException ex) {
            Logger.getLogger(IT_UserManagementSecurity.class.getName()).log(Level.SEVERE, "Error loading data", ex);
        }
    
       // Display welcome message with employee number
        JOptionPane.showMessageDialog(this, 
            "Welcome to IT Dashboard\nEmployee Number: " + employeeNumber, 
            "IT Dashboard", 
            JOptionPane.INFORMATION_MESSAGE);
            
        // Center window on screen
        setLocationRelativeTo(null);
    }
    
    
private void openDashboard() {
   try {
        // Create employee info array using the current employee number
        String[] employeeInfo = new String[] {
            this.employeeNumber,
            "Admin", // Last name placeholder 
            "IT"     // First name placeholder
        };
        
        IT_Admin_Dashboard dashboard = new IT_Admin_Dashboard(employeeInfo);
        WindowUtils.switchToWindow(this, dashboard);
    } catch (Exception ex) {
        ErrorHandler.handleException(this, ex, 
            "Dashboard Error", 
            "Error opening IT Admin dashboard");
    }

}

private void showEmployeeManagement() {
    // Already in User Management
    JOptionPane.showMessageDialog(this, "You are already in User Management & Security!", 
                                "User Management", JOptionPane.INFORMATION_MESSAGE);
}

private void resetButtonColors() {
    Dashboard.setBackground(new Color(153, 204, 255));
    Dashboard.setForeground(Color.BLACK);
    UserManagementSecurity.setBackground(new Color(153, 204, 255));
    UserManagementSecurity.setForeground(Color.BLACK);
    SystemConfig.setBackground(new Color(153, 204, 255));
    SystemConfig.setForeground(Color.BLACK);
    SignOut.setBackground(new Color(153, 204, 255));
    SignOut.setForeground(Color.BLACK);
}



public IT_UserManagementSecurity() {
        this("0"); // Pass employee number 0 for caparas
        
        // Add any specific initialization for the default case
        JOptionPane.showMessageDialog(this, 
            "Logged in as: caparas (Employee #0)\nAdministrator Access", 
            "Administrator Mode", 
            JOptionPane.INFORMATION_MESSAGE);
    }
  
/**
 * Get all components in the container and its children
 * @param container The container to search
 * @return List of all components
 */
private List<Component> getAllComponents(Container container) {
    List<Component> componentList = new ArrayList<>();
    addComponents(container, componentList);
    return componentList;
}

/**
 * Helper method to recursively add components
 * @param container The container to search
 * @param componentList The list to add components to
 */
private void addComponents(Container container, List<Component> componentList) {
    Component[] components = container.getComponents();
    for (Component component : components) {
        componentList.add(component);
        if (component instanceof Container) {
            addComponents((Container) component, componentList);
        }
    }
}
    /**
     * Process CSV file and populate employee data
     */
    private void csvRun(String csvFile) throws FileNotFoundException, IOException, CsvException {
        List<String[]> records = FileHandling.readCSV(csvFile);
        List<Employee> employees = parseRecords(records);
        informationTable(employees);
    }
    
    /**
     * Parse CSV records into Employee objects
     * @param records
     * @return 
     */
    public List<Employee> parseRecords(List<String[]> records) {
        List<Employee> employees = new ArrayList<>();
        for (String[] record : records) {
            String employeeNumber = record[0];
            String lastName = record[1];
            String firstName = record[2];
            String employeeBirthday = record[3];
            String address = record[4];
            String phoneNumber = record[5];
            String sssNumber = record[6];
            String philHealthNumber = record[7];
            String tinNumber = record[8];
            String pagIbigNumber = record[9];
            String status = record[10];
            String position = record[11];
            String immediateSupervisor = record[12];
            String basicSalary = record[13];
            String riceSubsidy = record[14];
            String phoneAllowance = record[15];
            String clothingAllowance = record[16];

            Employee employee = new Employee(employeeNumber, lastName, firstName, employeeBirthday, address, phoneNumber, sssNumber, philHealthNumber, tinNumber, pagIbigNumber, status, position, immediateSupervisor, basicSalary, riceSubsidy, phoneAllowance, clothingAllowance);
            employees.add(employee);
        }

        return employees;
    }
    
    /**
     * Set up phone number validation
     */
    private void setupPhoneNumberValidation() {
        // Set document filter to allow only digits and max 11 characters
        ((javax.swing.text.AbstractDocument) jTextFieldPhoneNum.getDocument()).setDocumentFilter(
            new javax.swing.text.DocumentFilter() {
                @Override
                public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws BadLocationException {
                    String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                    String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
                    
                    // Only allow digits
                    if (!text.matches("\\d*")) {
                        return;
                    }
                    
                    // Check if the new length would be <= 11
                    if (newText.length() <= 11) {
                        super.replace(fb, offset, length, text, attrs);
                    }
                }
                
                @Override
                public void insertString(FilterBypass fb, int offset, String text, javax.swing.text.AttributeSet attr) throws BadLocationException {
                    String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                    String newText = currentText.substring(0, offset) + text + currentText.substring(offset);
                    
                    // Only allow digits
                    if (!text.matches("\\d*")) {
                        return;
                    }
                    
                    // Check if the new length would be <= 11
                    if (newText.length() <= 11) {
                        super.insertString(fb, offset, text, attr);
                    }
                }
            }
        );
    }
    
    /**
     * Display employee data in the table
     */
    private void informationTable(List<Employee> employees) {
        DefaultTableModel tableModel = (DefaultTableModel) jTableEmployeeList.getModel();
        tableModel.setRowCount(0); // Clear existing rows
        
        for (Employee employee : employees) {
            // Format the ID numbers to ensure they're displayed as full strings
            String sssFormatted = formatIdNumber(employee.getSssNumber());
            String philHealthFormatted = formatIdNumber(employee.getPhilHealthNumber());
            String tinFormatted = formatIdNumber(employee.getTinNumber());
            String pagIbigFormatted = formatIdNumber(employee.getPagIbigNumber());
            
            tableModel.addRow(new Object[]{
                employee.getEmployeeNumber(),
                employee.getLastName(),
                employee.getFirstName(),
                employee.getEmployeeBirthday(),
                employee.getAddress(),
                employee.getPhoneNumber(),
                sssFormatted,
                philHealthFormatted,
                tinFormatted,
                pagIbigFormatted,
                employee.getStatus(),
                employee.getPosition(),
                employee.getImmediateSupervisor(),
                employee.getBasicSalary(),
                employee.getRiceSubsidy(),
                employee.getPhoneAllowance(),
                employee.getClothingAllowance()}
            );
        }
    }
    
    /**
     * Format ID number for display
     */
    private String formatIdNumber(String idNumber) {
        try {
            // If it can be parsed as a number, format it without scientific notation
            if (idNumber.matches(".*\\d.*")) {
                return String.format("%s", idNumber.replaceAll("[^0-9-]", ""));
            }
        } catch (Exception e) {
            // If any error occurs, just return the original string
        }
        return idNumber;
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
        
        // Replace this pattern matching switch with if-else
        if (dateObj instanceof Date date) {
            return date;
        } else if (dateObj instanceof String dateStr) {
            if (dateStr.trim().isEmpty()) {
                return null;
            }
            
            // Try different date formats
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
            JOptionPane.showMessageDialog(this,
                    "Could not parse date: " + dateStr,
                    "Date Format Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        JOptionPane.showMessageDialog(this, 
            "Unsupported date type: " + dateObj.getClass().getName(), 
            "Date Format Error", 
            JOptionPane.ERROR_MESSAGE);
        return null;
    } catch (Exception e) {
        Logger.getLogger(IT_UserManagementSecurity.class.getName()).log(Level.SEVERE, "Error converting date", e);
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

        jTextFieldEmployeeNum.setDisabledTextColor(Color.BLACK);
        jTextFieldLastName.setDisabledTextColor(Color.BLACK);
        jTextFieldFirstName.setDisabledTextColor(Color.BLACK);
        jTextAreaAddress.setDisabledTextColor(Color.BLACK);
        jTextFieldPhoneNum.setDisabledTextColor(Color.BLACK);
        jTextFieldSSSnum.setDisabledTextColor(Color.BLACK);
        jTextFieldPhilhealthNum.setDisabledTextColor(Color.BLACK);
        jTextFieldTINnum.setDisabledTextColor(Color.BLACK);
        jTextFieldPagibigNum.setDisabledTextColor(Color.BLACK);
        jTextFieldStatus.setDisabledTextColor(Color.BLACK);
        jTextFieldPosition.setDisabledTextColor(Color.BLACK);
        jTextFieldSupervisor.setDisabledTextColor(Color.BLACK);
        jTextFieldBasicSalary.setDisabledTextColor(Color.BLACK);
        jTextFieldRiceSubsidy.setDisabledTextColor(Color.BLACK);
        jTextFieldPhoneAllow.setDisabledTextColor(Color.BLACK);
        jTextFieldClothAllow.setDisabledTextColor(Color.BLACK);
    }
    
    /**
     * Clear all text fields
     */
    public void clearTextField() {
        jTextFieldStatus.setText("");
        jTextFieldEmployeeNum.setText("");
        jTextFieldLastName.setText("");
        jTextFieldFirstName.setText("");
        jDateChooserBirthday.setDate(null);
        jTextFieldPhoneNum.setText("");
        jTextAreaAddress.setText("");

        jTextFieldPosition.setText("");
        jTextFieldSupervisor.setText("");

        jTextFieldSSSnum.setText("");
        jTextFieldPhilhealthNum.setText("");
        jTextFieldPagibigNum.setText("");
        jTextFieldTINnum.setText("");

        jTextFieldBasicSalary.setText("");
        jTextFieldRiceSubsidy.setText("");
        jTextFieldPhoneAllow.setText("");
        jTextFieldClothAllow.setText("");
    }
    
    /**
     * Check if all required entries are filled
     */
    private boolean checkEntries() {
        if (isEmpty(jTextAreaAddress.getText())
                || isEmpty(jTextFieldBasicSalary.getText())
                || isEmpty(formatDate(jDateChooserBirthday.getDate()))
                || isEmpty(jTextFieldClothAllow.getText())
                || isEmpty(jTextFieldEmployeeNum.getText())
                || isEmpty(jTextFieldFirstName.getText())
                || isEmpty(jTextFieldLastName.getText())
                || isEmpty(jTextFieldPagibigNum.getText())
                || isEmpty(jTextFieldPhilhealthNum.getText())
                || isEmpty(jTextFieldPhoneAllow.getText())
                || isEmpty(jTextFieldPhoneNum.getText())
                || isEmpty(jTextFieldPosition.getText())
                || isEmpty(jTextFieldRiceSubsidy.getText())
                || isEmpty(jTextFieldSSSnum.getText())
                || isEmpty(jTextFieldStatus.getText())
                || isEmpty(jTextFieldSupervisor.getText())
                || isEmpty(jTextFieldTINnum.getText())) {

            JOptionPane.showMessageDialog(null, "All fields must be filled in", "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Indicate that not all fields are filled
        }
        return true; // Indicate that all fields are filled
    }
    
    /**
     * Check if string is empty
     * @param text
     * @return 
     */
    public boolean isEmpty(String text) {
        return text.trim().isEmpty();
    }
    
    /**
     * Format date for display
     * @param date
     * @return 
     */
    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);
    }
    
    /**
     * Add new employee
     */
    public void addEmployee() {
        List<String> tableIdList = createTableIdList();
        boolean isUnique = isUniqueEmployeeId(tableIdList);

        if (isUnique) {
            DefaultTableModel model = (DefaultTableModel) jTableEmployeeList.getModel();

            model.addRow(new Object[]{
                jTextFieldEmployeeNum.getText(),
                jTextFieldLastName.getText(),
                jTextFieldFirstName.getText(),
                formatDate(jDateChooserBirthday.getDate()),
                jTextAreaAddress.getText(),
                jTextFieldPhoneNum.getText(),
                jTextFieldSSSnum.getText(),
                jTextFieldPhilhealthNum.getText(),
                jTextFieldTINnum.getText(),
                jTextFieldPagibigNum.getText(),
                jTextFieldStatus.getText(),
                jTextFieldPosition.getText(),
                jTextFieldSupervisor.getText(),
                jTextFieldBasicSalary.getText(),
                jTextFieldRiceSubsidy.getText(),
                jTextFieldPhoneAllow.getText(),
                jTextFieldClothAllow.getText()});

            JOptionPane.showMessageDialog(this, "Employee added successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add employee", "Duplicate ID", JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Create list of employee IDs from table
     * @return 
     */
    public List<String> createTableIdList() {
        DefaultTableModel model = (DefaultTableModel) jTableEmployeeList.getModel();
        List<String> tableIdList = new ArrayList<>();

        for (int i = 0; i < model.getRowCount(); i++) {
            String id = model.getValueAt(i, 0).toString();
            tableIdList.add(id);
        }
        return tableIdList;
    }
    
    /**
     * Check if employee ID is unique
     * @param tableIdList
     * @return 
     */
    public boolean isUniqueEmployeeId(List<String> tableIdList) {
        String newEmployeeId = jTextFieldEmployeeNum.getText().trim();

        for (int i = 0; i < tableIdList.size(); i++) {
            if (tableIdList.get(i).equals(newEmployeeId)) {
                JOptionPane.showMessageDialog(this, "ID number already exists");
                return false;
            }
        }

        return true;
    }
    
    /**
     * Update employee information
     */
    public void updateEmployee() {
        // Ask if user wants to proceed with updating the information of the employee
        int response = JOptionPane.showConfirmDialog(null, "Do you want to proceed with updating the entry?",
                "Update Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        // Check the user's response
        if (response == JOptionPane.YES_OPTION) {
            int selectedRowIndex = jTableEmployeeList.getSelectedRow();
            DefaultTableModel model = (DefaultTableModel) jTableEmployeeList.getModel();

            if (selectedRowIndex >= 0) {
                model.setValueAt(jTextFieldEmployeeNum.getText(), selectedRowIndex, 0);
                model.setValueAt(jTextFieldLastName.getText(), selectedRowIndex, 1);
                model.setValueAt(jTextFieldFirstName.getText(), selectedRowIndex, 2);
                model.setValueAt(formatDate(jDateChooserBirthday.getDate()), selectedRowIndex, 3);
                model.setValueAt(jTextAreaAddress.getText(), selectedRowIndex, 4);
                model.setValueAt(jTextFieldPhoneNum.getText(), selectedRowIndex, 5);
                model.setValueAt(jTextFieldSSSnum.getText(), selectedRowIndex, 6);
                model.setValueAt(jTextFieldPhilhealthNum.getText(), selectedRowIndex, 7);
                model.setValueAt(jTextFieldTINnum.getText(), selectedRowIndex, 8);
                model.setValueAt(jTextFieldPagibigNum.getText(), selectedRowIndex, 9);
                model.setValueAt(jTextFieldStatus.getText(), selectedRowIndex, 10);
                model.setValueAt(jTextFieldPosition.getText(), selectedRowIndex, 11);
                model.setValueAt(jTextFieldSupervisor.getText(), selectedRowIndex, 12);
                model.setValueAt(jTextFieldBasicSalary.getText(), selectedRowIndex, 13);
                model.setValueAt(jTextFieldRiceSubsidy.getText(), selectedRowIndex, 14);
                model.setValueAt(jTextFieldPhoneAllow.getText(), selectedRowIndex, 15);
                model.setValueAt(jTextFieldClothAllow.getText(), selectedRowIndex, 16);

                JOptionPane.showMessageDialog(this, "Employee information Updated successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Error");
            }
        }
    }
    
    /**
     * Determine last employee ID in table
     * @return 
     */
    public int determineLastEmployeeId() {
        ArrayList<Integer> list = new ArrayList<>();
        int rowCount = jTableEmployeeList.getRowCount();
        int lastNumber = 0;

        for (int i = 0; i < rowCount; i++) {
            list.add(Integer.valueOf(jTableEmployeeList.getValueAt(i, 0).toString()));
        }

        // Sorting the ArrayList in descending order
        Collections.sort(list, Collections.reverseOrder());

        lastNumber = list.get(0);

        return lastNumber;
    }
    
    /**
     * Generate unique employee ID
     * @return 
     */
    public int generateUniqueId() {
        int lastEmployeeID = determineLastEmployeeId();
        int newEmployeeId = lastEmployeeID + 1;

        return newEmployeeId;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        MotorPH = new javax.swing.JLabel();
        MainMenu = new javax.swing.JLabel();
        Dashboard = new javax.swing.JButton();
        UserManagementSecurity = new javax.swing.JButton();
        SystemConfig = new javax.swing.JButton();
        SignOut = new javax.swing.JButton();
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
        jPanel4 = new javax.swing.JPanel();
        jButtonClear = new javax.swing.JButton();
        jButtonProfileAdd = new javax.swing.JButton();
        jButtonProfileUpdate = new javax.swing.JButton();
        jButtonProfileDelete = new javax.swing.JButton();
        jButtonUpdateDBS = new javax.swing.JButton();
        jButtonViewEmployee = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        RESET = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));

        MotorPH.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        MotorPH.setText("MOTORPH ");

        MainMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        MainMenu.setForeground(new java.awt.Color(102, 102, 102));
        MainMenu.setText("ADMINISTRATION");

        Dashboard.setBackground(new java.awt.Color(153, 204, 255));
        Dashboard.setText("Dashboard");
        Dashboard.setBorder(null);
        Dashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DashboardActionPerformed(evt);
            }
        });

        UserManagementSecurity.setBackground(new java.awt.Color(51, 204, 255));
        UserManagementSecurity.setText("User Management & Security");
        UserManagementSecurity.setBorder(null);
        UserManagementSecurity.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        UserManagementSecurity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserManagementSecurityActionPerformed(evt);
            }
        });

        SystemConfig.setBackground(new java.awt.Color(153, 204, 255));
        SystemConfig.setText("System Configuration");
        SystemConfig.setBorder(null);
        SystemConfig.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SystemConfig.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SystemConfigActionPerformed(evt);
            }
        });

        SignOut.setBackground(new java.awt.Color(153, 204, 255));
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
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SystemConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(UserManagementSecurity, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MainMenu)
                    .addComponent(MotorPH)
                    .addComponent(SignOut, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
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
                .addGap(2, 2, 2)
                .addComponent(SystemConfig, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(UserManagementSecurity, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 470, Short.MAX_VALUE)
                .addComponent(SignOut, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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

        jPanel4.setBackground(new java.awt.Color(153, 204, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButtonClear.setText("CLEAR");
        jButtonClear.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonClear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 150, 22));

        jButtonProfileAdd.setText("ADD");
        jButtonProfileAdd.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonProfileAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProfileAddActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonProfileAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 150, 22));

        jButtonProfileUpdate.setText("UPDATE");
        jButtonProfileUpdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonProfileUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProfileUpdateActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonProfileUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 150, 22));

        jButtonProfileDelete.setText("DELETE");
        jButtonProfileDelete.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonProfileDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProfileDeleteActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonProfileDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 150, 22));

        jButtonUpdateDBS.setText("UPDATE DATABASE");
        jButtonUpdateDBS.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonUpdateDBS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUpdateDBSActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonUpdateDBS, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 150, 22));

        jButtonViewEmployee.setText("VIEW EMPLOYEE");
        jButtonViewEmployee.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonViewEmployee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonViewEmployeeActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonViewEmployee, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 150, 22));

        jButtonSave.setText("SAVE");
        jButtonSave.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 150, 22));

        RESET.setText("RESET ATTEMPTS");
        RESET.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RESETActionPerformed(evt);
            }
        });
        jPanel4.add(RESET, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 630, 147, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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

    private void RESETActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RESETActionPerformed
         int response = JOptionPane.showConfirmDialog(
        this,
        "Are you sure you want to reset all login attempts? This will allow all users to log in again.",
        "Confirm Reset",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE);
        
    if (response == JOptionPane.YES_OPTION) {
        try {
            // Reset the attempts file
            resetAttemptsFile();
            
            // Show success message
            JOptionPane.showMessageDialog(
                this,
                "All login attempts have been reset successfully.",
                "Reset Complete",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(IT_UserManagementSecurity.class.getName()).log(
                Level.SEVERE, "Failed to reset attempts file", ex);
                
            JOptionPane.showMessageDialog(
                this,
                "Error resetting login attempts: " + ex.getMessage(),
                "Reset Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

// Implementation of the resetAttemptsFile method
private void resetAttemptsFile() throws IOException {
    final String CSV_FILE = "login_attempts.csv";
    
    try (FileWriter writer = new FileWriter(CSV_FILE, false)) { // false = overwrite file
        // Write header line only - nothing else
        writer.write("username,attempts\n");
        System.out.println("Login attempts file reset successfully.");
    }


    }//GEN-LAST:event_RESETActionPerformed

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

    }//GEN-LAST:event_SignOutActionPerformed
    }
    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed

        // First check if all fields are filled properly
        if (!checkEntries()) {
            return;  // If any field is empty, stop the save action
        }

        // Add specific validation for phone number
        if (jTextFieldPhoneNum.isEditable()) {  // Only validate when it's an editable field (new employee)
            String phoneNumber = jTextFieldPhoneNum.getText().trim();
            if (phoneNumber.length() != 11) {
                JOptionPane.showMessageDialog(this,
                    "Phone number must be exactly 11 digits.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                jTextFieldPhoneNum.requestFocus();
                return;
            }
        }

        // Continue with the existing save logic
        ArrayList<Integer> list = new ArrayList<>();
        int rowCount = jTableEmployeeList.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            list.add(Integer.valueOf(jTableEmployeeList.getValueAt(i, 0).toString()));
        }

        int employeeNum = Integer.parseInt(jTextFieldEmployeeNum.getText());
        if (list.contains(employeeNum)) {
            updateEmployee();
        } else {
            addEmployee();
        }

        // Make the phone number field non-editable again after saving
        jTextFieldPhoneNum.setEditable(false);
        textFieldEditSetting(false);
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void jButtonViewEmployeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonViewEmployeeActionPerformed
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
    }//GEN-LAST:event_jButtonViewEmployeeActionPerformed

    private void jButtonUpdateDBSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUpdateDBSActionPerformed
        // TODO add your handling code here:
        int response = JOptionPane.showConfirmDialog(null, "Do you want to proceed with saving the changes to the database?",
            "Update Database Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {
            FileHandling.exportTableToCSV(jTableEmployeeList);
        }
    }//GEN-LAST:event_jButtonUpdateDBSActionPerformed

    private void jButtonProfileDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProfileDeleteActionPerformed

        // Get the selected row index
        int selectedRowIndex = jTableEmployeeList.getSelectedRow();

        // Check if a row is actually selected
        if (selectedRowIndex == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select an employee to delete first.",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Ask for confirmation
        int response = JOptionPane.showConfirmDialog(this,
            "Do you want to proceed with deleting the entry?",
            "Delete Entry Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);

        // If user confirms deletion
        if (response == JOptionPane.YES_OPTION) {
            try {
                // Get the table model
                DefaultTableModel model = (DefaultTableModel) jTableEmployeeList.getModel();

                // Get employee info for confirmation message
                String empNum = model.getValueAt(selectedRowIndex, 0).toString();
                String empName = model.getValueAt(selectedRowIndex, 1).toString() + ", " +
                model.getValueAt(selectedRowIndex, 2).toString();

                // Remove the row
                model.removeRow(selectedRowIndex);

                // Show success message with employee details
                JOptionPane.showMessageDialog(this,
                    "Employee " + empNum + " (" + empName + ") deleted successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

                // Clear the text fields after deletion
                clearTextField();
            } catch (HeadlessException e) {
                // Log and show error if deletion fails
                Logger.getLogger(UserManagement.class.getName()).log(Level.SEVERE,
                    "Error deleting employee", e);
                JOptionPane.showMessageDialog(this,
                    "Failed to delete employee: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }

        // Reset the text field edit settings
        textFieldEditSetting(false);
    }//GEN-LAST:event_jButtonProfileDeleteActionPerformed

    private void jButtonProfileUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProfileUpdateActionPerformed
        textFieldEditSetting(true);
    }//GEN-LAST:event_jButtonProfileUpdateActionPerformed

    private void jButtonProfileAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonProfileAddActionPerformed
        clearTextField();

        String newEmployeeId = Integer.toString(generateUniqueId());
        jTextFieldEmployeeNum.setText(newEmployeeId);

        textFieldEditSetting(true);
        jTextFieldEmployeeNum.setEditable(false);

        // Make phone number field editable and set up validation
        jTextFieldPhoneNum.setEditable(true);
        setupPhoneNumberValidation();
    }//GEN-LAST:event_jButtonProfileAddActionPerformed

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        clearTextField();
        textFieldEditSetting(false);
        // Explicitly make phone number non-editable
        jTextFieldPhoneNum.setEditable(false);
    }//GEN-LAST:event_jButtonClearActionPerformed

    private void jDateChooserBirthdayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooserBirthdayKeyTyped
        // TODO add your handling code here:
        allowOnlyDate(evt);
    }//GEN-LAST:event_jDateChooserBirthdayKeyTyped

    private void jTextFieldPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPositionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPositionActionPerformed

    private void jTextFieldFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFirstNameActionPerformed

    private void jTextFieldBasicSalaryKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBasicSalaryKeyTyped
        // TODO add your handling code here:
        allowOnlyDigits(evt);
    }//GEN-LAST:event_jTextFieldBasicSalaryKeyTyped

    private void jTextFieldBasicSalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBasicSalaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBasicSalaryActionPerformed

    private void jTextFieldClothAllowKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldClothAllowKeyTyped
        // TODO add your handling code here:
        allowOnlyDigits(evt);
    }//GEN-LAST:event_jTextFieldClothAllowKeyTyped

    private void jTextFieldClothAllowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldClothAllowActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldClothAllowActionPerformed

    private void jTextFieldPhoneAllowKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPhoneAllowKeyTyped
        // TODO add your handling code here:
        allowOnlyDigits(evt);
    }//GEN-LAST:event_jTextFieldPhoneAllowKeyTyped

    private void jTextFieldRiceSubsidyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldRiceSubsidyKeyTyped
        // TODO add your handling code here:

        allowOnlyDigits(evt);
    }//GEN-LAST:event_jTextFieldRiceSubsidyKeyTyped

    private void jTextFieldSupervisorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSupervisorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSupervisorActionPerformed

    private void jTextFieldPhilhealthNumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPhilhealthNumKeyTyped
        // TODO add your handling code here:
        allowOnlyDigitsSpecial(evt);
    }//GEN-LAST:event_jTextFieldPhilhealthNumKeyTyped

    private void jTextFieldPhilhealthNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPhilhealthNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPhilhealthNumActionPerformed

    private void jTextFieldPhoneNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPhoneNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPhoneNumActionPerformed

    private void jTextFieldTINnumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldTINnumKeyTyped
        // TODO add your handling code here:
        allowOnlyDigitsSpecial(evt);
    }//GEN-LAST:event_jTextFieldTINnumKeyTyped

    private void jTextFieldTINnumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTINnumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTINnumActionPerformed

    private void jTextFieldPagibigNumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPagibigNumKeyTyped
        // TODO add your handling code here:
        allowOnlyDigitsSpecial(evt);
    }//GEN-LAST:event_jTextFieldPagibigNumKeyTyped

    private void jTextFieldSSSnumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSSSnumKeyTyped
        // TODO add your handling code here:
        allowOnlyDigitsSpecial(evt);
    }//GEN-LAST:event_jTextFieldSSSnumKeyTyped

    private void jTextFieldSSSnumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSSSnumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSSSnumActionPerformed

    private void jTextFieldLastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLastNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLastNameActionPerformed

    private void jTextFieldEmployeeNumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEmployeeNumKeyTyped
        // TODO add your handling code here:
        allowOnlyDigits(evt);
    }//GEN-LAST:event_jTextFieldEmployeeNumKeyTyped

    private void jTextFieldEmployeeNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEmployeeNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEmployeeNumActionPerformed

    private void jTextFieldStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldStatusActionPerformed

    private void DashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DashboardActionPerformed
        // TODO add your handling code here:
        // Dashboard button
        openDashboard();
        resetButtonColors();
        Dashboard.setBackground(new Color(0, 204, 102));
        Dashboard.setForeground(Color.WHITE);
    }//GEN-LAST:event_DashboardActionPerformed

    private void UserManagementSecurityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserManagementSecurityActionPerformed
        // User Management button
        showEmployeeManagement();
        resetButtonColors();
        UserManagementSecurity.setBackground(new Color(0, 204, 102));
        UserManagementSecurity.setForeground(Color.WHITE);
    }//GEN-LAST:event_UserManagementSecurityActionPerformed

    private void SystemConfigActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SystemConfigActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SystemConfigActionPerformed

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
            java.util.logging.Logger.getLogger(IT_UserManagementSecurity.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IT_UserManagementSecurity.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IT_UserManagementSecurity.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IT_UserManagementSecurity.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

      
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new IT_UserManagementSecurity().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Dashboard;
    private javax.swing.JLabel MainMenu;
    private javax.swing.JLabel MotorPH;
    private javax.swing.JButton RESET;
    private javax.swing.JButton SignOut;
    private javax.swing.JButton SystemConfig;
    private javax.swing.JButton UserManagementSecurity;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonProfileAdd;
    private javax.swing.JButton jButtonProfileDelete;
    private javax.swing.JButton jButtonProfileUpdate;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonUpdateDBS;
    private javax.swing.JButton jButtonViewEmployee;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
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
