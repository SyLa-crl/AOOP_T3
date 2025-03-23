/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oop_crud_payrollsystem;

/**
 *
 * @author Sylani
 */
public class EmployeeLogin {
  // Attributes
    private String username;
    private String password;
    private String employeeNumber;
    private String userEmployeeNumber;

    // Constructor
    public EmployeeLogin(String employeeNumber, String username, String password) {
        this.employeeNumber = employeeNumber;
        this.username = username;
        this.password = password;
    }

    // contructor for saving user employee number
    public EmployeeLogin(String userEmployeeNumber) {
        this.userEmployeeNumber = userEmployeeNumber;
    }

    // Getters 
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public String getUserEmployeeNumber() {
        return userEmployeeNumber;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public void setUserEmployeeNumber(String userEmployeeNumber) {
        this.userEmployeeNumber = userEmployeeNumber;
    }
}