/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop_crud_payrollsystem;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles user authentication by validating login credentials against a CSV file
 */
public class UserAuthentication {
    private final String csvFilePath;
    private Map<String, String[]> userCredentials;
    
    /**
     * Constructor initializes the authentication system with the specified CSV file path
     * 
     * @param csvFilePath Path to the CSV file containing user credentials
     */
    public UserAuthentication(String csvFilePath) {
        this.csvFilePath = csvFilePath;
        this.userCredentials = new HashMap<>();
        loadCredentials();
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
                    // Uncomment the continue if your CSV has headers
                    // continue;
                }
                
                if (nextLine.length >= 3) {
                    String employeeNumber = nextLine[0];
                    String username = nextLine[1].toLowerCase(); // Store username in lowercase for case-insensitive comparison
                    String password = nextLine[2]; // Keep password case-sensitive
                    
                    userCredentials.put(username, new String[] {password, employeeNumber});
                }
            }
            
            Logger.getLogger(UserAuthentication.class.getName())
                  .log(Level.INFO, "Loaded {0} user credentials", userCredentials.size());
            
        } catch (IOException | CsvValidationException ex) {
            Logger.getLogger(UserAuthentication.class.getName())
                  .log(Level.SEVERE, "Error loading user credentials from " + csvFilePath, ex);
        }
    }
    
    /**
     * Authenticates a user by checking credentials
     * 
     * @param username The username to check
     * @param password The password to verify
     * @return true if authentication is successful, false otherwise
     */
    public boolean authenticate(String username, String password) {
        String[] credentials = userCredentials.get(username.toLowerCase());
        
        if (credentials != null) {
            return credentials[0].equals(password);
        }
        
        return false;
    }
    
    /**
     * Gets the employee number associated with a username
     * 
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
     * Reloads credentials from the CSV file
     * Useful when credentials are updated externally
     */
    public void refreshCredentials() {
        userCredentials.clear();
        loadCredentials();
    }
}