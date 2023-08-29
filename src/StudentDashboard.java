import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class StudentDashboard extends MouseAdapter implements ActionListener {
    JFrame frame = new JFrame("Student Dashboard");
    JMenuBar menu;    // menu items
    JMenu home, exitM;
    JLabel heading, name, contact, email, address, regno, enroll, searchC, toEnroll;
    JTextField oname, ocontact, oemail, oaddress, oreg, searchField, toEnrollField;
    JPanel panel;
    JButton searchBtn, enrollBtn, reset;
    JLabel message1, message2;
    String key;

    // database credentials
    String  username = "Sunil Mahato";
    String  password = "sunil9860";
   String url = "jdbc:sqlserver://DELL:1433;trustServerCertificate=true;databaseName=studentdatabase";
    String url1 = "jdbc:sqlserver://DELL:1433;trustServerCertificate=true;databaseName=coursedatabase";

    String NAME, CONTACT, REGISTRATION, ADDRESS, EMAIL, COURSE;     // DATA FROM THE DATABASE

    // default object for  showing course table
    JTable aCourseTable, eCourseTable;
    DefaultTableModel aCourseTableModel, eCourseTableModel;
    String[] allCourse = {"ID", "COURSE", "TYPE"};  // for showing availabel courses
    String[] enrolledCourse = {"ID", "COURSE", "CREDIT", "SCORE", "TYPE"};

    Object[][] allCourseData, enrollCourseData;

    // variables to show in the tables for enrolled / selected only
    String course, courseId, courseAssessment;
    Double courseCr, courseScore;

    // variables to show all the list of available courses
    String aCourseId, aCourse, aCourseAssess;

    // creation of arraylist to store the details  of course
    ArrayList<Object> AllCourseList = new ArrayList<>();
    ArrayList<Object> AllEnrollCourseList = new ArrayList<>();

    // Create a JScrollPane for the course table
    JScrollPane aCourseScrollPane, eCourseScrollPane;

    // font for the overall text
    Font customFont = new Font(null, Font.PLAIN, 20);

    // getting the whole sizez of the page
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();
    int screenHeight = (int) screenSize.getHeight();

    // default condtructor
    StudentDashboard(String ID) {   // id here refers to email
        key = ID;       // assigning the email to the key
        setDimensions();
        addComponents();
        setMenu();
        showStudentDetail();
        showCourseData();

    }

    // setting the menu
    public void setMenu() {
        menu = new JMenuBar();
        home = new JMenu("Home");
        home.addMouseListener(this);
        exitM = new JMenu("Exit");
        exitM.addMouseListener(this);
        menu.add(home);     // adding the menu
        menu.add(exitM);
        frame.setJMenuBar(menu);
    }

    // seting dimensions of the components
    public void setDimensions() {
        panel = new JPanel();
        panel.setSize(1400, 750);
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

        toEnrollField = new JTextField();
        toEnrollField.setBounds(850, 600, 300, 45);

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

        message1 = new JLabel("Error Searching course");
        message1.setBounds(850, 170, 300, 40);
        message1.setForeground(Color.RED);
        message1.setFont(customFont);

        message2 = new JLabel(" Error Enrolling in course");
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
        panel.add(toEnrollField);
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
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connect = DriverManager.getConnection(url, username, password);
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
                COURSE = resultSet.getString("Course");
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

    // event listener
    @Override
    public void mouseClicked(MouseEvent e) {
        // invoking the menu items -> home, exit
        if (e.getSource().equals(home)) {
            new HomePage();
            frame.setVisible(false);
        }
        if (e.getSource().equals(exitM)) System.exit(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(reset))     // for reset button
        {
            toEnrollField.setText("");
            searchField.setText("");
        }

        if (e.getSource().equals(searchBtn)) {        // for search button
            String searchKey = searchField.getText();
            if (searchKey.isEmpty()) {
                System.out.println("Please Enter the key ");
            } else System.out.println(searchKey);
        }

        if (e.getSource().equals(enrollBtn))     // for course enrollment
        {
            String enrollKey = toEnrollField.getText();
            if (enrollKey.isEmpty()) {
                System.out.println("Enter the name first ");

            } else System.out.println(enrollKey);
        }
    }

    // method to show the course details in the table
    public void showCourseData() {
        String query = "SELECT * FROM studentregistration WHERE Registration = ?";
        String query0 = "SELECT * FROM studentscore WHERE Registration = ?";
        String query1 = "SELECT * FROM Course";

        try {
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Connection connect = DriverManager.getConnection(url, username, password);  // student
            Connection connect1 = DriverManager.getConnection(url1, username, password); // course databse

            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, REGISTRATION);
            ResultSet resultSet = statement.executeQuery();

            // data from student database
            while (resultSet.next()) {

                course = resultSet.getString("Course");

                // from studentscore table
                PreparedStatement statement0 = connect.prepareStatement(query0);
                statement0.setString(1, REGISTRATION);
                ResultSet resultSet0 = statement0.executeQuery();

                while (resultSet0.next()) {
                    courseId = resultSet0.getString("Course_Id");
                    courseScore = Double.parseDouble(resultSet0.getString("Score"));
                }

                // from course table
                PreparedStatement statement1 = connect1.prepareStatement(query1);
                ResultSet resultSet1 = statement1.executeQuery();

                // data from  course database
                while (resultSet1.next()) {
                    aCourseId = resultSet1.getString("Id");
                    aCourse = resultSet1.getString("Course");
                    courseCr = Double.parseDouble(resultSet1.getString("Credit"));
                    courseAssessment = resultSet1.getString("Assessment");
                    aCourseAssess = courseAssessment;

                    AllCourseList.add(new Object[]{aCourseId, aCourse, aCourseAssess}); // for all course list
                }
                AllEnrollCourseList.add(new Object[]{courseId, course, courseCr, courseScore, courseAssessment});   //for all enrolled course list
            }
            // Update table models with new data
            allCourseData = AllCourseList.toArray(new Object[0][]);
            aCourseTableModel.setDataVector(allCourseData, allCourse);
            enrollCourseData = AllEnrollCourseList.toArray(new Object[0][]);
            eCourseTableModel.setDataVector(enrollCourseData, enrolledCourse);

            connect1.close();
            connect.close();
        } catch (Exception err) {
            System.out.println("Error : " + err);
        }
    }
}
