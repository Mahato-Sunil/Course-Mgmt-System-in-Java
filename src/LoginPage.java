import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage implements ActionListener {

    // instantiating a components
    JFrame frame = new JFrame("Login");       // for frame
    // for main contains
    JPanel panel;
    JLabel  userIdLabel, userPwdLabel, message, userModeLabel;
    JComboBox userMode;
    JTextField userIdInp;
    JPasswordField userPwdInp;
    JButton submit;

    // main constructor class
    LoginPage() {
        setDimensions();
        addComp();
    }

    // adding the components
    private void addComp() {
        panel.add(userModeLabel);   // for user mode label
        panel.add(userMode);        // for jcombo box
        panel.add(userIdLabel);     // username label
        panel.add(userPwdLabel);    // password label
        panel.add(message);         // message label
        panel.add(userIdInp);       // username input area
        panel.add(userPwdInp);      // password input area
        panel.add(submit);          // submit button
        frame.add(panel);
    }

    // setting the dimensions of the frames
    public void setDimensions() {
        String[] mode = {"Admin", "Teacher", "Student"};   // string array for the user mode seletciton

        // designing the gui
        panel = new JPanel();
        panel.setSize(640, 460);
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(180, 6, 100));
        panel.setLocation(30, 10);

        userModeLabel = new JLabel("Select your Role :-");
        userModeLabel.setBounds(120, 100, 150, 50);

        userMode = new JComboBox(mode);
        userMode.setBounds(250, 100, 150, 50);

        userIdLabel = new JLabel("Username or Email :- ");
        userIdLabel.setBounds(120, 160, 180, 50); // username

        userIdInp = new JTextField();
        userIdInp.setBounds(120, 200, 260, 50);

        userPwdLabel = new JLabel("Password :-");
        userPwdLabel.setBounds(120, 240, 100, 50);    // password

        userPwdInp = new JPasswordField();
        userPwdInp.setBounds(120, 280, 250, 50);

        message = new JLabel();
        message.setBounds(140, 390, 250, 50);   // message

        submit = new JButton("Login");
        submit.setBounds(120, 340, 180, 50);
        submit.setFocusable(false);      // remove the focus / box from text
        submit.addActionListener(this);  // adding action listener

        // setting the frame width and height and making it visible
        frame.setVisible(true);
        frame.setSize(720, 540);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
    }

    // adding the action listener
    @Override
    public void actionPerformed(ActionEvent e) {
        // login button
        if (e.getSource() == submit) {
            String usermode = (String) userMode.getSelectedItem();
            String id = userIdInp.getText();
            String password = String.valueOf(userPwdInp.getPassword());
            // checking for empty input fields
            if (id.isEmpty() || password.isEmpty()) {
                message.setForeground(Color.red);
                message.setText("Please Enter All Credentials !");
            } else {
                // creationg of validate class
                Validate valid = new Validate(id, password);
                switch (usermode) {
                    case "Admin": {
                        if (valid.checkValidity(usermode)) {
                            frame.setVisible(false);
                            new AdminDashboard();
                        } else {
                            message.setForeground(Color.red);
                            message.setText("Username or Password Incorrect !");
                        }
                    }
                     break;

                    case "Teacher": {
                        if (valid.checkValidity(usermode)) {
                            frame.setVisible(false);
                            new TeacherDashboard(id);
                        } else {
                            message.setForeground(Color.red);
                            message.setText("Username or Password Incorrect !");
                        }
                    }
                    break;
                    case "Student": {
                        if (valid.checkValidity(usermode)) {
                            frame.setVisible(false);
                            new StudentDashboard(id);
                        } else {
                            message.setForeground(Color.red);
                            message.setText("Username or Password Incorrect !");
                        }
                    }
                    break;
                }
            }
        }
    }
}
