import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class CourseDashboard implements ActionListener {
    // instantiating the componenets
    JFrame frame = new JFrame("Course Dashboard");
    JPanel panel;
    JLabel heading, courseName, courseId, courseCredit, courseAssess, search;
    JButton create, update, delete, reset, searchBtn;
    JTextField courseNameField, courseIdField, courseCreditField, searchField;
    JMenuBar menuBar;
    JMenu homeMenu, studentMenu, teacherMenu, optionMenu, courseMenu;
    JMenuItem exitMenu;

    JRadioButton Theory, Practical;
    Font customFont = new Font(null, Font.PLAIN, 20);

    // getting the whole sizez of the page
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();
    int screenHeight = (int) screenSize.getHeight();

    // manage class constructor
    CourseDashboard() {
        setDimension();
        addComp();

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
        exitMenu.addActionListener(e -> System.exit(0));
        optionMenu.add(exitMenu);
        menuBar.add(homeMenu);
        menuBar.add(studentMenu);
        menuBar.add(teacherMenu);
        menuBar.add(courseMenu);
        menuBar.add(optionMenu);
        frame.setJMenuBar(menuBar);
    }

    //    fuction to set the components
    public void setDimension() {
        panel = new JPanel();
        panel.setSize(1450, 750);
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(180, 6, 100));
        panel.setLocation(40, 10);

        // setting the dimension of the actual components
        heading = new JLabel("Course Dashboard");
        heading.setBounds(50, 10, 600, 50);
        heading.setFont(new Font(null, Font.BOLD, 35));
        heading.setForeground(Color.BLACK);

        courseName = new JLabel("Course Name :-");      // label for course name
        courseName.setBounds(60, 100, 150, 40);
        courseName.setFont(customFont);

        courseNameField = new JTextField();                // text field for course name
        courseNameField.setBounds(60, 150, 400, 45);

        courseId = new JLabel("Course ID :-");      // course id
        courseId.setBounds(60, 200, 150, 40);
        courseId.setFont(customFont);

        courseIdField = new JTextField();
        courseIdField.setBounds(60, 250, 400, 45);

        courseCredit = new JLabel(" Credit Hour :-");       // for course credit hour
        courseCredit.setBounds(60, 300, 350, 40);
        courseCredit.setFont(customFont);

        courseCreditField = new JTextField();
        courseCreditField.setBounds(60, 350, 400, 45);

        courseAssess = new JLabel(" Assessment Mode :-");       // for assesment
        courseAssess.setBounds(60, 400, 200, 40);
        courseAssess.setFont(customFont);

        Theory = new JRadioButton("Theory");        // checkbox 1
        Theory.setBounds(80, 450, 150, 50);
        Theory.setFont(customFont);
        Theory.setFocusable(false);

        Practical = new JRadioButton("Theory");        // checkbox 1
        Practical.setBounds(250, 450, 150, 50);
        Practical.setFont(customFont);
        Practical.setFocusable(false);

        search = new JLabel(" Search By ID :- ");
        search.setBounds(60, 530, 200, 40);
        search.setFont(customFont);

        searchField = new JTextField();
        searchField.setBounds(250, 530, 250, 45);

        searchBtn = new JButton(" Search");
        searchBtn.setBounds(60, 585, 180, 50);
        searchBtn.setBackground(new Color(204, 255, 255));
        searchBtn.addActionListener(this);
        searchBtn.setFocusable(false);

        create = new JButton("CREATE");
        create.setBounds(60, 680, 145, 50);
        create.addActionListener(this);
        create.setFocusable(false);

        update = new JButton("UPDATE");
        update.setBounds(210, 680, 145, 50);
        update.addActionListener(this);
        update.setFocusable(false);

        delete = new JButton("DELETE");
        delete.setBounds(360, 680, 145, 50);
        delete.addActionListener(this);
        delete.setFocusable(false);

        reset = new JButton("RESET");
        reset.setBounds(510, 680, 145, 50);
        reset.addActionListener(this);
        reset.setFocusable(false);

        frame.setSize(screenWidth, screenHeight);
        frame.setLayout(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void addComp() {
        panel.add(heading);
        panel.add(courseName);
        panel.add(courseId);
        panel.add(courseCredit);
        panel.add(courseAssess);
        panel.add(search);
        panel.add(Theory);
        panel.add(Practical);
        panel.add(searchBtn);
        panel.add(courseNameField);
        panel.add(courseIdField);
        panel.add(courseCreditField);
        panel.add(searchField);
        panel.add(create);
        panel.add(update);
        panel.add(delete);
        panel.add(reset);
        frame.add(panel);
    }


    //  event handlere
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(create))
            System.out.println("Create Button Clicked");
        if (e.getSource().equals(update))
            System.out.println("Update Button Clicked");
        if (e.getSource().equals(delete))
            System.out.println("Delete Button Clicked");
        if (e.getSource().equals(reset)) {
            courseNameField.setText(null);
            courseCreditField.setText(null);
            Theory.setSelected(false);
            Practical.setSelected(false);
            searchField.setText(null);
        }

    }

    public static void main(String[] args) {
        new CourseDashboard();
    }

}
