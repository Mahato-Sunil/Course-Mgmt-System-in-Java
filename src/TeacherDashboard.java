import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

class TeacherDashboard extends PublicMenu implements ActionListener, DatabaseCredentials {
    // Declare JFrame and other components
    JFrame frame = new JFrame("Teacher DashBoard");
    JPanel panel;
    JLabel heading, TName, TId, search, SName, reg, overview;
    JTextField TNameField, TIdField, SearchField, SNameField, RegField;
    JButton SearchBtn, Update, Reset;

    // Variables to store student name and registration number
    String nameinp, reginp;

    // Components for an editable table displaying course scores and remarks
    JTable editableTable;
    DefaultTableModel editableTableModel;
    String[] editableTableHeading = {"Course", "Score", "Remarks"};
    String editableCourse, editableScore, editableRemarks;
    ArrayList<Object[]> editableList = new ArrayList<>();
    Object[][] editableData;
    JScrollPane editableScrollPane;

    String editedScore, editedRemark;

    // Components for a table displaying student details
    JTable studentTable;
    DefaultTableModel studentDataTableModel;
    String[] studentHeading = {"Registration No. ", "Student's Name", "Contact", "Address"};
    String regNo, studentName, studentContact, studentAddress;
    ArrayList<Object[]> AllStudentList = new ArrayList<>();
    Object[][] studentData;
    JScrollPane studentScrollPane;

    // Get the screen size and set some dimensions
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();
    int screenHeight = (int) screenSize.getHeight();

    Font customFont = new Font(null, Font.PLAIN, 18);
    String teacherEmail;

    // Static block for loading the JDBC driver
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    TeacherDashboard(String id) {
        this.teacherEmail = id;
        setDimensions();
        setMenu();
        setMenuDesign();
        setMenuLogic();
        frame.setJMenuBar(menu);
        addComponent();
        showTeacherData();
        showStudentDetail();
    }

    void setDimensions() {
        panel = new JPanel();
        // Set panel size, layout, and background color
        panel.setSize(1450, 750);
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(180, 6, 100));
        panel.setLocation(40, 10);

        heading = new JLabel("Teacher Dashboard");
        // Set heading label properties
        heading.setBounds(30, 10, 300, 50);
        heading.setFont(new Font(null, Font.BOLD, 30));
        heading.setForeground(Color.BLUE);

        TName = new JLabel("Name : ");
        // Set label for teacher name
        TName.setBounds(450, 15, 100, 50);
        TName.setFont(customFont);

        TNameField = new JTextField();
        // Set text field for teacher name
        TNameField.setBounds(580, 15, 250, 50);
        TNameField.setEditable(false);
        TNameField.setFont(customFont);

        TId = new JLabel("Teacher ID :");
        // Set label for teacher ID
        TId.setBounds(1000, 15, 200, 50);
        TId.setFont(customFont);

        TIdField = new JTextField();
        // Set text field for teacher ID
        TIdField.setBounds(1150, 15, 250, 50);
        TIdField.setEditable(false);
        TIdField.setFont(customFont);

        search = new JLabel("Search Student Details :-");
        // Set label for searching student details
        search.setBounds(30, 80, 250, 50);
        search.setFont(new Font(null, Font.BOLD, 20));

        SearchField = new JTextField("6-2-32-176-");
        // Set text field for entering the student registration number
        SearchField.setBounds(30, 140, 250, 50);
        SearchField.setFont(customFont);

        SearchBtn = new JButton("Search ");
        // Set search button
        SearchBtn.setBounds(30, 200, 150, 50);
        SearchBtn.setFocusable(false);
        SearchBtn.addActionListener(this);

        SName = new JLabel("Name :-");
        // Set label for displaying student name
        SName.setBounds(30, 260, 100, 50);
        SName.setFont(customFont);

        SNameField = new JTextField();
        // Set text field for displaying student name
        SNameField.setBounds(150, 260, 250, 50);
        SNameField.setFont(customFont);
        SNameField.setEditable(false);

        reg = new JLabel("Reg. No. :-");
        // Set label for displaying student registration number
        reg.setBounds(30, 320, 100, 50);
        reg.setFont(customFont);

        RegField = new JTextField();
        // Set text field for displaying student registration number
        RegField.setBounds(150, 320, 250, 50);
        RegField.setFont(customFont);
        RegField.setEditable(false);

        overview = new JLabel("Overview :-");
        // Set label for overview
        overview.setBounds(750, 80, 200, 50);
        overview.setFont(new Font(null, Font.BOLD, 22));

        Update = new JButton("Update");
        // Set update button
        Update.setBounds(170, 620, 150, 50);
        Update.setFocusable(false);
        Update.addActionListener(this);

        Reset = new JButton("Reset");
        // Set reset button
        Reset.setBounds(350, 620, 150, 50);
        Reset.setFocusable(false);
        Reset.addActionListener(this);

        studentDataTableModel = new DefaultTableModel(studentData, studentHeading);
        studentTable = new JTable(studentDataTableModel);
        studentTable.setRowHeight(50);
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        studentScrollPane = new JScrollPane(studentTable);
        studentScrollPane.setBounds(750, 130, 600, 550);

        editableTableModel = new DefaultTableModel(editableData, editableTableHeading);
        editableTable = new JTable(editableTableModel);
        editableTable.setRowHeight(40);
        editableTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        editableScrollPane = new JScrollPane(editableTable);
        editableScrollPane.setBounds(40, 390, 550, 200);

        frame.setSize(screenWidth, screenHeight);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    void addComponent() {
        // Add components to the panel and the panel to the frame
        panel.add(heading);
        panel.add(TName);
        panel.add(TNameField);
        panel.add(TId);
        panel.add(TIdField);
        panel.add(SName);
        panel.add(SNameField);
        panel.add(reg);
        panel.add(RegField);
        panel.add(search);
        panel.add(SearchField);
        panel.add(SearchBtn);
        panel.add(overview);
        panel.add(Update);
        panel.add(Reset);
        panel.add(studentScrollPane);
        panel.add(editableScrollPane);
        frame.add(panel);
    }

    void showTeacherData() {
        // Retrieve and display teacher data
        String query = "SELECT FirstName, LastName, TeacherId FROM teacherRegistration where Email = ?";
        try {
            Connection connect = DriverManager.getConnection(teacherUrl, username, password);
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, teacherEmail);
            ResultSet resultset = statement.executeQuery();

            while (resultset.next()) {
                String firstname = resultset.getString("FirstName");
                String lastname = resultset.getString("LastName");
                TNameField.setText(String.join(" ", firstname, lastname));
                TIdField.setText(resultset.getString("TeacherId"));
            }
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void showStudentDetail() {
        // Retrieve and display student details
        String query = "SELECT *FROM studentregistration";
        try {
            Connection connect = DriverManager.getConnection(studentUrl, username, password);
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String firstname = resultSet.getString("Firstname");
                String lastname = resultSet.getString("Lastname");
                studentName = String.join(" ", firstname, lastname);
                regNo = resultSet.getString("Registration");
                studentContact = resultSet.getString("Contact");
                studentAddress = resultSet.getString("Address");

                AllStudentList.add(new Object[]{regNo, studentName, studentContact, studentAddress});
            }
            studentData = AllStudentList.toArray(new Object[0][]);
            studentDataTableModel.setDataVector(studentData, studentHeading);
            studentDataTableModel.fireTableDataChanged();
            connect.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle button actions
        if (e.getSource().equals(Reset)) resetData();
        if (e.getSource().equals(SearchBtn)) {
            String searchKey = SearchField.getText();
            if (searchKey.equals("6-2-32-176-"))
                JOptionPane.showMessageDialog(null, "Please Enter the Complete Registration Number of Student!");
            else searchData(searchKey);
        }
        if (e.getSource().equals(Update)) {
            String searchKey = RegField.getText();
            if (searchKey.isEmpty()) JOptionPane.showMessageDialog(null, "Student Is not Selected !");
            else updateData(searchKey);
        }
    }

    void resetData() {
        // Reset input fields and editable data
        SearchField.setText(null);
        SNameField.setText(null);
        RegField.setText(null);
        SearchField.setText("6-2-32-176-");
        editableList.clear();
        editableData = new Object[0][];
        editableTableModel.setDataVector(editableData, editableTableHeading);
    }

    void searchData(String searchKey) {
        // Search for a student and display their details
        boolean matched = false;
        for (Object[] isMatchedData : AllStudentList) {
            reginp = isMatchedData[0].toString();
            nameinp = isMatchedData[1].toString();
            if (reginp.equals(searchKey)) {
                matched = true;
                break;
            }
        }

        if (matched) {
            SNameField.setText(nameinp);
            RegField.setText(searchKey);
            showSearchedStudent(searchKey);
        } else {
            JOptionPane.showMessageDialog(null, "Sorry! The student is not Registered in the College Database!");
        }
    }

    void updateData(String searchKey) {
//        getting the value of selected row
        int selectedRow = editableTable.getSelectedRow();
        if (selectedRow != -1) {
            // Check if a row is selected (selectedRow will be -1 if no row is selected)
            Object courseColumn = editableTable.getValueAt(selectedRow, 0);
            Object scoreColumn = editableTable.getValueAt(selectedRow, 1);
            Object remarksColumn = editableTable.getValueAt(selectedRow, 2);
            editPanel(courseColumn.toString(), scoreColumn.toString(), remarksColumn.toString(), searchKey);   // passing the value to method
        } else {
            // Handle the case where no row is selected
            JOptionPane.showMessageDialog(null, "Please select a data first.");
        }
    }

    // panel to show the input dialog box
    void editPanel(String courseColumn, String scoreColumn, String remarksColumn, String searchKey) {

        JComboBox<String> remarks;
        String[] option = {"COMPLETED", "PENDING"};
        remarks = new JComboBox<>(option);
        if (remarksColumn.equalsIgnoreCase("COMPLETED")) remarks.setSelectedItem(option[0]);
        else remarks.setSelectedItem(option[1]);

        JTextField score = new JTextField(scoreColumn, 20);

        // show panel
        JPanel editPanel = new JPanel();
        editPanel.setLayout(new GridLayout(0, 1));
        editPanel.add(new JLabel("Course : " + courseColumn));
        editPanel.add(new JLabel("\n"));
        editPanel.add(new JLabel("Score : "));
        editPanel.add(score);
        editPanel.add(new JLabel("\n"));
        editPanel.add(new JLabel("\nRemarks : \n"));
        editPanel.add(remarks);
        editPanel.add(new JLabel("\n"));

        // handle the operation
        int result = JOptionPane.showConfirmDialog(null, editPanel, "Updata Data", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // User clicked OK
            // Retrieve data from the JComboBox components in your custom panel
            editedScore = score.getText();
            editedRemark = (String) remarks.getSelectedItem();
            String insertRemark = (editedRemark.equals("COMPLETED")) ? "True" : "False";

            // storing the data to database
            String query = "UPDATE studentscore SET Score =?, isComplete = ? where Registration = ?";

            try {
                Connection connect = DriverManager.getConnection(studentUrl, username, password);
                PreparedStatement statement = connect.prepareStatement(query);
                statement.setString(1, editedScore);
                statement.setString(2, insertRemark);
                statement.setString(3, searchKey);

                int rowUpdated = statement.executeUpdate();
                if (rowUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Data Update Successful");
                    AllStudentList.clear();
                    studentData = new Object[0][];
                    studentDataTableModel.setDataVector(studentData, studentHeading);
                    showStudentDetail();
                    editableList.clear();
                    editableData = new Object[0][];
                    editableTableModel.setDataVector(editableData, editableTableHeading);
                    showSearchedStudent(searchKey);
                } else {
                    JOptionPane.showMessageDialog(null, "Sorry, Couldn't Update Data");
                }
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(editedRemark);
            System.out.println(editedScore);
        } else {
           JOptionPane.showMessageDialog(null, "Process Cancelled By User");
        }
    }

    // showing the searched student data
    void showSearchedStudent(String searchKey) {
        String query = "SELECT * from studentscore where Registration = ?";
        try {
            Connection connect = DriverManager.getConnection(studentUrl, username, password);
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, searchKey);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                editableScore = resultSet.getString("Score");
                editableCourse = resultSet.getString("Course");
                editableRemarks = (resultSet.getString("isComplete").equalsIgnoreCase("True")) ? "COMPLETED" : "PENDING";

                editableList.add(new Object[]{editableCourse, editableScore, editableRemarks});
            }
            editableData = editableList.toArray(new Object[0][]);
            editableTableModel.setDataVector(editableData, editableTableHeading);
            editableTableModel.fireTableDataChanged();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
