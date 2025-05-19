/*
 * Fixed FileHandling.java
 * Updated to work better with LoginManager
 */
package aoop_crud_payrollsystem;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * FileHandling class for CSV operations
 * @author Sylani
 */
public class FileHandling {
    
    /**
     * FIXED: Read CSV file and return records
     * Made header removal optional and added better error handling
     * @param csvFile
     * @return 
     * @throws java.io.IOException
     * @throws com.opencsv.exceptions.CsvException
     */
    public static List<String[]> readCSV(String csvFile) throws IOException, CsvException {
        return readCSV(csvFile, true); // Default: skip header
    }
    
    /**
     * Read CSV file with option to skip header
     * @param csvFile Path to CSV file
     * @param skipHeader True to skip first row (header), false to include it
     * @return List of String arrays representing CSV rows
     * @throws java.io.IOException
     * @throws com.opencsv.exceptions.CsvException
     */
    public static List<String[]> readCSV(String csvFile, boolean skipHeader) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            List<String[]> records = reader.readAll();
            
            // Log the total records read
            Logger.getLogger(FileHandling.class.getName()).log(Level.INFO, 
                "Read {0} records from {1}", new Object[]{records.size(), csvFile});
            
            // Remove header if requested and records exist
            if (skipHeader && !records.isEmpty()) {
                Logger.getLogger(FileHandling.class.getName()).log(Level.INFO, 
                    "Removing header row: {0}", java.util.Arrays.toString(records.get(0)));
                records.remove(0);
            }
            
            Logger.getLogger(FileHandling.class.getName()).log(Level.INFO, 
                "Returning {0} data records", records.size());
            return records;
            
        } catch (FileNotFoundException e) {
            Logger.getLogger(FileHandling.class.getName()).log(Level.SEVERE, 
                "File not found: " + csvFile, e);
            JOptionPane.showMessageDialog(null, 
                "File not found: " + csvFile + "\nPlease make sure the file exists in the project root directory.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        } catch (IOException | CsvException e) {
            Logger.getLogger(FileHandling.class.getName()).log(Level.SEVERE, 
                "Error reading CSV file: " + csvFile, e);
            JOptionPane.showMessageDialog(null, 
                "Error reading CSV file: " + csvFile + "\nError: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        }
    }
    
    /**
     * FIXED: Proper CSV Handler inner class implementation
     */
    public static class CSVHandler {
        private final String filePath;
        
        public CSVHandler(String filePath) { 
            this.filePath = filePath; 
        }
        
        public List<String[]> readCSV() throws IOException, CsvException {
            return FileHandling.readCSV(filePath);
        }
        
        public List<String[]> readCSV(boolean skipHeader) throws IOException, CsvException {
            return FileHandling.readCSV(filePath, skipHeader);
        }
        
        public void writeCSV(List<String[]> data) throws IOException {
            try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
                writer.writeAll(data);
                Logger.getLogger(FileHandling.class.getName()).log(Level.INFO, 
                    "Successfully wrote {0} records to {1}", new Object[]{data.size(), filePath});
            }
        }
    }

    /**
     * Export JTable data to CSV file
     * Your existing method - no changes needed
     * @param table
     */
    public static void exportTableToCSV(JTable table) {
        String csvFile = "MotorPHEmployeeData.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();

            // Write column headers
            int columnCount = model.getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = model.getColumnName(i);
            }
            writer.writeNext(columnNames);

            // Write rows
            int rowCount = model.getRowCount();
            for (int i = 0; i < rowCount; i++) {
                String[] rowData = new String[columnCount];
                for (int j = 0; j < columnCount; j++) {
                    Object value = model.getValueAt(i, j);
                    rowData[j] = value != null ? value.toString() : "";
                }
                writer.writeNext(rowData);
            }

            JOptionPane.showMessageDialog(null, "Database updated successfully");
            Logger.getLogger(FileHandling.class.getName()).log(Level.INFO, "Successfully exported table to {0}", csvFile);
                
        } catch (IOException e) {
            Logger.getLogger(FileHandling.class.getName()).log(Level.SEVERE, 
                "Failed to export table to CSV", e);
            JOptionPane.showMessageDialog(null, 
                "Failed to export data to CSV file.\nError: " + e.getMessage(),
                "Export Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * ADDED: Utility method to check if CSV file exists and is readable
     * @param csvFile
     * @return 
     */
    public static boolean validateCSVFile(String csvFile) {
        java.io.File file = new java.io.File(csvFile);
        
        if (!file.exists()) {
            Logger.getLogger(FileHandling.class.getName()).log(Level.WARNING, "CSV file does not exist: {0}", csvFile);
            return false;
        }
        
        if (!file.canRead()) {
            Logger.getLogger(FileHandling.class.getName()).log(Level.WARNING, "Cannot read CSV file: {0}", csvFile);
            return false;
        }
        
        if (file.length() == 0) {
            Logger.getLogger(FileHandling.class.getName()).log(Level.WARNING, "CSV file is empty: {0}", csvFile);
            return false;
        }
        
        Logger.getLogger(FileHandling.class.getName()).log(Level.INFO, 
            "CSV file validation passed: {0} (Size: {1} bytes)", 
            new Object[]{csvFile, file.length()});
        return true;
    }
}
