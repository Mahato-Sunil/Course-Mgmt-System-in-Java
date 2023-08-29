import javax.swing.*;
import java.awt.*;

public class ResponsiveFrameExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Responsive Frame Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Choose an appropriate layout manager

            JPanel panel1 = new JPanel();
            JPanel panel2 = new JPanel();
            JButton button1 = new JButton("Button 1");
            JButton button2 = new JButton("Button 2");
            JTextField txt = new JTextField("This is text field");

           panel1.add(button1);
           panel1.add(button2);

           panel2.add(txt);

           frame.add(panel1);
           frame.add(panel2);
            frame.pack(); // Set initial size based on components' preferred sizes
            frame.setVisible(true);
        });
    }
}
