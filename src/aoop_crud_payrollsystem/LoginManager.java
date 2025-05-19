/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aoop_crud_payrollsystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
/**
 *
 * @author Sylani
 */
public class LoginManager extends javax.swing.JFrame {
    
    // File paths for credentials and login attempts
    private static final String CREDENTIALS_FILE = "employee_Credentials.csv";
    private static final String LOGIN_ATTEMPTS_FILE = "login_attempts.csv";
    private static final String USER_TYPES_FILE = "user_types.csv";
    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private static final Logger LOGGER = Logger.getLogger(LoginManager.class.getName());
    
    /**
     * Creates new form LoginManager
     */
    public LoginManager() {
       initComponents();
    fixFormSize();
    
    // Delete existing user_types.csv to ensure a fresh start
    File userTypesFile = new File(USER_TYPES_FILE);
    if (userTypesFile.exists()) {
        userTypesFile.delete();
    }
    
    // Initialize required files
    initializeRequiredFiles();
    
    // Fix input fields
    fixInputFields();
    
    // Set up window and panel centering
    setTitle("MotorPH Payroll System");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setResizable(false);
    
    // Add a component listener to handle resizing events
    addComponentListener(new java.awt.event.ComponentAdapter() {
        @Override
        public void componentResized(java.awt.event.ComponentEvent e) {
            centerLoginPanel();
        }
        
        @Override
        public void componentShown(java.awt.event.ComponentEvent e) {
            centerLoginPanel();
        }
    });
    
    // Center on screen
    setLocationRelativeTo(null);
    
    // Initial centering of the panel
    SwingUtilities.invokeLater(this::centerLoginPanel);
}
 
/**
 * Centers the login panel in the window
 * This method will be called when the window is resized or shown
 */
private void centerLoginPanel() {
    // Get content pane and panel dimensions
    int contentWidth = getContentPane().getWidth();
    int contentHeight = getContentPane().getHeight();
    int panelWidth = jPanel1.getWidth();
    int panelHeight = jPanel1.getHeight();
    
    // Calculate center position
    int x = (contentWidth - panelWidth) / 2;
    int y = (contentHeight - panelHeight) / 2;
    
    // Set panel position
    jPanel1.setBounds(x, y, panelWidth, panelHeight);
    
    // Log the centering operation
    LOGGER.info("Centered login panel: x=" + x + ", y=" + y + 
                ", width=" + panelWidth + ", height=" + panelHeight);
    
    // Make sure the panel is visible
    jPanel1.setVisible(true);
    
    // Ensure content pane has null layout for absolute positioning
    if (getContentPane().getLayout() != null) {
        getContentPane().setLayout(null);
    }
    
    // Revalidate and repaint
    jPanel1.revalidate();
    jPanel1.repaint();
}
 /*  
 * Fix input fields in the login form with better error handling
 */
private void fixInputFields() {
    try {
        // 1. Get the login panel
        JPanel loginPanel = jPanel1;
        
        // 2. Remove the existing username and password fields
        loginPanel.remove(jTextField1UserName);
        loginPanel.remove(jPassWordField1);
        
        // 3. Create new text field components
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        
        // 4. Copy the bounds from the original components
        usernameField.setBounds(jTextField1UserName.getBounds());
        passwordField.setBounds(jPassWordField1.getBounds());
        
        // 5. Set placeholder text for better user guidance
        usernameField.setToolTipText("Enter your username");
        passwordField.setToolTipText("Enter your password");
        
        // 6. Add the new components to the panel
        loginPanel.add(usernameField);
        loginPanel.add(passwordField);
        
        // 7. Store references in the original variables
        jTextField1UserName = usernameField;
        jPassWordField1 = passwordField;
        
        // 8. Add proper keyboard navigation and focus traversal
        usernameField.addActionListener(e -> passwordField.requestFocus());
        passwordField.addActionListener(e -> processLogin());
        
        // 9. Fix the sign in button action
        if (jButton1SignIn.getActionListeners().length > 0) {
            jButton1SignIn.removeActionListener(jButton1SignIn.getActionListeners()[0]);
        }
        jButton1SignIn.addActionListener(e -> processLogin());
        
        // 10. Fix the exit button action
        if (jButton2Exit.getActionListeners().length > 0) {
            jButton2Exit.removeActionListener(jButton2Exit.getActionListeners()[0]);
        }
        jButton2Exit.addActionListener(e -> System.exit(0));
        
        // 11. Fix the show password checkbox
        if (jCheckBoxShowPass.getActionListeners().length > 0) {
            jCheckBoxShowPass.removeActionListener(jCheckBoxShowPass.getActionListeners()[0]);
        }
        jCheckBoxShowPass.addActionListener(e -> {
            if (jCheckBoxShowPass.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        });
        
        // 12. Apply focus traversal keys for better keyboard navigation
        // Set focus traversal keys (Tab, Shift+Tab) to move between fields
        java.util.Set<java.awt.AWTKeyStroke> forwardKeys = new java.util.HashSet<>();
        forwardKeys.add(java.awt.AWTKeyStroke.getAWTKeyStroke(java.awt.event.KeyEvent.VK_TAB, 0));
        usernameField.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);
        passwordField.setFocusTraversalKeys(java.awt.KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);
        
        java.util.Set<java.awt.AWTKeyStroke> backwardKeys = new java.util.HashSet<>();
        backwardKeys.add(java.awt.AWTKeyStroke.getAWTKeyStroke(java.awt.event.KeyEvent.VK_TAB, java.awt.event.InputEvent.SHIFT_DOWN_MASK));
        usernameField.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);
        passwordField.setFocusTraversalKeys(java.awt.KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);
        
        // 13. Update the UI
        loginPanel.revalidate();
        loginPanel.repaint();
        
        // 14. Set focus to username field
        SwingUtilities.invokeLater(() -> usernameField.requestFocus());
        
        LOGGER.info("Input fields fixed successfully");
        
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Error fixing input fields", e);
        JOptionPane.showMessageDialog(null, 
            "Error fixing input fields: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
}
//FIX Frames
   private void fixFormSize() {
    // Set specific size for the entire form
    // Use WindowSizeUtil to set the standard frame size
    WindowSizeUtils.setStandardSize(this);
    
    // Center on screen
    setLocationRelativeTo(null);
    
    // Prevent resizing
    setResizable(false);
    
    // Set title
    setTitle("MotorPH Payroll System - Login");
    
    // Set background color for the content pane
    getContentPane().setBackground(new java.awt.Color(204, 255, 204));
    
    // Use null layout manager for absolute positioning
    getContentPane().setLayout(null);
    
    // Set the login panel size but don't position it yet (will be centered later)
    jPanel1.setSize(400, 350);
    jPanel1.setPreferredSize(new java.awt.Dimension(400, 350));
    jPanel1.setLayout(null); // Use absolute positioning
    
    // Set fixed positions for components within the panel
    // Labels
    jLabel10.setBounds(80, 20, 240, 25); // MOTORPH PAYROLL SYSTEM
    jLabel11.setBounds(120, 50, 160, 20); // Sign-in to access...
    jLabel12.setBounds(50, 90, 100, 25); // Username
    jLabel13.setBounds(50, 150, 100, 25); // Password
    jLabel14.setBounds(50, 310, 300, 20); // Forgot your password...
    
    // Input fields and buttons
    jTextField1UserName.setBounds(50, 115, 300, 30); // Username field
    jPassWordField1.setBounds(50, 175, 300, 30); // Password field
    jCheckBoxShowPass.setBounds(220, 210, 130, 20); // Show password checkbox
    jButton1SignIn.setBounds(50, 240, 300, 30); // Sign In button
    jButton2Exit.setBounds(50, 275, 300, 30); // EXIT button
    
    // Apply border to the panel
    jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(
        new java.awt.Color(0, 153, 0), 1));
    
    // Revalidate and repaint to apply changes
    revalidate();
    repaint();
}
   /**
 * Initialize all required CSV files for the login system
 */
private void initializeRequiredFiles() {
    // Check if employee_Credentials.csv exists
    File credentialsFile = new File(CREDENTIALS_FILE);
    
    if (!credentialsFile.exists()) {
        LOGGER.warning("Credentials file not found: " + CREDENTIALS_FILE);
        JOptionPane.showMessageDialog(this,
            "Credentials file not found. Creating empty credentials file.",
            "Missing File",
            JOptionPane.WARNING_MESSAGE);
        
        try (FileWriter writer = new FileWriter(credentialsFile)) {
            writer.write("username,password,employeeNumber\n");
            // Add default admin account
            writer.write("admin,admin,0\n");
            LOGGER.info("Created empty credentials file with default admin account");
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error creating credentials file", e);
        }
    } else {
        LOGGER.info("Using existing credentials file: " + CREDENTIALS_FILE);
    }
    
    // Create login attempts file if it doesn't exist
    File attemptsFile = new File(LOGIN_ATTEMPTS_FILE);
    
    if (!attemptsFile.exists()) {
        try (FileWriter writer = new FileWriter(attemptsFile)) {
            writer.write("username,attempts\n");
            LOGGER.info("Created login attempts file: " + LOGIN_ATTEMPTS_FILE);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error creating login attempts file", e);
        }
    }
    
    // Create or recreate user types file
    File userTypesFile = new File(USER_TYPES_FILE);
    
    // Delete if exists to ensure fresh data
    if (userTypesFile.exists()) {
        userTypesFile.delete();
    }
    
    try (FileWriter writer = new FileWriter(userTypesFile)) {
        writer.write("employeeNumber,userType\n");
        
        // IT Admin users
        writer.write("0,admin\n");
        writer.write("35,admin\n");
        writer.write("36,admin\n");
        
        // Finance users
        writer.write("37,finance\n");
        writer.write("38,finance\n");
        writer.write("39,finance\n");
        writer.write("40,finance\n");
        
        // HR users
        writer.write("41,hr\n");
        writer.write("42,hr\n");
        
        // Employee users (1-34)
        for (int i = 1; i <= 34; i++) {
            writer.write(i + ",employee\n");
        }
        
        // Default type for any other employee number
        writer.write("default,employee\n");
        
        LOGGER.info("Created user types file: " + USER_TYPES_FILE);
    } catch (IOException e) {
        LOGGER.log(Level.SEVERE, "Error creating user types file", e);
    }
}

 /**
 * Unified method to determine user role based on employee number
 * 
 * @param employeeNumber The employee number to check
 * @return The corresponding user role (admin, finance, hr, or employee)
 */
private String getUserRole(String employeeNumber) {
    // Validate input
    if (employeeNumber == null || employeeNumber.trim().isEmpty()) {
        throw new IllegalArgumentException("Employee number cannot be null or empty");
    }
    
    // Trim the employee number to remove any whitespace
    String empNum = employeeNumber.trim();
    
    // Display debug info
    System.out.println("Determining role for employee number: " + empNum);
    
    try {
        int employeeNum = Integer.parseInt(empNum);
        
        // Admin users - employee numbers 0, 35, 36
        if (employeeNum == 0 || employeeNum == 35 || employeeNum == 36) {
            return "admin";
        }
        
        // Finance users - employee numbers 37-40
        else if (employeeNum >= 37 && employeeNum <= 40) {
            return "finance";
        }
        
        // HR users - employee numbers 41-42
        else if (employeeNum == 41 || employeeNum == 42) {
            return "hr";
        }
        
        // Employee users - employee numbers 1-34
        else if (employeeNum >= 1 && employeeNum <= 34) {
            return "employee";
        }
        
        // Default to employee role for unknown employee numbers
        else {
            LOGGER.warning("No specific role defined for employee number: " + empNum + ". Defaulting to 'employee'");
            return "employee";
        }
    }
    catch (NumberFormatException e) {
        LOGGER.warning("Invalid employee number format: " + empNum + ". Defaulting to 'employee'");
        return "employee";
    }
}
   /**
 * Process the login request with improved error handling and logging
 */
private void processLogin() {
    // Get input from form fields and trim whitespace
    String username = jTextField1UserName.getText().trim();
    String password = new String(jPassWordField1.getPassword());
    
    // Add more detailed logging
    LOGGER.info("Login attempt with username: " + username);
    
    // Validate input
    if (username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this,
            "Username and password are required",
            "Login Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    try {
        // Get user authentication
        UserAuthentication auth = new UserAuthentication(CREDENTIALS_FILE);
        
        // Check if account is locked
        if (auth.isAccountLocked(username)) {
            JOptionPane.showMessageDialog(this, 
                "Account is locked due to too many failed login attempts.\n" +
                "Please contact an administrator.",
                "Account Locked",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Authenticate with basic method
        boolean authenticated = auth.authenticate(username, password);
        
        if (authenticated) {
            // Successful login
            LOGGER.info("Authentication successful for: " + username);
            
            // Get employee number
            String employeeNumber = auth.getEmployeeNumber(username, password);
            LOGGER.info("Retrieved employee number: " + employeeNumber);
            
            if (employeeNumber == null || employeeNumber.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Account authenticated but no employee number found.\n" +
                    "Please contact your system administrator.",
                    "User Configuration Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            try {
                // Get role based on employee number - using the unified method
                String userRole = getUserRole(employeeNumber);
                LOGGER.info("Assigned role: " + userRole + " for employee #" + employeeNumber);
                
                // Create employee info array
                String[] employeeInfo = {
                    employeeNumber,
                    userRole,
                    username
                };
                
                // Navigate based on determined role
                navigateByUserRole(userRole, employeeInfo);
            }
            catch (Exception e) {
                // If there was any error in role determination or navigation
                LOGGER.log(Level.SEVERE, "Error assigning role or navigating", e);
                JOptionPane.showMessageDialog(this,
                    "Error in role assignment or dashboard navigation.\n" + 
                    e.getMessage() + "\n" +
                    "Please contact your system administrator.",
                    "Configuration Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Failed login - with improved logging
            LOGGER.warning("Authentication failed for: " + username);
            auth.incrementLoginAttempts(username);
            int remainingAttempts = MAX_LOGIN_ATTEMPTS - auth.getLoginAttempts(username);
            
            if (remainingAttempts <= 0) {
                LOGGER.warning("Account locked for user: " + username);
                JOptionPane.showMessageDialog(this, 
                    "Account is now locked due to too many failed login attempts.\n" +
                    "Please contact an administrator.",
                    "Account Locked",
                    JOptionPane.ERROR_MESSAGE);
            } else {
                LOGGER.info("Login failed - Remaining attempts: " + remainingAttempts);
                JOptionPane.showMessageDialog(this,
                    "Invalid username or password.\n" +
                    "Remaining attempts: " + remainingAttempts,
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (Exception e) {
        // Handle any other exceptions
        LOGGER.log(Level.SEVERE, "Error in login process", e);
        JOptionPane.showMessageDialog(this,
            "An error occurred during login: " + e.getMessage() + "\n" +
            "Please contact your system administrator.",
            "System Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

    /**
     * Get user type for an employee number from the user_types.csv file
     * 
     * @param employeeNumber The employee number to look up
     * @return The user type (admin, finance, hr, or employee)
     */
    private String getUserTypeForEmployee(String employeeNumber) {
 // Display debug info
    System.out.println("Determining role for employee number: " + employeeNumber);
    
    // Convert to integer for easier comparison
    try {
        int empNum = Integer.parseInt(employeeNumber);
        
        // Admin users - employee numbers 0, 35, 36
        if (empNum == 0 || empNum == 35 || empNum == 36) {
            System.out.println("Assigned role: admin");
            return "admin";
        }
        
        // Finance users - employee numbers 37-40
        else if (empNum >= 37 && empNum <= 40) {
            System.out.println("Assigned role: finance");
            return "finance";
        }
        
        // HR users - employee numbers 41-42
        else if (empNum == 41 || empNum == 42) {
            System.out.println("Assigned role: hr");
            return "hr";
        }
        
        // Employee users - employee numbers 1-34
        else if (empNum >= 1 && empNum <= 34) {
            System.out.println("Assigned role: employee");
            return "employee";
        }
        
        // If no match was found, throw an exception
        else {
            throw new IllegalArgumentException("No role defined for employee number: " + employeeNumber);
        }
    }
    catch (NumberFormatException e) {
        // If the employee number isn't an integer, throw an exception
        throw new IllegalArgumentException("Invalid employee number format: " + employeeNumber);
    }
}
    /**
 * Check if credentials file has proper format and data
 * 
 * @return true if credentials file is valid, false otherwise
 */
private boolean validateCredentialsFile() {
    File file = new File(CREDENTIALS_FILE);
    
    if (!file.exists() || file.length() == 0) {
        LOGGER.warning("Credentials file is missing or empty");
        return false;
    }
    
    try (java.util.Scanner scanner = new java.util.Scanner(file)) {
        // Check if the file has a header line
        if (!scanner.hasNextLine()) {
            LOGGER.warning("Credentials file is empty");
            return false;
        }
        
        String header = scanner.nextLine();
        if (!header.contains("username") || !header.contains("password") || !header.contains("employeeNumber")) {
            LOGGER.warning("Credentials file has invalid header: " + header);
            return false;
        }
        
        // Check if the file has at least one user
        if (!scanner.hasNextLine()) {
            LOGGER.warning("Credentials file has no user entries");
            return false;
        }
        
        // File seems valid
        return true;
        
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Error validating credentials file", e);
        return false;
    }
}

    /**
     * Initialize user types file with default mappings if it doesn't exist
     */
    private void initializeUserTypesFile() {

    File file = new File(USER_TYPES_FILE);
    
    try {
        // Create parent directories if they don't exist
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        
        // Create file
        boolean fileCreated = file.createNewFile();
        if (fileCreated) {
            LOGGER.info("Created user types file: " + USER_TYPES_FILE);
            
            // Write content with custom employee number to role mappings
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("employeeNumber,userType\n");
                
                // IT Admin users
                writer.write("0,admin\n");
                writer.write("35,admin\n");
                writer.write("36,admin\n");
                
                // Finance users
                writer.write("37,finance\n");
                writer.write("38,finance\n");
                writer.write("39,finance\n");
                writer.write("40,finance\n");
                
                // HR users
                writer.write("41,hr\n");
                writer.write("42,hr\n");
                
                // Employee users (1-34)
                for (int i = 1; i <= 34; i++) {
                    writer.write(i + ",employee\n");
                }
                
                // Default type for any other employee number
                writer.write("default,employee\n");
            }
            
            LOGGER.info("Successfully initialized user types with custom mappings");
        } else {
            LOGGER.warning("Failed to create user types file: " + USER_TYPES_FILE);
        }
    } catch (IOException e) {
        LOGGER.log(Level.SEVERE, "Error creating user types file: " + USER_TYPES_FILE, e);
    }
}

    /**
     * Navigate to the appropriate dashboard based on user role
     */
    private void navigateByUserRole(String userRole, String[] employeeInfo) {
     try {
        System.out.println("Navigating to " + userRole + " dashboard for employee #" + employeeInfo[0]);
        
        if ("admin".equals(userRole.toLowerCase())) {
            openIT_Admin_Dashboard(employeeInfo);
        }
        else if ("finance".equals(userRole.toLowerCase())) {
            openFinance_Dashboard(employeeInfo);
        }
        else if ("hr".equals(userRole.toLowerCase())) {
            openHR_Dashboard(employeeInfo);
        }
        else if ("employee".equals(userRole.toLowerCase())) {
            openEmployee_Dashboard(employeeInfo);
        }
        else {
            // If we get here, there's a role with no matching dashboard
            throw new IllegalArgumentException("No dashboard defined for role: " + userRole);
        }
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Error opening dashboard", e);
        JOptionPane.showMessageDialog(this,
            "Error opening dashboard: " + e.getMessage() + "\n" +
            "Please contact system administrator.",
            "Navigation Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
 /**
 * Open IT Admin dashboard
 */
private void openIT_Admin_Dashboard(String[] employeeInfo) {
     try {
        // Check if employee info is valid
        if (employeeInfo == null || employeeInfo.length < 3) {
            throw new IllegalArgumentException("Invalid employee information");
        }
        
        // Log the action
        LOGGER.log(Level.INFO, "Opening IT Admin dashboard for employee #{0}", employeeInfo[0]);
        
        // Create the dashboard with employee info
        IT_Admin_Dashboard dashboard = new IT_Admin_Dashboard(employeeInfo);
        
        // Make dashboard visible and close login window
        dashboard.setVisible(true);
        this.dispose();
        
    } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "Error opening IT Admin dashboard", e);
        JOptionPane.showMessageDialog(this,
            "Error opening IT Admin dashboard: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
/**
 * Open HR dashboard with improved error handling for Java version issues
 */
private void openHR_Dashboard(String[] employeeInfo) {
    try {
        // Check if employee info is valid
        if (employeeInfo == null || employeeInfo.length < 3) {
            throw new IllegalArgumentException("Invalid employee information");
        }
        
        // Log the action
        System.out.println("Opening HR dashboard for employee #" + employeeInfo[0]);
        
        // Create the dashboard with try-catch for class loading issues
        try {
            HR_Dashboard dashboard = new HR_Dashboard(employeeInfo);
            
            // Center the dashboard
            WindowSizeUtils.setStandardSize(dashboard);
            
            
            // Make dashboard visible and close login window
            dashboard.setVisible(true);
            this.dispose();
            
        } catch (UnsupportedClassVersionError e) {
            // Special handling for Java version incompatibility
            System.err.println("Java version compatibility issue: " + e.getMessage());
            
            JOptionPane.showMessageDialog(this,
                "The HR Dashboard was compiled with a different Java version.\n\n" +
                "Error details: " + e.getMessage() + "\n\n" +
                "Please contact your system administrator to recompile the application with a compatible Java version.",
                "Java Version Error",
                JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (Exception e) {
        System.err.println("Error opening HR dashboard: " + e.getMessage());
        JOptionPane.showMessageDialog(this,
            "Error opening HR dashboard: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

/**
 * Open Finance dashboard with improved error handling
 */
private void openFinance_Dashboard(String[] employeeInfo) {
    try {
        // Check if employee info is valid
        if (employeeInfo == null || employeeInfo.length < 3) {
            throw new IllegalArgumentException("Invalid employee information");
        }
        
        // Log the action
        System.out.println("Opening Finance dashboard for employee #" + employeeInfo[0]);
        
        // Create the dashboard with try-catch for class loading issues
        try {
            Finance_Dashboard dashboard = new Finance_Dashboard(employeeInfo);
            
            // Center the dashboard
            WindowSizeUtils.setStandardSize(dashboard);
      
            
            // Make dashboard visible and close login window
            dashboard.setVisible(true);
            this.dispose();
            
        } catch (UnsupportedClassVersionError e) {
            // Special handling for Java version incompatibility
            System.err.println("Java version compatibility issue: " + e.getMessage());
            
            JOptionPane.showMessageDialog(this,
                "The Finance Dashboard was compiled with a different Java version.\n\n" +
                "Error details: " + e.getMessage() + "\n\n" +
                "Please contact your system administrator to recompile the application with a compatible Java version.",
                "Java Version Error",
                JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (Exception e) {
        System.err.println("Error opening Finance dashboard: " + e.getMessage());
        JOptionPane.showMessageDialog(this,
            "Error opening Finance dashboard: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

/**
 * Open Employee dashboard with improved error handling
 */
private void openEmployee_Dashboard(String[] employeeInfo) {
    try {
        // Check if employee info is valid
        if (employeeInfo == null || employeeInfo[0] == null) {
            throw new IllegalArgumentException("Invalid employee information");
        }
        
        // Log the action
        System.out.println("Opening Employee dashboard for employee #" + employeeInfo[0]);
        
        // Create the dashboard with try-catch for class loading issues
        try {
            Employee_Dashboard dashboard = new Employee_Dashboard(employeeInfo);
            
            // Center the dashboard
            WindowSizeUtils.setStandardSize(dashboard);
  
            
            // Make dashboard visible and close login window
            dashboard.setVisible(true);
            this.dispose();
            
        } catch (UnsupportedClassVersionError e) {
            // Special handling for Java version incompatibility
            System.err.println("Java version compatibility issue: " + e.getMessage());
            
            JOptionPane.showMessageDialog(this,
                "The Employee Dashboard was compiled with a different Java version.\n\n" +
                "Error details: " + e.getMessage() + "\n\n" +
                "Please contact your system administrator to recompile the application with a compatible Java version.",
                "Java Version Error",
                JOptionPane.ERROR_MESSAGE);
        }
        
    } catch (Exception e) {
        System.err.println("Error opening Employee dashboard: " + e.getMessage());
        JOptionPane.showMessageDialog(this,
            "Error opening Employee dashboard: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
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
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jTextField1UserName = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jPassWordField1 = new javax.swing.JPasswordField();
        jCheckBoxShowPass = new javax.swing.JCheckBox();
        jButton1SignIn = new javax.swing.JButton();
        jButton2Exit = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 255, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel10.setFont(new java.awt.Font("Berlin Sans FB Demi", 0, 14)); // NOI18N
        jLabel10.setText("MOTORPH PAYROLL SYSTEM");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel11.setText("Sign-in to access your dashboard");

        jLabel12.setBackground(new java.awt.Color(192, 168, 137));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel12.setText("Username");

        jLabel13.setBackground(new java.awt.Color(192, 168, 137));
        jLabel13.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel13.setText("Password");

        jCheckBoxShowPass.setBackground(new java.awt.Color(153, 255, 153));
        jCheckBoxShowPass.setText("Show Password");
        jCheckBoxShowPass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxShowPassActionPerformed(evt);
            }
        });

        jButton1SignIn.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jButton1SignIn.setText("Sign In");

        jButton2Exit.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jButton2Exit.setText("EXIT");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        jLabel14.setText("Forgot your password? Contact system Administrator");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12)
                    .addComponent(jTextField1UserName, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1SignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jCheckBoxShowPass, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPassWordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 13, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(85, 85, 85))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(68, 68, 68))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(32, 32, 32)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1UserName, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPassWordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBoxShowPass)
                .addGap(29, 29, 29)
                .addComponent(jButton1SignIn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(424, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(449, 449, 449))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(140, 140, 140)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(168, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBoxShowPassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxShowPassActionPerformed
             // Show/hide password checkbox action
        if (jCheckBoxShowPass.isSelected()) {
            jPassWordField1.setEchoChar((char) 0);
        } else {
            jPassWordField1.setEchoChar('*');
        }
    }//GEN-LAST:event_jCheckBoxShowPassActionPerformed

    /**
     * @param args the command line arguments
     */
/**
 * Main method to start the application with improved error handling
     * @param args
 */

public static void main(String args[]) {
    // Set up logging with a file handler
    try {
        // Create logs directory if it doesn't exist
        File logsDir = new File("logs");
        if (!logsDir.exists()) {
            logsDir.mkdir();
        }
        
        // Set up file handler for logging
        java.util.logging.FileHandler fileHandler = new java.util.logging.FileHandler(
                "logs/payroll_system_%g.log", 
                5 * 1024 * 1024,  // 5MB
                5,                // 5 files max
                true);            // Append mode
        
        fileHandler.setFormatter(new java.util.logging.SimpleFormatter());
        LOGGER.addHandler(fileHandler);
        
        // Also log to console during development
        java.util.logging.ConsoleHandler consoleHandler = new java.util.logging.ConsoleHandler();
        consoleHandler.setLevel(Level.ALL);
        LOGGER.addHandler(consoleHandler);
        
        // Set the logger level
        LOGGER.setLevel(Level.INFO);
    } catch (Exception e) {
        System.err.println("Failed to initialize logging: " + e.getMessage());
    }
    
    /* Set the Nimbus look and feel */
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        LOGGER.log(Level.SEVERE, "Error setting look and feel", ex);
        // Fall back to system look and feel
        try {
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Failed to set system look and feel", e);
        }
    }

    /* Create and display the form */
    SwingUtilities.invokeLater(() -> {
        try {
            LOGGER.info("Starting MotorPH Payroll System");
            LoginManager loginManager = new LoginManager();
            
            // Add window listener to handle window events
            loginManager.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowOpened(java.awt.event.WindowEvent e) {
                    // Center login panel when window is first shown
                    loginManager.centerLoginPanel();
                }
            });
            
            loginManager.setVisible(true);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error starting application", e);
            JOptionPane.showMessageDialog(null, 
                "Error starting application: " + e.getMessage(), 
                "Startup Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1SignIn;
    private javax.swing.JButton jButton2Exit;
    private javax.swing.JCheckBox jCheckBoxShowPass;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPassWordField1;
    private javax.swing.JTextField jTextField1UserName;
    // End of variables declaration//GEN-END:variables
}
