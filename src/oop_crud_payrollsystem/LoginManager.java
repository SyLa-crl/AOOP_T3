/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package oop_crud_payrollsystem;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sylani
 */
public final class LoginManager extends javax.swing.JFrame {
public List<EmployeeLogin> employeeDetails = new ArrayList<>();
    private static final int MAX_ATTEMPTS = 3;
    private final Map<String, Integer> userAttempts = new HashMap<>(); // https://www.callicoder.com/java-hashmap/
    private static final String CSV_FILE = "login_attempts.csv";

    /**
     * Creates new form LoginManager
     * @throws java.io.FileNotFoundException
     * @throws com.opencsv.exceptions.CsvException
     */
public LoginManager() throws IOException, FileNotFoundException, CsvException {
    initComponents();

    String csvFile = "employee_Credentials.csv";
    List<String[]> records = FileHandling.readCSV(csvFile);
    parseUserCredentials(records);
    
    // Reset the attempts file to fix corruption
    resetAttemptsFile();
    
    loadAttemptsFromCSV();
    }

    public List<EmployeeLogin> parseUserCredentials(List<String[]> records) {
    for (String[] record : records) {
        String employeeNumber = record[0];
        String username = record[1];
        String password = record[2];

        EmployeeLogin employeeLogin = new EmployeeLogin(employeeNumber, username, password);
        employeeDetails.add(employeeLogin);
    }
    return employeeDetails;
}

    // Load login attempts from CSV
    private void loadAttemptsFromCSV() {
    // First check if file exists, if not create it
    File file = new File(CSV_FILE);
    if (!file.exists()) {
        resetAttemptsFile();
        return; // No data to load yet
    }
    
    try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
        String line;
        boolean isFirstLine = true;
        
        while ((line = reader.readLine()) != null) {
            // Skip the header row
            if (isFirstLine) {
                isFirstLine = false;
                continue;
            }
            
            String[] parts = line.split(",");
            if (parts.length >= 2) {
                String username = parts[0];
                try {
                    int attempts = Integer.parseInt(parts[1]);
                    userAttempts.put(username, attempts);
                } catch (NumberFormatException e) {
                    Logger.getLogger(LoginManager.class.getName()).log(Level.WARNING,
                        "Invalid attempt count for user: " + username, e);
                }
            }
        }
    } catch (IOException e) {
        Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE,
            "Error reading login attempts file", e);
    }
}


    // Save all login attempts to CSV
    private void saveAllAttemptsToCSV() {
    try {
        // First ensure the file exists with proper header
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            resetAttemptsFile();
        }
        
        // Now write the data - first to a temporary list
        List<String[]> lines = new ArrayList<>();
        lines.add(new String[]{"username", "attempts"}); // Header
        
        // Add all user attempts
        for (Map.Entry<String, Integer> entry : userAttempts.entrySet()) {
            lines.add(new String[]{
                entry.getKey(),
                String.valueOf(entry.getValue())
            });
        }
        
        // Write to the file
        try (CSVWriter writer = new CSVWriter(new FileWriter(CSV_FILE))) {
            writer.writeAll(lines);
        }
    } catch (IOException e) {
        Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE,
            "Failed to save login attempts", e);
    }
}
    private void resetAttemptsFile() {
    try (FileWriter writer = new FileWriter(CSV_FILE, false)) { // false = overwrite file
        // Write header line only - nothing else
        writer.write("username,attempts\n");
        System.out.println("Login attempts file reset successfully.");
    } catch (IOException e) {
        Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, 
            "Failed to reset attempts file", e);
    }
}


    public boolean authenticate() throws IOException {
    String inputUsername = jTextFieldUsername2.getText().toLowerCase();
    String inputPassword = jPasswordFieldInput2.getText();
    
   
    // Original authentication logic
    for (int i = 0; i < employeeDetails.size(); i++) {
        EmployeeLogin employee_ = employeeDetails.get(i);
        String storedUsername = employee_.getUsername().toLowerCase();
        boolean usernameMatch = storedUsername.equals(inputUsername);
        boolean passwordMatch = employee_.getPassword().equals(inputPassword);
        
        if (usernameMatch && passwordMatch) {
            System.out.println("Authentication successful!");
            return true;
        }
    }
    System.out.println("Authentication failed!");
    return false;
    }
    

    // Method to check login
    public boolean logIn() throws IOException {
        boolean isAuthenticated = authenticate();
        String inputUsername = jTextFieldUsername2.getText();

        if (!isAuthenticated) {
            int attempts = userAttempts.getOrDefault(inputUsername, 0) + 1;
            userAttempts.put(inputUsername, attempts); //data to be written in the csv file

            // Save attempt to CSV
            saveAllAttemptsToCSV();

            if (attempts >= MAX_ATTEMPTS) {

                JOptionPane.showMessageDialog(null, "User " + inputUsername + " is blocked due to too many failed attempts.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {

                JOptionPane.showMessageDialog(null, "Attempt " + attempts + " of " + MAX_ATTEMPTS + ".", "Login Failed", JOptionPane.ERROR_MESSAGE);

                return false;
            }
        }

    JOptionPane.showMessageDialog(null, 
        "Welcome " + inputUsername + "!", 
        "Login Successful", JOptionPane.INFORMATION_MESSAGE);
    return true;
}

    public String matchEmployeeNumber() throws IOException {
        String inputUsername = jTextFieldUsername2.getText().toLowerCase(); // accept any case 
        String inputPassword = jPasswordFieldInput2.getText();
        String determineEmployeeNumber = "0";

        for (int i = 0; i < employeeDetails.size(); i++) {
            EmployeeLogin employee_ = employeeDetails.get(i);
            if (employee_.getUsername().toLowerCase().equals(inputUsername) && employee_.getPassword().equals(inputPassword)) {
                determineEmployeeNumber = employeeDetails.get(i).getEmployeeNumber();
                return determineEmployeeNumber;
            }
        }
        return null;
    }

    private void openDashboard() throws IOException, CsvException {
    String employeeNum = matchEmployeeNumber();
    if (employeeNum == null) {
        // Handle the case when no matching employee is found
        JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    if (employeeNum.equals("0")) {
        setVisible(false);
        new EmployeeProfile().setVisible(true); // open Admin Dashboard
    } else {
        setVisible(false);
        EmployeeProfileUser profileUser = new EmployeeProfileUser(employeeNum);
        profileUser.setVisible(true); //open Employee Dashboard
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

        jPanel2 = new javax.swing.JPanel();
        jTextFieldUsername = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPasswordFieldInput = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jCheckBoxShowPassword = new javax.swing.JCheckBox();
        jButtonExit = new javax.swing.JButton();
        jButtonLogIn = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jTextFieldUsername2 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPasswordFieldInput2 = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        jCheckBoxShowPassword2 = new javax.swing.JCheckBox();
        jButtonExit2 = new javax.swing.JButton();
        jButtonLogIn2 = new javax.swing.JButton();

        jPanel2.setBackground(new java.awt.Color(192, 168, 137, 220));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(51, 51, 255), new java.awt.Color(204, 153, 255)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextFieldUsername.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldUsernameActionPerformed(evt);
            }
        });
        jPanel2.add(jTextFieldUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 160, 22));

        jLabel1.setBackground(new java.awt.Color(192, 168, 137));
        jLabel1.setText("Username");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 24, -1, -1));

        jLabel2.setBackground(new java.awt.Color(192, 168, 137));
        jLabel2.setText("Password");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 53, -1, -1));

        jPasswordFieldInput.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jPanel2.add(jPasswordFieldInput, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 160, 22));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 14, 958, -1));

        jCheckBoxShowPassword.setBackground(new java.awt.Color(148, 129, 93));
        jCheckBoxShowPassword.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jCheckBoxShowPassword.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxShowPassword.setText("Show Password");
        jCheckBoxShowPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxShowPasswordActionPerformed(evt);
            }
        });
        jPanel2.add(jCheckBoxShowPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, -1));

        jButtonExit.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonExit.setText("EXIT");
        jButtonExit.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 223, 87), new java.awt.Color(255, 223, 87), new java.awt.Color(254, 44, 57), new java.awt.Color(254, 44, 57)));
        jButtonExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExitActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 60, 25));

        jButtonLogIn.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonLogIn.setText("LOG IN");
        jButtonLogIn.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 223, 87), new java.awt.Color(255, 223, 87), new java.awt.Color(254, 44, 57), new java.awt.Color(254, 44, 57)));
        jButtonLogIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogInActionPerformed(evt);
            }
        });
        jPanel2.add(jButtonLogIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 60, 25));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(192, 168, 137, 220));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(51, 51, 255), new java.awt.Color(204, 153, 255)));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextFieldUsername2.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldUsername2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldUsername2ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextFieldUsername2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 160, 22));

        jLabel7.setBackground(new java.awt.Color(192, 168, 137));
        jLabel7.setText("Username");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 24, -1, -1));

        jLabel8.setBackground(new java.awt.Color(192, 168, 137));
        jLabel8.setText("Password");
        jPanel4.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(17, 53, -1, -1));

        jPasswordFieldInput2.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jPanel4.add(jPasswordFieldInput2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 160, 22));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 14, 958, -1));

        jCheckBoxShowPassword2.setBackground(new java.awt.Color(148, 129, 93));
        jCheckBoxShowPassword2.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        jCheckBoxShowPassword2.setForeground(new java.awt.Color(255, 255, 255));
        jCheckBoxShowPassword2.setText("Show Password");
        jCheckBoxShowPassword2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxShowPassword2ActionPerformed(evt);
            }
        });
        jPanel4.add(jCheckBoxShowPassword2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, -1, -1));

        jButtonExit2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonExit2.setText("EXIT");
        jButtonExit2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 223, 87), new java.awt.Color(255, 223, 87), new java.awt.Color(254, 44, 57), new java.awt.Color(254, 44, 57)));
        jButtonExit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExit2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonExit2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 60, 25));

        jButtonLogIn2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonLogIn2.setText("LOG IN");
        jButtonLogIn2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 223, 87), new java.awt.Color(255, 223, 87), new java.awt.Color(254, 44, 57), new java.awt.Color(254, 44, 57)));
        jButtonLogIn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonLogIn2ActionPerformed(evt);
            }
        });
        jPanel4.add(jButtonLogIn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 60, 25));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 541, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldUsernameActionPerformed

    private void jCheckBoxShowPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxShowPasswordActionPerformed
        
    }//GEN-LAST:event_jCheckBoxShowPasswordActionPerformed

    private void jButtonExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButtonExitActionPerformed

    private void jButtonLogInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogInActionPerformed
        try {
            // TODO add your handling code here:
            logIn();
            try {
                openDashboard();
            } catch (CsvException ex) {
                Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonLogInActionPerformed

    private void jTextFieldUsername2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldUsername2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldUsername2ActionPerformed

    private void jCheckBoxShowPassword2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxShowPassword2ActionPerformed
    if (jCheckBoxShowPassword2.isSelected()) {
        jPasswordFieldInput2.setEchoChar((char) 0);
    } else {
        jPasswordFieldInput2.setEchoChar('*');
    }

    }//GEN-LAST:event_jCheckBoxShowPassword2ActionPerformed

    private void jButtonExit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExit2ActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jButtonExit2ActionPerformed

    private void jButtonLogIn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonLogIn2ActionPerformed
        try {
            // TODO add your handling code here:
            logIn();
            try {
                openDashboard();
            } catch (CsvException ex) {
                Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonLogIn2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
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
            java.util.logging.Logger.getLogger(LoginManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
 java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    try {
                        new LoginManager().setVisible(true);
                    } catch (FileNotFoundException | CsvException ex) {
                        Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(LoginManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonExit;
    private javax.swing.JButton jButtonExit2;
    private javax.swing.JButton jButtonLogIn;
    private javax.swing.JButton jButtonLogIn2;
    private javax.swing.JCheckBox jCheckBoxShowPassword;
    private javax.swing.JCheckBox jCheckBoxShowPassword2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPasswordField jPasswordFieldInput;
    private javax.swing.JPasswordField jPasswordFieldInput2;
    private javax.swing.JTextField jTextFieldUsername;
    private javax.swing.JTextField jTextFieldUsername2;
    // End of variables declaration//GEN-END:variables
}
