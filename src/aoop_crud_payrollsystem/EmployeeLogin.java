/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aoop_crud_payrollsystem;

/**
 *
 * @author Sylani
 */
public class EmployeeLogin {
  // Attributes
    private String employeeNumber;
    private String username;
    private String password;

    /**
     * Constructor for EmployeeLogin
     * @param employeeNumber The employee's ID number
     * @param username The employee's username
     * @param password The employee's password
     */
    public EmployeeLogin(String employeeNumber, String username, String password) {
        this.employeeNumber = employeeNumber;
        this.username = username;
        this.password = password;
    }

    // Getter and Setter methods
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "EmployeeLogin{" +
                "employeeNumber='" + employeeNumber + '\'' +
                ", username='" + username + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }

    // Utility method to check if credentials match
    public boolean authenticate(String inputUsername, String inputPassword) {
        return this.username.equalsIgnoreCase(inputUsername) && this.password.equals(inputPassword);
    }
}