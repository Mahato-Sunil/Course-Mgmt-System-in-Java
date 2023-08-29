import java.sql.*;

public class Validate {
   String url = "jdbc:sqlserver://DELL:1433;trustServerCertificate=true;databaseName=studentdatabase";
    String username = "Sunil Mahato"; 

    String password = "sunil9860";
    String uid, upassword;
    String orgUserName, orgUserPassword;

    Validate(String uid, String upassword) {
        this.uid = uid;
        this.upassword = upassword;
    }

    public Boolean checkValidity() {
        boolean isValid = false;
        // connecting the database
        try {
             Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection connect = DriverManager.getConnection(url, username, password);
            Statement statement = connect.createStatement();
            String query = "SELECT Username, Password FROM studentpassword";
            ResultSet result = statement.executeQuery(query);
            int i = 0;
            while (result.next()) {
                orgUserName = result.getString("Username");
                orgUserPassword = result.getString("Password");
                isValid = (uid.equals(orgUserName) && upassword.equals(orgUserPassword));
                if(isValid)
                    break;
            }
            connect.close();
        } catch (ClassNotFoundException | SQLException err) {
            err.printStackTrace();
        }
        return isValid;
    }
}