import javax.swing.*;
import java.awt.*;

public class LoadingPage {
    // initialization of object
    JFrame frame = new JFrame();
    JLabel image = new JLabel(new ImageIcon("Image/loadingpage.png"));
    JProgressBar progressbar = new JProgressBar();
    JLabel message = new JLabel();
    Timer timer;
    protected int progress = 0;
    // constructor
    LoadingPage() {
        CreateGui();
        SwingUtilities.invokeLater(this::loadProgress);
    }

    // function to show the gui
    void CreateGui() {
        frame = new JFrame();
        frame.getContentPane().setLayout(null);
        frame.setUndecorated(true);
        frame.setSize(950, 640);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.black);
        frame.setVisible(true);

        // adding the image
        image.setBounds(180, 20, 600, 355);

        // adding the progress bar
        progressbar.setBounds(200, 520, 600, 10);
        progressbar.setBorderPainted(true);
        progressbar.setStringPainted(true);
        progressbar.setBackground(Color.BLACK);
        progressbar.setForeground(Color.WHITE);
        progressbar.setMinimum(0);
        progressbar.setMaximum(100);

        // adding the messages
        message.setBounds(460, 530, 200, 50);
        message.setForeground(Color.WHITE);
        message.setFont(new Font("arial", Font.PLAIN, 18));

        // adding components
        frame.add(image);
        frame.add(progressbar);
        frame.add(message);
    }

    // function to animate the progress bar
    void loadProgress() {
                timer = new Timer(50, e -> {
                    if (progress <= 100) {
                        progressbar.setValue(progress);
                        message.setText("Loading ... " + progress + "%");
                        progress++;
                    } else {
                        timer.stop();
                        Timer pauseTimer = new Timer(300, ev -> {
                            frame.dispose();
                            SwingUtilities.invokeLater(Home::new);
                        });
                        pauseTimer.setRepeats(false);
                        pauseTimer.start();
                        //frame.dispose();
                    }
                });

        timer.start(); // Start the timer
    }
}
