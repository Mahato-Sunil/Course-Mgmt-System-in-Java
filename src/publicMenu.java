import javax.swing.*;
import java.awt.*;

public class publicMenu {
    // for menu
    static JMenuBar menu;
    static JMenu homeMenu, viewMenu, aboutMenu;
    static JMenuItem loginPageMenu, adminPageMenu, studentDataMenu, teacherDataMenu, aboutMenuItem, settingMenu, aboutDeveloper, exitMenu;
    static Font menuFont = new Font(null, Font.PLAIN, 16);

    static void setMenu()      // for menu items
    {
        menu = new JMenuBar();

        homeMenu = new JMenu("Home");
        viewMenu = new JMenu("View");
        aboutMenu = new JMenu("About");

        loginPageMenu = new JMenuItem("Login");
        adminPageMenu = new JMenuItem("Admin");
        exitMenu = new JMenuItem("Exit");

        homeMenu.add(loginPageMenu);
        homeMenu.add(adminPageMenu);
        homeMenu.add(exitMenu);

        studentDataMenu = new JMenuItem("Student Details");
        teacherDataMenu = new JMenuItem("Teacher Details");
        aboutDeveloper = new JMenuItem("Developer");

        viewMenu.add(studentDataMenu);
        viewMenu.add(teacherDataMenu);
        viewMenu.add(aboutDeveloper);

        aboutMenuItem = new JMenuItem("About Us");
        settingMenu = new JMenuItem("Setting");

        aboutMenu.add(aboutMenuItem);
        aboutMenu.add(settingMenu);

        menu.add(homeMenu);
        menu.add(viewMenu);
        menu.add(aboutMenu);
    }

    static void setMenuDesign() {
        homeMenu.setFont(menuFont);
        aboutMenu.setFont(menuFont);
        viewMenu.setFont(menuFont);
        settingMenu.setFont(menuFont);
        aboutMenuItem.setFont(menuFont);
        aboutDeveloper.setFont(menuFont);
        teacherDataMenu.setFont(menuFont);
        studentDataMenu.setFont(menuFont);
        exitMenu.setFont(menuFont);
        adminPageMenu.setFont(menuFont);
        loginPageMenu.setFont(menuFont);
    }

    static void setMenuLogic() {
        homeMenu.addActionListener(e -> new HomePage());
        loginPageMenu.addActionListener(e -> new HomePage());
        adminPageMenu.addActionListener(e -> new AdminDashboard());
        exitMenu.addActionListener(e -> System.exit(0));
        studentDataMenu.addActionListener(e -> new UserDataDatabase("Student"));
        teacherDataMenu.addActionListener(e -> new UserDataDatabase("Teacher"));
        aboutDeveloper.addActionListener(e -> JOptionPane.showMessageDialog(null, " Developer Name = Sunil Mahato \n Education : BCA 4th Sem \n Contact : +977-9860650642 \n Email : sunilmaht642@gmail.com"));
        aboutMenuItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "We are BCA Students! This is 3rd Sem College Project "));
        settingMenu.addActionListener(e -> JOptionPane.showMessageDialog(null, "The setting is currently unavailable "));

    }
}

/* // extends the class and add the methods in the constructor of the given class to achieve the menu in the given class

setMenu();                  // menu dimensions
setMenuDesign();            //menu design
setMenuLogic                // menu logic
frame.setJMenuBar(menu);    // to show the menu frame
*/

/*
for classes which cant be extended
use following code :
        publicMenu.setMenu();
        publicMenu.setMenuDesign();
        publicMenu.setMenuLogic();
        frame.setJMenuBar(publicMenu.menu);

 */
