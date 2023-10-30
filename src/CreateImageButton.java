import javax.swing.*;
import java.awt.*;

public class CreateImageButton {

    // code for button
       static JButton createButton(String buttonText, String imagePath, int width, int height) {
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
}
