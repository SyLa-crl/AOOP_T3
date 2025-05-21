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
        
        new LoginManager().setVisible(true);
    }
}