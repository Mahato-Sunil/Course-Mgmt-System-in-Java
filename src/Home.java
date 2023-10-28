import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home implements ActionListener {
    JFrame frame;
    JPanel panel;
    JButton login, about, exit;
    GridBagConstraints grid;

    Home() {
        initComponents();
    }

    void initComponents() {
        frame = new JFrame("Course Management System");
        frame.setSize(720, 480);
        frame.getContentPane().setBackground(new Color(169, 170, 171));
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        grid = new GridBagConstraints();    // for positioning center
        grid.gridx = GridBagConstraints.CENTER;
        grid.gridy = GridBagConstraints.CENTER;

        panel = new JPanel();
        panel.setSize(700, 460);
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(169, 170, 171));

        login = createButton(null, "Image/login.png", 150, 150);
        login.addActionListener(this);

        about = createButton(null, "Image/about.png", 150, 150);
        about.addActionListener(this);

        exit = createButton(null, "Image/exit.png", 150, 150);
        exit.addActionListener(this);

        panel.add(login);
        panel.add(about);
        panel.add(exit);

        frame.setResizable(false);
        frame.getContentPane().add(panel, grid);
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
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(false);
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Home::new);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login) JOptionPane.showMessageDialog(null, "Login Button Clicked");
        if (e.getSource() == about)
            JOptionPane.showMessageDialog(null, " Developer Name = Sunil Mahato \n Education : BCA 4th Sem \n Contact : +977-9860650642 \n Email : sunilmaht642@gmail.com \n We are BCA Students! This is 3rd Sem College Project ");
        if (e.getSource() == exit) System.exit(0);
    }
}
