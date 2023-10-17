import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.sql.*;
import java.util.ArrayList;


public class StudentRegistration extends MouseAdapter implements ActionListener, DatabaseCredentials {
    // instanting the variables
    JFrame frame = new JFrame("Student Management");       // for frame
    // for main contains
    JPanel panel;
    JLabel heading, fname, lname, regno, contact, email, address, gender, course, message, dob;
    JTextField fnameInp, lnameInp, regInp, contactInp, emailInp, addressInp, dobInp;
    JRadioButton maleRadio, femaleRadio;

    JComboBox<String> courselist;
    JButton register;
    ButtonGroup btngrp;

    //  string for the courses -> combo box
    String COURSE, COURSEID;
    ArrayList<String> courseList = new ArrayList<>();
    ArrayList<Object[]> tempCourseList = new ArrayList<>();
    String[] courseDropDown;

    // getting the whole sizez of the page
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();
    int screenHeight = (int) screenSize.getHeight();

    // font for the overall text
    Font customFont = new Font(null, Font.PLAIN, 20);

    // main class constructor
    StudentRegistration() {
        setDimensions();
        setHeading();
        setCourse();
        populateCourseComboBox();
        addComp();
//        menuitem call
        publicMenu.setMenu();
        publicMenu.setMenuDesign();
        publicMenu.setMenuLogic();
        frame.setJMenuBar(publicMenu.menu);
    }

    public void setDimensions() {
        // designing the gui
        panel = new JPanel();
        panel.setSize(1500, 750);
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(180, 6, 100));
        panel.setLocation(30, 10);

        // setting the dimension of the actual components

        fname = new JLabel("First Name :-");
        fname.setBounds(50, 100, 200, 50);
        fname.setFont(customFont);

        lname = new JLabel("Last Name :-");
        lname.setBounds(50, 150, 200, 50);
        lname.setFont(customFont);

        contact = new JLabel("Contact No. :-");
        contact.setBounds(50, 250, 250, 50);
        contact.setFont(customFont);

        address = new JLabel("Email ID :-");
        address.setBounds(50, 300, 200, 50);
        address.setFont(customFont);

        email = new JLabel("Address :-");
        email.setBounds(50, 350, 200, 50);
        email.setFont(customFont);

        dob = new JLabel(" DOB :-");
        dob.setBounds(50, 400, 200, 50);
        dob.setFont(customFont);

        fnameInp = new JTextField();
        fnameInp.setBounds(350, 100, 350, 45);

        lnameInp = new JTextField();
        lnameInp.setBounds(350, 150, 350, 45);

        regInp = new JTextField();
        regInp.setBounds(350, 200, 350, 45);

        contactInp = new JTextField();
        contactInp.setBounds(350, 250, 350, 45);

        emailInp = new JTextField();
        emailInp.setBounds(350, 300, 350, 45);

        addressInp = new JTextField();
        addressInp.setBounds(350, 350, 350, 45);

        gender = new JLabel("Gender");
        gender.setBounds(900, 100, 250, 55);
        gender.setFont(customFont);

        maleRadio = new JRadioButton("Male");
        maleRadio.setBounds(950, 160, 100, 50);
        maleRadio.setFocusable(false);
        maleRadio.setSelected(true);
        maleRadio.setFont(customFont);

        femaleRadio = new JRadioButton("Female");
        femaleRadio.setBounds(1050, 160, 100, 50);
        femaleRadio.setFocusable(false);
        femaleRadio.setFont(customFont);

        btngrp = new ButtonGroup();
        btngrp.add(maleRadio);
        btngrp.add(femaleRadio);

        register = new JButton(" Register ");
        register.setBounds(700, 600, 200, 50);
        register.setBackground(new Color(204, 255, 255));
        register.addActionListener(this);
        register.setFocusable(false);
        register.setFont(customFont);

        message = new JLabel();
        message.setBounds(400, 500, 600, 50);
        message.setForeground(Color.RED);
        message.setFont(new Font(null, Font.PLAIN, 20));

        dobInp = new JTextField("dd/mm/yyyy");
        dobInp.setBounds(350, 400, 250, 55);
        dobInp.setForeground(Color.LIGHT_GRAY);

        // setting the frame width and height and making it visible
        frame.setVisible(true);
        frame.setSize(screenWidth, screenHeight);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // method for setting the headings
    public void setHeading() {
//for heading
        heading = new JLabel("Student Registration System");
        heading.setBounds(30, 10, 500, 50);
        heading.setFont(new Font(null, Font.BOLD, 30));
        heading.setForeground(Color.BLACK);

        // for registration number
        regno = new JLabel("Registration Number :-");
        regno.setBounds(50, 200, 250, 50);
        regno.setFont(customFont);
    }

    public void setCourse() {
        course = new JLabel("Course :-");
        course.setBounds(900, 350, 100, 50);
        course.setFont(customFont);
        panel.add(course);

        courselist = new JComboBox<>();
        courselist.setBounds(950, 400, 250, 50);
        panel.add(courselist);
    }

    // adding the components
    public void addComp() {
        panel.add(heading);
        panel.add(fname);
        panel.add(lname);
        panel.add(regno);
        panel.add(contact);
        panel.add(email);
        panel.add(address);
        panel.add(fnameInp);
        panel.add(fnameInp);
        panel.add(lnameInp);
        panel.add(regInp);
        panel.add(contactInp);
        panel.add(emailInp);
        panel.add(addressInp);
        panel.add(gender);
        panel.add(maleRadio);
        panel.add(femaleRadio);
        panel.add(register);
        panel.add(message);
        frame.add(panel);
    }

    // method for invoking the function call and event listeners
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(register)) studentDatabase();
    }
    // method to store the student details in database

    public void studentDatabase() {
        // getting and storing all the details of student in string variable
        String ufname = fnameInp.getText();
        String ulname = lnameInp.getText();
        String uregno = regInp.getText();
        String ucontact = contactInp.getText();
        String uemail = emailInp.getText();
        String uaddress = addressInp.getText();
        String ugender = (maleRadio.isSelected()) ? "Male" : "Female";
        String ucourse = (String) courselist.getSelectedItem();

        // checing for the empty values
        if (ufname.isEmpty() || ulname.isEmpty() || uregno.isEmpty() || ucontact.isEmpty() || uemail.isEmpty() || uaddress.isEmpty())
            message.setText("Please Enter All the credentials !");
        else {
            storeStudentDatabase(ufname, ulname, uregno, ucontact, uemail, uaddress, ugender, ucourse);
        }
    }

    public void storeStudentDatabase(String ufname, String ulname, String uregno, String ucontact, String uemail, String uaddress, String ugender, String ucourse) {
        String query0 = "INSERT INTO studentscore(Course_Id, Registration, Score, Course, isComplete) Values (?,?,?,?,?)";
        String query = "INSERT INTO studentregistration (Firstname, Lastname, Registration, Contact, Email, Address, Gender) VALUES (?,?,?,?,?,?,?)";

        String matchedId = null;
        // retireving the course data
        for (Object[] temp : tempCourseList) {
            String tempCourse = temp[0].toString();
            String tempId = temp[1].toString();
            if (tempCourse.equals(ucourse)) {
                matchedId = tempId;
                break;
            }
        }

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Connection connect = DriverManager.getConnection(studentUrl, username, password);
            Connection connect0 = DriverManager.getConnection(studentUrl, username, password);

            PreparedStatement statement = connect.prepareStatement(query);
            statement.setString(1, ufname);
            statement.setString(2, ulname);
            statement.setString(3, uregno);
            statement.setString(4, ucontact);
            statement.setString(5, uemail);
            statement.setString(6, uaddress);
            statement.setString(7, ugender);

            PreparedStatement statement1 = connect0.prepareStatement(query0);
            statement1.setString(1, matchedId);
            statement1.setString(2, uregno);
            statement1.setString(3, "0.0");
            statement1.setString(4, ucourse);
            statement1.setString(5, "FALSE");

            int rowsInserted = statement.executeUpdate();
            int rowsInserted0 = statement1.executeUpdate();

            if (rowsInserted > 0 && rowsInserted0 > 0) {
                frame.setVisible(false);
                new PasswordStorage(uemail, username, password, studentUrl);
            }
            connect.close();
            connect0.close();

        } catch (ClassNotFoundException | SQLException err) {
            message.setText("Server Error ! Failed to Establish Connection. ");
            err.printStackTrace();
        }
    }

    // Method to populate the course combo box
    public void populateCourseComboBox() {
        String query = "SELECT Id, Course FROM course";

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connect = DriverManager.getConnection(courseUrl, username, password);
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                COURSE = resultSet.getString("Course");
                COURSEID = resultSet.getString("Id");
                courseList.add(COURSE);
                tempCourseList.add(new Object[]{COURSE, COURSEID});
            }
            courseDropDown = courseList.toArray(new String[0]);
            courselist.setModel(new DefaultComboBoxModel<>(courseDropDown));

            connect.close();
        } catch (ClassNotFoundException | SQLException err) {
            // Handle the exception and show an error message to the user
            err.printStackTrace();
        }
    }
}