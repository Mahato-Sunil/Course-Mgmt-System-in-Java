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
        button.setPreferredSize(new Dimension(width + 100, height + 100));

        // Remove button border
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(false);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setIconTextGap(5);
        button.setFocusable(false);
        return button;
    }
}
