import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherRegistration extends StudentRegistration implements DatabaseCredentials, ActionListener {
    // Initialize the variables
    private DefaultListModel<String> courseModel;

    TeacherRegistration() {
        super();
        setHeading();
        populateCourseComboBox();
        setCourse();
    }

    @Override
    public void setHeading() {
        // For heading
        heading = new JLabel("Teacher Registration System");
        heading.setBounds(30, 10, 500, 50);
        heading.setFont(new Font(null, Font.BOLD, 30));
        heading.setForeground(Color.BLACK);

        // For registration number
        regno = new JLabel("Teacher ID :-");
        regno.setBounds(50, 200, 250, 50);
        regno.setFont(customFont);
    }

    @Override
    public void setCourse() {
        course = new JLabel("Field of Expertise:-");
        course.setBounds(900, 350, 250, 50);
        course.setFont(customFont);
        panel.add(course);

        courseModel = new DefaultListModel<>();
        JList<String> courseList = new JList<>(courseModel);
        courseList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        courseList.setFixedCellHeight(30);

        JScrollPane courseListPane = new JScrollPane(courseList);
        courseListPane.setBounds(950, 400, 300, 200);
        panel.add(courseListPane);
    }

    @Override
    public void populateCourseComboBox() {
        String query = "SELECT Course FROM course";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            try (Connection connect = DriverManager.getConnection(courseUrl, username, password); PreparedStatement statement = connect.prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {
                List<String> courseDataList = new ArrayList<>();
                while (resultSet.next()) {
                    String courseData = resultSet.getString("Course");
                    courseDataList.add(courseData);
                }
                courseModel.addAll(courseDataList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String Fname, Lname, TeacherID, TContact, TAddress, TEmail, TGender, TCourse;
        Fname = fnameInp.getText();
        Lname = lnameInp.getText();
        TeacherID = regInp.getText();
        TContact = contactInp.getText();
        TAddress = addressInp.getText();
        TEmail = emailInp.getText();
        TGender = (maleRadio.isSelected()) ? "Male" : "Female";
//        TCourse = (String) courselist.getSelectedItem();
        TCourse = "JAVA";

        if (e.getSource().equals(register)) {
            // checking for emptiness of the components
            if (Fname.isEmpty() || Lname.isEmpty() || TeacherID.isEmpty() || TContact.isEmpty() || TEmail.isEmpty() || TAddress.isEmpty())
                message.setText("Please Enter All the credentials !");
            else {
                // storing the data to the data base
                String query = "INSERT INTO teacherRegistration (Firstname, Lastname, TeacherId, Contact, Email, Address, Gender, Course) VALUES (?,?,?,?,?,?,?,?)";
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection connect = DriverManager.getConnection(teacherUrl, username, password);
                    PreparedStatement statement = connect.prepareStatement(query);
                    statement.setString(1, Fname);
                    statement.setString(2, Lname);
                    statement.setString(3, TeacherID);
                    statement.setString(4, TContact);
                    statement.setString(5, TEmail);
                    statement.setString(6, TAddress);
                    statement.setString(7, TGender);
                    statement.setString(8, TCourse);

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        frame.setVisible(false);
                        new PasswordStorage(TEmail, username, password, teacherUrl);
                    }
                    connect.close();
                } catch (ClassNotFoundException | SQLException err) {
                    message.setText("Server Error ! Failed to Establish Connection. ");
                    err.printStackTrace();
                }
            }
        }
    }
}