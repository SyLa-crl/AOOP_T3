/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aoop_crud_payrollsystem;
import com.opencsv.exceptions.CsvException;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Sylani
 */
public class Main {
    public static void main(String args[]) throws FileNotFoundException, IOException, CsvException {
        // You can add any system properties here if needed
        // System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        
        new LoginManager().setVisible(true);
    }
}