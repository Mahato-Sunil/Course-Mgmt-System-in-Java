import java.sql.*;

public class Validate implements DatabaseCredentials {
    String uid, upassword;
    String orgUserName, orgUserPassword;
    String usermode;

    Validate(String uid, String upassword) {
        this.uid = uid;
        this.upassword = upassword;
    }

    public Boolean checkValidity(String usermode) {
        this.usermode = usermode;
        boolean isValid = false;
        // connecting the database
        try {
            String query = "SELECT *FROM studentpassword";
            String query1 = "SELECT *FROM teacherpassword";

            String finalQuery = (usermode.equals("Teacher")) ? query1 : query;
            String URL = (usermode.equals("Teacher")) ? teacherUrl : studentUrl;

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connect = DriverManager.getConnection(URL, username, password);
            Statement statement = connect.createStatement();

            ResultSet result = statement.executeQuery(finalQuery);
            int i = 0;
            while (result.next()) {
                orgUserName = result.getString("Username");
                String userSalt = result.getString("salt");
                String userHashCode = result.getString("hashCode");

                String retrievedKey  = userSalt + upassword ;
                System.out.println(retrievedKey);
                isValid = (uid.equals(orgUserName) && userHashCode.equals(retrievedKey));
                if (isValid)
                    break;
            }
            connect.close();
        } catch (ClassNotFoundException | SQLException err) {
            err.printStackTrace();
        }
        return isValid;
    }
}