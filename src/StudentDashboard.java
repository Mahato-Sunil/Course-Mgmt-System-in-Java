import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.*;
import java.util.ArrayList;

public class StudentDashboard extends MouseAdapter implements ActionListener, DatabaseCredentials {
    JFrame frame = new JFrame("Student Dashboard");

    JLabel heading, name, contact, email, address, regno, enroll, searchC, toEnroll;
    JTextField oname, ocontact, oemail, oaddress, oreg, searchField;
    JPanel panel;
    JButton searchBtn, enrollBtn, reset;
    JLabel message1, message2;
    String key;

    //  string for the courses -> combo box
    JComboBox<String> enrollCourselist;
    ArrayList<String> enrollCourseList = new ArrayList<>();
    ArrayList<String> courseCompleteList = new ArrayList<>();
    String[] enrollCourseDropdown;

    String NAME;
    String CONTACT;
    String REGISTRATION;
    String ADDRESS;
    String EMAIL;

    // default object for  showing course table
    JTable aCourseTable, eCourseTable;
    DefaultTableModel aCourseTableModel, eCourseTableModel;
    String[] allCourse = {"ID", "COURSE", "TYPE"};  // for showing availabel courses
    String[] enrolledCourse = {"ID", "COURSE", "SCORE", "COMPLETED"};

    Object[][] allCourseData, enrollCourseData;

    // variables to show in the tables for enrolled / selected only
    String course, courseId, courseComplete, courseScore;

    // variables to show all the list of available courses
    String aCourseId, aCourse, aCourseAssess;

    // creation of arraylist to store the details  of course
    ArrayList<Object[]> AllCourseList = new ArrayList<>();
    ArrayList<Object[]> AllEnrollCourseList = new ArrayList<>();

    // Create a JScrollPane for the course table
    JScrollPane aCourseScrollPane, eCourseScrollPane;

    Boolean isTableChanged = false;
    // font for the overall text
    Font customFont = new Font(null, Font.PLAIN, 20);

    // getting the whole sizez of the page
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();
    int screenHeight = (int) screenSize.getHeight();

    // Static block to initialize static fields
    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // default condtructor
    public StudentDashboard(String ID) {   // id here refers to email
        key = ID;       // assigning the email to the key
        setDimensions();
        addComponents();
        showStudentDetail();
        showEnrolledCourseData();
        showCourseData();
        // methods call for showing menu
        publicMenu.setMenu();
        publicMenu.setMenuDesign();
        publicMenu.setMenuLogic();
        frame.setJMenuBar(publicMenu.menu);
    }

    // seting dimensions of the components
    public void setDimensions() {
        panel = new JPanel();
        panel.setSize(1450, 750);
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(180, 6, 100));
        panel.setLocation(40, 10);

        // setting the dimension of the actual components
        heading = new JLabel("Student Dashboard");
        heading.setBounds(80, 10, 600, 50);
        heading.setFont(new Font(null, Font.BOLD, 40));
        heading.setForeground(Color.BLACK);

        name = new JLabel("Name :-");
        name.setBounds(120, 120, 100, 40);
        name.setFont(customFont);

        contact = new JLabel("Contact :-");
        contact.setBounds(120, 170, 100, 40);
        contact.setFont(customFont);

        email = new JLabel("Email ID :-");
        email.setBounds(120, 220, 100, 40);
        email.setFont(customFont);

        address = new JLabel("Address :-");
        address.setBounds(120, 270, 100, 40);
        address.setFont(customFont);

        regno = new JLabel("Reg. No. :-");
        regno.setBounds(120, 320, 100, 40);
        regno.setFont(customFont);

        oname = new JTextField();
        oname.setBounds(280, 120, 300, 45);
        oname.setEditable(false);

        ocontact = new JTextField();
        ocontact.setBounds(280, 170, 300, 45);
        ocontact.setEditable(false);

        oemail = new JTextField();
        oemail.setBounds(280, 220, 300, 45);
        oemail.setEditable(false);

        oaddress = new JTextField();
        oaddress.setBounds(280, 270, 300, 45);
        oaddress.setEditable(false);

        oreg = new JTextField();
        oreg.setBounds(280, 320, 300, 45);
        oreg.setEditable(false);

        // for showing enrolled courses
        enroll = new JLabel("Enrolled Courses");
        enroll.setBounds(80, 420, 400, 40);
        enroll.setForeground(new Color(81, 121, 252));
        enroll.setFont(new Font(null, Font.BOLD, 25));

        // for searching
        searchC = new JLabel("Search Courses :-");
        searchC.setBounds(850, 60, 250, 40);
        searchC.setFont(customFont);

        searchField = new JTextField();
        searchField.setBounds(850, 120, 300, 45);

        searchBtn = new JButton("Find");
        searchBtn.setBounds(1150, 120, 150, 45);
        searchBtn.setBackground(new Color(204, 255, 255));
        searchBtn.setFocusable(false);
        searchBtn.addActionListener(this);

        toEnroll = new JLabel("Get Enrolled In ");
        toEnroll.setBounds(850, 550, 150, 40);
        toEnroll.setFont(customFont);

        enrollCourselist = new JComboBox<>();
        enrollCourselist.setBounds(850, 600, 300, 45);

        enrollBtn = new JButton("Get Course");
        enrollBtn.setFocusable(false);
        enrollBtn.setBounds(850, 650, 150, 45);
        enrollBtn.setBackground(new Color(204, 255, 255));
        enrollBtn.addActionListener(this);

        reset = new JButton("Reset");
        reset.setFocusable(false);
        reset.setBounds(1030, 650, 150, 45);
        reset.setBackground(new Color(255, 128, 128));
        reset.addActionListener(this);

        message1 = new JLabel();
        message1.setBounds(850, 170, 300, 40);
        message1.setForeground(Color.RED);
        message1.setFont(customFont);

        message2 = new JLabel();
        message2.setBounds(1150, 600, 300, 40);
        message2.setForeground(Color.RED);
        message2.setFont(customFont);

        // setting dimensions for table
        // Initialize the DefaultTableModel for the course table
        aCourseTableModel = new DefaultTableModel(allCourseData, allCourse);
        aCourseTable = new JTable(aCourseTableModel);
        aCourseTable.setRowHeight(50);
        aCourseTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        aCourseScrollPane = new JScrollPane(aCourseTable);
        aCourseScrollPane.setBounds(850, 220, 500, 300);

        // initialize the default table model for the enrolled courses
        eCourseTableModel = new DefaultTableModel(enrollCourseData, enrolledCourse);
        eCourseTable = new JTable(eCourseTableModel);
        eCourseTable.setRowHeight(50);
        eCourseTable.getColumnModel().getColumn(0).setPreferredWidth(20);
        eCourseTable.getColumnModel().getColumn(2).setPreferredWidth(25);
        eCourseScrollPane = new JScrollPane(eCourseTable);
        eCourseScrollPane.setBounds(80, 460, 550, 250);

        frame.setSize(screenWidth, screenHeight);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // adding the components
    public void addComponents() {

        panel.add(heading);
        panel.add(name);
        panel.add(contact);
        panel.add(email);
        panel.add(address);
        panel.add(regno);
        panel.add(oname);
        panel.add(ocontact);
        panel.add(oemail);
        panel.add(oaddress);
        panel.add(oreg);
        panel.add(enroll);
        panel.add(searchC);
        panel.add(searchField);
        panel.add(searchBtn);
        panel.add(toEnroll);
        panel.add(enrollCourselist);
        panel.add(enrollBtn);
        panel.add(reset);
        panel.add(message1);
        panel.add(message2);
        panel.add(aCourseScrollPane);
        panel.add(eCourseScrollPane);
        frame.add(panel);
        frame.revalidate();
    }

    public void showStudentDetail() {
        String query = "SELECT * FROM studentregistration WHERE Email = ?";

        try {
            Connection connect = DriverManager.getConnection(studentUrl, username, password);
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, key);
            ResultSet resultSet = statement.executeQuery();

            // retrieving the value
            while (resultSet.next()) {
                String fname = resultSet.getString("Firstname");
                String lname = resultSet.getString("Lastname");
                NAME = String.join(" ", fname, lname);
                CONTACT = resultSet.getString("Contact");
                ADDRESS = resultSet.getString("Address");
                EMAIL = resultSet.getString("Email");
                REGISTRATION = resultSet.getString("Registration");
            }
        } catch (Exception err) {
            System.out.println("Error" + err);
        }
        // showing the details in the boxes
        oname.setText(NAME);
        ocontact.setText(CONTACT);
        oreg.setText(REGISTRATION);
        oemail.setText(EMAIL);
        oaddress.setText(ADDRESS);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(reset))     // for reset button
        {
            searchField.setText("");
            message1.setText("");
            message2.setText("");
            if (isTableChanged) showCourseData();
        }

        if (e.getSource().equals(searchBtn)) {        // for search button
            String searchKey = searchField.getText();
            if (searchKey.isEmpty()) {
                message1.setText("Cannot Search Empty field !!");
            } else showSearchResult(searchKey);
        }

        if (e.getSource().equals(enrollBtn))     // for course enrollment
        {
            String enrollKey = String.valueOf(enrollCourselist.getSelectedItem());
            if (enrollKey.isEmpty()) {
                message2.setText("Please Enter Name of Course First !!!");

            } else getEnrolled(enrollKey);
        }

    }

    // method to show the course details in the table
    public void showEnrolledCourseData() {
        String query = "SELECT * FROM studentscore WHERE Registration = ?";
        try {
            Connection connect = DriverManager.getConnection(studentUrl, username, password); // course databse
            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, REGISTRATION);
            ResultSet resultSet = statement.executeQuery();
            // from studentscore table
            while (resultSet.next()) {
                courseId = resultSet.getString("Course_Id");
                courseScore = resultSet.getString("Score");
                courseComplete = resultSet.getString("isComplete");
                course = resultSet.getString("Course");
                courseCompleteList.add(courseComplete);
                AllEnrollCourseList.add(new Object[]{courseId, course, courseScore, courseComplete});   //for all enrolled course list
            }
            // Update table models with new data
            enrollCourseData = AllEnrollCourseList.toArray(new Object[0][]);
            eCourseTableModel.setDataVector(enrollCourseData, enrolledCourse);
            eCourseTableModel.fireTableDataChanged();
            connect.close();
        } catch (Exception err) {
            System.out.println("Error : " + err);
            err.printStackTrace();
        }
    }

    // to show the course data
    void showCourseData() {
        String query = "SELECT *FROM course";
        try {
            Connection connect = DriverManager.getConnection(courseUrl, username, password); // course databse
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            // from course table
            while (resultSet.next()) {
                aCourseId = resultSet.getString("Id");
                aCourse = resultSet.getString("Course");
                aCourseAssess = resultSet.getString("Assessment");
                AllCourseList.add(new Object[]{aCourseId, aCourse, aCourseAssess});   //for all enrolled course list
                enrollCourseList.add(aCourse);
            }
            enrollCourseDropdown = enrollCourseList.toArray(new String[0]);
            enrollCourselist.setModel(new DefaultComboBoxModel<>(enrollCourseDropdown));

            allCourseData = AllCourseList.toArray(new Object[0][]);
            aCourseTableModel.setDataVector(allCourseData, allCourse);
            eCourseTableModel.fireTableDataChanged();
            connect.close();
        } catch (Exception err) {
            System.out.println("Error : " + err);
        }
    }

    // function to show th details of the searched students
    void showSearchResult(String searchKey) {
        DefaultTableModel searchModel = (DefaultTableModel) aCourseTable.getModel();
        searchModel.setRowCount(0); // Clear the current table

        boolean courseFound = false;

        for (Object[] courseData : AllCourseList) { // Use Object[] as the correct data type
            String courseName = ((String) courseData[1]).toLowerCase();
            if (courseName.contains(searchKey.toLowerCase())) {
                searchModel.addRow(courseData);
                courseFound = true;
                isTableChanged = true;
            }
        }

        if (!courseFound) {
            JOptionPane.showMessageDialog(null, "Sorry! Unable to find the Course!");
        }
    }

    // function to get enrolled in the course
    void getEnrolled(String enrollRequest) {
        String COURSEID = null;

        if (courseCompleteList.contains("FALSE")) {
            JOptionPane.showMessageDialog(null, "Sorry ! \n Please Complete previous course before enrolling to next one ! ");
        } else {
            // getting the course id
            String query0 = "SELECT ID from course where Course = ?";
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connect0 = DriverManager.getConnection(courseUrl, username, password);
                PreparedStatement statement0 = connect0.prepareStatement(query0);
                statement0.setString(1, enrollRequest);
                ResultSet resultSet = statement0.executeQuery();
                // from course table
                while (resultSet.next()) COURSEID = resultSet.getString("Id");
            } catch (SQLException | ClassNotFoundException err) {
                err.printStackTrace();
            }

// inserting the new enrolled course to the database
            String query = "INSERT INTO studentscore(Course_Id, Registration, Course, isComplete) Values (?,?,?,?)";
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                Connection connect = DriverManager.getConnection(studentUrl, username, password);
                PreparedStatement statement = connect.prepareStatement(query);
                statement.setString(1, COURSEID);
                statement.setString(2, REGISTRATION);
                statement.setString(3, enrollRequest);
                statement.setString(4, "FALSE");

                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, " Welcome to the new Course ! \n Have fun  exploring ");
                    showEnrolledCourseData();
                }
                connect.close();
            } catch (ClassNotFoundException | SQLException err) {
                err.printStackTrace();
            }
        }
    }
}
