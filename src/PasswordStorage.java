import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PasswordStorage implements ActionListener {
    JFrame frame;
    JLabel label, message, upassword, uconfirmPwd;
    JButton Verify;
    JPasswordField upwdField, uconfirmField;
    String uemail, username, password, url;

    PasswordStorage(String uemail, String username, String password, String url) {
        // assigning the credentials
        this.uemail = uemail;
        this.username = username;   // database username
        this.password = password;    // database password
        this.url = url;      // database url

        createFrame();
        setComponents();
        addComp();
    }

    // frame ui
    public void createFrame() {
        frame = new JFrame("Create Password... ");
        frame.setVisible(true);
        frame.setSize(420, 420);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // setting the dimessions of components
    public void setComponents() {
        label = new JLabel("Enter your Credentials...");
        label.setBounds(120, 30, 250, 40);
        label.setFont(new Font(null, Font.ITALIC, 16));
        label.setForeground(Color.BLUE);

        upassword = new JLabel("New Password :-");
        upassword.setBounds(80, 80, 150, 40);

        upwdField = new JPasswordField();
        upwdField.setBounds(80, 120, 230, 40);

        uconfirmPwd = new JLabel("Confirm Password :-");
        uconfirmPwd.setBounds(80, 150, 150, 40);

        uconfirmField = new JPasswordField();
        uconfirmField.setBounds(80, 190, 230, 40);

        Verify = new JButton("Verify Me");
        Verify.setBounds(150, 250, 150, 40);
        Verify.addActionListener(this);

        message = new JLabel();
        message.setBounds(100, 300, 280, 40);
        message.setForeground(Color.RED);
    }

    public void addComp() {
        frame.add(label);
        frame.add(upassword);
        frame.add(uconfirmPwd);
        frame.add(upwdField);
        frame.add(uconfirmField);
        frame.add(Verify);
        frame.add(message);
    }

    // adding action listener
    @Override
    public void actionPerformed(ActionEvent e) {
        // getting the input and passing the values to global variables
        String pwdinp = String.valueOf(upwdField.getPassword());
        String confirmPwdInp = String.valueOf(uconfirmField.getPassword());

        // register button
        if (e.getSource() == Verify) {
            if (pwdinp.isEmpty() || confirmPwdInp.isEmpty()) {
                message.setForeground(Color.red);
                message.setText("Please Enter All Credentials !");
            } else if (pwdinp.equals(confirmPwdInp)) {
                storePassword(confirmPwdInp);
            } else {
                message.setForeground(Color.red);
                message.setText("Password Doesn't Match");
            }
        }
    }

    // storing the passwords in the database
    private void storePassword(String confirmPwdInp) {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connect = DriverManager.getConnection(url, username, password);
            String query = "INSERT INTO studentpassword (Username, salt, hashCode) VALUES(?,?,?)";
            String query1 = "INSERT INTO teacherpassword (Username, salt, hashCode) VALUES(?,?,?)";

            String finalQuery = (url.equals("jdbc:mysql://localhost:3306/teacherdatabase")) ? query1 : query;     // checking between the teacher and student

            PreparedStatement statement = connect.prepareStatement(finalQuery);

            // generating the salt and hashcode using confirmPwdInp
            int hashFunction = (int) (Math.random() * 100000000);
            String salt = String.valueOf(hashFunction);
            String hashCode = salt+confirmPwdInp;

            statement.setString(1, uemail);
            statement.setString(2, salt);
            statement.setString(3, hashCode);

            // checking if data is inserted in the database
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                frame.setVisible(false);
                JOptionPane.showMessageDialog(null, "Congratulations ! You have been registered. \n Redirecting to Login Page.... ");
                new LoginPage();
            }
            connect.close();
        } catch (Exception err) {
            message.setText("Error: Database Offline ");
        }
    }
}