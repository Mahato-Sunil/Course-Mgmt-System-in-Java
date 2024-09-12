import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends CreateImageButton implements ActionListener {
    // Initialize the components
    JFrame frame = new JFrame("Course Management System");
    JPanel headerPanel, centerPanel, footerPanel;
    JLabel header, footer;
    JButton loginButton, aboutButton, exitButton, registerButton;

    // Constructor to initialize the components
    Home() {
        initComp();
    }

    // initComp()
    void initComp() {
        // Frame setup
        frame.setSize(1080, 720);
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Main layout for the frame

        // Header panel setup
        headerPanel = new JPanel();
        headerPanel.setPreferredSize(new Dimension(1080, 80)); // Header height
        headerPanel.setBackground(new Color(60, 179, 113)); // Minimalistic green color

        header = new JLabel("WELCOME TO NEW ERA OF LEARNING!", JLabel.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 30));
        headerPanel.add(header); // Add header to the header panel

        // Footer panel setup
        footerPanel = new JPanel();
        footerPanel.setPreferredSize(new Dimension(1080, 70)); // Footer height
        footerPanel.setBackground(new Color(220, 220, 220)); // Light grey background

        footer = new JLabel("Copyright©️@SunilMahato", JLabel.CENTER);
        footer.setFont(new Font("SansSerif", Font.PLAIN, 14));
        footerPanel.add(footer); // Add footer to the footer panel

        // Center panel setup (for buttons)
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // Centered horizontal layout

        // Create and add buttons
        loginButton = createButton("Login", "Image/login.png", 30, 30);
        loginButton.addActionListener(this);
        centerPanel.add(Box.createVerticalStrut(20)); // Space between top and first button
        centerPanel.add(createCenteredPanel(loginButton)); // Centered login button

        aboutButton = createButton("About Us", "Image/info.png", 30, 30);
        aboutButton.addActionListener(this);
        centerPanel.add(Box.createVerticalStrut(20)); // Space between buttons
        centerPanel.add(createCenteredPanel(aboutButton)); // Centered about button

        registerButton = createButton("Register", "Image/register.png", 30, 30);
        registerButton.addActionListener(this);
        centerPanel.add(Box.createVerticalStrut(20)); // Space between buttons
        centerPanel.add(createCenteredPanel(registerButton)); // Centered register button

        exitButton = createButton("Log Out", "Image/exit.png", 30, 30);
        exitButton.addActionListener(this);
        centerPanel.add(Box.createVerticalStrut(20)); // Space between buttons
        centerPanel.add(createCenteredPanel(exitButton)); // Centered exit button

        // Add panels to the frame
        frame.add(headerPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER); // Add the center panel with buttons
        frame.add(footerPanel, BorderLayout.SOUTH);

        frame.setVisible(true); // Make the frame visible
    }

    // Helper method to center buttons horizontally
    private JPanel createCenteredPanel(JButton button) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Center the button in the panel
        panel.setOpaque(false); // Make panel transparent
        panel.add(button);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            SwingUtilities.invokeLater(LoginPage::new);
        } else if (e.getSource() == aboutButton) {
            JOptionPane.showMessageDialog(null, "Developer: Sunil Mahato\nCompleted Year: 2023\nEmail: sunilmaht642@gmail.com\nWebsite: mahatosunil.com.np");
        } else if (e.getSource() == registerButton) {
            SwingUtilities.invokeLater(AdminDashboard::new);
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}
