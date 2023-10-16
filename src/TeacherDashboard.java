import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

class TeacherDashboard extends publicMenu implements ActionListener, DatabaseCredentials {
    // initializing the global variables
    JFrame frame = new JFrame("Teacher DashBoard");
    JPanel panel;
    JLabel heading, TName, TId, search, SName, reg, overview;
    JTextField TNameField, TIdField, SearchField, SNameField, RegField;
    JButton SearchBtn, Update, Reset;

    String nameinp, reginp;   // to store the details of the searched student
    // Define custom cell editor for the editable columns

    TableCellEditor editableCellEditor;
    // Define a JComboBox for "Remarks" column
    JComboBox<String> remarksComboBox;

    // Define custom cell editor for "Remarks" column
    TableCellEditor remarksCellEditor;
    JTable editableTable; //  editable table to show the detail of searched student
    DefaultTableModel editableTableModel;
    String[] editableTableHeading = {"Course", "Score", "Remarks"};
    String editableCourse, editableScore, editableRemarks;
    ArrayList<Object[]> editableList = new ArrayList<>();
    Object[][] editableData;
    JScrollPane editableScrollPane;

    // default object for  showing studnet  table
    JTable studentTable;
    DefaultTableModel studentDataTableModel;
    String[] studentHeading = {"Registration No. ", "Student's Name", "Contact", "Address"};  // for showing availabel student heading
    String regNo, studentName, studentContact, studentAddress;
    ArrayList<Object[]> AllStudentList = new ArrayList<>();
    Object[][] studentData;
    JScrollPane studentScrollPane;

    // getting the whole sizez of the page
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();
    int screenHeight = (int) screenSize.getHeight();

    //    font for the overall text
    Font customFont = new Font(null, Font.PLAIN, 18);

    String teacherEmail;    // retrieved from the login page

    // Static block to initialize static fields
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    TeacherDashboard(String id) { // id refers to the email
        this.teacherEmail = id;  // passing the value
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
        panel.setSize(1450, 750);
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(180, 6, 100));
        panel.setLocation(40, 10);

        // setting the dimension of the actual components
        heading = new JLabel("Teacher Dashboard");
        heading.setBounds(30, 10, 300, 50);
        heading.setFont(new Font(null, Font.BOLD, 30));
        heading.setForeground(Color.BLUE);

        TName = new JLabel("Name : ");
        TName.setBounds(450, 15, 100, 50);
        TName.setFont(customFont);

        TNameField = new JTextField();
        TNameField.setBounds(580, 15, 250, 50);
        TNameField.setEditable(false);
        TNameField.setFont(customFont);

        TId = new JLabel("Teacher ID :");
        TId.setBounds(1000, 15, 200, 50);
        TId.setFont(customFont);

        TIdField = new JTextField();
        TIdField.setBounds(1150, 15, 250, 50);
        TIdField.setEditable(false);
        TIdField.setFont(customFont);

        search = new JLabel("Search Student Details :-");
        search.setBounds(30, 80, 250, 50);
        search.setFont(new Font(null, Font.BOLD, 20));

        SearchField = new JTextField("6-2-32-176-");
        SearchField.setBounds(30, 140, 250, 50);
        SearchField.setFont(customFont);

        SearchBtn = new JButton("Search ");
        SearchBtn.setBounds(30, 200, 150, 50);
        SearchBtn.setFocusable(false);
        SearchBtn.addActionListener(this);

        // student details
        SName = new JLabel("Name :-");
        SName.setBounds(30, 260, 100, 50);
        SName.setFont(customFont);

        SNameField = new JTextField();
        SNameField.setBounds(150, 260, 250, 50);
        SNameField.setFont(customFont);
        SNameField.setEditable(false);

        reg = new JLabel("Reg. No. :-");
        reg.setBounds(30, 320, 100, 50);
        reg.setFont(customFont);

        RegField = new JTextField();
        RegField.setBounds(150, 320, 250, 50);
        RegField.setFont(customFont);
        RegField.setEditable(false);

        overview = new JLabel("Overview :-");
        overview.setBounds(750, 80, 200, 50);
        overview.setFont(new Font(null, Font.BOLD, 22));

        Update = new JButton("Update");
        Update.setBounds(170, 620, 150, 50);
        Update.setFocusable(false);
        Update.addActionListener(this);

        Reset = new JButton("Reset");
        Reset.setBounds(350, 620, 150, 50);
        Reset.setFocusable(false);
        Reset.addActionListener(this);

        // dimension for table
        studentDataTableModel = new DefaultTableModel(studentData, studentHeading);
        studentTable = new JTable(studentDataTableModel);
        studentTable.setRowHeight(50);
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        studentScrollPane = new JScrollPane(studentTable);
        studentScrollPane.setBounds(750, 130, 600, 550);

        // dimension for editable table
        editableTableModel = new DefaultTableModel(editableData, editableTableHeading);
        editableTable = new JTable(editableTableModel);
        editableTable.setRowHeight(40);
        editableTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        editableScrollPane = new JScrollPane(editableTable);
        editableScrollPane.setBounds(40, 390, 550, 200);

        // Create a DefaultCellEditor for text fields (editable columns)
        editableCellEditor = new DefaultCellEditor(new JTextField());
        // Create a JComboBox with options
        String[] remarksOptions = {"COMPLETED", "PENDING"};
        remarksComboBox = new JComboBox<>(remarksOptions);

        // Create a custom cell editor for JComboBox
        remarksCellEditor = new DefaultCellEditor(remarksComboBox);

        // Set custom cell editor for "Remarks" column

        // Set custom cell editor for "Score" and "Remarks" columns
        editableTable.getColumnModel().getColumn(1).setCellEditor(editableCellEditor);
        editableTable.getColumnModel().getColumn(2).setCellEditor(remarksCellEditor);

        frame.setSize(screenWidth, screenHeight);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // adding the components
    void addComponent() {
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

    // show teacher detail
    void showTeacherData() {
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

    //    show student details in the table
    void showStudentDetail() {
        String query = "SELECT *FROM studentregistration";
        try {
            Connection connect = DriverManager.getConnection(studentUrl, username, password); // course databse
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            // from course table
            while (resultSet.next()) {
                String firstname = resultSet.getString("Firstname");
                String lastname = resultSet.getString("Lastname");
                studentName = String.join(" ", firstname, lastname);
                regNo = resultSet.getString("Registration");
                studentContact = resultSet.getString("Contact");
                studentAddress = resultSet.getString("Address");

                AllStudentList.add(new Object[]{regNo, studentName, studentContact, studentAddress});   //for all student list
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
        if (e.getSource().equals(Reset)) resetData();

        // search field
        if (e.getSource().equals(SearchBtn)) {
            String searchKey = SearchField.getText();
            if (searchKey.equals("6-2-32-176-"))
                JOptionPane.showMessageDialog(null, "Please Enter the Complete Registration Number  of Student ! ");
            else searchData(searchKey);
        }
    }

    // reset function
    void resetData() {
        SearchField.setText(null);
        SNameField.setText(null);
        RegField.setText(null);
        SearchField.setText("6-2-32-176-");
        editableList.clear();
        editableData = new Object[0][];
        editableTableModel.setDataVector(editableData, editableTableHeading);
    }

    // void search field function
    void searchData(String searchKey) {
        boolean matched = false;
        // retrieving the data of student
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

            // for other details
            String query = "SELECT *from studentscore where Registration = ?";
            try {
                Connection connect = DriverManager.getConnection(studentUrl, username, password);
                PreparedStatement statement = connect.prepareStatement(query);
                statement.setString(1, searchKey);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    editableScore = resultSet.getString("Score");
                    editableCourse = resultSet.getString("Course");
                    editableRemarks = (resultSet.getString("isComplete").equalsIgnoreCase("True")) ? "COMPLETED" : "PENDING";

                    // adding to arraylist
                    editableList.add(new Object[]{editableCourse, editableScore, editableRemarks});
                }
                editableData = editableList.toArray(new Object[0][]);
                editableTableModel.setDataVector(editableData, editableTableHeading);
                editableTableModel.fireTableDataChanged();
                connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else JOptionPane.showMessageDialog(null, "Sorry ! The student is not Registered in College Database !");
    }

    // function to update the details of student
    void updateData(String searchKey) {
        String editedScore = (String) editableTable.getValueAt(0, 1);
        String editedRemark = (String) editableTable.getValueAt(0, 2);

        String query = "UPDATE studentscore SET Score =?, isComplete = ?  where Registration = ?";
        try {
            Connection connect = DriverManager.getConnection(studentUrl, username, password);
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, editedScore);
            statement.setString(2, editedRemark);
            statement.setString(3, searchKey);

            int rowUpdated = statement.executeUpdate();
            if (rowUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Data Update Successfull");
                AllStudentList.clear();
                studentData = new Object[0][];
                studentDataTableModel.setDataVector(studentData, studentHeading);
            } else {
                JOptionPane.showMessageDialog(null, "Sorry Couldn't Update Data");
            }
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new TeacherDashboard("manoj@gmail.com");
    }
}