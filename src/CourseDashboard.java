import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class CourseDashboard {
    // instantiating the componenets
    JFrame frame = new JFrame("Course Dashboard");
    JPanel panel;
    JLabel heading, courseName, courseId, courseCredit, courseAssess, search;
    JButton create, update, delete, reset, searchBtn;
    JTextField courseNameField, courseIdField, CourseCreditField, searchField;
    JMenuBar menuBar;
    JMenu homeMenu, studentMenu, teacherMenu, optionMenu, courseMenu;
    JMenuItem exitMenu;

    Font customFont = new Font(null, Font.PLAIN, 20);

    // manage class constructor
    CourseDashboard() {
        displayFrame();
        displayMenu();
    }

    public void displayMenu() {
        menuBar = new JMenuBar();
        homeMenu = new JMenu("Home");
        studentMenu = new JMenu("Student");
        teacherMenu = new JMenu("Teacher");
        optionMenu = new JMenu("Option");
        courseMenu = new JMenu("Courses");
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
        menuBar.add(courseName);
        menuBar.add(optionMenu);
        frame.setJMenuBar(menuBar);
    }

    //    fuction to set the components
    public void setDimension() {
        panel = new JPanel();       //for panel
        panel.setBounds(50, 10, 1000, 700);

        heading = new JLabel("Course Management");      // for heading
        heading.setBounds(50, 50, 200, 50);
        heading.setFont(new Font(null, Font.BOLD, 25));

        courseName = new JLabel("Course Name :-");      // label for course name
        courseName.setBounds(50, 80, 150, 40);
        courseName.setFont(customFont);

        courseId = new JLabel("Course ID :-");      // course id
        courseId.setBounds(50, 180, 150, 40);
        courseId.setFont(customFont);

    }

    public void addComp()
    {
        panel.add(heading);
        panel.add(courseName);
        panel.add(courseId);

        frame.add(panel);
    }
    public void displayFrame() {        // method to display the frame

        frame.setSize(1080, 720); // setting the size of the frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new CourseDashboard();
    }

}
