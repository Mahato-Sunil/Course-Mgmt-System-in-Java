import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

class TeacherDashboard extends publicMenu implements ActionListener, DatabaseCredentials {
    // initializing the global variables
    JFrame frame = new JFrame("Teacher DashBoard");
    JPanel panel;
    JLabel heading, TName, TId, search, SName, reg, course, score, overview;
    JTextField TNameField, TIdField, SearchField, SNameField, RegField, CourseField, ScoreField; // search field
    JButton SearchBtn, Update, Reset;
    JCheckBox isCompleteBox;

    String nameinp, reginp, courseinp, scoreinp, completeinp;   // to store the details of the searched student

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
        addComponent();
        showTeacherData();
        showStudentDetail();
        setMenu();
        setMenuDesign();
        setMenuLogic();
        frame.setJMenuBar(menu);
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

        isCompleteBox = new JCheckBox("Completed");
        isCompleteBox.setBounds(500, 260, 100, 50);
        isCompleteBox.setFont(customFont);

        reg = new JLabel("Reg. No. :-");
        reg.setBounds(30, 320, 100, 50);
        reg.setFont(customFont);

        RegField = new JTextField();
        RegField.setBounds(150, 320, 250, 50);
        RegField.setFont(customFont);

        course = new JLabel("Course :-");
        course.setBounds(30, 380, 150, 50);
        course.setFont(customFont);

        CourseField = new JTextField();
        CourseField.setBounds(150, 380, 250, 50);
        CourseField.setFont(customFont);

        score = new JLabel("Score :-");
        score.setBounds(30, 440, 100, 50);
        score.setFont(customFont);

        ScoreField = new JTextField();
        ScoreField.setBounds(150, 440, 250, 50);
        ScoreField.setFont(customFont);

        overview = new JLabel("Overview :-");
        overview.setBounds(750, 80, 200, 50);
        overview.setFont(new Font(null, Font.BOLD, 22));


        Update = new JButton("Update");
        Update.setBounds(170, 550, 150, 50);
        Update.setFocusable(false);
        Update.addActionListener(this);

        Reset = new JButton("Reset");
        Reset.setBounds(350, 550, 150, 50);
        Reset.setFocusable(false);
        Reset.addActionListener(this);

        // dimension for table
        studentDataTableModel = new DefaultTableModel(studentData, studentHeading);
        studentTable = new JTable(studentDataTableModel);
        studentTable.setRowHeight(50);
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        studentScrollPane = new JScrollPane(studentTable);
        studentScrollPane.setBounds(750, 130, 600, 500);

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
        panel.add(course);
        panel.add(CourseField);
        panel.add(score);
        panel.add(ScoreField);
        panel.add(reg);
        panel.add(RegField);
        panel.add(search);
        panel.add(SearchField);
        panel.add(SearchBtn);
        panel.add(overview);
        panel.add(Update);
        panel.add(Reset);
        panel.add(isCompleteBox);
        panel.add(studentScrollPane);
        frame.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // search button
        if (e.getSource().equals("SearchBtn")) {
            String searchKey = SearchField.getText();
            if (searchKey.isEmpty()) {
                System.out.println(searchKey);
                JOptionPane.showMessageDialog(null, "Please Enter Student Registration Number !");
            } else searchDetail(searchKey);
        }

        // reset button
        if (e.getSource().equals("Reset")) {
            resetData();
        }

        //        getting values
        nameinp = SNameField.getText();
        reginp = RegField.getText();
        courseinp = CourseField.getText();
        scoreinp = ScoreField.getText();
        completeinp = (isCompleteBox.isSelected()) ? "True" : "False";

        // checking for empty field
        if (nameinp.isEmpty() || reginp.isEmpty() || courseinp.isEmpty() || scoreinp.isEmpty())
            JOptionPane.showMessageDialog(null, "Please fill empty fields !");
        else {
            // update button
            if (e.getSource().equals("Update")) {
                updateData();
            }
        }

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
            connect.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
    }

    // function to search the detals of the student
    void searchDetail(String searchKey) {
        String query = "SELECT Score, Registration, Course, isComplete FROM studentscore  where Registration = ?";
        String query0 = "SELECT Firstname, Lastname from studentregistration  where Registration = ?";
        try {
            Connection connect = DriverManager.getConnection(studentUrl, username, password);
            Connection connect0 = DriverManager.getConnection(studentUrl, username, password);
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, searchKey);

            PreparedStatement statement0 = connect0.prepareStatement(query0);
            statement0.setString(1, searchKey);
            ResultSet resultSet = statement.executeQuery();
            ResultSet resultSet0 = statement0.executeQuery();

            while (resultSet.next()) // from student score
            {
                ScoreField.setText(resultSet.getString("Score"));
                RegField.setText(resultSet.getString("Registration"));
                CourseField.setText(resultSet.getString("Course"));
                String isComplete = resultSet.getString("isComplete");
                isCompleteBox.setSelected(isComplete.equals("True"));
            }
            while (resultSet0.next())        // from student registration
            {
                String firstname = resultSet0.getString("Firstname");
                String lastname = resultSet0.getString("Lastname");
                SNameField.setText(String.join(" ", firstname, lastname));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // function to reset data
    void resetData() {
        SearchBtn.setText("");
        SNameField.setText("");
        RegField.setText("");
        ScoreField.setText("");
        CourseField.setText("");
        isCompleteBox.setSelected(false);
    }

    //function to update data
    void updateData() {
        String query = "UPDATE studentscore SET Score=?, isComplete = ? WHERE Registration = ?";
        try {
            Connection connect = DriverManager.getConnection(studentUrl, username, password);
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, scoreinp);
            statement.setString(2, completeinp);
            statement.setString(3, reginp);
            int resultset = statement.executeUpdate();
            if (resultset > 0) {
                JOptionPane.showMessageDialog(null, "Data Updated Sucessfully");
                resetData();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        new TeacherDashboard("manoj@gmail.com");
    }
}