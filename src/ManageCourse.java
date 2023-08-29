import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ManageCourse {
    // instantiating the componenets
    JFrame frame = new JFrame("Available Courses");
    JPanel panel;
    JLabel heading, courseName, courseId, courseCredit, courseAssess, search;
    JButton create, update, delete, reset, searchBtn;
    JTextField courseNameField, courseIdField, CourseCreditField, searchField;
    JMenuBar menuBar;
    JMenu homeMenu, studentMenu, teacherMenu, optionMenu;
    JMenuItem exitMenu;

    // manage class constructor
    ManageCourse() {
        displayFrame();
        displayMenu();
    }

    public void displayMenu() {
        menuBar = new JMenuBar();
        homeMenu = new JMenu("Home");
        studentMenu = new JMenu("Student");
        teacherMenu = new JMenu("Teacher");
        optionMenu = new JMenu("Option");

        exitMenu = new JMenuItem("Exit");
        exitMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        optionMenu.add(exitMenu);
        menuBar.add(homeMenu);
        menuBar.add(studentMenu);
        menuBar.add(teacherMenu);
        menuBar.add(optionMenu);
        frame.setJMenuBar(menuBar);
    }

    public void displayFrame() {        // method to display the frame
        // getting the dimensions of the overall screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

        frame.setSize(screenWidth, screenHeight); // setting the size of the frame
      //  frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new ManageCourse();
    }

}
