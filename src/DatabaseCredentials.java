interface DatabaseCredentials {

    // database credentials
    String username = "root";
    String password = "";

    String studentUrl = "jdbc:mysql://localhost:3306/cms_studentdatabase";
    String teacherUrl = "jdbc:mysql://localhost:3306/cms_teacherdatabase";
    String adminUrl = "jdbc:mysql://localhost:3306/cms_admindatabase";
    String courseUrl = "jdbc:mysql://localhost:3306/cms_coursedatabase";
}