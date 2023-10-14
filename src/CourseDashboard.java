import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class CourseDashboard extends publicMenu implements ActionListener, DatabaseCredentials {
    // instantiating the componenets
    JFrame frame = new JFrame("Course Dashboard");
    JPanel panel;
    JLabel heading, courseName, courseId, courseCredit, courseAssess, search;
    JButton create, update, delete, reset, searchBtn;
    JTextField courseNameField, courseIdField, courseCreditField, searchField;

    JRadioButton Theory, Practical;
    Font customFont = new Font(null, Font.PLAIN, 20);

    JRadioButton isEditable;     // for locking and unlocking the menus

    // getting the whole sizez of the page
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();
    int screenHeight = (int) screenSize.getHeight();

    // default object for  showing course table
    JTable courseTable;
    DefaultTableModel courseTableModel;
    String[] courseHeading = {"ID", "COURSE NAME", "CR. HOUR", "ASSESSMENT"};  // for showing availabel courses heading
    Object[][] courseData;
    JLabel TableHeading = new JLabel("Available Courses ");
    JScrollPane courseScrollPane;

    String tCourse, tCourseId, tCourseAssessment;
    Double tCourseCr;

    ArrayList<Object> AllCourseList = new ArrayList<>();

    String tempCourse, tempCourseId, tempCourseAssessment;
    Double tempCourseCr;

    // database connection
    Connection connect = null;
    String key;     // search key

    // manage class constructor
    CourseDashboard() {
        setDimension();
        addComp();
        showCourseData();
        setMenu();
        setMenuDesign();
        setMenuLogic();
        frame.setJMenuBar(menu);
    }

    //    function to set the components
    public void setDimension() {
        panel = new JPanel();
        panel.setSize(1450, 750);
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(180, 6, 100));
        panel.setLocation(40, 10);

        // setting the dimension of the actual components
        heading = new JLabel("Course Dashboard");
        heading.setBounds(50, 10, 600, 50);
        heading.setFont(new Font(null, Font.BOLD, 35));
        heading.setForeground(Color.BLACK);

        courseName = new JLabel("Course Name :-");      // label for course name
        courseName.setBounds(60, 100, 150, 40);
        courseName.setFont(customFont);

        courseNameField = new JTextField();                // text field for course name
        courseNameField.setBounds(60, 150, 400, 45);
        courseNameField.setEditable(false);

        courseId = new JLabel("Course ID :-");      // course id
        courseId.setBounds(60, 200, 150, 40);
        courseId.setFont(customFont);

        courseIdField = new JTextField();
        courseIdField.setBounds(60, 250, 400, 45);
        courseIdField.setEditable(false);

        courseCredit = new JLabel(" Credit Hour :-");       // for course credit hour
        courseCredit.setBounds(60, 300, 350, 40);
        courseCredit.setFont(customFont);

        courseCreditField = new JTextField();
        courseCreditField.setBounds(60, 350, 400, 45);
        courseCreditField.setEditable(false);

        courseAssess = new JLabel(" Assessment Mode :-");       // for assesment
        courseAssess.setBounds(60, 400, 200, 40);
        courseAssess.setFont(customFont);

        Theory = new JRadioButton("Theory");        // checkbox 1
        Theory.setBounds(80, 450, 150, 50);
        Theory.setFont(customFont);
        Theory.setFocusable(false);
        Theory.setEnabled(false);
        Theory.setSelected(true);

        Practical = new JRadioButton("Practical");        // checkbox 2
        Practical.setBounds(250, 450, 150, 50);
        Practical.setFont(customFont);
        Practical.setFocusable(false);
        Practical.setEnabled(false);

        search = new JLabel(" Search By ID :- ");
        search.setBounds(60, 530, 200, 40);
        search.setFont(customFont);

        searchField = new JTextField();
        searchField.setBounds(250, 530, 250, 45);

        searchBtn = new JButton(" Search");
        searchBtn.setBounds(60, 585, 180, 50);
        searchBtn.setBackground(new Color(204, 255, 255));
        searchBtn.addActionListener(this);
        searchBtn.setFocusable(false);
        searchBtn.addActionListener(this);

        create = new JButton("CREATE");
        create.setBounds(800, 640, 145, 50);
        create.addActionListener(this);
        create.setFocusable(false);
        create.setEnabled(false);

        update = new JButton("UPDATE");
        update.setBounds(950, 640, 145, 50);
        update.addActionListener(this);
        update.setFocusable(false);
        update.setEnabled(false);

        delete = new JButton("DELETE");
        delete.setBounds(1100, 640, 145, 50);
        delete.addActionListener(this);
        delete.setFocusable(false);
        delete.setEnabled(false);

        reset = new JButton("RESET");
        reset.setBounds(1250, 640, 145, 50);
        reset.addActionListener(this);
        reset.setFocusable(false);
        reset.setEnabled(false);

        // Initialize the DefaultTableModel for the course table
        // heading for the course table
        TableHeading.setBounds(800, 80, 250, 50);
        TableHeading.setFont(new Font(null, Font.BOLD, 22));
        TableHeading.setForeground(Color.GREEN);

        courseTableModel = new DefaultTableModel(courseData, courseHeading);
        courseTable = new JTable(courseTableModel);
        courseTable.setRowHeight(50);
        courseTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        courseScrollPane = new JScrollPane(courseTable);
        courseScrollPane.setBounds(800, 150, 550, 300);

        isEditable = new JRadioButton("Unlock Interface ");
        isEditable.setBounds(800, 580, 200, 50);
        isEditable.setFocusable(false);
        isEditable.setSelected(false);
        isEditable.addActionListener(this);

        frame.setSize(screenWidth, screenHeight);
        frame.setLayout(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void addComp() {
        panel.add(heading);
        panel.add(courseName);
        panel.add(courseId);
        panel.add(courseCredit);
        panel.add(courseAssess);
        panel.add(search);
        panel.add(Theory);
        panel.add(Practical);
        panel.add(searchBtn);
        panel.add(courseNameField);
        panel.add(courseIdField);
        panel.add(courseCreditField);
        panel.add(searchField);
        panel.add(create);
        panel.add(update);
        panel.add(delete);
        panel.add(reset);
        panel.add(courseScrollPane);
        panel.add(TableHeading);
        panel.add(isEditable);
        frame.add(panel);
    }

    //  event handlere
    @Override
    public void actionPerformed(ActionEvent e) {
        // event handler for search button
        if (e.getSource().equals(searchBtn)) {
            key = searchField.getText();
            if (key.isEmpty()) JOptionPane.showMessageDialog(null, "Enter the search key first !");
            else searchResult(key);
        }
        // event handler for the isEditable button
        if (e.getSource().equals(isEditable)) {
            // checking for isEditable
            if (isEditable.isSelected()) {      // if true
                // function to perform when the interface is unlocked
                isEditable.setText("Lock Interface ");

                //setting the text field as editable
                courseNameField.setEditable(true);
                courseIdField.setEditable(true);
                courseCreditField.setEditable(true);
                Theory.setEnabled(true);
                Practical.setEnabled(true);
                create.setEnabled(true);
                update.setEnabled(true);
                delete.setEnabled(true);
                reset.setEnabled(true);

            } else {
                isEditable.setText("Unlock Interface ");
                // function to perform when the interface is locked
                courseNameField.setEditable(false);
                courseIdField.setEditable(false);
                courseCreditField.setEditable(false);
                Theory.setEnabled(false);
                Practical.setEnabled(false);
                create.setEnabled(false);
                update.setEnabled(false);
                delete.setEnabled(false);
                reset.setEnabled(false);
            }
        }

        //getting the value of the text string
        tempCourse = courseNameField.getText();
        tempCourseId = courseIdField.getText();
        String courseCreditText = courseCreditField.getText();
        tempCourseAssessment = (Theory.isSelected()) ? "TH" : "PR";

        // reset button
        if (e.getSource().equals(reset)) {
            courseNameField.setText(null);
            courseCreditField.setText(null);
            courseIdField.setText(null);
            Theory.setSelected(true);
            Practical.setSelected(false);
            searchField.setText(null);
        }

        //checking for empty fields
        boolean isFieldEmpty = (tempCourse.isEmpty() || tempCourseId.isEmpty() || courseCreditText.isEmpty());
        try {

            if (e.getSource().equals(create)) {
                if (isFieldEmpty) JOptionPane.showMessageDialog(null, "Please Enter all the values.");
                else {
                    tempCourseCr = Double.parseDouble(courseCreditText);
                    addCourseData();
                }

            }
            if (e.getSource().equals(update)) {
                if (isFieldEmpty) JOptionPane.showMessageDialog(null, "Please Enter all the values.");
                else {
                    tempCourseCr = Double.parseDouble(courseCreditText);
                    updateCourseData();
                }
            }
            if (e.getSource().equals(delete)) {
                if (tempCourseId.isEmpty()) JOptionPane.showMessageDialog(null, "Please Enter  the ID of Course !");
                else {
                    deleteCourseData();
                }
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid Credit Hour value.");
        }
    }

    // method to show the course details in the table
    void showCourseData() {
        String query = "SELECT * FROM Course";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection(courseUrl, username, password); // course databse
            // from course table
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            // data from  course database
            while (resultSet.next()) {
                tCourseId = resultSet.getString("Id");
                tCourse = resultSet.getString("Course");
                tCourseCr = Double.parseDouble(resultSet.getString("Credit"));
                tCourseAssessment = resultSet.getString("Assessment");
                AllCourseList.add(new Object[]{tCourseId, tCourse, tCourseCr, tCourseAssessment}); // for all course list
            }
            // Update table models with new data
            courseData = AllCourseList.toArray(new Object[0][]);
            courseTableModel.setDataVector(courseData, courseHeading);
            connect.close();
        } catch (Exception err) {
            System.out.println("Error : " + err);
        }
    }

    // function to add course to the database
    void addCourseData() {
        String query = "INSERT INTO course(Id, Course, Credit, Assessment) VALUES (?,?,?,?)";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection(courseUrl, username, password); // course databse
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, tempCourseId);
            statement.setString(2, tempCourse);
            statement.setDouble(3, tempCourseCr);
            statement.setString(4, tempCourseAssessment);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Course Added Successfully");
            }
            connect.close();
        } catch (ClassNotFoundException | SQLException err) {
            JOptionPane.showMessageDialog(null, "Server Error ! Failed to Establish Connection. ");
        }
    }

    // function to update the data base
    void updateCourseData() {
        String query = "UPDATE course SET Course = ?, Credit = ?, Assessment = ? WHERE Id = ?";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection(courseUrl, username, password); // course database
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, tempCourse);
            statement.setDouble(2, tempCourseCr);
            statement.setString(3, tempCourseAssessment);
            statement.setString(4, tempCourseId);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Course Updated Successfully");

            } else {
                JOptionPane.showMessageDialog(null, "No course found with this ID.");
            }
            connect.close();
        } catch (ClassNotFoundException | SQLException err) {
            JOptionPane.showMessageDialog(null, "Server Error! Failed to Establish Connection.");
        }
    }

    // function to delete the course in data base
    void deleteCourseData() {
        String query = "DELETE FROM course WHERE Id = ?";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection(courseUrl, username, password); // course database
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, tempCourseId);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Course Deleted Successfully");
            } else {
                JOptionPane.showMessageDialog(null, "No course found with this ID.");
            }
            connect.close();
        } catch (ClassNotFoundException | SQLException err) {
            JOptionPane.showMessageDialog(null, "Server Error! Failed to Establish Connection.");
        }
    }

    // displaying the course content when searched for the id
    void searchResult(String key) {
        String searchQuery = "SELECT * FROM course where ID = ?";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection(courseUrl, username, password); // course database
            PreparedStatement statement = connect.prepareStatement(searchQuery);
            statement.setString(1, key);

            ResultSet rowSearch = statement.executeQuery();
            // data from  course database
            if (rowSearch.next()) {
                courseIdField.setText(rowSearch.getString("Id"));
                courseNameField.setText(rowSearch.getString("Course"));
                courseCreditField.setText(rowSearch.getString("Credit"));
                String tempCourseCredit = rowSearch.getString("Assessment");
                if (tempCourseCredit.equals("Th")) {
                    Theory.setSelected(true);
                    Practical.setSelected(false);
                } else if (tempCourseCredit.equals("Th/Pr")) {
                    Theory.setSelected(true);
                    Practical.setSelected(true);
                } else {
                    Theory.setSelected(false);
                    Practical.setSelected(true);
                }
            } else JOptionPane.showMessageDialog(null, "Sorry Course doesn't Exist! \n Please  Recheck the ID !");

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}