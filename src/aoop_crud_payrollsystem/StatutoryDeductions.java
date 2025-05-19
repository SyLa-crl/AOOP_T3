/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aoop_crud_payrollsystem;
import java.util.ArrayList;

/**
 *
 * @author Sylani
 */
 public class StatutoryDeductions {
    public static double calculateSSS(double grossIncome) {
        // SSS calculation logic - placeholder
        if (grossIncome <= 3250) return 135.0;
        if (grossIncome <= 3750) return 157.5;
        if (grossIncome <= 4250) return 180.0;
        if (grossIncome <= 4750) return 202.5;
        if (grossIncome <= 5250) return 225.0;
        if (grossIncome <= 5750) return 247.5;
        if (grossIncome <= 6250) return 270.0;
        if (grossIncome <= 6750) return 292.5;
        if (grossIncome <= 7250) return 315.0;
        if (grossIncome <= 7750) return 337.5;
        if (grossIncome <= 8250) return 360.0;
        if (grossIncome <= 8750) return 382.5;
        if (grossIncome <= 9250) return 405.0;
        if (grossIncome <= 9750) return 427.5;
        if (grossIncome <= 10250) return 450.0;
        // Continue with SSS table...
        return 450.0; // Maximum
    }
    
    public static double calculatePhilHealth(double grossIncome) {
        // PhilHealth calculation - 1.25% of basic salary (employee share)
        double contribution = grossIncome * 0.0125;
        // Minimum and maximum contributions
        if (contribution < 150) return 150.0;
        if (contribution > 1800) return 1800.0;
        return contribution;
    }
    
    public static double calculatePagIbig(double grossIncome) {
        // Pag-IBIG calculation - 1% of basic salary (minimum 100)
        double contribution = grossIncome * 0.01;
        if (contribution < 100) return 100.0;
        return contribution;
    }
    
    public static double calculateWHTax(double taxableIncome) {
        // Withholding tax calculation based on BIR tax table
        if (taxableIncome <= 20833) return 0.0;
        if (taxableIncome <= 33333) return (taxableIncome - 20833) * 0.15;
        if (taxableIncome <= 66667) return 1875 + (taxableIncome - 33333) * 0.20;
        if (taxableIncome <= 166667) return 8541.67 + (taxableIncome - 66667) * 0.25;
        if (taxableIncome <= 666667) return 33541.67 + (taxableIncome - 166667) * 0.30;
        return 183541.67 + (taxableIncome - 666667) * 0.35;
    }
}