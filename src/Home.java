import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home implements ActionListener {
    // initialize the components
    JFrame frame = new JFrame("Course Management System");
    JPanel panel1, panel2, panel3, panel4, panel5;
    JLabel header, footer, theme;
    JButton login, about, exit, register;

    // constructor to initialize the functions
    Home() {
        initComp();
    }

    // initComp()
    void initComp() {
        frame.setSize(1080, 720);
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // top panel
        panel1 = new JPanel();
        panel1.setPreferredSize(new Dimension(1080, 80)); // Use setPreferredSize
        panel1.setBackground(Color.WHITE);

        header = new JLabel("WELCOME TO NEW ERA OF LEARNING !", JLabel.CENTER);
        header.setFont(new Font(null, Font.BOLD, 30));

        // left panel
        panel2 = new JPanel();
        panel2.setPreferredSize(new Dimension(250, 630)); // Use setPreferredSize

        login = createButton(null, "Image/login.png", 125, 125);
        login.addActionListener(this);

        exit = createButton(null, "Image/exit.png", 125, 125);
        exit.addActionListener(this);

        register = createButton(null, "Image/register.png", 125, 125);
        register.addActionListener(this);

        // right panel
        panel3 = new JPanel();
        panel3.setPreferredSize(new Dimension(250, 630)); // Use setPreferredSize

        about = createButton(null, "Image/about.png", 125, 125);
        about.addActionListener(this);

        // footer
        panel4 = new JPanel();
        panel4.setPreferredSize(new Dimension(1080, 70)); // Use setPreferredSize
        panel4.setBackground(new Color(214, 250, 250));

        footer = new JLabel("Copyright©️@SunilMahato", JLabel.CENTER);

        //  center
        panel5 = new JPanel();
        panel5.setPreferredSize(new Dimension(500, 500)); // Use setPreferredSize
        panel5.setBackground(Color.WHITE);

        theme = new JLabel();
        ImageIcon image = new ImageIcon("Image/theme.jpg");
        Image scaledImg = image.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImg);
        theme.setIcon(imageIcon);

        // adding the required components
        panel1.add(header);

        panel2.add(login);
        panel2.add(register);
        panel2.add(exit);

        panel3.add(about);

        panel4.add(footer);

        panel5.add(theme);

        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.WEST);
        frame.add(panel3, BorderLayout.EAST);
        frame.add(panel4, BorderLayout.SOUTH);
        frame.add(panel5, BorderLayout.CENTER);
    }

    // code for button
    JButton createButton(String buttonText, String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        // Scale the image to fit the specified dimensions
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);

        JButton button = new JButton(buttonText, scaledIcon);
        button.setHorizontalTextPosition(SwingConstants.CENTER);

        // Set the preferred size of the button
        button.setPreferredSize(new Dimension(width + 50, height + 50));

        // Remove button border
        button.setBorderPainted(false);
        button.setFocusPainted(true);
        button.setContentAreaFilled(false);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Home::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) {
                                  SwingUtilities.invokeLater(LoginPage::new);

        } else if (e.getSource() == register) {
                SwingUtilities.invokeLater(RegistrationPage::new);
        } else if (e.getSource() == about)
            JOptionPane.showMessageDialog(null, "Developer : Sunil Mahato \n Completed Year : 2023 \n Email : sunilmaht642@gmail.com \n Website : mahatosunil.com.np");
        else if (e.getSource() == exit) System.exit(0);
    }
}