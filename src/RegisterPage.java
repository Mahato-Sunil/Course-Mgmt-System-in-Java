import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.Customizer;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;


public class RegisterPage extends MouseAdapter implements ActionListener {
    // instanting the variables
    JFrame frame = new JFrame("Student Management");       // for frame
    JMenuBar menu;  // for top menu
    JMenu home, teacherMenu, adminMenu, option;     // contents of menu
    JMenuItem logItem, exitPg;  // menu item for option menu

    // for main contains
    JPanel panel;
    JLabel heading, fname, lname, regno, contact, email, address, gender, course, message, dob;
    JTextField fnameInp, lnameInp, regInp, contactInp, emailInp, addressInp, dobInp;
    JRadioButton maleRadio, femaleRadio;
    JComboBox<String> courselist;
    JButton register;
    ButtonGroup btngrp;

    //  string for the courses -> combo box
    String COURSE;
    ArrayList<String> courseList = new ArrayList<>();
    String[] courseDropDown;

    // getting the whole sizez of the page
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();
    int screenHeight = (int) screenSize.getHeight();

    // font for the overall text
    Font customFont = new Font(null, Font.PLAIN, 20);

    // main class constructor
    RegisterPage() {
        setDimensions();
        populateCourseComboBox();
        addComp();
        Menu();
    }

    // function to show the menu items
    public void Menu() {
        menu = new JMenuBar();

        home = new JMenu("Home");
        home.addMouseListener(this);
        home.setFont(customFont);

        adminMenu = new JMenu("Admin ");
        adminMenu.addMouseListener(this);
        adminMenu.setFont(customFont);

        teacherMenu = new JMenu("Teacher");
        teacherMenu.addMouseListener(this);
        teacherMenu.setFont(customFont);

        option = new JMenu("Option");
        option.setFont(customFont);

        logItem = new JMenuItem("Login");
        logItem.addActionListener(this);
        logItem.setFont(customFont);

        exitPg = new JMenuItem("Exit");
        exitPg.addActionListener(this);
        exitPg.setFont(customFont);

        // adding the files menu
        option.add(logItem);
        option.add(exitPg);
        menu.add(home);     // adding the menu
        menu.add(adminMenu);
        menu.add(teacherMenu);
        menu.add(option);   // adding menu items
        frame.setJMenuBar(menu);
    }

    public void setDimensions() {
        // designing the gui
        panel = new JPanel();
        panel.setSize(1500, 750);
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(180, 6, 100));
        panel.setLocation(30, 10);

        // setting the dimension of the actual components
        heading = new JLabel("Student Registration System");
        heading.setBounds(30, 10, 500, 50);
        heading.setFont(new Font(null, Font.BOLD, 30));
        heading.setForeground(Color.BLACK);

        fname = new JLabel("First Name :-");
        fname.setBounds(50, 100, 200, 50);
        fname.setFont(customFont);

        lname = new JLabel("Last Name :-");
        lname.setBounds(50, 150, 200, 50);
        lname.setFont(customFont);

        regno = new JLabel("Registration Number :-");
        regno.setBounds(50, 200, 250, 50);
        regno.setFont(customFont);

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

        course = new JLabel("Course :-");
        course.setBounds(900, 350, 100, 50);
        course.setFont(customFont);

        courselist = new JComboBox<>();
        courselist.setBounds(950, 400, 250, 50);

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
        panel.add(course);
        panel.add(courselist);
        panel.add(register);
        panel.add(message);
        frame.add(panel);
    }

    // method for invoking the function call and event listeners
    @Override
    public void actionPerformed(ActionEvent e) {
        // invoking the menu item -> exit and login
        if (e.getSource().equals(exitPg)) System.exit(0);
        if (e.getSource().equals(logItem)) {
            new HomePage();
            frame.setVisible(false);
        }
        if (e.getSource().equals(register)) studentDatabase();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // invoking the menu items -> home, teacher, student
        if (e.getSource().equals(home)) {
            new HomePage();
            frame.setVisible(false);
        }
        if (e.getSource().equals(adminMenu)) System.out.println("Admin menu clicked");
        if (e.getSource().equals(teacherMenu)) System.out.println("teacher menu clicked");
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
        // System.out.println(ugender + " " + ucourse);

        // checing for the empty values
        if (ufname.isEmpty() || ulname.isEmpty() || uregno.isEmpty() || ucontact.isEmpty() || uemail.isEmpty() || uaddress.isEmpty())
            message.setText("Please Enter All the credentials !");
        else {
            storeStudentDatabase(ufname, ulname, uregno, ucontact, uemail, uaddress, ugender, ucourse);
        }
    }

    public void storeStudentDatabase(String ufname, String ulname, String uregno, String ucontact, String uemail, String uaddress, String ugender, String ucourse) {
        String username = "Sunil Mahato"; 
        String password = "sunil9860";
       String url = "jdbc:sqlserver://DELL:1433;trustServerCertificate=true;databaseName=studentdatabase";

        String query = "INSERT INTO studentregistration (Firstname, Lastname, Registration, Contact, Email, Address, Gender, Course) VALUES (?,?,?,?,?,?,?,?)";
        try {
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            Connection connect = DriverManager.getConnection(url, username, password);

            PreparedStatement statement = connect.prepareStatement(query);

            statement.setString(1, ufname);
            statement.setString(2, ulname);
            statement.setString(3, uregno);
            statement.setString(4, ucontact);
            statement.setString(5, uemail);
            statement.setString(6, uaddress);
            statement.setString(7, ugender);
            statement.setString(8, ucourse);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                frame.setVisible(false);
                new PasswordStorage(uemail, username, password, url);
            }
            connect.close();
        } catch (ClassNotFoundException | SQLException err) {
            message.setText("Server Error ! Failed to Establish Connection. ");
        }
    }

    // Method to populate the course combo box
    private void populateCourseComboBox() {
        String username = "Sunil Mahato"; 
        String password = "sunil9860";
        String url = "jdbc:sqlserver://DELL:1433;trustServerCertificate=true;databaseName=coursedatabase";
        String query = "SELECT Course FROM course";

        try {
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connect = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connect.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                COURSE = resultSet.getString("Course");
                System.out.println(COURSE);
                courseList.add(COURSE);
            }

            courseDropDown = courseList.toArray(new String[0]);
            courselist.setModel(new DefaultComboBoxModel<>(courseDropDown));

            connect.close();
        } catch (ClassNotFoundException | SQLException err) {
            // Handle the exception and show an error message to the user
            message.setText("Server Error ! Failed to Establish Connection. ");
        }
    }
}