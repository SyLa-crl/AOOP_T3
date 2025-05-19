/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aoop_crud_payrollsystem;

/**
 *
 * @author Sylani
 */
public class EmployeeHoursWorked {
 // Attributes
    private String employeeNumber;
    private String lastName;
    private String firstName;
    private String coveredPeriod;
    private String noOfHoursWorked;

    // Constructor
    public EmployeeHoursWorked(String employeeNumber, String lastName, String firstName, String coveredPeriod, String noOfHoursWorked) {
        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.coveredPeriod = coveredPeriod;
        this.noOfHoursWorked = noOfHoursWorked;
    }

   // Getters and Setters
    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
    
    
      public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
         
    public String getCoveredPeriod() {
        return coveredPeriod;
    }

    public String getNoOfHoursWorked() {
        return noOfHoursWorked;
    }

    @Override
    public String toString() {
        return "EmployeeHoursWorked{"
                + "id='" + employeeNumber + '\''
                + ", lastName='" + lastName + '\''
                + ", firstName='" + firstName + '\''
                + ", coveredPeriod='" + coveredPeriod + '\''
                + ", noOfHoursWorked='" + noOfHoursWorked + '\''
                + '}';
    }
}