import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

class UserDataDatabase implements DatabaseCredentials {
    String user;

    UserDataDatabase(String user) {
        this.user = user;
        setDimension();
        addComponents();
        populateTable();
    }

    Connection connect, connect1, CONNECT = null;
    //initializing the components
    JFrame frame;
    JLabel heading;
    JTable userDataTable;
    DefaultTableModel userDataTableModel;
    String[] teacherDataHeading = {"Teacher ID ", "Teacher's Name", "Contact", "Email", "Address", "Expertise", "Gender"};  // for showing availabel student heading
    String[] studentDataHeading = {"Registration No. ", "Student's Name", "Contact", "Email", "Address", "Gender", "Course"};  // for showing availabel student heading
    String[] userDataHeading = ("Teacher".equals(user)) ? teacherDataHeading : studentDataHeading;
    Object[][] userData;
    ArrayList<Object> userDataList = new ArrayList<>();
    JScrollPane userScrollPane;

    Font customFont = new Font(null, Font.PLAIN, 20);

    // Static block to initialize static fields
   static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    void setDimension() {
        heading = new JLabel(user + " Database");
        heading.setBounds(420, 20, 200, 50);
        heading.setFont(customFont);

        userDataTableModel = new DefaultTableModel(userData, userDataHeading);
        userDataTable = new JTable(this.userDataTableModel);
        userDataTable.setRowHeight(40);
        userDataTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        userScrollPane = new JScrollPane(userDataTable);
        userScrollPane.setBounds(30, 90, 1000, 500);

        frame = new JFrame(user + " Database");
        frame.setVisible(true);
        frame.setSize(1080, 720);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
    }

    void addComponents() {
        frame.add(heading);
        frame.add(userScrollPane);
    }

    void populateTable() {
        String query = "SELECT * FROM teacherRegistration";
        String query1 = "SELECT * FROM studentregistration";
        String QUERY = ("Teacher".equals(user)) ? query : query1;
        try {
            connect = DriverManager.getConnection(teacherUrl, username, password); // teacher databse
            connect1 = DriverManager.getConnection(studentUrl, username, password); // student databse
            CONNECT = ("Teacher".equals(user)) ? connect : connect1;
            // from course table
            PreparedStatement statement = CONNECT.prepareStatement(QUERY);
            ResultSet resultSet = statement.executeQuery();
            // data from  course database

            while (resultSet.next()) {
                if ("Teacher".equals(user)) {
                    String teacherId = resultSet.getString("TeacherId");
                    String teacherName = resultSet.getString("FirstName") + resultSet.getString("LastName");
                    String teacherContact = resultSet.getString("Contact");
                    String teacherEmail = resultSet.getString("Email");
                    String teacherAddress = resultSet.getString("Address");
                    String teacherGender = resultSet.getString("Gender");
                    String teacherCourse = resultSet.getString("Course");

                    userDataList.add(new Object[]{teacherId, teacherName, teacherContact, teacherEmail, teacherAddress, teacherGender, teacherCourse}); // for all course list
                } else if (user.equals("Student")) {
                    String studentId = resultSet.getString("Registration");
                    String studentName = resultSet.getString("Firstname") + resultSet.getString("Lastname");
                    String studentContact = resultSet.getString("Contact");
                    String studentEmail = resultSet.getString("Email");
                    String studentAddress = resultSet.getString("Address");
                    String studentGender = resultSet.getString("Gender");
                    String studentCourse = resultSet.getString("Course");

                    userDataList.add(new Object[]{studentId, studentName, studentContact, studentEmail, studentAddress, studentCourse, studentGender}); // for all course list
                }
            }
            // Update table models with new data
            userData = userDataList.toArray(new Object[0][]);
            userDataTableModel.setDataVector(userData, userDataHeading);
            CONNECT.close();
        } catch (Exception err) {
            System.out.println("Error : " + err);
        }
    }
}