import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;

public class TeacherRegistration extends StudentRegistration implements DatabaseCredentials, ActionListener, ListSelectionListener {
    // variables for list drop down
    DefaultListModel<String> expertiseListModel;
    JList<String> expertiseList;
    JScrollPane expertiseScrollPane;

    List<String> selectedCourses; // Added to store selected courses

    TeacherRegistration() {
        super();
        setHeading();
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

        expertiseListModel = new DefaultListModel<>();

        expertiseList = new JList<>(expertiseListModel);
        expertiseList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        expertiseList.setFixedCellHeight(30);
        expertiseList.addListSelectionListener(this);

        expertiseScrollPane = new JScrollPane(expertiseList);
        expertiseScrollPane.setBounds(950, 400, 300, 200);
        panel.add(course);
        panel.add(expertiseScrollPane); // Add the expertiseScrollPane to the panel

        // Load expertise data from the database
        populateCourseComboBox();
    }

    // Load expertise data from the database

    @Override
    public void populateCourseComboBox() {
        try {
            Connection connection = DriverManager.getConnection(courseUrl, username, password);
            String sql = "SELECT Course FROM course";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String courseData = resultSet.getString("Course");
                expertiseListModel.addElement(courseData);
            }
            connection.close();
        } catch (SQLException e) {
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

        if (e.getSource().equals(register)) {
            // checking for emptiness of the components
            if (Fname.isEmpty() || Lname.isEmpty() || TeacherID.isEmpty() || TContact.isEmpty() || TEmail.isEmpty() || TAddress.isEmpty())
                JOptionPane.showMessageDialog(null, "Please Enter All the credentials !");
            else {
                if (TContact.length() > 10) JOptionPane.showMessageDialog(null, "!! Contact should be of 10 digit !!");
                else {
                    try {
                        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                        Connection connect = DriverManager.getConnection(teacherUrl, username, password);

                        // Insert teacher information
                        String teacherQuery = "INSERT INTO teacherRegistration (Firstname, Lastname, TeacherId, Contact, Email, Address, Gender) VALUES (?,?,?,?,?,?,?)";
                        PreparedStatement teacherStatement = connect.prepareStatement(teacherQuery);
                        teacherStatement.setString(1, Fname);
                        teacherStatement.setString(2, Lname);
                        teacherStatement.setString(3, TeacherID);
                        teacherStatement.setString(4, TContact);
                        teacherStatement.setString(5, TEmail);
                        teacherStatement.setString(6, TAddress);
                        teacherStatement.setString(7, TGender);

                        int rowsInserted = teacherStatement.executeUpdate();

                        if (rowsInserted > 0) {
                            // Insert selected expertise into the expertise table
                            insertSelectedExpertise(TeacherID);
                            frame.setVisible(false);
                            new PasswordStorage(TEmail, username, password, teacherUrl);
                        }
                        teacherStatement.close();
                        connect.close();
                    } catch (ClassNotFoundException | SQLException err) {
                        message.setText("Server Error! Failed to Establish Connection.");
                        err.printStackTrace();
                    }
                }
            }
        }
    }

    // Insert selected expertise into the expertise table
    private void insertSelectedExpertise(String teacherID) {
        try {
            Connection connect = DriverManager.getConnection(teacherUrl, username, password);
            // Insert teacher's expertise
            String expertiseQuery = "INSERT INTO expertise (TeacherId, Course) VALUES (?, ?)";
            PreparedStatement expertiseStatement = connect.prepareStatement(expertiseQuery);
            for (String course : selectedCourses) {
                expertiseStatement.setString(1, teacherID);
                expertiseStatement.setString(2, course);
                expertiseStatement.executeUpdate();
            }
            expertiseStatement.close();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
        // Handle selection change here
        selectedCourses = expertiseList.getSelectedValuesList();
    }
}