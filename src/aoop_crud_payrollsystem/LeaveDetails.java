/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aoop_crud_payrollsystem;

/**
 *
 * @author Sylani
 */
public class LeaveDetails {
    private String entryNum;
    private String employeeNumber;
    private String lastName;
    private String firstName;
    private String leaveStatus;
    private String submittedDate;
    private String leaveReason;
    private String startDate;
    private String endDate;
    private String leaveDay;
    
    public LeaveDetails(String entryNum, String employeeNumber, String lastName, String firstName,
                       String leaveStatus, String submittedDate, String leaveReason, 
                       String startDate, String endDate, String leaveDay) {
        this.entryNum = entryNum;
        this.employeeNumber = employeeNumber;
        this.lastName = lastName;
        this.firstName = firstName;
        this.leaveStatus = leaveStatus;
        this.submittedDate = submittedDate;
        this.leaveReason = leaveReason;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveDay = leaveDay;
    }
    
    // Getters
    public String getentryNum() { return entryNum; }
    public String getEmployeeNumber() { return employeeNumber; }
    public String getLastName() { return lastName; }
    public String getFirstName() { return firstName; }
    public String getLeaveStatus() { return leaveStatus; }
    public String getSubmittedDate() { return submittedDate; }
    public String getLeaveReason() { return leaveReason; }
    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getLeaveDay() { return leaveDay; }
    
    // Setters
    public void setentryNum(String entryNum) { this.entryNum = entryNum; }
    public void setEmployeeNumber(String employeeNumber) { this.employeeNumber = employeeNumber; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLeaveStatus(String leaveStatus) { this.leaveStatus = leaveStatus; }
    public void setSubmittedDate(String submittedDate) { this.submittedDate = submittedDate; }
    public void setLeaveReason(String leaveReason) { this.leaveReason = leaveReason; }
    public void setStartDate(String startDate) { this.startDate = startDate; }
    public void setEndDate(String endDate) { this.endDate = endDate; }
    public void setLeaveDay(String leaveDay) { this.leaveDay = leaveDay; }
}

