/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aoop_crud_payrollsystem;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles user authentication by validating login credentials against a CSV file
 */
public class UserAuthentication {
    private static final Logger LOGGER = Logger.getLogger(UserAuthentication.class.getName());
    private static final String ATTEMPTS_FILE = "login_attempts.csv";
    private static final int MAX_ATTEMPTS = 3;
    
    private final String csvFilePath;
    private Map<String, String[]> userCredentials;
    private final Map<String, Integer> loginAttempts;
    
    /**
     * Constructor initializes the authentication system with the specified CSV file path
     * @param csvFilePath Path to the CSV file containing user credentials
     */
    public UserAuthentication(String csvFilePath) {
        this.csvFilePath = csvFilePath;
        this.userCredentials = new HashMap<>();
        this.loginAttempts = new HashMap<>();
        loadCredentials();
        loadLoginAttempts();
    }
    
    /**
     * Loads credentials from the CSV file into memory
     */
    private void loadCredentials() {
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;
            // Skip header if present
            boolean firstLine = true;
            
            while ((nextLine = reader.readNext()) != null) {
                if (firstLine) {
                    firstLine = false;
                    // Skip header if your CSV has headers
                    continue;
                }
                
                if (nextLine.length >= 3) {
                    String employeeNumber = nextLine[0];
                    String username = nextLine[1].toLowerCase(); // Store username in lowercase for case-insensitive comparison
                    String password = nextLine[2]; // Keep password case-sensitive
                    
                    // Store role information if available (for enhanced authentication)
                    String role = nextLine.length >= 4 ? nextLine[3] : "user";
                    userCredentials.put(username, new String[] {password, employeeNumber, role});
                }
            }
            
            LOGGER.log(Level.INFO, "Loaded {0} user credentials", userCredentials.size());
            
        } catch (IOException | CsvValidationException ex) {
            LOGGER.log(Level.SEVERE, "Error loading user credentials from " + csvFilePath, ex);
        }
    }
    
    /**
     * Loads login attempts from CSV file
     */
    private void loadLoginAttempts() {
        File file = new File(ATTEMPTS_FILE);
        if (!file.exists()) {
            // Create initial file if it doesn't exist
            try {
                createAttemptsFile();
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, "Could not create login attempts file", ex);
            }
            return;
        }
        
        try (CSVReader reader = new CSVReader(new FileReader(ATTEMPTS_FILE))) {
            String[] nextLine;
            boolean firstLine = true;
            
            while ((nextLine = reader.readNext()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue; // Skip header
                }
                
                if (nextLine.length >= 2) {
                    String username = nextLine[0].toLowerCase();
                    int attempts = Integer.parseInt(nextLine[1]);
                    loginAttempts.put(username, attempts);
                }
            }
            
            LOGGER.log(Level.INFO, "Loaded login attempts for {0} users", loginAttempts.size());
        } catch (IOException | CsvValidationException ex) {
            LOGGER.log(Level.WARNING, "Error loading login attempts", ex);
        }
    }
    
    /**
     * Creates initial attempts file
     */
    private void createAttemptsFile() throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(ATTEMPTS_FILE))) {
            writer.writeNext(new String[]{"username", "attempts"});
        }
    }
    
    /**
     * Saves current login attempts
     */
    private void saveLoginAttempts() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(ATTEMPTS_FILE))) {
            writer.writeNext(new String[]{"username", "attempts"});
            
            for (Map.Entry<String, Integer> entry : loginAttempts.entrySet()) {
                writer.writeNext(new String[]{
                    entry.getKey(),
                    String.valueOf(entry.getValue())
                });
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Error saving login attempts", ex);
        }
    }
    
    /**
     * Get attempts for a username
     * @param username
     * @return 
     */
    public int getLoginAttempts(String username) {
        username = username.toLowerCase();
        return loginAttempts.getOrDefault(username, 0);
    }
    
    /**
     * Increment attempts for a username
     * @param username
     */
    public void incrementLoginAttempts(String username) {
        username = username.toLowerCase();
        int attempts = loginAttempts.getOrDefault(username, 0) + 1;
        loginAttempts.put(username, attempts);
        saveLoginAttempts();
    }
    
    /**
     * Reset attempts for a username
     * @param username
     */
    public void resetLoginAttempts(String username) {
        username = username.toLowerCase();
        loginAttempts.put(username, 0);
        saveLoginAttempts();
    }
    
    /**
     * Check if account is locked
     * @param username
     * @return 
     */
    public boolean isAccountLocked(String username) {
        username = username.toLowerCase();
        return loginAttempts.getOrDefault(username, 0) >= MAX_ATTEMPTS;
    }
    
    /**
     * Reset all login attempts
     */
    public void resetAllLoginAttempts() {
        loginAttempts.clear();
        saveLoginAttempts();
        LOGGER.log(Level.INFO, "All login attempts have been reset");
    }
    
    /**
     * Authenticates a user by checking credentials
     * Enhanced to track login attempts
     * @param username The username to check
     * @param password The password to verify
     * @return true if authentication is successful, false otherwise
     */
    public boolean authenticate(String username, String password) {
        username = username.toLowerCase();
        
        // Check if account is locked
        if (isAccountLocked(username)) {
            LOGGER.log(Level.WARNING, "Attempted login to locked account: {0}", username);
            return false;
        }
        
        String[] credentials = userCredentials.get(username);
        
        if (credentials != null) {
            boolean authenticated = credentials[0].equals(password);
            
            if (authenticated) {
                // Reset attempts on successful login
                resetLoginAttempts(username);
                return true;
            } else {
                // Increment attempts on failed login
                incrementLoginAttempts(username);
                return false;
            }
        }
        
        // Username not found
        incrementLoginAttempts(username);
        return false;
    }
    
    /**
     * Gets the employee number associated with a username
     * Maintains compatibility with original method
     * @param username The username to look up
     * @param password The password to verify
     * @return The employee number if credentials are valid, null otherwise
     */
    public String getEmployeeNumber(String username, String password) {
        String[] credentials = userCredentials.get(username.toLowerCase());
        
        if (credentials != null && credentials[0].equals(password)) {
            return credentials[1]; // Return employee number
        }
        
        return null;
    }
    
    /**
     * Gets the user role associated with a username
     * New method for role-based access control
     * @param username The username to look up
     * @return The role if found, null otherwise
     */
    public String getUserRole(String username) {
        String[] credentials = userCredentials.get(username.toLowerCase());
        
        if (credentials != null && credentials.length >= 3) {
            return credentials[2]; // Return role
        }
        
        return "user"; // Default role
    }
    
    /**
     * Reloads credentials from the CSV file
     * Maintains compatibility with original method
     */
    public void refreshCredentials() {
        userCredentials.clear();
        loadCredentials();
    }
    
    /**
     * Authentication result class for detailed authentication results
     */
    public static class AuthResult {
        private final boolean success;
        private final String employeeNumber;
        private final String userRole;
        private final String message;
        private final boolean accountLocked;
        
        public AuthResult(boolean success, String employeeNumber, String userRole, String message, boolean accountLocked) {
            this.success = success;
            this.employeeNumber = employeeNumber;
            this.userRole = userRole;
            this.message = message;
            this.accountLocked = accountLocked;
        }
        
        public boolean isSuccess() { return success; }
        public String getEmployeeNumber() { return employeeNumber; }
        public String getUserRole() { return userRole; }
        public String getMessage() { return message; }
        public boolean isAccountLocked() { return accountLocked; }
    }
    
    /**
     * Full authentication with detailed result
     * New method for enhanced authentication
     * @param username
     * @param password
     * @return 
     */
    public AuthResult authenticateWithDetails(String username, String password) {
        username = username.toLowerCase();
        
        // Check if account is locked
        if (isAccountLocked(username)) {
            return new AuthResult(
                false, 
                null,
                null,
                "Account is locked due to too many failed attempts. Contact administrator.", 
                true
            );
        }
        
        String[] credentials = userCredentials.get(username);
        
        if (credentials == null) {
            // Username not found
            incrementLoginAttempts(username);
            int attemptsLeft = MAX_ATTEMPTS - getLoginAttempts(username);
            
            return new AuthResult(
                false,
                null,
                null,
                "Invalid username or password. " + attemptsLeft + " attempt(s) remaining.",
                false
            );
        }
        
        boolean authenticated = credentials[0].equals(password);
        
        if (authenticated) {
            // Reset attempts on successful login
            resetLoginAttempts(username);
            
            String role = credentials.length >= 3 ? credentials[2] : "user";
            
            return new AuthResult(
                true,
                credentials[1], // Employee number
                role,
                "Authentication successful",
                false
            );
        } else {
            // Increment attempts on failed login
            incrementLoginAttempts(username);
            int attemptsLeft = MAX_ATTEMPTS - getLoginAttempts(username);
            
            if (attemptsLeft <= 0) {
                return new AuthResult(
                    false,
                    null,
                    null,
                    "Account is now locked due to too many failed attempts. Contact administrator.",
                    true
                );
            } else {
                return new AuthResult(
                    false,
                    null,
                    null,
                    "Invalid username or password. " + attemptsLeft + " attempt(s) remaining.",
                    false
                );
            }
        }
    }
    
    /**
     * Generate a secure password hash (for future implementation)
     * @param password
     * @param salt
     * @return 
     */
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(bytes);
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.log(Level.SEVERE, "Error hashing password", ex);
            return "";
        }
    }
    
    /**
     * Generate a random salt (for future implementation)
     * @return 
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}