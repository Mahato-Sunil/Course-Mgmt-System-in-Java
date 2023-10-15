import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage implements ActionListener {
    // instantiating a components
    JFrame frame = new JFrame("Course Management");       // for frame
    JMenuBar menu;  // for top menu
    JMenu home, option, registerMenu;     // contents of menu
    JMenuItem courseMenu, exitPg;  // menu item for option menu
    JMenuItem TeachReg, StudentReg;     // menu item for Register menu

    // for main contains
    JPanel panel;
    JLabel heading, userIdLabel, userPwdLabel, message, userModeLabel;
    JComboBox userMode;
    JTextField userIdInp;
    JPasswordField userPwdInp;
    JButton submit;

    // main constructor class
    HomePage() {
        setDimensions();
        addComp();
        Menu();
    }

    // function to show the menu items
    private void Menu() {
        menu = new JMenuBar();

        home = new JMenu("Home");
        option = new JMenu("Option ");
        registerMenu = new JMenu("Register");

        TeachReg = new JMenuItem("Teacher Registration");
        TeachReg.addActionListener(this);

        StudentReg = new JMenuItem("Student Registration");
        StudentReg.addActionListener(this);

        // registerMenu.addActionListener(this);

        exitPg = new JMenuItem("Exit");
        exitPg.addActionListener(this);

        courseMenu = new JMenuItem("Courses ");
        courseMenu.addActionListener(this);

        // adding the files menu
        option.add(registerMenu);
        option.add(courseMenu);
        option.add(exitPg);

        registerMenu.add(StudentReg);
        registerMenu.add(TeachReg);

        menu.add(home);     // adding the menu
        menu.add(option);   // adding menu items
        frame.setJMenuBar(menu);
    }

    // adding the components
    private void addComp() {
        panel.add(userModeLabel);   // for user mode label
        panel.add(userMode);        // for jcombo box
        panel.add(heading);         // heading
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

        heading = new JLabel("Welcome to Course Management System");
        heading.setBounds(125, 10, 500, 50);
        heading.setFont(new Font(null, Font.ITALIC, 25));
        heading.setForeground(Color.blue);

        userModeLabel = new JLabel("User Mode :-");
        userModeLabel.setBounds(120, 100, 100, 50);

        userMode = new JComboBox(mode);
        userMode.setBounds(200, 100, 150, 50);

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
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // adding the action listener
    @Override
    public void actionPerformed(ActionEvent e) {
        // menu -> register
        if (e.getSource() == StudentReg) {
            new StudentRegistration();
            frame.setVisible(false);
        }

        if (e.getSource() == TeachReg) {
            new TeacherRegistration();
            frame.setVisible(false);
        }
        if (e.getSource() == courseMenu) {
            new CourseDashboard();
            frame.setVisible(false);
        }

        // menu ->exit
        if (e.getSource() == exitPg) System.exit(0);

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
                    case "Admin":
                        message.setText("Admin  Panel Currently Not Available !");
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