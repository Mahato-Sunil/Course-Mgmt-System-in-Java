import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TeacherDashboard extends publicMenu implements ActionListener {
    // initializing the global variables
    JFrame frame = new JFrame("Teacher DashBoard");
    JPanel panel;
    JLabel heading, TName, TId, search, SName, reg, course, score, overview;
    JTextField TNameField, TIdField, SearchField, SNameField, RegField, CourseField, ScoreField;
    JButton SearchBtn, Assign, Update, Reset;

    // default object for  showing studnet  table
    JTable studentTable;
    DefaultTableModel teacherDataTableModel;
    String[] studentHeading = {"Registration No. ", "Student's Name", "Course", "Score"};  // for showing availabel student heading
    Object[][] studentData;
    JScrollPane studentScrollPane;

    // getting the whole sizez of the page
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int screenWidth = (int) screenSize.getWidth();
    int screenHeight = (int) screenSize.getHeight();

    //    font for the overall text
    Font customFont = new Font(null, Font.PLAIN, 20);

    TeacherDashboard() {
        setDimensions();
        addComponent();
        setMenu();
        setMenuDesign();
        setMenuLogic();
        frame.setJMenuBar(menu);

    }

    void setDimensions() {
        panel = new JPanel();
        panel.setSize(1450, 750);
        panel.setLayout(null);
        panel.setBackground(Color.getHSBColor(180, 6, 100));
        panel.setLocation(40, 10);

        // setting the dimension of the actual components
        heading = new JLabel("Teacher Dashboard");
        heading.setBounds(30, 10, 300, 50);
        heading.setFont(new Font(null, Font.BOLD, 30));
        heading.setForeground(Color.BLUE);

        TName = new JLabel("Name : ");
        TName.setBounds(450, 15, 100, 50);
        TName.setFont(customFont);

        TNameField = new JTextField();
        TNameField.setBounds(580, 15, 250, 50);
        TNameField.setEditable(false);

        TId = new JLabel("Teacher ID :");
        TId.setBounds(1000, 15, 200, 50);
        TId.setFont(customFont);

        TIdField = new JTextField();
        TIdField.setBounds(1150, 15, 250, 50);
        TIdField.setEditable(false);

        search = new JLabel("Search Student Details :-");
        search.setBounds(30, 80, 250, 50);
        search.setFont(new Font(null, Font.BOLD, 20));

        SearchField = new JTextField();
        SearchField.setBounds(30, 140, 250, 50);

        SearchBtn = new JButton("Search ");
        SearchBtn.setBounds(30, 200, 150, 50);
        SearchBtn.setFocusable(false);

        // student details
        SName = new JLabel("Name :-");
        SName.setBounds(30, 260, 100, 50);
        SName.setFont(customFont);

        SNameField = new JTextField();
        SNameField.setBounds(150, 260, 250, 50);

        reg = new JLabel("Reg. No. :-");
        reg.setBounds(30, 320, 100, 50);
        reg.setFont(customFont);

        RegField = new JTextField();
        RegField.setBounds(150, 320, 250, 50);

        course = new JLabel("Course :-");
        course.setBounds(30, 380, 150, 50);
        course.setFont(customFont);

        CourseField = new JTextField();
        CourseField.setBounds(150, 380, 250, 50);

        score = new JLabel("Score :-");
        score.setBounds(30, 440, 100, 50);
        score.setFont(customFont);

        ScoreField = new JTextField();
        ScoreField.setBounds(150, 440, 250, 50);

        overview = new JLabel("Overview :-");
        overview.setBounds(750, 80, 200, 50);
        overview.setFont(new Font(null, Font.BOLD, 22));

        Assign = new JButton("Assign");
        Assign.setBounds(30, 550, 150, 50);
        Assign.setFocusable(false);

        Update = new JButton("Update");
        Update.setBounds(190, 550, 150, 50);
        Update.setFocusable(false);

        Reset = new JButton("Reset");
        Reset.setBounds(350, 550, 150, 50);
        Reset.setFocusable(false);

        // dimension for table

        teacherDataTableModel = new DefaultTableModel(studentData, studentHeading);
        studentTable = new JTable(teacherDataTableModel);
        studentTable.setRowHeight(50);
        studentTable.setFont(customFont);
        studentTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        studentScrollPane = new JScrollPane(studentTable);
        studentScrollPane.setBounds(750, 130, 600, 500);

        frame.setSize(screenWidth, screenHeight);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // adding the components
    void addComponent() {
        panel.add(heading);
        panel.add(TName);
        panel.add(TNameField);
        panel.add(TId);
        panel.add(TIdField);
        panel.add(SName);
        panel.add(SNameField);
        panel.add(course);
        panel.add(CourseField);
        panel.add(score);
        panel.add(ScoreField);
        panel.add(reg);
        panel.add(RegField);
        panel.add(search);
        panel.add(SearchField);
        panel.add(SearchBtn);
        panel.add(overview);
        panel.add(Assign);
        panel.add(Update);
        panel.add(Reset);
        panel.add(studentScrollPane);
        frame.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}