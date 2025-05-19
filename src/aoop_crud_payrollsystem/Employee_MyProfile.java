/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package aoop_crud_payrollsystem;
import com.opencsv.exceptions.CsvException;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Sylani
 */

public final class Employee_MyProfile extends javax.swing.JFrame {
//EmployeeProfileUser: This class extends javax.swing.JFrame to create a GUI application.
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


    private final List<Employee> employees = new ArrayList<>(); //employees: List to store Employee objects.
    private static String userEmployeeNumber;            //userEmployeeNumber: Static variable to store the employee number.
    
            //EmployeeProfileUser(String employeeNumber): Constructor that initializes the GUI and loads employee data.
    public Employee_MyProfile(String employeeNumber) throws FileNotFoundException, IOException, CsvException {
        Employee_MyProfile.userEmployeeNumber = employeeNumber;

        initComponents();//nitComponents(): Initializes GUI components.
        String csvFile = "MotorPHEmployeeData.csv"; 
        csvRun(csvFile);//csvRun(csvFile): Reads and processes the CSV file.
        showDetails();//showDetails(): Displays details of the employee..
        setFieldsEnabled(false);//setFieldsEnabled(false): Disables editing of text fields.
    }

    public String getEmployeeNumber() { //getEmployeeNumber(): Returns the employee number.
        return userEmployeeNumber;
    }
//csvRun(String csvFile): Reads the CSV file and parses records into Employee objects.
    public void csvRun(String csvFile) throws FileNotFoundException, IOException, CsvException {
        List<String[]> records = FileHandling.readCSV(csvFile);
        List<Employee> employees = parseRecords(records);

    }

    // Method to parse records into Employee objects
    public List<Employee> parseRecords(List<String[]> records) {
//parseRecords(List<String[]> records): Converts CSV records into Employee objects and adds them to the list
        for (String[] record : records) {
            String employeeNumber = record[0];
            String lastName = record[1];
            String firstName = record[2];
            String employeeBirthday = record[3];
            String address = record[4];
            String phoneNumber = record[5];
            String sssNumber = record[6];
            String philHealthNumber = record[7];
            String tinNumber = record[8];
            String pagIbigNumber = record[9];
            String status = record[10];
            String position = record[11];
            String immediateSupervisor = record[12];
            String basicSalary = record[13];
            String riceSubsidy = record[14];
            String phoneAllowance = record[15];
            String clothingAllowance = record[16];

            Employee employee = new Employee(employeeNumber, lastName, firstName, employeeBirthday, address, phoneNumber, sssNumber, philHealthNumber, tinNumber, pagIbigNumber, status, position, immediateSupervisor, basicSalary, riceSubsidy, phoneAllowance, clothingAllowance);
            employees.add(employee);
        }

        return employees;
    }
//showDetails(): Displays employee details in the text fields.
    public void showDetails() {
        jTextFieldEmployeeNum.setText(getEmployeeNumber());

        String basis = jTextFieldEmployeeNum.getText();
        for (Employee employee : employees) {
            if (employee.getEmployeeNumber().equals(basis)) {
                jTextFieldLastName.setText(employee.getLastName());
                jTextFieldFirstName.setText(employee.getFirstName());
                jTextFieldBirthday.setText(employee.getEmployeeBirthday());
                jTextAreaAddress.setText(employee.getAddress());
                jTextFieldPhoneNum.setText(employee.getPhoneNumber());
                jTextFieldSSSnum.setText(employee.getSssNumber());
                jTextFieldPhilhealthNum.setText(employee.getPhilHealthNumber());
                jTextFieldTINnum.setText(employee.getTinNumber());
                jTextFieldPagibigNum.setText(employee.getPagIbigNumber());
                jTextFieldStatus.setText(employee.getStatus());
                jTextFieldPosition.setText(employee.getPosition());
                jTextFieldSupervisor.setText(employee.getImmediateSupervisor());
                jTextFieldBasicSalary.setText(employee.getBasicSalary());
                jTextFieldRiceSubsidy.setText(employee.getRiceSubsidy());
                jTextFieldPhoneAllow.setText(employee.getPhoneAllowance());
                jTextFieldClothAllow.setText(employee.getClothingAllowance());

            }
        }
    }
//sendInformation(): Returns an array with the employee number, last name, and first name.
    public String[] sendInformation() {
        String[] userInformation = new String[3];

        userInformation[0] = jTextFieldEmployeeNum.getText();
        userInformation[1] = jTextFieldLastName.getText();
        userInformation[2] = jTextFieldFirstName.getText();
        
        return userInformation;

    }
    //setFieldsEnabled(boolean condition): Enables or disables the text fields based on the condition parameter.
       public void setFieldsEnabled(boolean condition) {
        jTextAreaAddress.setEnabled(condition);
        jTextAreaAddress.setDisabledTextColor(Color.BLACK);

        jTextFieldBasicSalary.setEnabled(condition);
        jTextFieldBasicSalary.setDisabledTextColor(Color.BLACK);

        jTextFieldBirthday.setEnabled(condition);
        jTextFieldBirthday.setDisabledTextColor(Color.BLACK);

        jTextFieldClothAllow.setEnabled(condition);
        jTextFieldClothAllow.setDisabledTextColor(Color.BLACK);

        jTextFieldEmployeeNum.setEnabled(condition);
        jTextFieldEmployeeNum.setDisabledTextColor(Color.BLACK);

        jTextFieldFirstName.setEnabled(condition);
        jTextFieldFirstName.setDisabledTextColor(Color.BLACK);

        jTextFieldLastName.setEnabled(condition);
        jTextFieldLastName.setDisabledTextColor(Color.BLACK);

        jTextFieldPagibigNum.setEnabled(condition);
        jTextFieldPagibigNum.setDisabledTextColor(Color.BLACK);

        jTextFieldPhilhealthNum.setEnabled(condition);
        jTextFieldPhilhealthNum.setDisabledTextColor(Color.BLACK);

        jTextFieldPhoneAllow.setEnabled(condition);
        jTextFieldPhoneAllow.setDisabledTextColor(Color.BLACK);

        jTextFieldPhoneNum.setEnabled(condition);
        jTextFieldPhoneNum.setDisabledTextColor(Color.BLACK);

        jTextFieldPosition.setEnabled(condition);
        jTextFieldPosition.setDisabledTextColor(Color.BLACK);

        jTextFieldRiceSubsidy.setEnabled(condition);
        jTextFieldRiceSubsidy.setDisabledTextColor(Color.BLACK);

        jTextFieldSSSnum.setEnabled(condition);
        jTextFieldSSSnum.setDisabledTextColor(Color.BLACK);

        jTextFieldStatus.setEnabled(condition);
        jTextFieldStatus.setDisabledTextColor(Color.BLACK);

        jTextFieldSupervisor.setEnabled(condition);
        jTextFieldSupervisor.setDisabledTextColor(Color.BLACK);

        jTextFieldTINnum.setEnabled(condition);
        jTextFieldTINnum.setDisabledTextColor(Color.BLACK);
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jTextFieldStatus = new javax.swing.JTextField();
        jTextFieldEmployeeNum = new javax.swing.JTextField();
        jTextFieldLastName = new javax.swing.JTextField();
        jTextFieldBirthday = new javax.swing.JTextField();
        jTextFieldSSSnum = new javax.swing.JTextField();
        jTextFieldPagibigNum = new javax.swing.JTextField();
        jTextFieldTINnum = new javax.swing.JTextField();
        jTextFieldPhoneNum = new javax.swing.JTextField();
        jTextFieldPhilhealthNum = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldSupervisor = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jTextFieldRiceSubsidy = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextFieldPhoneAllow = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jTextFieldClothAllow = new javax.swing.JTextField();
        jTextFieldBasicSalary = new javax.swing.JTextField();
        jLabelBasicSalary = new javax.swing.JLabel();
        jTextFieldFirstName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaAddress = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        jTextFieldPosition = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        MotorPH = new javax.swing.JLabel();
        MainMenu = new javax.swing.JLabel();
        Dashboard = new javax.swing.JButton();
        SignOut = new javax.swing.JButton();
        MyProfile = new javax.swing.JButton();
        Payroll = new javax.swing.JButton();
        LeaveApplication = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255, 0));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setText("Status");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 33, -1, -1));

        jLabel3.setText("Employee No.");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 73, -1, -1));

        jLabel4.setText("Last Name");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 113, -1, -1));

        jLabel6.setText("Birthday");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 193, -1, -1));

        jLabel7.setText("SSS No.");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 113, -1, -1));

        jLabel8.setText("Phone Number");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 233, -1, -1));

        jLabel9.setText("PhilHealth No.");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 153, -1, -1));

        jLabel10.setText("TIN");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 193, -1, -1));

        jLabel11.setText("Pagibig No.");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 233, -1, -1));

        jTextFieldStatus.setEditable(false);
        jTextFieldStatus.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldStatus.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldStatusActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 30, 205, 22));

        jTextFieldEmployeeNum.setEditable(false);
        jTextFieldEmployeeNum.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldEmployeeNum.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldEmployeeNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldEmployeeNumActionPerformed(evt);
            }
        });
        jTextFieldEmployeeNum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldEmployeeNumKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldEmployeeNumKeyTyped(evt);
            }
        });
        jPanel1.add(jTextFieldEmployeeNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, 205, 22));

        jTextFieldLastName.setEditable(false);
        jTextFieldLastName.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldLastName.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldLastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldLastNameActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldLastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 205, 22));

        jTextFieldBirthday.setEditable(false);
        jTextFieldBirthday.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldBirthday.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldBirthday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBirthdayActionPerformed(evt);
            }
        });
        jTextFieldBirthday.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldBirthdayKeyTyped(evt);
            }
        });
        jPanel1.add(jTextFieldBirthday, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 190, 205, 22));

        jTextFieldSSSnum.setEditable(false);
        jTextFieldSSSnum.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldSSSnum.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldSSSnum.setName(""); // NOI18N
        jTextFieldSSSnum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSSSnumActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldSSSnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 110, 205, 22));

        jTextFieldPagibigNum.setEditable(false);
        jTextFieldPagibigNum.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldPagibigNum.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jPanel1.add(jTextFieldPagibigNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 230, 205, 22));

        jTextFieldTINnum.setEditable(false);
        jTextFieldTINnum.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldTINnum.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldTINnum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTINnumActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldTINnum, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 190, 205, 22));

        jTextFieldPhoneNum.setEditable(false);
        jTextFieldPhoneNum.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldPhoneNum.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldPhoneNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPhoneNumActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldPhoneNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 230, 205, 22));

        jTextFieldPhilhealthNum.setEditable(false);
        jTextFieldPhilhealthNum.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldPhilhealthNum.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldPhilhealthNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPhilhealthNumActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldPhilhealthNum, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 150, 205, 22));

        jLabel14.setText("Immediate Supervisor");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 73, -1, -1));

        jTextFieldSupervisor.setEditable(false);
        jTextFieldSupervisor.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldSupervisor.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldSupervisor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSupervisorActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldSupervisor, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 70, 205, 22));

        jLabel15.setText("Rice Subsidy");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 273, -1, -1));

        jTextFieldRiceSubsidy.setEditable(false);
        jTextFieldRiceSubsidy.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldRiceSubsidy.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldRiceSubsidy.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldRiceSubsidyKeyTyped(evt);
            }
        });
        jPanel1.add(jTextFieldRiceSubsidy, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 270, 205, 22));
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(311, 364, -1, -1));

        jLabel17.setText("Phone Allowance");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 313, -1, -1));

        jTextFieldPhoneAllow.setEditable(false);
        jTextFieldPhoneAllow.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldPhoneAllow.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldPhoneAllow.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPhoneAllowKeyTyped(evt);
            }
        });
        jPanel1.add(jTextFieldPhoneAllow, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 310, 205, 22));

        jLabel19.setText("Clothing  Allowance");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 353, -1, -1));

        jTextFieldClothAllow.setEditable(false);
        jTextFieldClothAllow.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldClothAllow.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldClothAllow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldClothAllowActionPerformed(evt);
            }
        });
        jTextFieldClothAllow.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldClothAllowKeyTyped(evt);
            }
        });
        jPanel1.add(jTextFieldClothAllow, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 350, 205, 22));

        jTextFieldBasicSalary.setEditable(false);
        jTextFieldBasicSalary.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldBasicSalary.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldBasicSalary.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBasicSalaryActionPerformed(evt);
            }
        });
        jTextFieldBasicSalary.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldBasicSalaryKeyTyped(evt);
            }
        });
        jPanel1.add(jTextFieldBasicSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 350, 205, 22));

        jLabelBasicSalary.setText("Basic Salary");
        jPanel1.add(jLabelBasicSalary, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 353, -1, -1));

        jTextFieldFirstName.setEditable(false);
        jTextFieldFirstName.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldFirstName.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldFirstName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFirstNameActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldFirstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 150, 205, 22));

        jLabel5.setText("First Name");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 153, -1, -1));

        jLabel20.setText("Address");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 290, -1, -1));

        jTextAreaAddress.setEditable(false);
        jTextAreaAddress.setBackground(new java.awt.Color(240, 240, 240));
        jTextAreaAddress.setColumns(20);
        jTextAreaAddress.setLineWrap(true);
        jTextAreaAddress.setRows(5);
        jTextAreaAddress.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextAreaAddress.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jScrollPane1.setViewportView(jTextAreaAddress);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 207, 56));

        jLabel18.setText("Positon");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(365, 33, -1, -1));

        jTextFieldPosition.setEditable(false);
        jTextFieldPosition.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.white, java.awt.Color.gray));
        jTextFieldPosition.setDisabledTextColor(new java.awt.Color(51, 51, 51));
        jTextFieldPosition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPositionActionPerformed(evt);
            }
        });
        jPanel1.add(jTextFieldPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(495, 30, 205, 22));

        jPanel3.setBackground(new java.awt.Color(255, 204, 204));

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

        SignOut.setBackground(new java.awt.Color(255, 204, 204));
        SignOut.setText("Sign Out");
        SignOut.setBorder(null);
        SignOut.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SignOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignOutActionPerformed(evt);
            }
        });

        MyProfile.setBackground(new java.awt.Color(255, 153, 153));
        MyProfile.setText("My Profile");
        MyProfile.setBorder(null);
        MyProfile.setHideActionText(true);
        MyProfile.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MyProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MyProfileActionPerformed(evt);
            }
        });

        Payroll.setBackground(new java.awt.Color(255, 204, 204));
        Payroll.setText("Payroll");
        Payroll.setBorder(null);
        Payroll.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Payroll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PayrollActionPerformed(evt);
            }
        });

        LeaveApplication.setBackground(new java.awt.Color(255, 204, 204));
        LeaveApplication.setText("Leave Request");
        LeaveApplication.setBorder(null);
        LeaveApplication.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LeaveApplication.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LeaveApplicationActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(LeaveApplication, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Payroll, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(MyProfile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SignOut, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                    .addComponent(MainMenu)
                    .addComponent(Dashboard, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                    .addComponent(MotorPH))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(MotorPH)
                .addGap(30, 30, 30)
                .addComponent(MainMenu)
                .addGap(18, 18, 18)
                .addComponent(Dashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(MyProfile, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LeaveApplication, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Payroll, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 289, Short.MAX_VALUE)
                .addComponent(SignOut, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(190, 297, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 291, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LeaveApplicationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LeaveApplicationActionPerformed
        try {
            // TODO add your handling code here:
            String[] employeeInformation = sendInformation();
            Employee_LeaveApplication leaveUser = null;
            try {
                leaveUser = new Employee_LeaveApplication(employeeInformation);
            } catch (FileNotFoundException | CsvException ex) {
                Logger.getLogger(Employee_MyProfile.class.getName()).log(Level.SEVERE, null, ex);
            }
            setVisible(false);
            leaveUser.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(Employee_MyProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_LeaveApplicationActionPerformed

    private void PayrollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PayrollActionPerformed
        try {
            // TODO add your handling code here:
            String[] employeeInformation = sendInformation();
            Employee_Payroll payrollUser = null;
            try {
                payrollUser = new Employee_Payroll(employeeInformation);
            } catch (FileNotFoundException | CsvException ex) {
                Logger.getLogger(Employee_MyProfile.class.getName()).log(Level.SEVERE, null, ex);
            }
            setVisible(false);
            payrollUser.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(Employee_MyProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_PayrollActionPerformed

    private void jTextFieldStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldStatusActionPerformed

    private void jTextFieldEmployeeNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldEmployeeNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEmployeeNumActionPerformed

    private void jTextFieldEmployeeNumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEmployeeNumKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEmployeeNumKeyPressed

    private void jTextFieldEmployeeNumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEmployeeNumKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldEmployeeNumKeyTyped

    private void jTextFieldLastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldLastNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLastNameActionPerformed

    private void jTextFieldBirthdayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBirthdayActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBirthdayActionPerformed

    private void jTextFieldBirthdayKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBirthdayKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBirthdayKeyTyped

    private void jTextFieldSSSnumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSSSnumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSSSnumActionPerformed

    private void jTextFieldTINnumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTINnumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTINnumActionPerformed

    private void jTextFieldPhoneNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPhoneNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPhoneNumActionPerformed

    private void jTextFieldPhilhealthNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPhilhealthNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPhilhealthNumActionPerformed

    private void jTextFieldSupervisorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSupervisorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSupervisorActionPerformed

    private void jTextFieldRiceSubsidyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldRiceSubsidyKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldRiceSubsidyKeyTyped

    private void jTextFieldPhoneAllowKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPhoneAllowKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPhoneAllowKeyTyped

    private void jTextFieldClothAllowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldClothAllowActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldClothAllowActionPerformed

    private void jTextFieldClothAllowKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldClothAllowKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldClothAllowKeyTyped

    private void jTextFieldBasicSalaryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBasicSalaryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBasicSalaryActionPerformed

    private void jTextFieldBasicSalaryKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldBasicSalaryKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBasicSalaryKeyTyped

    private void jTextFieldFirstNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFirstNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldFirstNameActionPerformed

    private void jTextFieldPositionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPositionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPositionActionPerformed

    private void DashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DashboardActionPerformed
        // TODO add your handling code here:
        openDashboard();
        resetButtonColors();
        Dashboard.setBackground(new Color(0, 204, 102));
        Dashboard.setForeground(Color.WHITE);
    }//GEN-LAST:event_DashboardActionPerformed

    private void SignOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignOutActionPerformed
        // TODO add your handling code here:
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

    private void MyProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MyProfileActionPerformed
        // TODO add your handling code here:
      
    }//GEN-LAST:event_MyProfileActionPerformed

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
            java.util.logging.Logger.getLogger(Employee_MyProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Employee_MyProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Employee_MyProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Employee_MyProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                try {
                    new Employee_MyProfile(userEmployeeNumber).setVisible(true);
                } catch (FileNotFoundException | CsvException ex) {
                    Logger.getLogger(Employee_MyProfile.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } catch (IOException ex) {
                Logger.getLogger(Employee_MyProfile.class.getName()).log(Level.SEVERE, null, ex);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelBasicSalary;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreaAddress;
    private javax.swing.JTextField jTextFieldBasicSalary;
    private javax.swing.JTextField jTextFieldBirthday;
    private javax.swing.JTextField jTextFieldClothAllow;
    public javax.swing.JTextField jTextFieldEmployeeNum;
    private javax.swing.JTextField jTextFieldFirstName;
    private javax.swing.JTextField jTextFieldLastName;
    private javax.swing.JTextField jTextFieldPagibigNum;
    private javax.swing.JTextField jTextFieldPhilhealthNum;
    private javax.swing.JTextField jTextFieldPhoneAllow;
    private javax.swing.JTextField jTextFieldPhoneNum;
    private javax.swing.JTextField jTextFieldPosition;
    private javax.swing.JTextField jTextFieldRiceSubsidy;
    private javax.swing.JTextField jTextFieldSSSnum;
    private javax.swing.JTextField jTextFieldStatus;
    private javax.swing.JTextField jTextFieldSupervisor;
    private javax.swing.JTextField jTextFieldTINnum;
    // End of variables declaration//GEN-END:variables
}
