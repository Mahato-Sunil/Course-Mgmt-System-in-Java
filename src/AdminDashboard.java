import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDashboard extends PublicMenu implements ActionListener {
    // initializing the components
    JFrame frame;
    JPanel panel1, panel2, panel3;
    JButton studentRegister, teacherRegister, exit, manageCourse;
    JLabel header, footer;

    // constructor
    AdminDashboard() {
        adminVerification();
    }

    // admin verification
    private void adminVerification() {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(480, 480));
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel userPwd, image;

        image = new JLabel();
        ImageIcon icon = new ImageIcon("Image/verification.png");
        Image imageIcon = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imageIcon);
        image.setIcon(scaledIcon);
        image.setBounds(100, 0, 300, 300);

        userPwd = new JLabel("Please Enter Your passphrase :");
        userPwd.setFont(new Font(null, Font.BOLD, 20));
        userPwd.setBounds(50, 290, 300, 50);

        JPasswordField pwdInp = new JPasswordField(null, 2);
        pwdInp.setBounds(50, 360, 400, 50);

        panel.add(image, JLabel.CENTER);
        panel.add(userPwd);
        panel.add(pwdInp);

        int response = JOptionPane.showConfirmDialog(null, panel, "Admin Verification", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (response == JOptionPane.OK_OPTION) {
            // User clicked OK
            // Retrieve data from the  components in your custom panel
            String role = "Admin";
            String username = "admin";
            String passPhrase = String.valueOf(pwdInp.getPassword());
            Validate valid = new Validate(username, passPhrase);
            if (valid.checkValidity(role)) {
                initComponents();
                addComponents();
                setMenu();                  // menu dimensions
                setMenuDesign();            //menu design
                setMenuLogic();                // menu logic
                frame.setJMenuBar(menu);    // to show the menu frame
            } else {
                JOptionPane.showMessageDialog(null, "Denied Access ! \n Invalid Credentials");
            }
        } else JOptionPane.showMessageDialog(null, "Process Cancelled by User");
    }

    // initializing the ccomponets
    void initComponents() {
        frame = new JFrame("Admin Panel");
        frame.setSize(1080, 720);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // top header
        panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(1080, 100));
        panel1.setBackground(Color.WHITE);
        header = new JLabel("Welcome Admin !!", JLabel.CENTER);
        header.setFont(new Font(null, Font.BOLD, 30));

        // center part
        panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(1080, 630));
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER));

        studentRegister = CreateImageButton.createButton(null, "Image/studentReg.png", 150, 150);
        studentRegister.addActionListener(this);

        teacherRegister = CreateImageButton.createButton(null, "image/teacherReg.png", 150, 150);
        teacherRegister.addActionListener(this);

        manageCourse = CreateImageButton.createButton(null, "Image/courseMgmt.png", 150, 150);
        manageCourse.addActionListener(this);

        exit = CreateImageButton.createButton(null, "Image/exit.png", 150, 150);
        exit.addActionListener((event) -> System.exit(0));

        // footer
        panel3 = new JPanel();
        panel3.setSize(1080, 50);
        panel3.setBackground(new Color(196, 191, 191));
        footer = new JLabel("copyright-2023@SunilMahato", JLabel.CENTER);
    }

    void addComponents()        // method to add the components
    {
        panel1.add(header);
        panel2.add(teacherRegister);
        panel2.add(studentRegister);
        panel2.add(manageCourse);
        panel2.add(exit);
        panel3.add(footer);
        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.CENTER);
        frame.add(panel3, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == studentRegister) {
            frame.setVisible(false);
            frame.dispose();
            SwingUtilities.invokeLater(StudentRegistration::new);
        }
        if (e.getSource() == teacherRegister) {
            frame.setVisible(false);
            frame.dispose();
            SwingUtilities.invokeLater(TeacherRegistration::new);
        }
        if (e.getSource() == manageCourse) frame.setVisible(false);
        frame.dispose();
        SwingUtilities.invokeLater(CourseDashboard::new);
    }
}