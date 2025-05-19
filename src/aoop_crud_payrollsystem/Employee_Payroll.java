/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aoop_crud_payrollsystem;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sylani
 */
public final class Employee_Payroll extends javax.swing.JFrame {
private final String userEmployeeNumber;
    private final String userLastName;
    private final String userFirstName;
    public List<PayrollUserInformation> payrollData = new ArrayList<>();
    private static String[] userPayroll;

    private void openDashboard() {
    try {
        String[] employeeInfo = sendInformation();
        Employee_Dashboard dashboard = new Employee_Dashboard(employeeInfo);
        dashboard.setLocationRelativeTo(this);
        dashboard.setVisible(true);
        this.dispose();
    } catch (Exception ex) {
        Logger.getLogger(Employee_MyProfile.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(this, 
            "Error opening dashboard: " + ex.getMessage(), 
            "Error", JOptionPane.ERROR_MESSAGE);
    }
} 
    

    /**
     * Creates new form PayrollProcessing
     *
     * @param userPayroll
     * @throws java.io.FileNotFoundException
     * @throws com.opencsv.exceptions.CsvException
     */
//     public PayrollUser(String[] userPayroll) throws FileNotFoundException, IOException {
    public Employee_Payroll(String[] userPayroll) throws FileNotFoundException, IOException, CsvException {
        this.userEmployeeNumber = userPayroll[0];
        this.userLastName = userPayroll[1];
        this.userFirstName = userPayroll[2];

        initComponents();
        String csvPayrollUser = "PayrollRecords.csv";
        List<String[]> records = FileHandling.readCSV(csvPayrollUser);
        parsePayrollRecords(records);
        populatecomboboxCoveredPeriods();
        showEmployeeInformation();
//        setIconImage();

    }

    public String getEmployeeNumber() {
        return userEmployeeNumber;
    }

    public String getLastName() {
        return userLastName;
    }

    public String getFirstName() {
        return userFirstName;
    }

    public class PayrollUserInformation {

        private final String employeeNo;
        private final String lastName;
        private final String firstName;
        private final String workedHours;
        private final String basicSalary;
        private final String hourlyRate;
        private final String grossIncome;
        private final String sssDeduction;
        private final String philhealthDeduction;
        private final String pagibigDeduction;
        private final String withholdingTax;
        private final String coveredMonth;
        private final String coveredYear;
        private final String benefits;
        private final String totalDeductions;
        private final String takeHomePay;

        // Constructor to initialize all fields
        public PayrollUserInformation(
                String employeeNo, String lastName, String firstName, String workedHours,
                String basicSalary, String hourlyRate, String grossIncome, String sssDeduction,
                String philhealthDeduction, String pagibigDeduction, String withholdingTax,
                String coveredMonth, String coveredYear, String benefits, String totalDeductions,
                String takeHomePay
        ) {
            this.employeeNo = employeeNo;
            this.lastName = lastName;
            this.firstName = firstName;
            this.workedHours = workedHours;
            this.basicSalary = basicSalary;
            this.hourlyRate = hourlyRate;
            this.grossIncome = grossIncome;
            this.sssDeduction = sssDeduction;
            this.philhealthDeduction = philhealthDeduction;
            this.pagibigDeduction = pagibigDeduction;
            this.withholdingTax = withholdingTax;
            this.coveredMonth = coveredMonth;
            this.coveredYear = coveredYear;
            this.benefits = benefits;
            this.totalDeductions = totalDeductions;
            this.takeHomePay = takeHomePay;
        }

        // Getter methods for each attribute
        public String getEmployeeNo() {
            return employeeNo;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getWorkedHours() {
            return workedHours;
        }

        public String getBasicSalary() {
            return basicSalary;
        }

        public String getHourlyRate() {
            return hourlyRate;
        }

        public String getGrossIncome() {
            return grossIncome;
        }

        public String getSssDeduction() {
            return sssDeduction;
        }

        public String getPhilhealthDeduction() {
            return philhealthDeduction;
        }

        public String getPagibigDeduction() {
            return pagibigDeduction;
        }

        public String getWithholdingTax() {
            return withholdingTax;
        }

        public String getCoveredMonth() {
            return coveredMonth;
        }

        public String getCoveredYear() {
            return coveredYear;
        }

        public String getBenefits() {
            return benefits;
        }

        public String getTotalDeductions() {
            return totalDeductions;
        }

        public String getTakeHomePay() {
            return takeHomePay;
        }
    }

    public void parsePayrollRecords(List<String[]> records) {

        for (String[] record : records) {
            String employeeNo = record[0];
            String lastName = record[1];
            String firstName = record[2];
            String workedHours = record[3];
            String basicSalary = record[4];
            String hourlyRate = record[5];
            String grossIncome = record[6];
            String sssDeduction = record[7];
            String philhealthDeduction = record[8];
            String pagibigDeduction = record[9];
            String withholdingTax = record[10];
            String coveredMonth = record[11];
            String coveredYear = record[12];
            String benefits = record[13];
            String totalDeductions = record[14];
            String takeHomePay = record[15];

            PayrollUserInformation payrollUserInfo = new PayrollUserInformation(
                    employeeNo, lastName, firstName, workedHours, basicSalary, hourlyRate,
                    grossIncome, sssDeduction, philhealthDeduction, pagibigDeduction,
                    withholdingTax, coveredMonth, coveredYear, benefits, totalDeductions,
                    takeHomePay
            );

            payrollData.add(payrollUserInfo);
        }
    }

    private String[] populateByMonth() {
        String[] months = {"",
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
        };

        return months;
    }

    private List<Integer> populateByYear() {
        List<Integer> years = new ArrayList<>();
        int currentYear = Year.now().getValue();
        for (int i = currentYear - 4; i <= currentYear; i++) {  //assuming company store data for 5years
            years.add(i);
        }
        return years;
    }

    private void populatecomboboxCoveredPeriods() {
        String[] months = populateByMonth();
        for (String month : months) {
            jComboBoxCoveredMonth.addItem(month);
        }

        List<Integer> years = populateByYear();
        for (int i = 0; i < years.size(); i++) {
            jComboBoxCoveredYear.addItem(years.get(i).toString());
        }

    }

    public static boolean isPayrollRecordsCsvEmpty(String csvFile) throws CsvValidationException {

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile))) {
            String[] nextLine = csvReader.readNext();

            // If nextLine is null, the file is empty
            return nextLine == null; // check if statement is true
        } catch (IOException e) {

            return true; // return boolean true if csvFile can't be found in the directory. 
        }
    }

    public Integer determineIndex() {
        String searchId = jTextFieldEmployeeNum.getText();
        String searchMonth = jComboBoxCoveredMonth.getSelectedItem().toString();
        String searchYear = jComboBoxCoveredYear.getSelectedItem().toString();

        for (int i = 0; i < payrollData.size(); i++) {
            PayrollUserInformation payrollUserInfo = payrollData.get(i);
            if (payrollUserInfo.getEmployeeNo().equals(searchId) && payrollUserInfo.getCoveredMonth().equals(searchMonth) && payrollUserInfo.getCoveredYear().equals(searchYear)) {

                return i;
            }
        }
        clearField();
        JOptionPane.showMessageDialog(null, "No record for the selected covered period");

        return -1; // Return -1 if no match is found
    }

    public void showEmployeeInformation() {
        jTextFieldEmployeeNum.setText(getEmployeeNumber());
        jTextFieldLastName.setText(getLastName());
        jTextFieldFirstName.setText(getFirstName());
    }

    public void showUserPayroll() {

        String basis = jTextFieldEmployeeNum.getText();
        for (PayrollUserInformation employee : payrollData) {
            if (employee.getEmployeeNo().equals(basis)) {
                jTextFieldWorkedHours.setText(employee.getWorkedHours());
                jTextFieldBasicSalary.setText(employee.getBasicSalary());
                jTextFieldHourlyRate.setText(employee.getHourlyRate());
                jTextFieldGrossIncome.setText(employee.getGrossIncome());
                jTextSssDeduction.setText(employee.getSssDeduction());
                jTextFieldPhilHealthDeduction.setText(employee.getPhilhealthDeduction());
                jTextFieldPagibigDeduction.setText(employee.getPagibigDeduction());
                jTextFieldWHTax.setText(employee.getWithholdingTax());
                jTextFieldGrossIncome_S.setText(employee.getGrossIncome());
                jTextFieldBenefits.setText(employee.getBenefits());
                jTextFieldTotalDeductions.setText(employee.getTotalDeductions());
                jTextFieldTakeHomePay.setText(employee.getTakeHomePay());
            }
        }
    }

@SuppressWarnings("empty-statement")
    public String[] sendInformation() {
        String[] userInformation = new String[3];;

        userInformation[0] = jTextFieldEmployeeNum.getText();
        userInformation[1] = jTextFieldLastName.getText();
        userInformation[2] = jTextFieldFirstName.getText();

        return userInformation;

    }

    public void clearField() {
        jTextFieldWorkedHours.setText("");
        jTextFieldBasicSalary.setText("");
        jTextFieldHourlyRate.setText("");
        jTextFieldGrossIncome.setText("");
        jTextSssDeduction.setText("");
        jTextFieldPhilHealthDeduction.setText("");
        jTextFieldPagibigDeduction.setText("");
        jTextFieldWHTax.setText("");
        jTextFieldGrossIncome_S.setText("");
        jTextFieldBenefits.setText("");
        jTextFieldTotalDeductions.setText("");
        jTextFieldTakeHomePay.setText("");
    }
private void resetButtonColors() {
            Dashboard.setBackground(new Color(255, 204, 204));
            MyProfile.setBackground(new Color(255, 204, 204));
            LeaveApplication.setBackground(new Color(255, 204, 204));
            Payroll.setBackground(new Color(255, 204, 204));

            Dashboard.setForeground(Color.BLACK);
            MyProfile.setForeground(Color.BLACK);
            LeaveApplication.setForeground(Color.BLACK);
           Payroll.setForeground(Color.BLACK);
        }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldPagibigDeduction = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldWHTax = new javax.swing.JTextField();
        jTextFieldEmployeeNum = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldLastName = new javax.swing.JTextField();
        jTextFieldGrossIncome_S = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldFirstName = new javax.swing.JTextField();
        jTextFieldBenefits = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jComboBoxCoveredYear = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jTextFieldWorkedHours = new javax.swing.JTextField();
        jButtonView = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jTextFieldGrossIncome = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextSssDeduction = new javax.swing.JTextField();
        jTextFieldPhilHealthDeduction = new javax.swing.JTextField();
        jTextFieldTotalDeductions = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextFieldTakeHomePay = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextFieldHourlyRate = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldBasicSalary = new javax.swing.JTextField();
        jComboBoxCoveredMonth = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        MotorPH = new javax.swing.JLabel();
        MainMenu = new javax.swing.JLabel();
        Dashboard = new javax.swing.JButton();
        MyProfile = new javax.swing.JButton();
        Payroll = new javax.swing.JButton();
        LeaveApplication = new javax.swing.JButton();
        SignOut = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setText("PhilHealth Deduction ");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 333, -1, -1));

        jLabel11.setText("Pagibig Deduction ");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 373, -1, -1));

        jTextFieldPagibigDeduction.setEditable(false);
        jTextFieldPagibigDeduction.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldPagibigDeduction.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldPagibigDeduction.setEnabled(false);
        jTextFieldPagibigDeduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPagibigDeductionActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldPagibigDeduction, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 370, 170, 22));

        jLabel12.setText("Withholding Tax");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 413, -1, -1));

        jLabel3.setText("Employee No.");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 13, -1, -1));

        jTextFieldWHTax.setEditable(false);
        jTextFieldWHTax.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldWHTax.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldWHTax.setEnabled(false);
        jTextFieldWHTax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldWHTaxActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldWHTax, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 410, 170, 22));

        jTextFieldEmployeeNum.setEditable(false);
        jTextFieldEmployeeNum.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldEmployeeNum.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldEmployeeNum.setEnabled(false);
        jTextFieldEmployeeNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEmployeeNumActionPerformed(evt);
            }
        });
        jTextFieldEmployeeNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldEmployeeNumKeyTyped(evt);
            }
        });
        jPanel1.add(jTextFieldEmployeeNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 170, 22));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel13.setText("Summary");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 213, -1, -1));

        jLabel4.setText("Last Name");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 53, -1, -1));

        jLabel14.setText("Gross Income");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(395, 253, -1, -1));

        jTextFieldLastName.setEditable(false);
        jTextFieldLastName.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldLastName.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldLastName.setEnabled(false);
        jTextFieldLastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLastNameActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 170, 22));

        jTextFieldGrossIncome_S.setEditable(false);
        jTextFieldGrossIncome_S.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldGrossIncome_S.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldGrossIncome_S.setEnabled(false);
        jTextFieldGrossIncome_S.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldGrossIncome_SActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldGrossIncome_S, new org.netbeans.lib.awtextra.AbsoluteConstraints(505, 250, 112, 22));

        jLabel5.setText("First Name");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 93, -1, -1));

        jLabel15.setText("Benefits");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(395, 293, -1, -1));

        jTextFieldFirstName.setEditable(false);
        jTextFieldFirstName.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldFirstName.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldFirstName.setEnabled(false);
        jTextFieldFirstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFirstNameActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 170, 22));

        jTextFieldBenefits.setEditable(false);
        jTextFieldBenefits.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldBenefits.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldBenefits.setEnabled(false);
        jTextFieldBenefits.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBenefitsActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldBenefits, new org.netbeans.lib.awtextra.AbsoluteConstraints(505, 290, 112, 22));

        jLabel23.setText("Year");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(535, 35, -1, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        jLabel6.setText("Covered Period");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(385, 10, -1, -1));

        jComboBoxCoveredYear.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        jComboBoxCoveredYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCoveredYearActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBoxCoveredYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 55, 79, -1));

        jLabel7.setText("Worked Hours");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 133, -1, -1));

        jTextFieldWorkedHours.setEditable(false);
        jTextFieldWorkedHours.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldWorkedHours.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldWorkedHours.setEnabled(false);
        jTextFieldWorkedHours.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldWorkedHoursActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldWorkedHours, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 170, 22));

        jButtonView.setText("View");
        jButtonView.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        jButtonView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonViewActionPerformed(evt);
            }
        });
        jPanel1.add(jButtonView, new org.netbeans.lib.awtextra.AbsoluteConstraints(532, 90, 79, 23));

        jLabel8.setText("Gross Income");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 253, -1, -1));

        jTextFieldGrossIncome.setEditable(false);
        jTextFieldGrossIncome.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldGrossIncome.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldGrossIncome.setEnabled(false);
        jTextFieldGrossIncome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldGrossIncomeActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldGrossIncome, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 250, 170, 22));

        jLabel9.setText("SSS Deduction ");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 293, -1, -1));

        jTextSssDeduction.setEditable(false);
        jTextSssDeduction.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextSssDeduction.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextSssDeduction.setEnabled(false);
        jTextSssDeduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextSssDeductionActionPerformed(evt);
            }
        });
        jPanel1.add(jTextSssDeduction, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 290, 170, 22));

        jTextFieldPhilHealthDeduction.setEditable(false);
        jTextFieldPhilHealthDeduction.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldPhilHealthDeduction.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldPhilHealthDeduction.setEnabled(false);
        jTextFieldPhilHealthDeduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPhilHealthDeductionActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldPhilHealthDeduction, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, 170, 22));

        jTextFieldTotalDeductions.setEditable(false);
        jTextFieldTotalDeductions.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldTotalDeductions.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldTotalDeductions.setEnabled(false);
        jTextFieldTotalDeductions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTotalDeductionsActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldTotalDeductions, new org.netbeans.lib.awtextra.AbsoluteConstraints(505, 330, 112, 22));

        jLabel16.setText("Total Deductions");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(395, 333, -1, -1));

        jTextFieldTakeHomePay.setEditable(false);
        jTextFieldTakeHomePay.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldTakeHomePay.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldTakeHomePay.setEnabled(false);
        jTextFieldTakeHomePay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTakeHomePayActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldTakeHomePay, new org.netbeans.lib.awtextra.AbsoluteConstraints(505, 370, 112, 22));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("TAKE-HOME PAY");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(395, 373, -1, -1));

        jTextFieldHourlyRate.setEditable(false);
        jTextFieldHourlyRate.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldHourlyRate.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldHourlyRate.setEnabled(false);
        jTextFieldHourlyRate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldHourlyRateActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldHourlyRate, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 210, 170, 22));

        jLabel18.setText("Basic Salary");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 173, -1, -1));

        jLabel19.setText("Hourly Rate");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(15, 213, -1, -1));

        jTextFieldBasicSalary.setEditable(false);
        jTextFieldBasicSalary.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldBasicSalary.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldBasicSalary.setEnabled(false);
        jTextFieldBasicSalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBasicSalaryActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldBasicSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 170, 170, 22));

        jComboBoxCoveredMonth.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.white));
        jComboBoxCoveredMonth.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBoxCoveredMonthMouseClicked(evt);
            }
        });
        jComboBoxCoveredMonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCoveredMonthActionPerformed(evt);
            }
        });
        jPanel1.add(jComboBoxCoveredMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 55, 112, -1));

        jLabel20.setText("Month");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(395, 35, -1, -1));

        jPanel4.setBackground(new java.awt.Color(255, 204, 204));

        MotorPH.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        MotorPH.setText("MOTORPH ");

        MainMenu.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        MainMenu.setForeground(new java.awt.Color(102, 102, 102));
        MainMenu.setText("EMPLOYEE ");

        Dashboard.setBackground(new java.awt.Color(255, 204, 204));
        Dashboard.setText("Dashboard");
        Dashboard.setBorder(null);
        Dashboard.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Dashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DashboardActionPerformed(evt);
            }
        });

        MyProfile.setBackground(new java.awt.Color(255, 204, 204));
        MyProfile.setText("My Profile");
        MyProfile.setBorder(null);
        MyProfile.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MyProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MyProfileActionPerformed(evt);
            }
        });

        Payroll.setBackground(new java.awt.Color(255, 153, 153));
        Payroll.setText("Payroll");
        Payroll.setBorder(null);
        Payroll.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Payroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PayrollActionPerformed(evt);
            }
        });

        LeaveApplication.setBackground(new java.awt.Color(255, 204, 204));
        LeaveApplication.setText("Leave Application");
        LeaveApplication.setBorder(null);
        LeaveApplication.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LeaveApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeaveApplicationActionPerformed(evt);
            }
        });

        SignOut.setBackground(new java.awt.Color(255, 204, 204));
        SignOut.setText("Sign Out");
        SignOut.setBorder(null);
        SignOut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SignOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignOutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SignOut, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Payroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MyProfile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Dashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MainMenu)
                    .addComponent(MotorPH)
                    .addComponent(LeaveApplication, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MotorPH)
                .addGap(30, 30, 30)
                .addComponent(MainMenu)
                .addGap(18, 18, 18)
                .addComponent(Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MyProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LeaveApplication, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Payroll, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(SignOut, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 789, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(105, 105, 105))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MyProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MyProfileActionPerformed
        try {
            // TODO add your handling code here:
            setVisible(false);
            Employee_MyProfile profileUser = null;
            try {
                profileUser = new Employee_MyProfile(getEmployeeNumber());
            } catch (FileNotFoundException | CsvException ex) {
                Logger.getLogger(Employee_Payroll.class.getName()).log(Level.SEVERE, null, ex);
            }
            profileUser.setVisible(true); //open Employee Dashboard
        } catch (IOException ex) {
            Logger.getLogger(Employee_Payroll.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_MyProfileActionPerformed

    private void LeaveApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeaveApplicationActionPerformed
        try {
            // TODO add your handling code here:
            String[] employeeInformation = sendInformation();
            Employee_LeaveApplication leaveUser = new Employee_LeaveApplication(employeeInformation);
            setVisible(false);
            leaveUser.setVisible(true);
        } catch (IOException | CsvException ex) {
            Logger.getLogger(Employee_Payroll.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_LeaveApplicationActionPerformed

    private void PayrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PayrollActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PayrollActionPerformed

    private void SignOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignOutActionPerformed
        // TODO add your handling code here:
//Send back to LoginManager                
// Confirm before exiting
     int response = JOptionPane.showConfirmDialog(
        this,
        "Are you sure you want to sign out?",
        "Confirm Sign Out",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE
    );
    
    if (response == JOptionPane.YES_OPTION) {
        WindowUtils.switchToWindow(this, new LoginManager());
    }

    }//GEN-LAST:event_SignOutActionPerformed

    private void jTextFieldPagibigDeductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPagibigDeductionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPagibigDeductionActionPerformed

    private void jTextFieldWHTaxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldWHTaxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldWHTaxActionPerformed

    private void jTextFieldEmployeeNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEmployeeNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEmployeeNumActionPerformed

    private void jTextFieldEmployeeNumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEmployeeNumKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEmployeeNumKeyTyped

    private void jTextFieldLastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLastNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLastNameActionPerformed

    private void jTextFieldGrossIncome_SActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldGrossIncome_SActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldGrossIncome_SActionPerformed

    private void jTextFieldFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFirstNameActionPerformed

    private void jTextFieldBenefitsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBenefitsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBenefitsActionPerformed

    private void jComboBoxCoveredYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCoveredYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCoveredYearActionPerformed

    private void jTextFieldWorkedHoursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldWorkedHoursActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldWorkedHoursActionPerformed

    private void jButtonViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonViewActionPerformed
        // TODO add your handling code here:
        int index = determineIndex();
        if (index != -1) {
            showUserPayroll();
        }
    }//GEN-LAST:event_jButtonViewActionPerformed

    private void jTextFieldGrossIncomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldGrossIncomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldGrossIncomeActionPerformed

    private void jTextSssDeductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextSssDeductionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextSssDeductionActionPerformed

    private void jTextFieldPhilHealthDeductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPhilHealthDeductionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPhilHealthDeductionActionPerformed

    private void jTextFieldTotalDeductionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTotalDeductionsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTotalDeductionsActionPerformed

    private void jTextFieldTakeHomePayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTakeHomePayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTakeHomePayActionPerformed

    private void jTextFieldHourlyRateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldHourlyRateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldHourlyRateActionPerformed

    private void jTextFieldBasicSalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBasicSalaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBasicSalaryActionPerformed

    private void jComboBoxCoveredMonthMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBoxCoveredMonthMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCoveredMonthMouseClicked

    private void jComboBoxCoveredMonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCoveredMonthActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxCoveredMonthActionPerformed

    private void DashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DashboardActionPerformed
        // TODO add your handling code here:
        // Dashboard button
       openDashboard();
        resetButtonColors();
        Dashboard.setBackground(new Color(0, 204, 102));
        Dashboard.setForeground(Color.WHITE);
    }//GEN-LAST:event_DashboardActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Employee_Payroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Employee_Payroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Employee_Payroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Employee_Payroll.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(() -> {
            try {
                try {
                    new Employee_Payroll(userPayroll).setVisible(true);
                } catch (FileNotFoundException | CsvException ex) {
                    Logger.getLogger(Employee_Payroll.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (IOException ex) {
                Logger.getLogger(Employee_Payroll.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Dashboard;
    private javax.swing.JButton LeaveApplication;
    private javax.swing.JLabel MainMenu;
    private javax.swing.JLabel MotorPH;
    private javax.swing.JButton MyProfile;
    private javax.swing.JButton Payroll;
    private javax.swing.JButton SignOut;
    private javax.swing.JButton jButtonView;
    private javax.swing.JComboBox<String> jComboBoxCoveredMonth;
    private javax.swing.JComboBox<String> jComboBoxCoveredYear;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    public javax.swing.JTextField jTextFieldBasicSalary;
    public javax.swing.JTextField jTextFieldBenefits;
    private javax.swing.JTextField jTextFieldEmployeeNum;
    public javax.swing.JTextField jTextFieldFirstName;
    public javax.swing.JTextField jTextFieldGrossIncome;
    public javax.swing.JTextField jTextFieldGrossIncome_S;
    public javax.swing.JTextField jTextFieldHourlyRate;
    public javax.swing.JTextField jTextFieldLastName;
    public javax.swing.JTextField jTextFieldPagibigDeduction;
    public javax.swing.JTextField jTextFieldPhilHealthDeduction;
    public javax.swing.JTextField jTextFieldTakeHomePay;
    public javax.swing.JTextField jTextFieldTotalDeductions;
    public javax.swing.JTextField jTextFieldWHTax;
    public javax.swing.JTextField jTextFieldWorkedHours;
    public javax.swing.JTextField jTextSssDeduction;
    // End of variables declaration//GEN-END:variables
}
